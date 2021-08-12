package dev.kaira.service;

import dev.kaira.model.constant.ItemType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TossAdviserServiceTest {

    @Test
    void getRandomItem() {
        TossAdviserService service = new TossAdviserService();
        ItemType result = service.getRandomItem();
        assertInstanceOf(ItemType.class, result);
    }
}