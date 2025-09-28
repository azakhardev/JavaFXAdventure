package cz.vse.adventurefx.logic.commands;

import cz.vse.adventurefx.logic.GamePlan;
import cz.vse.adventurefx.logic.Room;
import cz.vse.adventurefx.logic.entities.Obstacle;

/**
 * Třída PrikazJdi implementuje pro hru příkaz jdi.
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author Jarmila Pavlickova, Luboš Pavlíček
 * @version pro školní rok 2016/2017
 */
public class CommandGo implements ICommand {
    public static final String NAME = "go";
    private GamePlan plan;

    /**
     * Konstruktor třídy
     *
     * @param plan herní plán, ve kterém se bude ve hře "chodit"
     */
    public CommandGo(GamePlan plan) {
        this.plan = plan;
    }

    /**
     * Provádí příkaz "jdi". Zkouší se vyjít do zadaného prostoru. Pokud prostor
     * existuje, vstoupí se do nového prostoru. Pokud zadaný sousední prostor
     * (východ) není, vypíše se chybové hlášení.
     *
     * @param params - jako  parametr obsahuje jméno prostoru (východu),
     *               do kterého se má jít.
     * @return zpráva, kterou vypíše hra hráči
     */
    @Override
    public String executeCommand(String... params) {
        if (params.length != 1) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "Where do you want to go? Usage: go <exit_name>";
        }

        String direction = params[0];
        // zkoušíme přejít do sousedního prostoru
        Room siblingRoom = plan.getCurrentRoom().getSiblingRoom(direction);

        if (siblingRoom == null) {
            return "You can't go there!";
        }

        Obstacle obstacle = null;
        for (Obstacle o : plan.getCurrentRoom().getObstacles().values()) {
            if (o.getBlockedRoom().equals(siblingRoom)) {
                obstacle = o;
                break;
            }
        }

        if (obstacle != null) {
            return "You can't go to the room " + siblingRoom.getName() +
                    " because it is blocked by " + obstacle.getName();
        } else {
            plan.setCurrentRoom(siblingRoom);
            return siblingRoom.getLongDescription();
        }
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
