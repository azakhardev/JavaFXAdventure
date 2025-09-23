package cz.vse.adventurefx.logic.commands;

import cz.vse.adventurefx.logic.items.Backpack;
import cz.vse.adventurefx.logic.items.Item;

/**
 * Třída představuje příkaz "inspect", který umožňuje hráči
 * detailně prozkoumat předmět ve svém batohu.
 */
public class CommandInspect implements ICommand {
    private final static String NAME = "inspect";
    private Backpack backpack;

    /**
     * Konstruktor třídy, nastaví batoh, ze kterého hráč vybírá předměty k prozkoumání.
     *
     * @param backpack instance batohu hráče
     */
    public CommandInspect(Backpack backpack) {
        this.backpack = backpack;
    }

    /**
     * Metoda provádí příkaz "inspect". Vyhledá zadaný předmět v batohu
     * a vrátí jeho popis.
     *
     * @param params jeden parametr — název předmětu, který chce hráč prozkoumat
     * @return popis předmětu nebo chybová hláška
     */
    @Override
    public String executeCommand(String... params) {
        if (params.length != 1) {
            return "You need to specify what you want to inspect. Usage: inspect <item_in_inventory>";
        }

        Item item = backpack.getItemWithName(params[0]);

        if (item == null) {
            return "You don't have this item.";
        }

        return item.getDescription();
    }

    /**
     * Vrací název příkazu, který hráč používá k jeho vyvolání ("inspect").
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return NAME;
    }
}
