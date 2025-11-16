package com.toyroom;

import com.toyroom.domain.Size;
import com.toyroom.domain.SortParam;
import com.toyroom.domain.Toy;
import com.toyroom.domain.ToyRoom;
import com.toyroom.repo.InMemoryToyRepository;
import com.toyroom.service.RoomServiceImpl;
import com.toyroom.service.ValidationResult;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("=================================================");
        System.out.println("🤖 Лабораторна робота 4: Ігрова Кімната");
        System.out.println("=================================================");

        // --- 1. Ініціалізація Системи ---
        // Виділяємо бюджет 3000 UAH і цільову кількість іграшок - 15
        ToyRoom room = new ToyRoom(3000.0, 15);


        InMemoryToyRepository repository = new InMemoryToyRepository();


        RoomServiceImpl roomService = new RoomServiceImpl(repository);

        System.out.println("\n--- 1. Формування Ігрової Кімнати (ФВ1) ---");
        roomService.autoPopulate(room);

        System.out.printf(" Кімната сформована: %d іграшок. Загальна вартість: %.2f UAH. Залишок бюджету: %.2f UAH.%n",
                room.getToys().size(), room.totalCost(), room.remainingBudget());

        System.out.println("\nПоточний вміст кімнати:");
        room.getToys().forEach(System.out::println);


        // --- 2. Сортування Іграшок (ФВ2) ---
        System.out.println("\n--- 2. Сортування Іграшок за ЦІНОЮ (від найменшої) ---");

        roomService.sortBy(room, SortParam.PRICE);


        // --- 3. Пошук Іграшок за Діапазоном (ФВ3) ---

        // 3.1. Пошук за діапазоном ціни (наприклад, 100 UAH до 300 UAH)
        double minP = 100.0;
        double maxP = 300.0;
        List<Toy> byPrice = roomService.findByPriceRange(room, minP, maxP);
        System.out.printf("\n--- 3.1. Знайдено іграшок у діапазоні ціни %.2f-%.2f UAH (%d шт.): ---%n", minP, maxP, byPrice.size());
        byPrice.forEach(System.out::println);

        // 3.2. Пошук за діапазоном розміру (наприклад, MEDIUM до LARGE)
        Size minS = Size.MEDIUM;
        Size maxS = Size.LARGE;
        List<Toy> bySize = roomService.findBySizeRange(room, minS, maxS);
        System.out.printf("\n--- 3.2. Знайдено іграшок у діапазоні розміру %s-%s (%d шт.): ---%n", minS, maxS, bySize.size());
        bySize.forEach(System.out::println);

        // 3.3. Пошук за діапазоном віку (наприклад, для дітей 5-10 років)
        int minA = 5;
        int maxA = 10;
        List<Toy> byAge = roomService.findByAgeRange(room, minA, maxA);
        System.out.printf("\n--- 3.3. Знайдено іграшок для віку %d-%d років (%d шт.): ---%n", minA, maxA, byAge.size());
        byAge.forEach(System.out::println);


        // --- 4. Валідація (Додатковий функціонал) ---
        System.out.println("\n--- 4. Результат Валідації Кімнати ---");
        ValidationResult validation = roomService.validateRoom(room);
        System.out.println(validation);

        System.out.println("\n=================================================");
        System.out.println("🎉 Програма завершена успішно!");
    }
}