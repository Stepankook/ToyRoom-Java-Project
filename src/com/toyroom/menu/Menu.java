package com.toyroom.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Menu (Invoker). Відповідає за відображення меню та виклик команди.
 */
public class Menu {
    private final List<Command> commands = new ArrayList<>();
    private final Command exitCommand = new ExitCommand();

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            displayMenu();
            System.out.print("\nВведіть номер команди (0 для виходу): ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                executeCommand(choice);
            } else {
                System.out.println("Помилка: Невірний ввід. Спробуйте ще раз.");
                scanner.next(); // очистити буфер
                choice = -1;
            }
        } while (choice != 0);

        System.out.println("Дякуємо за використання програми!");
    }

    private void displayMenu() {
        System.out.println("\n===== МЕНЮ ІГРОВОЇ КІМНАТИ =====");
        for (int i = 0; i < commands.size(); i++) {
            System.out.println(commands.get(i).getTitle());
        }
        System.out.println("0. Вихід");
        System.out.println("================================");
    }

    private void executeCommand(int choice) {
        if (choice == 0) {
            exitCommand.execute();
        } else if (choice > 0 && choice <= commands.size()) {
            commands.get(choice - 1).execute();
        } else {
            System.out.println("Невірна команда. Спробуйте число від 0 до " + commands.size());
        }
    }

    // Приватна команда для виходу
    private static class ExitCommand implements Command {
        @Override
        public void execute() {
            // Фактичний вихід відбувається в циклі run()
        }
        @Override
        public String getTitle() { return "Вихід"; }
    }
}