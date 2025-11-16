package com.toyroom.domain;

public final class Car extends Toy {
    public Car(long id, String name, Size size, double price, int minAge, int maxAge, Material material, String brand) {
        super(id, name, size, price, minAge, maxAge, material, brand);
    }

    @Override
    public Category getCategory() {
        return Category.CAR;
    }

    @Override
    public String toString() {
        return "Car: " + super.toString();
    }
}