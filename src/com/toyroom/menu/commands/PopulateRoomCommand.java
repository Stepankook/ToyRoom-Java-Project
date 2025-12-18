package com.toyroom.menu.commands;

import com.toyroom.menu.Command;
import com.toyroom.service.RoomServiceImpl; // Receiver
import com.toyroom.domain.ToyRoom;         // Receiver

// Команда для автоматичного наповнення ігрової кімнати.
public class PopulateRoomCommand implements Command {

    // Receiver: об'єкти, які виконують фактичну логіку
    private final RoomServiceImpl service;
    private final ToyRoom room;

    public PopulateRoomCommand(RoomServiceImpl service, ToyRoom room) {
        this.service = service;
        this.room = room;
    }

    @Override
    public String getTitle() {
        return "1. Сформувати Ігрову Кімнату (autoPopulate)";
    }

    @Override
    public void execute() {
        //РЕАЛІЗАЦІЯ ФВ1

        System.out.println("\n--- Запуск формування кімнати ---");
        System.out.printf("   > Бюджет: %.2f UAH | Цільова к-сть: %d шт.%n", room.getBudget(), room.getTargetCount());

        // 1. Виклик реальної бізнес-логіки (ФВ1) на об'єкті Receiver
        service.autoPopulate(room);

        // 2. Виведення результату
        System.out.printf("\nКімната сформована!%n");
        System.out.printf("   > Додано іграшок: %d шт.%n", room.getToys().size());
        System.out.printf("   > Загальна вартість: %.2f UAH%n", room.totalCost());
        System.out.printf("   > Залишок бюджету: %.2f UAH%n", room.remainingBudget());

        // 3. Додатково: виведення вмісту кімнати
        if (!room.getToys().isEmpty()) {
            System.out.println("   > Поточний вміст (сортуємо за ID для читабельності):");
            // Сортуємо для відображення, хоча це не є частиною ФВ1
            room.getToys().stream()
                    .sorted(java.util.Comparator.comparing(com.toyroom.domain.Toy::getId))
                    .forEach(System.out::println);
        } else {
            System.out.println("   > Кімната порожня (можливо, не вистачило бюджету для жодної іграшки).");
        }
    }
}