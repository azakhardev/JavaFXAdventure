package cz.vse.adventurefx.logic.commands;

import cz.vse.adventurefx.logic.GamePlan;
import cz.vse.adventurefx.logic.Password;
import cz.vse.adventurefx.logic.entities.Prop;

import java.util.Objects;

/**
 * Třída představuje příkaz "interact", který umožňuje hráči
 * interagovat s objekty (entitami) ve hře.
 */
public class CommandInteract implements ICommand {

    public static final String NAME = "interact";
    public GamePlan plan;

    /**
     * Konstruktor třídy, nastaví herní plán, ve kterém se hráč pohybuje.
     *
     * @param plan instance herního plánu
     */
    public CommandInteract(GamePlan plan) {
        this.plan = plan;
    }

    /**
     * Metoda provádí příkaz "interact". Najde zadanou entitu v aktuální místnosti
     * a zavolá její metodu interakce.
     *
     * @param params jeden parametr — název entity, se kterou chce hráč interagovat
     * @return výsledek interakce nebo chybová hláška
     */
    @Override
    public String executeCommand(String... params) {
        if (params.length != 1) {
            return "You need to specify with which object you want to interact. Usage: interact <entity_name>";
        }

        Prop prop = plan.getCurrentRoom().getEntityByName(params[0]);

        if (prop == null) {
            return "There isn't object with name " + params[0];
        }

        return prop.interact();
    }

    /**
     * Vrací název příkazu, který hráč používá k jeho vyvolání ("interact").
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return NAME;
    }
}
