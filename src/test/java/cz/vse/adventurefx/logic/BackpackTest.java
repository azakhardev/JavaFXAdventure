package cz.vse.adventurefx.logic;

import cz.vse.adventurefx.logic.items.Backpack;
import cz.vse.adventurefx.logic.items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro ověření funkčnosti třídy Backpack.
 */
public class BackpackTest {

    private Backpack backpack;
    private Item item1;
    private Item item2;

    @BeforeEach
    void setUp() {
        backpack = new Backpack(5);
        item1 = new Item("needle", "Sharp", 1);
        item2 = new Item("shovel", "Heavy shovel", 5);
    }

    @Test
    void testAddCapacity() {
        backpack.addCapacity(3);
        assertEquals(8, backpack.getCapacity());
    }

    @Test
    void testStoreItemWhenEnoughSpace() throws Exception {
        Room room = new Room("TestRoom", "desc");
        room.addItem(item1);
        Item stored = backpack.storeItem(item1, room);
        assertEquals(item1, stored);
        assertTrue(backpack.getItems().contains(item1));
        assertFalse(room.getItems().containsKey("needle"));
    }

    @Test
    void testStoreItemFailsWhenNotEnoughSpace() {
        Room room = new Room("TestRoom", "desc");
        Item bigItem = new Item("big", "Sharp", 6);


        Exception exception = assertThrows(Exception.class, () -> {
            backpack.storeItem(bigItem, room);
        });

        assertTrue(exception.getMessage().contains("You don't have enough space"));
        assertFalse(backpack.getItems().contains(item2));
    }

    @Test
    void testStoreItemWithoutRoom() {
        backpack.storeItem(item1);
        assertTrue(backpack.getItems().contains(item1));
    }

    @Test
    void testDropItem() {
        Room room = new Room("TestRoom", "desc");

        backpack.storeItem(item1);

        backpack.dropItem(item1, room);

        assertNotNull(room.getItemByName("needle"));
        assertFalse(backpack.getItems().contains(item1));
    }

    @Test
    void testDeleteItem() {
        backpack.storeItem(item1);
        boolean stillExists = backpack.deleteItem(item1);
        assertFalse(stillExists);
    }

    @Test
    void testGetUsedCapacity() {
        backpack.storeItem(item1);
        assertEquals(1, backpack.getUsedCapacity());
    }

    @Test
    void testGetItemWithName() {
        backpack.storeItem(item1);
        Item found = backpack.getItemWithName("needle");
        assertEquals(item1, found);
    }

    @Test
    void testGetItemWithNameNotFound() {
        assertNull(backpack.getItemWithName("nonexistent"));
    }
}
