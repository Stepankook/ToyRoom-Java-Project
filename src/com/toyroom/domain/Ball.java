package com.toyroom.domain;

public final class Ball extends Toy {
    public Ball(long id, String name, Size size, double price, int minAge, int maxAge, Material material, String brand) {
        super(id, name, size, price, minAge, maxAge, material, brand);
    }

    @Override
    public Category getCategory() {
        return Category.BALL;
    }

    @Override
    public String toString() {
        return "Ball: " + super.toString();
    }
}