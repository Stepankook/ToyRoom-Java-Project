package com.toyroom.service;

import com.toyroom.domain.ToyRoom; // Не потрібно, але залишаємо для прикладу
import java.util.Collections;
import java.util.List;

/**
 * Клас, що представляє результат валідації (альтернатива Record).
 */
public final class ValidationResult {
    private final boolean ok;
    private final List<String> messages;

    // Приватний конструктор, щоб змусити використовувати статичні фабричні методи
    private ValidationResult(boolean ok, List<String> messages) {
        this.ok = ok;
        this.messages = messages;
    }

    public boolean isOk() {
        return ok;
    }

    public List<String> getMessages() {
        return messages;
    }

    // Статичні фабричні методи
    public static ValidationResult ok() {
        // Успіх = true, список повідомлень порожній
        return new ValidationResult(true, Collections.emptyList());
    }

    public static ValidationResult fail(List<String> messages) {
        // Невдача = false, створюємо незмінну копію вхідного списку
        return new ValidationResult(false, List.copyOf(messages));
    }

    @Override
    public String toString() {
        if (ok) {
            return "ValidationResult: OK";
        } else {
            return "ValidationResult: FAIL. Errors: " + messages;
        }
    }
}