package com.toyroom.domain;

public final class Doll extends Toy {
    public Doll(long id, String name, Size size, double price, int minAge, int maxAge, Material material, String brand) {
        super(id, name, size, price, minAge, maxAge, material, brand);
    }

    @Override
    public Category getCategory() {
        return Category.DOLL;
    }

    @Override
    public String toString() {
        return "Doll: " + super.toString();
    }
}