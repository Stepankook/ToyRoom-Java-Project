package com.toyroom.menu.commands;

import com.toyroom.menu.Command;

/**
 * Команда для пошуку іграшок за діапазоном (заглушка).
 */
public class FindToysCommand implements Command {

    @Override
    public String getTitle() {
        return "3. Знайти іграшки за діапазоном параметрів";
    }

    @Override
    public void execute() {
        System.out.println("-> Виконання команди: Пошук (потрібне введення діапазону)...");
        // Логіка пошуку тут.
    }
}