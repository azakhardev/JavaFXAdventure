package cz.vse.adventurefx.logic;

import cz.vse.adventurefx.logic.commands.UseResult;
import cz.vse.adventurefx.logic.entities.Prop;
import cz.vse.adventurefx.logic.items.Backpack;
import cz.vse.adventurefx.logic.items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro ověření vlastností a chování Itemu.
 */
public class ItemTest {

    @Test
    void testItemAttributes() {
        Item item = new Item("key", "Opens things", 1);
        assertEquals("key", item.getName());
        assertEquals("Opens things", item.getDescription());
        assertEquals(1, item.getVolume());
        assertTrue(item.isLootable());
    }

    @Test
    void testNegativeVolumeIsNotLootable() {
        Item item = new Item("ghost", "non-physical", -1);
        assertFalse(item.isLootable());
    }

    @Test
    void testUseItem() {
        Item item = new Item("key", "Opens things", 1);

        Prop door = new Prop("door", "can be opened with key", (i) -> {
            if (i.getName().equals("key")) {
                return new UseResult("You opened the door", true);
            }
            return new UseResult("You can't open the door with it", false);
        });

        UseResult result = item.useItem(door);

        assertEquals("You opened the door", result.getMessage());
        assertTrue(result.shouldRemoveItem());
    }

    @Test
    void testFailToUseItem() {
        Item item = new Item("brick", "Opens things", 1);

        Prop door = new Prop("door", "can be opened with key", (i) -> {
            if (i.getName().equals("key")) {
                return new UseResult("You opened the door", true);
            }
            return new UseResult("You can't open the door with it", false);
        });

        UseResult result = item.useItem(door);

        assertEquals("You can't open the door with it", result.getMessage());
        assertFalse(result.shouldRemoveItem());

    }
}
