package com.toyroom.service;

import com.toyroom.domain.*;
import com.toyroom.repo.ToyRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public final class RoomServiceImpl implements RoomService {

    private final ToyRepository repository;


    public RoomServiceImpl(ToyRepository repository) {
        this.repository = repository;
    }


    @Override
    public void autoPopulate(ToyRoom room) {
        List<Toy> availableToys = repository.listAll();


        room.clearToys();

        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < room.getTargetCount(); i++) {
            if (room.remainingBudget() <= 0) break;
            Toy toyToAdd = availableToys.get(random.nextInt(availableToys.size()));

            if (toyToAdd.getPrice() <= room.remainingBudget()) {

                room.addToy(toyToAdd);
            }
        }
    }

    // --- ФВ2: sortBy (Сортування) ---
    @Override
    public void sortBy(ToyRoom room, SortParam param) {
        Comparator<Toy> comparator;


        switch (param) {
            case PRICE:
                comparator = Comparator.comparing(Toy::getPrice);
                break;
            case SIZE:

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



        System.out.println("Sorting (Note: ToyRoom internal list should be sortable):");
        List<Toy> sortedToys = new ArrayList<>(room.getToys());
        sortedToys.sort(comparator);


        sortedToys.forEach(System.out::println);

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

        int fromOrdinal = from.ordinal();
        int toOrdinal = to.ordinal();

        return room.getToys().stream()
                .filter(t -> t.getSize().ordinal() >= fromOrdinal && t.getSize().ordinal() <= toOrdinal)
                .collect(Collectors.toUnmodifiableList());
    }

    // --- ФВ3: findByAgeRange (Пошук за Віком) ---
    @Override
    public List<Toy> findByAgeRange(ToyRoom room, int minAge, int maxAge) {

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



        if (errors.isEmpty()) {
            return ValidationResult.ok();
        } else {
            return ValidationResult.fail(errors);
        }
    }

}