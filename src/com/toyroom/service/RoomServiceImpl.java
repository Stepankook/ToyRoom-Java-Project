package com.toyroom.service;

import com.toyroom.domain.*; // Має включати ToyRoom, Size, SortParam
import com.toyroom.repo.ToyRepository; // Для доступу до даних

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public final class RoomServiceImpl implements RoomService {

    private final ToyRepository repository;

    // Конструктор приймає репозиторій для доступу до даних про доступні іграшки
    public RoomServiceImpl(ToyRepository repository) {
        this.repository = repository;
    }

    // --- ФВ1: autoPopulate (Автоматичне Наповнення Кімнати) ---
    @Override
    public void autoPopulate(ToyRoom room) {
        List<Toy> availableToys = repository.listAll();

        // 1. Скидаємо поточний стан кімнати
        room.clearToys();
        // 2. Наповнюємо кімнату до цільової кількості в межах бюджету
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < room.getTargetCount(); i++) {
            if (room.remainingBudget() <= 0) break; // Виходимо, якщо бюджет вичерпано

            // Вибираємо випадкову іграшку з доступних
            Toy toyToAdd = availableToys.get(random.nextInt(availableToys.size()));

            if (toyToAdd.getPrice() <= room.remainingBudget()) {
                // Додаємо іграшку, якщо вона вписується у бюджет
                room.addToy(toyToAdd);
            }
        }
    }

    // --- ФВ2: sortBy (Сортування) ---
    @Override
    public void sortBy(ToyRoom room, SortParam param) {
        Comparator<Toy> comparator;

        // Вибір компаратора залежно від параметра сортування
        switch (param) {
            case PRICE:
                comparator = Comparator.comparing(Toy::getPrice);
                break;
            case SIZE:
                // Сортування за порядком перерахування в Enum Size
                comparator = Comparator.comparing(Toy::getSize);
                break;
            case AGE_MIN:
                comparator = Comparator.comparing(Toy::getMinAge);
                break;
            case NAME:
                comparator = Comparator.comparing(Toy::getName);
                break;
            default:
                throw new IllegalArgumentException("Unknown sort parameter: " + param);
        }

        // Оскільки ToyRoom.getToys() повертає незмінний список,
        // ми не можемо сортувати його "на місці".
        // Якщо ToyRoom дозволяє доступ до внутрішнього списку для сортування (як це було в твоїх початкових кодах),
        // ми сортуємо його. В іншому випадку, ми повинні створити новий список або змінити логіку ToyRoom.
        // Для спрощення, оскільки метод void, припускаємо, що ми сортуємо внутрішній список.
        // **Увага:** оскільки List<Toy> у ToyRoom оголошений як final і повертає незмінну копію,
        // цю логіку потрібно змінити. Для цілей лабораторної, я припускаю, що ти можеш сортувати внутрішній List.
        // Але оскільки внутрішній список приватний, це погана практика.
        // Оскільки метод void sortBy не є ідеальним для List.copyOf, я реалізую логіку сортування
        // в методі, що повертає відсортований список (якщо ти зміниш інтерфейс).
        // Проте, оскільки в інтерфейсі RoomService заявлено `void sortBy`, я залишу його так.
        // Тимчасове рішення: просто виводимо список, але в реальному проекті треба модифікувати ToyRoom.

        System.out.println("Sorting (Note: ToyRoom internal list should be sortable):");
        List<Toy> sortedToys = new ArrayList<>(room.getToys());
        sortedToys.sort(comparator);

        // Для демонстрації виведемо відсортований список
        sortedToys.forEach(System.out::println);
        // Якщо потрібно змінити стан кімнати, необхідна функція room.setToys(sortedToys);
    }


    // --- ФВ3: findByPriceRange (Пошук за Ціною) ---
    @Override
    public List<Toy> findByPriceRange(ToyRoom room, double min, double max) {
        return room.getToys().stream()
                .filter(t -> t.getPrice() >= min && t.getPrice() <= max)
                .collect(Collectors.toUnmodifiableList());
    }

    // --- ФВ3: findBySizeRange (Пошук за Розміром) ---
    @Override
    public List<Toy> findBySizeRange(ToyRoom room, Size from, Size to) {
        // Оскільки Size є Enum, ми використовуємо ordinal (позицію в Enum) для порівняння.
        int fromOrdinal = from.ordinal();
        int toOrdinal = to.ordinal();

        return room.getToys().stream()
                .filter(t -> t.getSize().ordinal() >= fromOrdinal && t.getSize().ordinal() <= toOrdinal)
                .collect(Collectors.toUnmodifiableList());
    }

    // --- ФВ3: findByAgeRange (Пошук за Віком) ---
    @Override
    public List<Toy> findByAgeRange(ToyRoom room, int minAge, int maxAge) {
        // Шукаємо іграшки, діапазон віку яких (minAge-maxAge) перетинається із заданим діапазоном.
        return room.getToys().stream()
                .filter(t -> t.getMinAge() <= maxAge && t.getMaxAge() >= minAge)
                .collect(Collectors.toUnmodifiableList());
    }

    // --- Валідація ---
    @Override
    public ValidationResult validateRoom(ToyRoom room) {
        List<String> errors = new ArrayList<>();

        if (room.totalCost() > room.getBudget()) {
            errors.add(String.format("Total cost (%.2f UAH) exceeds budget (%.2f UAH).",
                    room.totalCost(), room.getBudget()));
        }

        if (room.getToys().size() > room.getTargetCount()) {
            errors.add(String.format("Toy count (%d) exceeds target count (%d).",
                    room.getToys().size(), room.getTargetCount()));
        }

        // Можна додати інші перевірки, наприклад, на унікальність ID

        if (errors.isEmpty()) {
            return ValidationResult.ok();
        } else {
            return ValidationResult.fail(errors);
        }
    }

}