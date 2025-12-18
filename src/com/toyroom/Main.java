package com.toyroom;

// domain
import com.toyroom.domain.SortParam;
import com.toyroom.domain.Toy;
import com.toyroom.domain.ToyRoom;

// repository
import com.toyroom.domain.repo.InMemoryToyRepository;

// service
import com.toyroom.service.RoomServiceImpl;
import com.toyroom.service.ValidationResult;

// utils
import com.toyroom.utils.FileLoader;
import com.toyroom.utils.EmailCriticalHandler;

// menu
import com.toyroom.menu.Menu;
import com.toyroom.menu.commands.FindToysCommand;
import com.toyroom.menu.commands.PopulateRoomCommand;
import com.toyroom.menu.commands.SortToysCommand;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        // Налаштування логера (Завдання №5)
        logger.addHandler(new EmailCriticalHandler());
        logger.setLevel(Level.ALL);

        try {
            System.out.println("=================================================");
            System.out.println("КОМПЛЕКСНА ЛАБОРАТОРНА: Ігрова Кімната");
            System.out.println("=================================================");


            System.out.println("Бажаєте надіслати тестовий критичний лог на пошту? (y/n)");
            String testMail = scanner.nextLine();
            if (testMail.equalsIgnoreCase("y")) {
                // Це спровокує відправку листа через EmailCriticalHandler
                logger.severe("ТЕСТОВА КРИТИЧНА ПОМИЛКА.");
                System.out.println("   > Запит на відправку листа надіслано.");
            }
            // --------------------------------------

            Map<String, String> initParams = FileLoader.loadInitParams();
            // ... далі твій код без змін ...
            double budget = Double.parseDouble(initParams.getOrDefault("BUDGET", "3000.0"));
            int targetCount = Integer.parseInt(initParams.getOrDefault("TARGET_COUNT", "15"));

            System.out.printf(
                    "   > Ініціалізовано: Бюджет = %.2f UAH, Цільова к-сть = %d%n",
                    budget, targetCount
            );

            ToyRoom room = new ToyRoom(budget, targetCount);
            InMemoryToyRepository repository = new InMemoryToyRepository();
            RoomServiceImpl roomService = new RoomServiceImpl(repository);

            System.out.println("\n--- ВИБІР РЕЖИМУ ---");
            System.out.println("1. Запустити послідовну демонстрацію (Лаб 1)");
            System.out.println("2. Запустити інтерактивне консольне меню (Лаб 2/3)");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            // Використовуємо scanner.nextLine() щоб уникнути проблем із вводом
            String input = scanner.nextLine();
            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1 -> runLab1Demonstration(room, roomService);
                case 2 -> runLab2Menu(room, roomService);
                case 0 -> System.out.println("Програма завершена.");
                default -> System.out.println("Невірний вибір.");
            }

        } catch (Exception e) {
            // Це також відправить лист, якщо станеться справжня помилка
            logger.log(Level.SEVERE, "Критична помилка в Main: " + e.getMessage(), e);
            System.out.println("Сталася критична помилка. Деталі записані в лог та надіслані на email.");
        }
    }

    // =======================================================
    // ЛАБ 1
    // =======================================================
    private static void runLab1Demonstration(ToyRoom room, RoomServiceImpl roomService) {

        roomService.autoPopulate(room);

        System.out.printf(
                "\nСформовано %d іграшок. Загальна ціна: %.2f, Залишок: %.2f%n",
                room.getToys().size(),
                room.totalCost(),
                room.remainingBudget()
        );

        roomService.sortBy(room, SortParam.PRICE);
        room.getToys().forEach(System.out::println);

        List<Toy> found = roomService.findByPriceRange(room, 100, 300);
        found.forEach(System.out::println);

        ValidationResult validation = roomService.validateRoom(room);
        System.out.println(validation);
    }

    // =======================================================
    // ЛАБ 2 / 3
    // =======================================================
    private static void runLab2Menu(ToyRoom room, RoomServiceImpl roomService) {

        Menu menu = new Menu();
        menu.addCommand(new PopulateRoomCommand(roomService, room));
        menu.addCommand(new SortToysCommand(roomService, room));
        menu.addCommand(new FindToysCommand(roomService, room));

        menu.run();
    }
}
