package cz.vse.adventurefx.logic.commands;

import cz.vse.adventurefx.logic.GamePlan;
import cz.vse.adventurefx.logic.items.Backpack;
import cz.vse.adventurefx.logic.items.Item;

/**
 * Třída představuje příkaz "pick", který umožňuje hráči
 * sebrat předmět z aktuální místnosti a uložit ho do batohu.
 */
public class CommandPick implements ICommand {

    public static final String NAME = "pick";

    private GamePlan gamePlan;
    private Backpack backpack;

    /**
     * Konstruktor nastaví herní plán a hráčův batoh,
     * do kterého se budou předměty ukládat.
     *
     * @param gamePlan herní plán hry
     * @param backpack batoh hráče
     */
    public CommandPick(GamePlan gamePlan, Backpack backpack) {
        this.gamePlan = gamePlan;
        this.backpack = backpack;
    }

    /**
     * Provádí příkaz "pick", který hráči umožní sebrat
     * předmět z aktuální místnosti. Pokud bude předmět nalezen
     * a v batohu bude dostatek místa, tak se do něj vloží
     *
     * @param params název předmětu, který chce hráč sebrat
     * @return zpráva o výsledku akce (úspěch, chyba, výjimka)
     */
    @Override
    public String executeCommand(String... params) {
        if (params.length != 1) {
            return "Invalid number of arguments! Usage: pick <item_name>";
        }

        Item item = this.gamePlan.getCurrentRoom().getItemByName(params[0]);

        if (item != null) {
            try {
                this.backpack.storeItem(item, this.gamePlan.getCurrentRoom());
                return "You picked " + item.getName();
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "There is no such item!";
        }

    }


    /**
     * Vrací název příkazu, pod kterým jej hráč může volat.
     *
     * @return název příkazu "pick"
     */
    @Override
    public String getName() {
        return NAME;
    }
}
