package cz.vse.adventurefx.logic;

import cz.vse.adventurefx.logic.commands.UseResult;
import cz.vse.adventurefx.logic.entities.Prop;
import cz.vse.adventurefx.logic.items.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropTest {

    @Test
    void defaultInteractionTest() {
        Prop prop = new Prop("Chair", "Just a wooden chair.");
        assertEquals("Just a wooden chair.", prop.interact());
    }

    @Test
    void defaultUseTest() {
        Prop prop = new Prop("Chair", "Old chair.");
        Item item = new Item("Hammer", "Rusty hammer", 4);
        UseResult result = prop.applyItem(item);
        assertFalse(result.shouldRemoveItem());
        assertEquals("You can't use Hammer on this object!", result.getMessage());
    }

    @Test
    void customUseLogicTest() {
        Item screwdriver = new Item("Screwdriver", "", 1);
        Prop panel = new Prop(
                "Panel",
                "Electrical panel",
                (item) -> item.getName().equals("Screwdriver")
                        ? new UseResult("Panel opened!", true)
                        : new UseResult("Wrong item", false)
        );

        UseResult result = panel.applyItem(screwdriver);
        assertTrue(result.shouldRemoveItem());
        assertEquals("Panel opened!", result.getMessage());
    }

    @Test
    void customInteractionLogicTest() {
        Prop door = new Prop("Door", "Closed door", () -> "You opened the door.");
        assertEquals("You opened the door.", door.interact());
    }
}
