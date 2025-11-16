package com.toyroom.repo;

import com.toyroom.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class InMemoryToyRepository implements ToyRepository {

    private final List<Toy> data;

    public InMemoryToyRepository() {
        this.data = seed();
    }

    @Override
    public List<Toy> listAll() {
        return List.copyOf(data);
    }

    @Override
    public List<Toy> findByCategory(Category category) {
        return data.stream().filter(t -> t.getCategory() == category).collect(Collectors.toUnmodifiableList());
    }

    private static List<Toy> seed() {
        List<Toy> list = new ArrayList<>();
        // Машинки (S/M/L)
        list.add(new Car(1001, "Car Mini",   Size.SMALL,  150, 3, 6,  Material.PLASTIC, "KidGo"));
        list.add(new Car(1002, "Car Mid",    Size.MEDIUM, 260, 4, 8,  Material.PLASTIC, "KidGo"));
        list.add(new Car(1003, "Car Large",  Size.LARGE,  390, 5, 10, Material.PLASTIC, "KidGo"));
        // Ляльки
        list.add(new Doll(1101, "Doll Classic", Size.MEDIUM, 320, 4, 9,  Material.FABRIC, "Lovely"));
        list.add(new Doll(1102, "Doll Baby",    Size.SMALL,  210, 3, 7,  Material.FABRIC, "Lovely"));
        // М’ячі
        list.add(new Ball(1201, "Ball Soft",   Size.SMALL,  120, 2, 6,  Material.RUBBER, "Sporty"));
        list.add(new Ball(1202, "Ball Street", Size.MEDIUM, 200, 6, 12, Material.RUBBER, "Sporty"));
        // Кубики
        list.add(new Cube(1301, "Cubes 24", Size.SMALL,  180, 3, 7,  Material.WOOD, "WoodPlay"));
        list.add(new Cube(1302, "Cubes 48", Size.MEDIUM, 290, 4, 9,  Material.WOOD, "WoodPlay"));
        return list;
    }
}
