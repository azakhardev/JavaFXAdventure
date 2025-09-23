package cz.vse.adventurefx.logic.entities;

import cz.vse.adventurefx.logic.commands.UseResult;
import cz.vse.adventurefx.logic.items.Item;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Třída Prop reprezentuje interaktivní objekt (např. nábytek, zařízení)
 * v místnosti, se kterým může hráč interagovat nebo na něj použít předmět.
 */
public class Prop {
    private String name;

    private String description;

    /**
     * Funkce, která definuje, co se stane, když hráč použije určitý předmět na tento objekt.
     * Výchozí implementace vrací zprávu, že předmět nelze použít.
     */
    protected Function<Item, UseResult> onUse = (item) -> {
        return new UseResult("You can't use " + item.getName() + " on this object!", false);
    };

    /**
     * Funkce, která definuje, co se stane, když hráč s objektem interaguje.
     * Výchozí chování je vrácení popisu objektu.
     */
    protected Supplier<String> onInteract = this::getDescription;


    /**
     * Konstruktor umožňující nastavit název, popis, reakci na použití předmětu a reakci na interakci.
     */
    public Prop(String name, String descripton, Function<Item, UseResult> onUse, Supplier<String> onInteract) {
        this.name = name;
        this.description = descripton;
        this.onUse = onUse;
        this.onInteract = onInteract;
    }

    /**
     * Konstruktor umožňující nastavit název, popis a reakci na použití předmětu.
     * Reakce na interakci je výchozí – vrácení popisu.
     */
    public Prop(String name, String descripton, Function<Item, UseResult> onUse) {
        this.name = name;
        this.description = descripton;
        this.onUse = onUse;
    }

    /**
     * Konstruktor umožňující nastavit název, popis a reakci na interakci.
     * Použití předmětu je výchozí – neúspěšné.
     */
    public Prop(String name, String descripton, Supplier<String> onInteract) {
        this.name = name;
        this.description = descripton;
        this.onInteract = onInteract;
    }

    /**
     * Konstruktor pro základní objekt bez vlastní logiky.
     */
    public Prop(String name, String descripton) {
        this.name = name;
        this.description = descripton;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOnUse(Function<Item, UseResult> onUse) {
        this.onUse = onUse;
    }

    public void setOnInteract(Supplier<String> onInteract) {
        this.onInteract = onInteract;
    }


    /**
     * Zavolá logiku interakce s objektem a vrátí zprávu pro hráče.
     */
    public String interact() {
        return onInteract.get();
    }

    /**
     * Zavolá logiku použití předmětu na tento objekt a vrátí výsledek.
     */
    public UseResult applyItem(Item item) {
        return onUse.apply(item);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prop)) return false;
        Prop prop = (Prop) o;
        return Objects.equals(name, prop.name) &&
                Objects.equals(description, prop.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
