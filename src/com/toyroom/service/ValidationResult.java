package com.toyroom.service;

import com.toyroom.domain.ToyRoom;
import java.util.Collections;
import java.util.List;


public final class ValidationResult {
    private final boolean ok;
    private final List<String> messages;


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


    public static ValidationResult ok() {
        // Успіх = true, список повідомлень порожній
        return new ValidationResult(true, Collections.emptyList());
    }

    public static ValidationResult fail(List<String> messages) {

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