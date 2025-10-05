package cz.vse.adventurefx.logic.commands;

import cz.vse.adventurefx.logic.GamePlan;
import cz.vse.adventurefx.logic.Room;
import cz.vse.adventurefx.logic.items.Backpack;
import cz.vse.adventurefx.logic.items.Item;

/**
 * Třída představuje příkaz "drop", který umožňuje hráči odhodit předmět
 * z batohu do aktuální místnosti.
 */
public class CommandDrop implements ICommand {

    public final static String NAME = "drop";
    private final GamePlan gamePlan;
    private final Backpack backpack;

    /**
     * Konstruktor třídy, nastaví plán hry a batoh, se kterým příkaz pracuje.
     *
     * @param gamePlan instance plánu hry
     * @param backpack instance batohu hráče
     */
    public CommandDrop(GamePlan gamePlan, Backpack backpack) {
        this.gamePlan = gamePlan;
        this.backpack = backpack;
    }

    /**
     * Metoda provádí příkaz "drop". Pokusí se odebrat předmět z batohu
     * a přidat ho do aktuální místnosti.
     *
     * @param params jeden parametr — název předmětu, který chce hráč odhodit
     * @return zpráva o úspěšném odhození předmětu, nebo chybová hláška
     */
    @Override
    public String executeCommand(String... params) {

        if (params.length != 1) {
            return "What do you want to drop? Usage: drop <item_name>";
        }

        Room currentRoom = gamePlan.getCurrentRoom();
        Item item = backpack.getItemWithName(params[0]);

        if (item == null) {
            return "You don't have this item.";
        }

        return this.backpack.dropItem(item, currentRoom);
    }

    /**
     * Vrací název příkazu, který hráč používá k jeho vyvolání ("drop").
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return NAME;
    }
}
