package com.toyroom.menu.commands;

import com.toyroom.domain.SortParam;
import com.toyroom.domain.ToyRoom;
import com.toyroom.menu.Command;
import com.toyroom.service.RoomServiceImpl;

import java.util.Scanner;

/**
 * Команда для сортування іграшок за параметром (ФВ2).
 */
public class SortToysCommand implements Command {

    private final RoomServiceImpl service; // Receiver
    private final ToyRoom room;           // Receiver
    private final Scanner scanner = new Scanner(System.in);

    // Додаємо Receiver-об'єкти до конструктора
    public SortToysCommand(RoomServiceImpl service, ToyRoom room) {
        this.service = service;
        this.room = room;
    }

    @Override
    public String getTitle() {
        return "2. Відсортувати іграшки за параметром";
    }

    @Override
    public void execute() {
        if (room.getToys().isEmpty()) {
            System.out.println("Сортування неможливе: кімната порожня. Спершу виконайте команду 'Сформувати Кімнату'.");
            return;
        }

        System.out.println("\n--- Параметри сортування ---");
        System.out.println("1: ЦІНА | 2: РОЗМІР | 3: МІН. ВІК | 4: НАЗВА");
        System.out.print("Виберіть номер параметра (1-4): ");

        if (!scanner.hasNextInt()) {
            System.out.println("Невірний ввід. Потрібне число.");
            scanner.nextLine();
            return;
        }

        int choice = scanner.nextInt();
        scanner.nextLine(); // Очищення буфера
        SortParam param;

        // Вибір параметра сортування
        param = switch (choice) {
            case 1 -> SortParam.PRICE;
            case 2 -> SortParam.SIZE;
            case 3 -> SortParam.AGE_MIN;
            case 4 -> SortParam.NAME;
            default -> null;
        };

        if (param != null) {
            // *** ВИКЛИК ЛОГІКИ ФВ2 ***
            service.sortBy(room, param);

            System.out.printf("Відсортовано за: %s. Поточний вміст (%d шт.):%n", param, room.getToys().size());
            room.getToys().forEach(System.out::println);
        } else {
            System.out.println("Невірний вибір параметра. Спробуйте ще раз.");
        }
    }
}