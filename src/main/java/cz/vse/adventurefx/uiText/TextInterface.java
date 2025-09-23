package cz.vse.adventurefx.uiText;


import java.io.*;
import java.util.Scanner;

import cz.vse.adventurefx.logic.IGame;

/**
 * Class TextoveRozhrani
 * <p>
 * Toto je uživatelského rozhraní aplikace Adventura
 * Tato třída vytváří instanci třídy Hra, která představuje logiku aplikace.
 * Čte vstup zadaný uživatelem a předává tento řetězec logice a vypisuje odpověď logiky na konzoli.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 * @version pro školní rok 2016/2017
 */

public class TextInterface {
    private IGame game;

    /**
     * Vytváří hru.
     */
    public TextInterface(IGame game) {
        this.game = game;
    }

    /**
     * Hlavní metoda hry. Vypíše úvodní text a pak opakuje čtení a zpracování
     * příkazu od hráče do konce hry (dokud metoda konecHry() z logiky nevrátí
     * hodnotu true). Nakonec vypíše text epilogu.
     */
    public void play() {
        System.out.println(game.getGreeting());
        // základní cyklus programu - opakovaně se čtou příkazy a poté
        // se provádějí do konce hry.

        //playFromFile("speedrun.txt");

        while (!game.isGameEnded()) {
            String line = readString();
            System.out.println(game.processCommand(line));
        }

        if (game.getGamePlan().getCurrentRoom().getName().equals("exit")) {
            System.out.println(game.getEpilogue());
        } else {
            System.out.println("Nice try, but try to finish the game next time!");
        }
        System.out.println("Thank you for playing!");
    }

    /**
     * Metoda přečte příkaz z příkazového řádku
     *
     * @return Vrací přečtený příkaz jako instanci třídy String
     */
    private String readString() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return scanner.nextLine();
    }

    public void playFromFile(String nazevSouboru) {
        try (
                // Dekorace (rozšiřování funkcionality) třídy FileReader třídou BufferedReader
                BufferedReader cteni = new BufferedReader(new FileReader(nazevSouboru));

                // Dekorace (rozšiřování funkcionality) třídy FileWriter třídou PrintWriter
                PrintWriter zapis = new PrintWriter(new FileWriter("vystup.txt"))
        ) {
            System.out.println(game.getGreeting());
            zapis.println(game.getGreeting());

            // Dokud je ve vstupním souboru další řádek textu, nebo hra neskončila, prováděj cyklus.
            for (String radek = cteni.readLine(); radek != null && !game.isGameEnded(); radek = cteni.readLine()) {
                // Vypiš příkaz do výstupu
                System.out.println("> " + radek);
                zapis.println("> " + radek);

                // Zpracuj příkaz
                String vystup = game.processCommand(radek);

                // Vypiš výsledek příkazu do výstupu
                System.out.println(vystup);
                zapis.println(vystup);
            }

//            System.out.println(game.getEpilogue());
//            zapis.println(game.getEpilogue());

        } catch (FileNotFoundException e) {
            File file = new File(nazevSouboru);
            System.out.println("Soubor nebyl nalezen!\nProhledávaná cesta byla: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Nelze hrát hru ze souboru, něco se pokazilo: " + e.getMessage());
        }
    }


}
