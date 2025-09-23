package cz.vse.adventurefx.logic.commands;

import cz.vse.adventurefx.logic.entities.Player;
import cz.vse.adventurefx.logic.items.Backpack;
import cz.vse.adventurefx.logic.items.Item;

/**
 * Třída představuje příkaz "inventory", který hráči zobrazí
 * obsah jeho batohu včetně informací o volné kapacitě.
 */
public class CommandInventory implements ICommand {

    private static final String NAME = "inventory";
    private Player player;

    /**
     * Konstruktor třídy, nastaví hráče, jehož inventář se bude zobrazovat.
     *
     * @param player instance hráče
     */
    public CommandInventory(Player player) {
        this.player = player;
    }

    /**
     * Metoda provádí příkaz "inventory". Vypíše všechny předměty,
     * které hráč aktuálně má v batohu, jejich velikost, a kolik místa
     * v batohu ještě zbývá.
     *
     * @param params tento příkaz nepřijímá žádné parametry
     * @return seznam předmětů v batohu nebo chybová hláška
     */
    @Override
    public String executeCommand(String... params) {
        Backpack backpack = player.getBackpack();

        if (params.length != 0) {
            return "Command inventory does not take any parameters.";
        }

        if (backpack.getItems().isEmpty()) {
            return "You don't have any items in your backpack" + "Your backpack has capacity of " + backpack.getCapacity();
        }

        StringBuilder storedItems = new StringBuilder("You have those items in your backpack: \n");

        for (Item item : backpack.getItems()) {
            storedItems.append(item.getName()).append(" that takes up ").append(item.getVolume()).append(" space unit.\n");
        }

        storedItems.append("You have ").append(backpack.getCapacity() - backpack.getUsedCapacity()).append(" free space left in your backpack.\n");

        return storedItems.toString();
    }

    /**
     * Vrací název příkazu, který hráč používá k jeho vyvolání ("inventory").
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return NAME;
    }
}
