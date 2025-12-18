package com.toyroom.menu.commands;

import com.toyroom.domain.Size;
import com.toyroom.domain.Toy;
import com.toyroom.domain.ToyRoom;
import com.toyroom.menu.Command;
import com.toyroom.service.RoomServiceImpl;

import java.util.List;
import java.util.Scanner;

/**
 * Команда для пошуку іграшок за діапазоном параметрів (ФВ3).
 */
public class FindToysCommand implements Command {

    private final RoomServiceImpl service;
    private final ToyRoom room;
    // Оскільки цей клас тепер обробляє ввід, він вимагає Scanner
    private final Scanner scanner = new Scanner(System.in);

    public FindToysCommand(RoomServiceImpl service, ToyRoom room) {
        this.service = service;
        this.room = room;
    }

    @Override
    public String getTitle() {
        return "3. Знайти іграшки за діапазоном параметрів";
    }

    @Override
    public void execute() {
        System.out.println("\n--- Вибір параметру пошуку ---");
        System.out.println("1: Ціна | 2: Розмір | 3: Вік");
        System.out.print("Виберіть номер (1-3): ");

        if (!scanner.hasNextInt()) {
            System.out.println("Невірний ввід. Спробуйте ще раз.");
            scanner.nextLine();
            return;
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Очищення буфера

        switch (choice) {
            case 1 -> findByPriceRange();
            case 2 -> findBySizeRange();
            case 3 -> findByAgeRange();
            default -> System.out.println("Невідома команда.");
        }
    }

    // --- Допоміжні методи для кожного типу пошуку ---

    private void findByPriceRange() {
        try {
            System.out.print("Введіть мінімальну ціну: ");
            double minP = scanner.nextDouble();
            System.out.print("Введіть максимальну ціну: ");
            double maxP = scanner.nextDouble();
            scanner.nextLine();

            List<Toy> results = service.findByPriceRange(room, minP, maxP);
            displayResults(results, String.format("ціни %.2f-%.2f UAH", minP, maxP));
        } catch (java.util.InputMismatchException e) {
            System.out.println("Невірний ввід для ціни. Потрібне числове значення.");
            scanner.nextLine();
        }
    }

    private void findBySizeRange() {
        try {
            System.out.println("Доступні розміри: SMALL (1), MEDIUM (2), LARGE (3)");
            System.out.print("Введіть мінімальний розмір (1-3): ");
            int minOrdinal = scanner.nextInt();
            System.out.print("Введіть максимальний розмір (1-3): ");
            int maxOrdinal = scanner.nextInt();
            scanner.nextLine();

            Size minS = Size.values()[minOrdinal - 1];
            Size maxS = Size.values()[maxOrdinal - 1];

            List<Toy> results = service.findBySizeRange(room, minS, maxS);
            displayResults(results, String.format("розміру %s-%s", minS, maxS));
        } catch (Exception e) {
            System.out.println("Невірний ввід для розміру. Введіть число від 1 до 3.");
            scanner.nextLine();
        }
    }

    private void findByAgeRange() {
        try {
            System.out.print("Введіть мінімальний вік: ");
            int minA = scanner.nextInt();
            System.out.print("Введіть максимальний вік: ");
            int maxA = scanner.nextInt();
            scanner.nextLine();

            List<Toy> results = service.findByAgeRange(room, minA, maxA);
            displayResults(results, String.format("віку %d-%d років", minA, maxA));
        } catch (java.util.InputMismatchException e) {
            System.out.println("Невірний ввід для віку. Потрібне ціле число.");
            scanner.nextLine();
        }
    }

    private void displayResults(List<Toy> results, String criteria) {
        if (results.isEmpty()) {
            System.out.printf("Не знайдено іграшок у діапазоні %s.%n", criteria);
        } else {
            System.out.printf("\nЗнайдено іграшок у діапазоні %s (%d шт.):%n", criteria, results.size());
            results.forEach(System.out::println);
        }
    }
}