package cz.vse.adventurefx.logic.commands;

import cz.vse.adventurefx.logic.GamePlan;
import cz.vse.adventurefx.logic.Room;
import cz.vse.adventurefx.logic.entities.Prop;
import cz.vse.adventurefx.logic.entities.Obstacle;
import cz.vse.adventurefx.logic.items.Item;

/**
 * Třída reprezentuje příkaz "look_around", který hráči umožní
 * rozhlédnout se po aktuální místnosti a zjistit, jaké překážky,
 * předměty a objekty se v ní nacházejí.
 */
public class CommandLookAround implements ICommand {

    private final static String NAME = "look_around";
    private final GamePlan plan;

    /**
     * Konstruktor třídy nastaví herní plán, ze kterého bude
     * brát informace o aktuální místnosti.
     *
     * @param plan herní plán hry
     */
    public CommandLookAround(GamePlan plan) {
        this.plan = plan;
    }

    /**
     * Metoda provádí příkaz "look_around". Vypíše všechny překážky,
     * předměty a objekty v aktuální místnosti, pokud nějaké existují.
     * Příkaz nepřijímá žádné parametry.
     *
     * @param params tento příkaz nepřijímá žádné parametry
     * @return textový popis toho, co hráč v místnosti vidí
     */
    @Override
    public String executeCommand(String... params) {

        if (params.length != 0) {
            return "Command look_around does not take any parameters!";
        }

        Room currentRoom = plan.getCurrentRoom();
        StringBuilder obstacles = new StringBuilder("that something is blocking your way to other rooms: ");
        StringBuilder items = new StringBuilder("some items that you can get use of: ");
        StringBuilder props = new StringBuilder("different objects that have caught your eye: ");

        for (Obstacle obstacle : currentRoom.getObstacles().values()) {
            obstacles.append(obstacle.getName()).append(", ");
        }

        if (currentRoom.getObstacles().isEmpty()) {
            obstacles = new StringBuilder();
        } else {
            obstacles.append("\n");
        }

        for (Item item : currentRoom.getItems().values()) {
            items.append(item.getName()).append(", ");
        }

        if (currentRoom.getItems().isEmpty()) {
            items = new StringBuilder();
        } else {
            items.append("\n");
        }

        for (Prop prop : currentRoom.getProps().values()) {
            props.append(prop.getName()).append(", ");
        }

        if (currentRoom.getProps().isEmpty()) {
            props = new StringBuilder();
        }

        return "You are looking around in room " + currentRoom.getName() + " and you see "
                + obstacles + items + props;
    }

    /**
     * Vrací název příkazu, který hráč používá k jeho vyvolání ("look_around").
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return NAME;
    }
}
