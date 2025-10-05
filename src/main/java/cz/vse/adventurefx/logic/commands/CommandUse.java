package cz.vse.adventurefx.logic.commands;

import cz.vse.adventurefx.logic.GamePlan;
import cz.vse.adventurefx.logic.entities.Prop;
import cz.vse.adventurefx.logic.items.Backpack;
import cz.vse.adventurefx.logic.items.Item;

/**
 * Třída CommandUse implementuje příkaz "use", který umožňuje
 * hráči použít předmět z batohu na nějaký objekt (entitu) v aktuální místnosti.
 */
public class CommandUse implements ICommand {

    public static final String NAME = "use";
    private GamePlan plan;
    private Backpack backpack;

    /**
     * Konstruktor nastavuje herní plán a batoh hráče,
     * který slouží ke kontrole dostupných předmětů.
     *
     * @param plan     herní plán, který uchovává aktuální místnost
     * @param backpack batoh hráče s předměty
     */
    public CommandUse(GamePlan plan, Backpack backpack) {
        this.plan = plan;
        this.backpack = backpack;
    }

    /**
     * Metoda vykoná příkaz "use", který použije zvolený předmět
     * na vybranou entitu v místnosti.
     *
     * @param params dva parametry: název předmětu a název entity
     * @return zpráva o výsledku akce
     */
    @Override
    public String executeCommand(String... params) {
        if (params.length != 2) {
            return "You need to enter two parameters! Usage: interact <item_name> <entity_name>";
        }

        Item item = backpack.getItemWithName(params[0]);
        Prop prop = plan.getCurrentRoom().getEntityByName(params[1]);

        if (item == null) {
            return "You do not have an item in backpack with that name. Use command inventory to display your carried items. ";
        }

        if (prop == null) {
            return "There is no such entity in the room. Use command look_around to see entities in the room.";
        }

        UseResult result = item.useItem(prop);

        if (result.shouldRemoveItem()) {
            backpack.deleteItem(item);
        }

        return result.getMessage();
    }

    @Override
    public String getName() {
        return NAME;
    }
}
