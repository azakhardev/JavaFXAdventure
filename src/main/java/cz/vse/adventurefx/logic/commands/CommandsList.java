package cz.vse.adventurefx.logic.commands;

import java.util.HashMap;
import java.util.Map;

/**
 * Class SeznamPrikazu - eviduje seznam přípustných příkazů adventury.
 * Používá se pro rozpoznávání příkazů
 * a vrácení odkazu na třídu implementující konkrétní příkaz.
 * Každý nový příkaz (instance implementující rozhraní Prikaz) se
 * musí do seznamu přidat metodou vlozPrikaz.
 * <p>
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 * @version pro školní rok 2016/2017
 */
public class CommandsList {
    // mapa pro uložení přípustných příkazů
    private Map<String, ICommand> commandsMap;


    /**
     * Konstruktor
     */
    public CommandsList() {
        commandsMap = new HashMap<>();
    }


    /**
     * Vkládá nový příkaz.
     *
     * @param command Instance třídy implementující rozhraní IPrikaz
     */
    public void insertCommand(ICommand command) {
        commandsMap.put(command.getName(), command);
    }

    /**
     * Vrací odkaz na instanci třídy implementující rozhraní IPrikaz,
     * která provádí příkaz uvedený jako parametr.
     *
     * @param query klíčové slovo příkazu, který chce hráč zavolat
     * @return instance třídy, která provede požadovaný příkaz
     */
    public ICommand returnCommand(String query) {
        if (commandsMap.containsKey(query)) {
            return commandsMap.get(query);
        } else {
            return null;
        }
    }

    /**
     * Kontroluje, zda zadaný řetězec je přípustný příkaz.
     *
     * @param retezec Řetězec, který se má otestovat, zda je přípustný příkaz
     * @return Vrací hodnotu true, pokud je zadaný
     * řetězec přípustný příkaz
     */
    public boolean isValidCommand(String retezec) {
        return commandsMap.containsKey(retezec);
    }

    /**
     * Vrací seznam přípustných příkazů, jednotlivé příkazy jsou odděleny mezerou.
     *
     * @return Řetězec, který obsahuje seznam přípustných příkazů
     */
    public String getCommandsName() {
        String seznam = "";
        for (String slovoPrikazu : commandsMap.keySet()) {
            seznam += slovoPrikazu + " ";
        }
        return seznam;
    }

}

