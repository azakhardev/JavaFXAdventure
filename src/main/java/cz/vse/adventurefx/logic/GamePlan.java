package cz.vse.adventurefx.logic;


import cz.vse.adventurefx.logic.commands.UseResult;
import cz.vse.adventurefx.logic.entities.Obstacle;
import cz.vse.adventurefx.logic.entities.Prop;
import cz.vse.adventurefx.logic.items.Item;
import cz.vse.adventurefx.logic.items.ItemFactory;
import cz.vse.adventurefx.main.GameChange;
import cz.vse.adventurefx.main.Observable;
import cz.vse.adventurefx.main.Observer;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.*;
import java.util.function.Function;

/**
 * Class HerniPlan - třída představující mapu a stav adventury.
 * <p>
 * Tato třída inicializuje prvky ze kterých se hra skládá:
 * vytváří všechny prostory,
 * propojuje je vzájemně pomocí východů
 * a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 * @author Artem Zacharčenko
 * @version pro školní rok 2024/2025
 */
public class GamePlan implements Observable {

    private Map<GameChange, Set<Observer>> observersList = new HashMap<>();

    private Room currentRoom;

    private Integer collectedNotes = 0;
    /**
     * Konstruktor, který při inicializaci spustí funkci pro nastavení místností.
     */
    public GamePlan() {
        setupRooms();
        for (GameChange gameChange : GameChange.values()) {
            observersList.put(gameChange, new HashSet<>());
        }
    }

