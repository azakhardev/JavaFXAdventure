package cz.vse.adventurefx.logic;

import cz.vse.adventurefx.logic.commands.CommandGo;
import cz.vse.adventurefx.logic.commands.CommandEnd;
import cz.vse.adventurefx.logic.commands.CommandsList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*******************************************************************************
 * Testovací třída SeznamPrikazuTest slouží ke komplexnímu otestování třídy  
 * SeznamPrikazu
 *
 * @author Luboš Pavlíček
 * @version pro školní rok 2016/2017
 */
public class CommandsListTest {
    private Game game;
    private CommandEnd commandEnd;
    private CommandGo commandGo;

    @BeforeEach
    public void setUp() {
        game = new Game();
        commandEnd = new CommandEnd(game);
        commandGo = new CommandGo(game.getGamePlan());
    }

    @Test
    public void testInsertCommand() {
        CommandsList seznPrikazu = new CommandsList();
        seznPrikazu.insertCommand(commandEnd);
        seznPrikazu.insertCommand(commandGo);
        assertEquals(commandEnd, seznPrikazu.returnCommand("end"));
        assertEquals(commandGo, seznPrikazu.returnCommand("go"));
        assertEquals(null, seznPrikazu.returnCommand("help"));
    }

    @Test
    public void testIsValidCommand() {
        CommandsList seznPrikazu = new CommandsList();
        seznPrikazu.insertCommand(commandEnd);
        seznPrikazu.insertCommand(commandGo);
        assertEquals(true, seznPrikazu.isValidCommand("end"));
        assertEquals(true, seznPrikazu.isValidCommand("go"));
        assertEquals(false, seznPrikazu.isValidCommand("help"));
        assertEquals(false, seznPrikazu.isValidCommand("End"));
    }

    @Test
    public void testCommandsNames() {
        CommandsList seznPrikazu = new CommandsList();
        seznPrikazu.insertCommand(commandEnd);
        seznPrikazu.insertCommand(commandGo);
        String nazvy = seznPrikazu.getCommandsName();
        assertEquals(true, nazvy.contains("end"));
        assertEquals(true, nazvy.contains("go"));
        assertEquals(false, nazvy.contains("help"));
        assertEquals(false, nazvy.contains("End"));
    }

}
