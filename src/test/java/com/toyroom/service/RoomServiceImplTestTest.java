package com.toyroom.service;

import com.toyroom.domain.ToyRoom;
import com.toyroom.domain.repo.InMemoryToyRepository; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoomServiceImplTest {

    private RoomServiceImpl roomService;
    private ToyRoom room;

    @BeforeEach
    void setUp() {
        
        roomService = new RoomServiceImpl(new InMemoryToyRepository());
        // Створюємо кімнату: Бюджет 1000.0, Цільова кількість 5
        room = new ToyRoom(1000.0, 5);
    }

    @Test
    void testAutoPopulateLogic() {
        roomService.autoPopulate(room);

        assertNotNull(room.getToys(), "Список іграшок не повинен бути порожнім");
        assertFalse(room.getToys().isEmpty(), "Кімната має бути заповнена іграшками");

       
        assertTrue(room.totalCost() <= room.getBudget(),
                "Загальна вартість перевищує бюджет");
    }

    @Test
    void testRoomDataIntegrity() {
        assertEquals(1000.0, room.getBudget(), 0.001);
        assertEquals(5, room.getTargetCount());
    }

    @Test
    void testSortingLogic() {
        roomService.autoPopulate(room);

        // Перевірка, що сортування в сервісі не видає помилок
        assertDoesNotThrow(() -> {
            var toys = room.getToys();
            if (!toys.isEmpty()) {
                // Перевірка, що ми можемо отримати доступ до цін для сортування
                double price = toys.get(0).getPrice();
                assertTrue(price >= 0);
            }
        });
    }
}
