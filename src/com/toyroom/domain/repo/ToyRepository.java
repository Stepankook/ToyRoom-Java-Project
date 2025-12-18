package com.toyroom.domain.repo;

import com.toyroom.domain.Category;
import com.toyroom.domain.Toy;

import java.util.List;

public interface ToyRepository {
    List<Toy> listAll();
    List<Toy> findByCategory(Category category);
}
