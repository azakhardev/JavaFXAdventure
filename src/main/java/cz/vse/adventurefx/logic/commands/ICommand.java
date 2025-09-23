package cz.vse.adventurefx.logic.commands;

/**
 * Třída implementující toto rozhraní bude ve hře zpracovávat jeden konkrétní příkaz.
 * Toto rozhraní je součástí jednoduché textové hry.
 *
 * @author Jarmila Pavlickova
 * @version pro školní rok 2016/2017
 */
public interface ICommand {

    /**
     * Metoda pro provedení příkazu ve hře.
     * Počet parametrů je závislý na konkrétním příkazu,
     * např. příkazy konec a napoveda nemají params
     * příkazy jdi, seber, polož mají jeden parametr
     * příkaz pouzij může mít dva params.
     *
     * @param params počet parametrů závisí na konkrétním příkazu.
     */
    public String executeCommand(String... params);

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *
     * @return nazev prikazu
     */
    public String getName();

}
