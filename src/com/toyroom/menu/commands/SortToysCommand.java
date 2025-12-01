package com.toyroom.menu.commands;

import com.toyroom.menu.Command;

/**
 * Команда для сортування іграшок (заглушка).
 */
public class SortToysCommand implements Command {

    public SortToysCommand(/* тут будуть Receiver-об'єкти, як і в PopulateRoomCommand */) {
        // Присвоєння Receiver
    }

    @Override
    public String getTitle() {
        return "2. Відсортувати іграшки за параметром";
    }

    @Override
    public void execute() {
        System.out.println("-> Виконання команди: Сортування (потрібен вибір параметра)...");
        // Логіка сортування тут.
    }
}