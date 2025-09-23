package cz.vse.adventurefx.logic.commands;

/**
 * Třída PrikazNapoveda implementuje pro hru příkaz napoveda.
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author Jarmila Pavlickova, Luboš Pavlíček
 * @version pro školní rok 2016/2017
 */
public class CommandHelp implements ICommand {

    private static final String NAME = "help";
    private final CommandsList availableCommands;


    /**
     * Konstruktor třídy
     *
     * @param availableCommands seznam příkazů,
     *                          které je možné ve hře použít,
     *                          aby je nápověda mohla zobrazit uživateli.
     */
    public CommandHelp(CommandsList availableCommands) {
        this.availableCommands = availableCommands;
    }

    /**
     * Vrací základní nápovědu po zadání příkazu "napoveda". Nyní se vypisuje
     * vcelku primitivní zpráva a seznam dostupných příkazů.
     *
     * @return napoveda ke hre
     */
    @Override
    public String executeCommand(String... params) {
        return "Your task is to escape the bunker\n"
                + "You control the game trough the terminal with commands\n"
                + "\n"
                + "You can use these commands:\n"
                + availableCommands.getCommandsName();
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *
     * @ return nazev prikazu
     */
    @Override
    public String getName() {
        return NAME;
    }

}
