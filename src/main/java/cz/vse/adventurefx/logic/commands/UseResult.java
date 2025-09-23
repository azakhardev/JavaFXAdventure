package cz.vse.adventurefx.logic.commands;

/**
 * Třída UseResult reprezentuje výsledek použití předmětu na entitu (např. objekt v místnosti).
 * Uchovává zprávu, kterou má hra hráči zobrazit, a informaci o tom,
 * zda má být předmět po použití odstraněn z batohu.
 */

public class UseResult {
    private final String message;
    private final boolean shouldRemoveItem;

    /**
     * Konstruktor nastaví zprávu a příznak pro odstranění předmětu.
     *
     * @param message          zpráva o výsledku použití předmětu
     * @param shouldRemoveItem true pokud se má předmět po použití odstranit, jinak false
     */
    public UseResult(String message, boolean shouldRemoveItem) {
        this.message = message;
        this.shouldRemoveItem = shouldRemoveItem;
    }

    public String getMessage() {
        return message;
    }

    public boolean shouldRemoveItem() {
        return shouldRemoveItem;
    }
}