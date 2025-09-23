package cz.vse.adventurefx.logic.entities;

import cz.vse.adventurefx.logic.Room;
import cz.vse.adventurefx.logic.commands.UseResult;
import cz.vse.adventurefx.logic.items.Item;

import java.util.function.Function;

/**
 * Třída Obstacle představuje překážku v místnosti, která brání přístupu do jiné místnosti.
 * Překážku lze odstranit použitím vhodného předmětu.
 */
public class Obstacle extends Prop {
    private Room blockedRoom;

    /**
     * Konstruktor vytvoří překážku s názvem, popisem, reakcí na použití předmětu a odkazem na blokovanou místnost.
     *
     * @param name       Název překážky.
     * @param descripton Popis překážky.
     * @param onUse      Funkce definující, co se stane při použití předmětu na tuto překážku.
     * @param room       Místnost, do které je zablokovaný vstup.
     */
    public Obstacle(String name, String descripton, Function<Item, UseResult> onUse, Room room) {
        super(name, descripton, onUse);
        this.blockedRoom = room;
    }

    /**
     * Vrátí místnost, kterou překážka blokuje.
     * Pokud byla překážka odstraněna, vrací null.
     */
    public Room getBlockedRoom() {
        return blockedRoom;
    }

    /**
     * Odstraní překážku — nastaví blokovanou místnost na null.
     * Typicky se volá při úspěšném použití předmětu.
     */
    public void clearPath() {
        this.blockedRoom = null;
    }
}
