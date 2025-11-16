package com.toyroom.domain;

public abstract class Toy {
    private final long id;
    private final String name;
    private final Size size; // Загальний параметр
    private final double price;
    private final int minAge;
    private final int maxAge;
    private final Material material;
    private final String brand;

    public Toy(long id, String name, Size size, double price, int minAge, int maxAge, Material material, String brand) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.price = price;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.material = material;
        this.brand = brand;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public Size getSize() { return size; }
    public double getPrice() { return price; }
    public int getMinAge() { return minAge; }
    public int getMaxAge() { return maxAge; }
    public Material getMaterial() { return material; }
    public String getBrand() { return brand; }

    // Абстрактний метод, щоб кожна іграшка знала свою категорію
    public abstract Category getCategory();

    @Override
    public String toString() {
        return String.format("%s (ID:%d) | Size: %s | Price: %.2f UAH | Age: %d-%d",
                name, id, size, price, minAge, maxAge);
    }
}