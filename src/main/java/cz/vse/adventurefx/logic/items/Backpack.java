package cz.vse.adventurefx.logic.items;

import cz.vse.adventurefx.logic.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Třída "Backpack" slouží k uchování věcí do inventáře,
 * je obsažena ve třídě Player.
 */
public class Backpack {
    private int capacity;
    private List<Item> items = new ArrayList<>();
    private Item selectedItem;

    /**
     * Konstruktor batohu s danou kapacitou.
     *
     * @param capacity maximální kapacita batohu
     */
    public Backpack(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Zvýší kapacitu batohu o zadanou hodnotu.
     *
     * @param capacity kolik kapacity přidat
     * @return zpráva pro hráče s novou kapacitou
     */
    public String addCapacity(int capacity) {
        this.capacity += capacity;
        return "Now you have more capacity in your backpack: " + this.capacity;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public List<Item> getItems() {
        return this.items;
    }

    /**
     * Najde předmět v batohu podle jména.
     *
     * @param name název hledaného předmětu
     * @return nalezený předmět nebo null, pokud neexistuje
     */
    public Item getItemWithName(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public Item getSelectedItem() {
        return this.selectedItem;
    }

    public void setSelectedItem(Item item) {
        this.selectedItem = item;
    }
    /**
     * Pokusí se uložit předmět do batohu (a zároveň jej odebere z místnosti).
     *
     * @param item předmět, který se má uložit
     * @param room místnost, odkud je předmět brán
     * @return uložený předmět
     * @throws Exception pokud není dostatek místa nebo předmět není lootable
     */
    public Item storeItem(Item item, Room room) throws Exception {
        int itemVolume = item.getVolume();
        int freeSpace = this.capacity - this.getUsedCapacity();

        if ((itemVolume <= freeSpace) && item.isLootable()) {
            items.add(item);
            room.getItems().remove(item.getName());
            return item;
        }

        throw new Exception("You don't have enough space in your inventory for this item. You need at least " + itemVolume + " space.");
    }

    /**
     * Pokusí se uložit předmět do batohu bez interakce s místností.
     *
     * @param item předmět, který se má uložit
     * @return vložený předmět (i pokud se nevešel)
     */
    public Item storeItem(Item item) {
        int itemVolume = item.getVolume();
        int freeSpace = this.capacity - this.getUsedCapacity();

        if ((itemVolume <= freeSpace) && item.isLootable()) {
            items.add(item);
        }

        return item;
    }

    /**
     * Zahodí předmět – odstraní ho z batohu a přidá do místnosti.
     *
     * @param item předmět, který se má zahodit
     * @param room místnost, do které se má zahodit
     * @return zpráva pro hráče
     */
    public String dropItem(Item item, Room room) {
        room.addItem(item);
        deleteItem(item);
        return "You've dropped " + item.getName() + " in " + room.getName() + " room.";
    }

    /**
     * Odstraní předmět z batohu.
     *
     * @param item předmět k odstranění
     * @return true, pokud v batohu stále zůstává (což je zvláštní), false pokud byl správně odebrán
     */
    public boolean deleteItem(Item item) {
        items.remove(item);
        return items.contains(item);
    }

    /**
     * Spočítá aktuální zaplněnou kapacitu batohu.
     *
     * @return zaplněná kapacita
     */
    public int getUsedCapacity() {
        int usedCapacity = 0;

        for (Item item : items) {
            usedCapacity += item.getVolume();
        }

        return usedCapacity;
    }

}
