package cz.vse.adventurefx.logic.commands;

import cz.vse.adventurefx.logic.GamePlan;
import cz.vse.adventurefx.logic.entities.Prop;

/**
 * Třída představuje příkaz "description", který hráči umožňuje získat popis aktuální místnosti
 * nebo popis konkrétního objektu (předmětu) v místnosti.
 */
public class CommandDescription implements ICommand {

    private final static String NAME = "description";
    private final GamePlan gamePlan;

    /**
     * Konstruktor třídy, nastaví plán hry, ze kterého bude příkaz získávat informace o místnosti a objektech.
     *
     * @param gamePlan instance plánu hry
     */
    public CommandDescription(GamePlan gamePlan) {
        this.gamePlan = gamePlan;
    }

    /**
     * Metoda provádí příkaz "description". Pokud hráč nezadá žádný parametr,
     * vrátí detailní popis aktuální místnosti. Pokud zadá název objektu, pokusí se vrátit jeho popis.
     *
     * @param params žádné parametry pro popis místnosti nebo jeden parametr s názvem objektu pro jeho popis
     * @return textový popis místnosti nebo objektu, případně chybová zpráva
     */
    @Override
    public String executeCommand(String... params) {

        if (params.length == 0) {
            return gamePlan.getCurrentRoom().getLongDescription();
        }

        if (params.length == 1) {
            Prop p = gamePlan.getCurrentRoom().getEntityByName(params[0]);
            if (p != null) {
                return p.getDescription();
            } else {
                return "There is no such object in this room.";
            }
        }

        return "Command 'description' takes none of the parameters for description of the room and one for description of the object.";

    }
    
    /**
     * Vrací název příkazu, který hráč používá k vyvolání této akce ("description").
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return NAME;
    }
}
