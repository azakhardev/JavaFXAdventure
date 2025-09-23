package cz.vse.adventurefx.logic;

import cz.vse.adventurefx.logic.items.Item;
import cz.vse.adventurefx.logic.items.ItemFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro ověření správného vytváření Itemů přes ItemFactory.
 */
public class ItemFactoryTest {


    @Test
    void testCreateAllItems() {
        for (ItemFactory.ItemName itemName : ItemFactory.ItemName.values()) {
            Item item = ItemFactory.createItem(itemName);
            assertNotNull(item, "Item should not be null for " + itemName);
            assertFalse(item.getName().isEmpty(), "Name should not be empty");
        }
    }

    @Test
    void testSpecificItemProperties() {
        Item item = ItemFactory.createItem(ItemFactory.ItemName.CROWBAR);
        assertEquals("crowbar", item.getName());
        assertEquals(4, item.getVolume());
    }
}
