package com.toyroom.domain;

public enum Size {
    SMALL, MEDIUM, LARGE;

    public static int ordinalValue(Size s) {
        return switch (s) {
            case SMALL -> 1;
            case MEDIUM -> 2;
            case LARGE -> 3;
        };
    }
}
