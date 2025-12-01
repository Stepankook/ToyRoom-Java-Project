package com.toyroom.menu;


 //Інтерфейс Команди. Оголошує метод для виконання дії.

public interface Command {


     //Виконує дію, пов'язану з цією командою.

    void execute();


     //Повертає назву команди для відображення в меню.

    String getTitle();
}