package cz.vse.adventurefx.logic.entities;

import cz.vse.adventurefx.logic.items.Backpack;

/**
 * Singleton třída Player (DU) – reprezentuje hráče ve hře.
 * Používá návrhový vzor Singleton – existuje pouze jedna instance hráče po celou dobu běhu hry.
 * Původně byla přidáná pro případné budoucí rozšíření - např. zdraví, brnění atd.
 */
public class Player {

    private static Player instance;

    private Backpack backpack;

    /**
     * Soukromý konstruktor – brání přímému vytvoření instance mimo tuto třídu
     * (část návrhového vzoru Singleton).
     */
    private Player(Backpack backpack) {
        this.backpack = backpack;
    }

    /**
     * Factory metoda – vytvoří nového hráče s daným batohem (pouze pokud ještě neexistuje).
     * Pokud instance už existuje, vrátí ji bez změny batohu.
     */
    public static Player createInstance(Backpack backpack) {
        if (instance == null) {
            instance = new Player(backpack);
        }
        return instance;
    }

    /**
     * Factory metoda – vrátí aktuální instanci hráče, nebo null, pokud zatím nebyla vytvořena.
     */
    public static Player getInstance() {
        return instance;
    }

    /**
     * Factory metoda – resetuje singleton instanci hráče.
     * Užitečné např. při restartu hry.
     */
    public static void resetInstance() {
        instance = null;
    }

    /**
     * Získání batohu hráče.
     */
    public Backpack getBackpack() {
        return this.backpack;
    }

    /**
     * Nastavení nového batohu hráči (např. při upgradu).
     */
    public void setBackpack(Backpack backpack) {
        this.backpack = backpack;
    }

}
