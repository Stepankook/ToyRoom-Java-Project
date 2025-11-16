package com.toyroom.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ToyRoom {
    private double budget;
    private int targetCount;
    private final List<Toy> toys = new ArrayList<>();


    public ToyRoom(double budget, int targetCount) {
        setBudget(budget);
        setTargetCount(targetCount);
    }

    public void setBudget(double budget) {
        if (budget < 0) throw new IllegalArgumentException("budget < 0");
        this.budget = budget;
    }

    public void setTargetCount(int targetCount) {
        if (targetCount < 0) throw new IllegalArgumentException("targetCount < 0");
        this.targetCount = targetCount;
    }

    public double getBudget() { return budget; }
    public int getTargetCount() { return targetCount; }

    public void addToy(Toy toy) {
        if (toy == null) throw new IllegalArgumentException("toy is null");
        toys.add(toy);
    }

    public boolean removeToy(long id) {
        return toys.removeIf(t -> t.getId() == id);
    }

    public List<Toy> getToys() {
        return Collections.unmodifiableList(toys);
    }

    public double totalCost() {
        return toys.stream().mapToDouble(Toy::getPrice).sum();
    }

    public double remainingBudget() {
        return budget - totalCost();
    }

    public void clearToys() {
        this.toys.clear();
    }
}
