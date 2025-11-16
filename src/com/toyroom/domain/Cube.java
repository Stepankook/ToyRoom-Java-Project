package com.toyroom.domain;

public final class Cube extends Toy {
    public Cube(long id, String name, Size size, double price, int minAge, int maxAge, Material material, String brand) {
        super(id, name, size, price, minAge, maxAge, material, brand);
    }

    @Override
    public Category getCategory() {
        return Category.CUBE;
    }

    @Override
    public String toString() {
        return "Cube: " + super.toString();
    }
}