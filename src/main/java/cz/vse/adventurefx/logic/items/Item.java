package cz.vse.adventurefx.logic.items;

import cz.vse.adventurefx.logic.commands.UseResult;
import cz.vse.adventurefx.logic.entities.Prop;

import java.util.Objects;

public class Item {
    private String name;
    private String description;
    private int volume;
    private boolean lootable;

    public Item(String name, String description, int volume) {
        this.name = name;
        this.description = description;
        this.volume = volume;
        this.lootable = volume >= 0;
    }

    public String getName() {
        return this.name;
    }

    public int getVolume() {
        return this.volume;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isLootable() {
        return this.lootable;
    }

    /**
     * Metoda sloužící k použití itemu na nějaký prop
     *
     * @param prop Prop, na který má být použit item
     * @return Vrací výsledek použití itemu UseResult, který vrátí Prop
     */
    public UseResult useItem(Prop prop) {
        return prop.applyItem(this);

    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return volume == item.volume &&
                lootable == item.lootable &&
                Objects.equals(name, item.name) &&
                Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, volume, lootable);
    }
}
