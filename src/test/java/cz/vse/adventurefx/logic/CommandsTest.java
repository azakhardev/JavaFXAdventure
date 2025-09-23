package cz.vse.adventurefx.logic;

import cz.vse.adventurefx.logic.commands.*;
import cz.vse.adventurefx.logic.entities.Player;
import cz.vse.adventurefx.logic.entities.Prop;
import cz.vse.adventurefx.logic.items.Backpack;
import cz.vse.adventurefx.logic.items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro ověření funkčnosti příkazů
 */
public class CommandsTest {

    private Game game;
    private GamePlan gamePlan;
    private Backpack backpack;
    private Room room;

    @BeforeEach
    public void setUp() {
        game = new Game();
        gamePlan = new GamePlan();
        backpack = new Backpack(5);
        room = new Room("lab", "Science lab full of strange devices.");
        gamePlan.setCurrentRoom(room);
    }

    @Test
    public void testCommandDescription() {
        CommandDescription descCommand = new CommandDescription(gamePlan);

        assertEquals(room.getLongDescription(), descCommand.executeCommand());

        Prop prop = new Prop("painting", "A creepy painting of a man staring.");
        room.addProp(prop);

        assertEquals(prop.getDescription(), descCommand.executeCommand("painting"));
        assertEquals("There is no such object in this room.", descCommand.executeCommand("statue"));
        assertEquals("Command 'description' takes none of the parameters for description of the room and one for description of the object.", descCommand.executeCommand("painting", "extra"));
    }

    @Test
    public void testCommandDrop() {
        CommandDrop dropCommand = new CommandDrop(gamePlan, backpack);

        Item key = new Item("key", "A small rusty key.", 1);
        backpack.storeItem(key);

        assertEquals("You don't have this item.", dropCommand.executeCommand("nonexistent"));
        assertEquals("What do you want to drop? Usage: drop <item_name>", dropCommand.executeCommand());

        assertEquals("You've dropped key in lab room.", dropCommand.executeCommand("key"));
        assertNull(backpack.getItemWithName("key"));
        assertNotNull(room.getItemByName("key"));
    }

    @Test
    public void testCommandCombine() {
        CommandCombine combineCommand = new CommandCombine(backpack);

        Item tape = new Item("tape", "Strong duct tape.", 1);
        Item cable = new Item("broken_cable", "Split cable, needs fixing.", 1);

        backpack.storeItem(tape);
        backpack.storeItem(cable);

        String result = combineCommand.executeCommand("tape", "broken_cable");

        assertTrue(result.contains("You crafted new item: sheathed_cable"));
        assertNull(backpack.getItemWithName("tape"));
        assertNull(backpack.getItemWithName("broken_cable"));
        assertNotNull(backpack.getItemWithName("sheathed_cable"));

        backpack.storeItem(new Item("apple", "Just an apple", 1));
        backpack.storeItem(new Item("book", "Old book", 1));

        assertEquals("You can't combine those items.", combineCommand.executeCommand("apple", "book"));
        assertEquals("You don't have those items in your backpack.", combineCommand.executeCommand("needle", "cloth"));
        assertEquals("What do you want to combine? Usage: combine <item_name> <item_name>", combineCommand.executeCommand("tape"));
    }

    @Test
    public void testCommandInspect() {
        CommandInspect inspectCommand = new CommandInspect(backpack);

        Item map = new Item("map", "A detailed map of the lab.", 1);
        backpack.storeItem(map);

        assertEquals("You don't have this item.", inspectCommand.executeCommand("key"));
        assertEquals("You need to specify what you want to inspect. Usage: inspect <item_in_inventory>", inspectCommand.executeCommand());
        assertEquals("A detailed map of the lab.", inspectCommand.executeCommand("map"));
    }

    @Test
    public void testCommandInteract() {
        CommandInteract interactCommand = new CommandInteract(gamePlan);

        Prop terminal = new Prop("terminal", "A flickering computer terminal.") {
            @Override
            public String interact() {
                return "You type on the keyboard. The screen lights up.";
            }
        };
        room.addProp(terminal);

        assertEquals("There isn't object with name robot", interactCommand.executeCommand("robot"));
        assertEquals("You need to specify with which object you want to interact. Usage: interact <entity_name>", interactCommand.executeCommand());
        assertEquals("You type on the keyboard. The screen lights up.", interactCommand.executeCommand("terminal"));
    }

    @Test
    public void testCommandInventory() {
        Player player = Player.createInstance(backpack);
        CommandInventory inventoryCommand = new CommandInventory(player);

        assertEquals("Command inventory does not take any parameters.", inventoryCommand.executeCommand("extra"));
        assertEquals("You don't have any items in your backpackYour backpack has capacity of 4", inventoryCommand.executeCommand());

        Item screwdriver = new Item("screwdriver", "Useful tool.", 2);
        Item wires = new Item("wires", "Insulated wires.", 1);

        player.getBackpack().storeItem(screwdriver);
        player.getBackpack().storeItem(wires);

        String result = inventoryCommand.executeCommand();
        assertTrue(result.contains("You have those items in your backpack:"));
        assertTrue(result.contains("screwdriver that takes up 2 space unit."));
        assertTrue(result.contains("wires that takes up 1 space unit."));
        assertTrue(result.contains("You have 1 free space left in your backpack."));
    }

    @Test
    void testCommandLookAround() {
        Room room = new Room("room", "A big room");
        room.addItem(new Item("key", "Small rusty key", 1));
        room.addProp(new Prop("lamp", "Thin lamp"));
        GamePlan plan = new GamePlan();
        plan.setCurrentRoom(room);

        CommandLookAround command = new CommandLookAround(plan);
        String result = command.executeCommand();

        assertTrue(result.contains("key"));
        assertTrue(result.contains("lamp"));
    }

    @Test
    void testCommandPick() {
        Room room = new Room("room", "desc");
        Item item = new Item("key", "Small rusty key", 1);
        room.addItem(item);
        GamePlan plan = new GamePlan();
        plan.setCurrentRoom(room);
        Backpack backpack = new Backpack(3);
        CommandPick command = new CommandPick(plan, backpack);

        String result = command.executeCommand("key");

        assertEquals("You picked key", result);
        assertNotNull(backpack.getItemWithName("key"));
    }

    @Test
    void testCommandUse() {
        Item item = new Item("key", "Small rusty key", 1) {
            @Override
            public UseResult useItem(Prop prop) {
                return new UseResult("You unlocked the door", true);
            }
        };

        Prop door = new Prop("door", "A heavy metal door.");
        Room room = new Room("hall", "desc");
        room.addProp(door);
        GamePlan plan = new GamePlan();
        plan.setCurrentRoom(room);

        Backpack backpack = new Backpack(1);
        backpack.storeItem(item);

        CommandUse command = new CommandUse(plan, backpack);
        String result = command.executeCommand("key", "door");

        assertEquals("You unlocked the door", result);
        assertNull(backpack.getItemWithName("key"));
    }
}
