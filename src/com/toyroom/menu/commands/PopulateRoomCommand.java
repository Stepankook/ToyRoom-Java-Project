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
        // Тут буде викликана реальна логіка. На цьому етапі - заглушка.
        System.out.println("-> Виконання команди: Заповнення кімнати...");
        // service.autoPopulate(room); // Заглушка, функціонал не реалізовано тут
    }
}