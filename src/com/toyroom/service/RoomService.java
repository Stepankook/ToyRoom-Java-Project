
package com.toyroom.service;

import com.toyroom.domain.Size;
import com.toyroom.domain.SortParam;
import com.toyroom.domain.Toy;
import com.toyroom.domain.ToyRoom;
import com.toyroom.service.ValidationResult;

import java.util.List;


public interface RoomService {
    void autoPopulate(ToyRoom room);
    void sortBy(ToyRoom room, SortParam param);
    List<Toy> findByPriceRange(ToyRoom room, double min, double max);
    List<Toy> findBySizeRange(ToyRoom room, Size from, Size to);
    List<Toy> findByAgeRange(ToyRoom room, int minAge, int maxAge);
    ValidationResult validateRoom(ToyRoom room);
}