    /**
     * Příprava místností, jejich vzájemné propojení a naplnění místností itemy a objekty.
     */
    private void setupRooms() {
        // vytvářejí se jednotlivé prostory
        Room barrack = new Room("barrack", "Your starting point. A simple room with beds and lockers once used by the facility’s staff.");
        Room kitchen = new Room("kitchen", "Dusty counters, rusted utensils, and the smell of something long forgotten.");
        Room storage = new Room("storage", "Old boxes, broken crates, and cobwebs. Maybe there’s still something useful left.");
        Room office = new Room("office", "Scattered papers and flickering lights. Someone left in a hurry.");
        Room hallway = new Room("hallway", "A dim corridor connecting various parts of the facility. Echoes make you feel uneasy.");
        Room greenhouse = new Room("greenhouse", "Overgrown plants have taken control. Nature is reclaiming its space.");
        Room engineRoom = new Room("engine_room", "Heavy machinery and tangled wires. Something here is still humming");
        Room serverRoom = new Room("server_room", "Cold and silent. Racks of old servers blink with a strange rhythm.");
        Room administration = new Room("administration", "A central control station with a blinking console. Perhaps you can access security here.");
        Room outpost = new Room("outpost", "An elevated station with a clear view of the surroundings. Once used to keep things in... or out.");
        Room catacombs = new Room("catacombs", "Dark tunnels beneath the facility. The air is thick, and the silence is deafening.");
        Room shaft = new Room("shaft", "A deep vertical tunnel with a broken lift. Getting down won’t be easy.");
        Room laboratory = new Room("lab", "Beakers, notes, and strange equipment. Experiments were conducted here... questionable ones.");
        Room armory = new Room("armory", " Locked cases and weapon racks. It's unfortunate that you don't have keys for them...");
        Room exitRoom = new Room("exit", "You are at exit - type end to end the game and exit the facility.");

        //Vytváří se itemy
        Item fuse = ItemFactory.createItem(ItemFactory.ItemName.FUSE);
        Item crowbar = ItemFactory.createItem(ItemFactory.ItemName.CROWBAR);
        Item dynamite = ItemFactory.createItem(ItemFactory.ItemName.DYNAMITE);
        Item needle = ItemFactory.createItem(ItemFactory.ItemName.NEEDLE);
        Item smallKey = ItemFactory.createItem(ItemFactory.ItemName.KEY);
        Item screwdriver = ItemFactory.createItem(ItemFactory.ItemName.SCREWDRIVER);
        Item matches = ItemFactory.createItem(ItemFactory.ItemName.MATCHES);
        Item acidBottle = ItemFactory.createItem(ItemFactory.ItemName.ACID_BOTTLE);
        Item journal1 = ItemFactory.createItem(ItemFactory.ItemName.JOURNAL_PAGE1);
        Item journal2 = ItemFactory.createItem(ItemFactory.ItemName.JOURNAL_PAGE2);
        Item catacombsNote = ItemFactory.createItem(ItemFactory.ItemName.CATACOMBS_NOTE);
        Item shovel = ItemFactory.createItem(ItemFactory.ItemName.SHOVEL);
        Item tape = ItemFactory.createItem(ItemFactory.ItemName.TAPE);
        Item bottle = ItemFactory.createItem(ItemFactory.ItemName.BOTTLE);
        Item cutters = ItemFactory.createItem(ItemFactory.ItemName.WIRE_CUTTERS);
        Item cloth = ItemFactory.createItem(ItemFactory.ItemName.CLOTH);
        Item cable = ItemFactory.createItem(ItemFactory.ItemName.BROKEN_CABLE);

        Function<Item, UseResult> used = (item) -> new UseResult("You have already used an item on this object", false);

        //Vytváří se herní objekty
        Prop toilet = new Prop("toilet_bowl", "Disgusting, but suspiciously clean inside", () -> "You reach in (gross), but you didn't find anything.");
        Prop mirror = new Prop("mirror", "A wall mirror, cracked down the middle, reflecting a distorted version of you.", () -> "You stare at your warped reflection.");
        Prop locker = new Prop("locker", "A dented metal locker, its paint peeling and a nameplate long scratched off.", () -> "You need some sort of key to open it.");
        locker.setOnUse((item) -> {
            if (item.getName().equals("key")) {
                barrack.addItem(journal1);
                locker.setOnInteract(() -> "The locker that you have unlocked a while ago.");
                locker.setOnUse(used);
                return new UseResult("You've succesfully opened a locker", false);
            }
            return new UseResult("This doesn't fit into the lock.", false);
        });

        Prop drawer = new Prop("oven_drawer", "A filthy oven drawer jammed halfway, something rattling inside.", () -> {
            kitchen.addItem(matches);
            return "You yank it open with effort. You see matches inside.";
        });
        Prop pans = new Prop("pans", "Rusted metal pans dangle from hooks, swaying gently despite no wind.", () -> "You accidentally knock one. The clang echoes.");
        Prop fridge = new Prop("fridge", "A bulky fridge covered in mold, humming faintly with a sickly odor seeping out.", () -> "You peek inside and quickly regret it.");

        Prop box = new Prop("box", " A dusty cardboard box sitting just out of reach on a steel shelf.");
        box.setOnInteract(() -> {
            storage.addItem(screwdriver);
            storage.removeProp(box.getName());
            return "There is something inside the box.";
        });

        Prop crate = new Prop("stamps_crate", "A wooden crate marked with faded facility codes.", () -> "You try to pry it open but it's sealed tight.");
        Prop tools = new Prop("tools_pile", "A pile of rusted wrenches, bolts, and screws scattered across the floor.", () -> "You sift through them, but most are unusable.");

        Prop corkboard = new Prop("corkboard", "A bulletin board with pinned papers and faded reminders.", () -> "You flip through the notes.");
        Prop fillingCabinet = new Prop("filling_cabinet", "A tall cabinet with warped drawers stuffed with papers, some dated decades ago.", () -> "You yank one drawer and papers spill everywhere.");
        Prop skeleton = new Prop("skeleton", "A decayed skeleton slumped on a chair, still dressed in a tattered leather jacket. The fabric is torn, but it looks like something's still intact.");
        skeleton.setOnInteract(() -> {
            office.addItem(cloth);
            skeleton.setOnInteract(() -> "It's good that he has this jacket.");
            return "You tear off a piece of the jacket’s fabric—it comes away with a soft rip.";
        });

        Prop panelCables = new Prop("panel_cables", "Exposed cables that are sticking from the wall, sparking from time to time.", item -> {
            if (item.getName().equals("wire_cutters")) {
                hallway.addItem(cable);
                return new UseResult("You cut the cables with a quick cut.", false);
            }
            return new UseResult("You can't cut the wires with that thing.", false);
        }, () -> "You have to cut them with something");
        Prop hallwayPanel = new Prop("loose_panel", "A rectangular section of wall with scuffed edges and mismatched screws. It looks like someone tried to put it back in a hurry.", () -> "If only you cold open it with something.");
        hallwayPanel.setOnUse(item -> {
            if (item.getName().equals("screwdriver")) {
                hallway.addProp(panelCables);
                hallway.removeProp(hallwayPanel.getName());
                return new UseResult("You unscrew the panel with effort, revealing the cables.", false);
            }
            return new UseResult("What did you think was going to happen?", false);
        });
        Prop closet = new Prop("utility_door", "A locked door with a faded label. Smells faintly of chemicals.", () -> "It’s sealed tight—needs a key or override.");

        Prop irrigator = new Prop("irrigation_panel", "A rusted water control unit with blinking lights and jammed valves.", () -> "You flip a few switches. Nothing responds.");
        Prop soil = new Prop("loose_soil", "A patch of soil darker and softer than the rest, as if something was recently buried there. The nearby roots are parted slightly, as if disturbed.", () -> "Something is shining in the soil.");
        soil.setOnUse((item) -> {
            if (item.getName().equals("shovel")) {
                greenhouse.addItem(bottle);
                soil.setOnInteract(() -> "The soil lies scattered, a shallow pit where something once rested.");
                return new UseResult("You've dig up a bottle", false);
            }
            return new UseResult("You can't use this to dig up a shiny object in a soil", false);
        });
        Prop pot = new Prop("hanging_pot", "A cracked ceramic pot suspended from above by a fraying wire.");
        pot.setOnInteract(() -> {
            greenhouse.removeProp(pot.getName());
            return "You tug it down gently—it falls and shatters.";
        });

        Prop console = new Prop("control_console", " A complex panel of buttons and dials, most worn beyond recognition.", () -> "You press a few—one flickers weakly.");
        Prop core = new Prop("power_core", "A large metal unit that once powered part of the facility. It's humming faintly.", () -> "You have a feeling that it still emits radiation...");
        Prop openedPanel = new Prop("opened_panel", "There are two contacts that have to be connected somehow", () -> "If only you had a sheathed cable...");
        Prop serverRack = new Prop("server", "A functional server, it can contain a lot of information.", () -> "Research Log: Facility X\n" +
                "Clearance Level: REDACTED\n" +
                "Day 189 — Subject #5 no longer responds to auditory input. However, the neural scans show increased activity during blackout phases. Attempts to sedate it result in violent resistance from connected systems.\n" +
                "Day 193 — Something’s wrong. The security system rebooted without command. Doors are locking randomly. We’ve lost containment in lower labs. Subject #5’s heartbeat was detected in multiple wings simultaneously.\n" +
                "Day 194 — We tried to wipe the servers. It didn’t work. The data… it grew back. Not copied. Not restored. Grew.\n" +
                "Day 195 — I saw my own face in the live feed. But I haven’t left the server room for three days.\n" +
                "There’s something inside the system. Or maybe we’re already inside it.\n" +
                "If anyone reads this — do not plug in.\n" +
                "Remember the sequence:\n" +
                "__" + Password.password.charAt(2) + Password.password.charAt(3) + "____");
        openedPanel.setOnUse((item -> {
            if (item.getName().equals("sheathed_cable")) {
                serverRoom.addProp(serverRack);
                openedPanel.setOnInteract(() -> "The connections hum quietly. The panel feels warm to the touch — operational.");
                openedPanel.setOnUse(used);
                return new UseResult("By connecting the contacts, you powered the server room.", true);
            }
            return new UseResult("It's better to stop experimenting with electricity.", false);
        }));
        Prop enginePanel = new Prop("wall_panel", "A removable side panel secured with old screws. Faint labels hint at internal circuitry.");
        enginePanel.setOnUse(item -> {
            if (item.getName().equals("screwdriver")) {
                engineRoom.addProp(openedPanel);
                engineRoom.removeItem(enginePanel.getName());
                return new UseResult("You opened the panel with your screwdriver. What a handy tool!", false);
            }
            return new UseResult("This won't help", false);
        });

        Prop radio = new Prop("radio_unit", "A bulky, military-grade radio, knobs worn smooth by anxious hands.", () -> "You twist the dial through static until a voice crackles in: \"If you're hearing this, it’s already too late.\"");
        Prop toolbox = new Prop("toolbox", "A dented red toolbox, half-buried in snowdrift seeping through the broken windo", () -> {
            outpost.addItem(tape);
            return "You open it. Inside lies a tape , untouched for years.";
        });
        Prop walkie = new Prop("walkie-talkie", "One half of a pair. A name is carved crudely into its side — 'Milo'.", () -> "You press the button. Silence... then static... then a voice: \"...don’t go to the lower levels...\"");

        Prop wall = new Prop("collapsed_wall", "Dust and gravel cover what looks like a tunnel behind the rubble.", () -> "Looks like the tunnel was blocked for a reason...");
        Prop tablet = new Prop("stone_tablet", "Carved symbols pulse faintly when touched.", () -> "You feel a shiver down your spine. The stone is warm.");
        Prop tombSkeleton = new Prop("skeleton", "Still propped against the wall. Something clenched in its fist.", () -> "Its fingers stiff around a corroded, rusted pistol. The weapon looks ancient and unusable.");

        Prop hatch = new Prop("emergency_hatch", "Barely visible behind loose wiring. Could lead somewhere deeper...");
        hatch.setOnInteract(() -> {
            shaft.addItem(crowbar);
            hatch.setOnInteract(() -> "An opened hatch, where you've found crowbar.");
            return "You move aside the loose wiring and find a hidden compartment. Inside, a crowbar lies covered in dust.";
        });
        Prop ladder = new Prop("loose_ladder", "A broken, unstable ladder missing an entire leg. Too risky to use in its current state.", () -> "The ladder creaks dangerously. It's beyond repair — you'd better find another way.");
        Prop cart = new Prop("mine_cart", "An old, rusted mine cart left abandoned. One wheel is broken, and a faint metallic smell lingers around it.", () -> " It groans under the slightest touch but won't budge. Whatever it carried is long gone.");

        Prop samples = new Prop("sample_storage", "A line of dusty shelves filled with broken vials and shattered glass. One cracked container still faintly glows.", () -> "You reach out carefully. As your fingers graze the surface, the fragile vial crumbles into powder. Nothing usable remains.");
        Prop chemTank = new Prop("chemical_tank", "Acid slowly leaks onto the floor, hissing and burning.", () -> "You could extract a little bit of the acid with some kind of vial.");
        chemTank.setOnUse((item) -> {
            if (item.getName().equals("bottle")) {
                laboratory.addItem(acidBottle);
                return new UseResult("You've filled the bottle with acid and left in on the counter", true);
            }
            return new UseResult("You can't pour the acid into this.", false);
        });
        Prop observation = new Prop("observation_window", "Looks into a sealed chamber. Something moved. Or did it?", () -> "The lights flicker when you press your hand to the glass.");

        Prop weaponCase = new Prop("weapon_case", "Steel-plated and secured with a biometric lock.", () -> " It would be better to have a weapon. Hopefully, I'll find a biometric card somewhere around here...");
        Prop ammoCrate = new Prop("ammo_crate", "Heavy and dusty. Faint rattle inside.");
        ammoCrate.setOnInteract(() -> {
            armory.addItem(new Item("pistol_ammo", "Could be useful for defending against threats.", 2));
            armory.addItem(new Item("shotgun_ammo", "Could be useful for defending against threats.", 3));
            ammoCrate.setOnInteract(() -> "You've already opened the crate, there is nothing more to do...");
            return "You open it and find several intact rounds.";
        });

        Prop keypad = new Prop("keypad", "A grimy keypad, scratched and worn from years of use. Faint fingerprints linger on the most pressed buttons.", () -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Keypad Access");
            dialog.setHeaderText("Enter the 8-digit password:");
            dialog.setContentText("Password:");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                String password = result.get().trim();

                if (password.equals(Password.password)) {
                    administration.removeObstacle("vault_door");
                    return "The keypad beeps and the door unlocks!";
                } else {
                    return "Incorrect password. Try again.";
                }
            } else {
                return "You decided not to enter a password.";
            }
        }

        );
        Prop folders = new Prop("folder_stack", "A bundle of dusty folders, stamped repeatedly with a faded 'TOP SECRET' mark.", () -> "You sift through the brittle papers — fragments about Facility X surface, but the text is too degraded to fully decipher.");

        Obstacle fuseBox = new Obstacle("fuse_box",
                "An old metal fuse box mounted to the wall. The cover hangs slightly ajar, and inside, one of the fuse slots is empty. Without it, the corridor remains dark and lifeless.",
                (item) -> {
                    if (!item.getName().equals("fuse")) {
                        return new UseResult("You can't use this item on the fuse box.", false);
                    }
                    kitchen.getObstacles().remove("fuse_box");
                    return new UseResult("You flipped the fuse. The hallway is now lit.", true);
                }, hallway);

        Obstacle stuckDoor = new Obstacle("stuck_door", "The door is jammed and won't budge. Maybe a crowbar could help.", (item) -> {
            if (!item.getName().equals("crowbar")) {
                return new UseResult("You can't use this item to pry the door open.", false);
            }
            greenhouse.getObstacles().remove("stuck_door");
            return new UseResult("With a sharp creak and a burst of force, the crowbar pries the door loose. The way ahead is now open, though the hinges will never be the same.", false);
        }, engineRoom);

        Obstacle overgrownPlants = new Obstacle("overgrown_plants", "Thick, overgrown plants block your way. They're too dense to move through.", (item) -> {
            if (!item.getName().equals("acid_bottle")) {
                return new UseResult("You can't use this item to burn the thick plants.", false);
            }
            outpost.getObstacles().remove("overgrown_plants");
            return new UseResult("You pour the corrosive mix onto the thick vines. They hiss and writhe before dissolving into a foul-smelling sludge. The path clears slowly, revealing the corridor beyond.", true);
        }, catacombs);

        Obstacle fallenRocks = new Obstacle("fallen_rocks", "A pile of large fallen rocks is blocking the path to the shafts.",
                (item) -> {
                    if (!item.getName().equals("primed_explosive")) {
                        return new UseResult("You can't use this item on pile of rocks... It won't do much to it...", false);
                    }
                    outpost.getObstacles().remove("fallen_rocks");
                    return new UseResult("You strike a match and light the fuse. You step back. A deep rumble follows... BOOM. The passage is clear.", true);
                }, shaft);
        Obstacle vaultDoor = new Obstacle("vault_door", "A massive vault door bars your way. There's a numeric keypad next to it.", (item) -> new UseResult("You can't use this item on the vault door.", false)
                , exitRoom);

        // přiřazují se průchody mezi prostory (sousedící prostory)
        barrack.setExit(kitchen);
        barrack.addProp(toilet);
        barrack.addProp(mirror);
        barrack.addProp(locker);
        barrack.addItem(needle);

        kitchen.setExit(barrack);
        kitchen.addItem(smallKey);
        kitchen.addProp(pans);
        kitchen.addProp(drawer);
        kitchen.addProp(fridge);
        kitchen.setExit(storage);
        kitchen.setExit(hallway);
        kitchen.addObstacle(fuseBox);

        storage.setExit(kitchen);
        storage.setExit(office);
        storage.addItem(fuse);
        storage.addProp(box);
        storage.addProp(crate);
        storage.addProp(tools);

        office.setExit(storage);
        office.addProp(fillingCabinet);
        office.addProp(corkboard);
        office.addProp(skeleton);

        hallway.setExit(kitchen);
        hallway.setExit(greenhouse);
        hallway.addProp(hallwayPanel);
        hallway.addProp(closet);

        greenhouse.setExit(hallway);
        greenhouse.setExit(engineRoom);
        greenhouse.setExit(outpost);
        greenhouse.addObstacle(stuckDoor);
        greenhouse.addProp(irrigator);
        greenhouse.addProp(pot);
        greenhouse.addProp(soil);
        greenhouse.addItem(shovel);

        engineRoom.setExit(greenhouse);
        engineRoom.setExit(serverRoom);
        engineRoom.addItem(cutters);
        engineRoom.addProp(enginePanel);
        engineRoom.addProp(console);
        engineRoom.addProp(core);

        serverRoom.setExit(engineRoom);
        serverRoom.setExit(administration);

        administration.setExit(serverRoom);
        administration.setExit(exitRoom);
        administration.addObstacle(vaultDoor);
        administration.addProp(keypad);
        administration.addProp(folders);

        outpost.setExit(greenhouse);
        outpost.setExit(catacombs);
        outpost.setExit(shaft);
        outpost.setExit(laboratory);
        outpost.addObstacle(overgrownPlants);
        outpost.addObstacle(fallenRocks);
        outpost.addProp(walkie);
        outpost.addProp(radio);
        outpost.addProp(toolbox);

        catacombs.setExit(outpost);
        catacombs.addItem(catacombsNote);
        catacombs.addProp(tombSkeleton);
        catacombs.addProp(tablet);
        catacombs.addProp(wall);

        shaft.setExit(outpost);
        shaft.addProp(hatch);
        shaft.addProp(cart);
        shaft.addProp(ladder);

        laboratory.setExit(outpost);
        laboratory.setExit(armory);
        laboratory.addItem(journal2);
        laboratory.addProp(chemTank);
        laboratory.addProp(samples);
        laboratory.addProp(observation);

        armory.setExit(laboratory);
        armory.addProp(ammoCrate);
        armory.addProp(weaponCase);
        armory.addItem(dynamite);

        currentRoom = barrack;
    }

    /**
     * Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     * @return aktuální prostor
     */

    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory
     *
     * @param room nový aktuální prostor
     */
    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
        notifyObservers(GameChange.ROOM_CHANGE);
    }

    @Override
    public void addObserver(GameChange gameChange, Observer observer) {
        observersList.get(gameChange).add(observer);
    }

    public void notifyObservers(GameChange gameChange) {
        observersList.get(gameChange).forEach(Observer::update);
    }
}
