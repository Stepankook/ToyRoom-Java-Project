
package com.toyroom.service;

import com.toyroom.domain.Size; // Потрібно для findBySizeRange
import com.toyroom.domain.SortParam; // Потрібно для sortBy
import com.toyroom.domain.Toy;
import com.toyroom.domain.ToyRoom; // Потрібно для всіх методів
import com.toyroom.service.ValidationResult; // Потрібно для validateRoom

import java.util.List;
// ... решта коду інтерфейсу

import com.toyroom.domain.*;

import java.util.List;

public interface RoomService {
    void autoPopulate(ToyRoom room); // реалізуємо в Частині 3
    void sortBy(ToyRoom room, SortParam param);
    List<Toy> findByPriceRange(ToyRoom room, double min, double max);
    List<Toy> findBySizeRange(ToyRoom room, Size from, Size to);
    List<Toy> findByAgeRange(ToyRoom room, int minAge, int maxAge);
    ValidationResult validateRoom(ToyRoom room);
}
