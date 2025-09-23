package cz.vse.adventurefx.logic.items;

import cz.vse.adventurefx.logic.Password;

/**
 * ItemFactory třída (DU), která pomáhá vytvářet Itemy
 * Využívá návrhový vzor Factory
 */
public class ItemFactory {

    /**
     * Enum (DU) který určuje, jaké instance Itemů jdou vytvořit
     */
    public enum ItemName {
        FUSE, CROWBAR, DYNAMITE, NEEDLE, KEY, SCREWDRIVER, MATCHES, ACID_BOTTLE,
        JOURNAL_PAGE1, JOURNAL_PAGE2, CATACOMBS_NOTE, SHOVEL, TAPE, BOTTLE, WIRE_CUTTERS, CLOTH, BROKEN_CABLE
    }

    /**
     * Factory metoda, která vytvoří item, podle zadaného itemName
     *
     * @param itemName název itemu, odpovídající výberu z Enum
     */
    public static Item createItem(ItemName itemName) {
        return switch (itemName) {
            case FUSE ->
                    new Item("fuse", "A small, cylindrical piece of metal with a glass window at the center. It looks like something that would restore power to the electrical systems, perhaps to the lights or machinery.", 2);
            case CROWBAR ->
                    new Item("crowbar", "A heavy-duty metal tool with a curved, flattened end. It looks worn from use, but still strong enough to pry open doors, crates, or anything that’s stubbornly stuck.", 4);
            case DYNAMITE ->
                    new Item("dynamite", "A bundle of explosive sticks wrapped in paper, with a fuse sticking out. The unmistakable smell of gunpowder lingers around it. A sure way to clear any large obstructions—if you’re brave enough to use it.", 3);
            case NEEDLE ->
                    new Item("needle", "A slender, slightly bent sewing needle. Still sharp enough to stitch something together—if you have thread or cloth.", 1);
            case KEY ->
                    new Item("key", "A tiny brass key, old and tarnished, but sturdy. It seems to fit a very specific lock — perhaps a drawer or cabinet that’s holding something valuable.", 1);
            case SCREWDRIVER -> new Item("screwdriver", "Flathead tool. Can remove panels or open crates.", 2);
            case MATCHES ->
                    new Item("matches", "A small box of matches, their tips dark and ready to strike. They’re useful for lighting candles, lamps, or anything that has to be lit.", 1);
            case ACID_BOTTLE ->
                    new Item("acid_bottle", "A carefully filled glass bottle, now holding a dangerous acid. Handle with caution.", 3);
            case JOURNAL_PAGE1 ->
                    new Item("journal_page1", "Day 3. The AI has started showing... anomalies. Small errors at first. Voices from the intercom. I thought I was imagining things until tonight. The terminal displayed a warning code: ____" + Password.password.charAt(4) + Password.password.charAt(5) + "__.", 0);
            case JOURNAL_PAGE2 ->
                    new Item("journal_page2", "Day 7. Security overridden itself. The doors lock us in at night. We pray it’s just glitches. My access code was forcibly changed to " + Password.password.charAt(0) + Password.password.charAt(1) + " without my input. I don't feel safe anymore.", 0);
            case CATACOMBS_NOTE ->
                    new Item("catacombs_note", "There is a second path. Buried long ago, for good reason. Should we ever return... don't. Last attempt at breaching the tomb failed. We lost contact after marker X" + Password.password.charAt(6) + Password.password.charAt(7), 0);
            case SHOVEL ->
                    new Item("shovel", "A rusty, heavy shovel with a worn wooden handle. The metal is chipped from years of digging, but it’s still sharp enough to dig through loose soil or break through debris.", 5);
            case TAPE ->
                    new Item("tape", "A roll of thick, sticky tape. It looks like it could fix broken cables, seams, or even secure loose items together. There’s an odd residue on the sticky side, making it seem like it has seen better days.", 2);
            case BOTTLE ->
                    new Item("bottle", "A small glass bottle, its edges smooth and clear. It’s empty now, but could easily hold liquid. It’s perfect for transporting the dangerous acid without spilling it.", 3);
            case WIRE_CUTTERS ->
                    new Item("wire_cutters", "A pair of wire cutters, with sharp, rusted edges. The handles are scuffed from years of use, but the tool is still functional and could easily snip through thick cables or wires.", 3);
            case CLOTH ->
                    new Item("cloth", "A torn piece of durable fabric, maybe from an old lab coat or curtain. Could be useful for patching or crafting.", 1);
            case BROKEN_CABLE ->
                    new Item("broken_cable", "A snapped power cable with frayed wires at both ends. Sparks occasionally flicker from the exposed metal.", 2);
        };
    }
}
