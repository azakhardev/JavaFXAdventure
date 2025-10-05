package cz.vse.adventurefx.logic.commands;

import cz.vse.adventurefx.logic.items.Backpack;
import cz.vse.adventurefx.logic.items.Item;

import java.util.Arrays;

/**
 * Třída představuje příkaz "combine", který umožňuje hráči kombinovat dva předměty z batohu
 * a vytvořit z nich nový předmět, případně rozšířit kapacitu batohu.
 */
public class CommandCombine implements ICommand {

    public final static String NAME = "combine";
    private final Backpack backpack;

    /**
     * Konstruktor třídy, přiřadí batoh hráče, se kterým bude příkaz pracovat.
     *
     * @param backpack instance batohu hráče
     */
    public CommandCombine(Backpack backpack) {
        this.backpack = backpack;
    }

    /**
     * Metoda provádí příkaz "combine". Ověří, zda hráč zadal dva existující předměty,
     * a pokud ano, pokusí se je spojit do nového předmětu podle předdefinovaných kombinací.
     * V případě úspěchu odstraní původní předměty a uloží nový, nebo upraví vlastnosti batohu.
     *
     * @param params názvy dvou předmětů, které chce hráč zkombinovat
     * @return zpráva o výsledku pokusu o kombinaci
     */
    @Override
    public String executeCommand(String... params) {
        if (params.length != 2) {
            return "What do you want to combine? Usage: combine <item_name> <item_name>";
        }

        Item item1 = backpack.getItemWithName(params[0]);
        Item item2 = backpack.getItemWithName(params[1]);

        if (item1 != null && item2 != null) {
            Item newItem = new Item("", "", 0);
            boolean crafted = false;

            if (Arrays.asList(params).contains("tape") && Arrays.asList(params).contains("broken_cable")) {
                crafted = true;
                newItem = new Item("sheathed_cable", "A once-split cable now firmly held together with layers of thick tape. It should be safe to use—for now.", 2);
            }

            if (Arrays.asList(params).contains("cloth") && Arrays.asList(params).contains("needle")) {
                this.backpack.deleteItem(item1);
                this.backpack.deleteItem(item2);
                return "You've crafted a hand-stitched fabric extension for your backpack. It’s rough, but it holds together well enough to carry more gear." + backpack.addCapacity(1);
            }

            if (Arrays.asList(params).contains("matches") && Arrays.asList(params).contains("dynamite")) {
                crafted = true;
                newItem = new Item("primed_explosive", "A bundle of aged sticks of dynamite with a fuse attached. Needs to be ignited to detonate—best used carefully at the right spot.", 3);
            }

            if (crafted) {
                this.backpack.deleteItem(item1);
                this.backpack.deleteItem(item2);
                this.backpack.storeItem(newItem);
                return "You crafted new item: " + newItem.getName() + " - " + newItem.getDescription();
            }

        } else {
            return "You don't have those items in your backpack.";
        }

        return "You can't combine those items.";
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *
     * @return nazev prikazu
     */
    @Override
    public String getName() {
        return NAME;
    }
}
