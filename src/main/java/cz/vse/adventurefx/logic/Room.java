package cz.vse.adventurefx.logic;

import cz.vse.adventurefx.logic.entities.Prop;
import cz.vse.adventurefx.logic.entities.Obstacle;
import cz.vse.adventurefx.logic.items.Item;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Trida Prostor - popisuje jednotlivé prostory (místnosti) hry
 * <p>
 * Tato třída je součástí jednoduché textové hry.
 * <p>
 * "Prostor" reprezentuje jedno místo (místnost, prostor, ..) ve scénáři hry.
 * Prostor může mít sousední prostory připojené přes východy. Pro každý východ
 * si prostor ukládá odkaz na sousedící prostor.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 * @version pro školní rok 2016/2017
 */
public class Room {

    private String name;
    private String description;
    private Set<Room> exits = new HashSet<>();
    private Map<String, Obstacle> obstacles = new HashMap<String, Obstacle>();
    private Map<String, Prop> props = new HashMap<>();
    private Map<String, Item> items = new HashMap<String, Item>();


    /**
     * Vytvoření prostoru se zadaným popisem, např. "kuchyň", "hala", "trávník
     * před domem"
     *
     * @param name        nazev prostoru, jednoznačný identifikátor, jedno slovo nebo
     *                    víceslovný název bez mezer.
     * @param description Popis prostoru.
     */
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Definuje východ z prostoru (sousední/vedlejsi prostor). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední prostor uveden
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední místnosti).
     * Druhé zadání stejného prostoru tiše přepíše předchozí zadání (neobjeví se
     * žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param vedlejsi prostor, který sousedi s aktualnim prostorem.
     */
    public void setExit(Room vedlejsi) {
        exits.add(vedlejsi);
    }

    public Map<String, Obstacle> getObstacles() {
        return this.obstacles;
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.put(obstacle.getName(), obstacle);
    }

    public void removeObstacle(String name) {
        obstacles.remove(name);
    }

    public Map<String, Prop> getProps() {
        return this.props;
    }

    public void addProp(Prop prop) {
        props.put(prop.getName(), prop);
    }

    public void removeProp(String name) {
        props.remove(name);
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.put(item.getName(), item);
    }

    public void removeItem(String name) {
        items.remove(name);
    }

    public Prop getEntityByName(String name) {
        if (obstacles.containsKey(name)) {
            return obstacles.get(name);
        }
        if (props.containsKey(name)) {
            return props.get(name);
        }
        return null;
    }

    public Obstacle getObstacleByName(String name) {
        return obstacles.get(name);
    }

    public Item getItemByName(String name) {
        return items.get(name);
    }

    /**
     * Metoda equals pro porovnání dvou prostorů. Překrývá se metoda equals ze
     * třídy Object. Dva prostory jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     * <p>
     * Bližší popis metody equals je u třídy Object.
     *
     * @param o object, který se má porovnávat s aktuálním
     * @return hodnotu true, pokud má zadaný prostor stejný název, jinak false
     */


    @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr 
        if (!(o instanceof Room)) {
            return false;    // pokud parametr není typu Prostor, vrátíme false
        }
        // přetypujeme parametr na typ Prostor 
        Room second = (Room) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů. 
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

        return (Objects.equals(this.name, second.name));
    }

    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     */
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = Objects.hashCode(this.name);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }


    /**
     * Vrací název prostoru (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return název prostoru
     */
    public String getName() {
        return name;
    }

    /**
     * Vrací "dlouhý" popis prostoru, který může vypadat následovně: Jsi v
     * mistnosti/prostoru vstupni hala budovy VSE na Jiznim meste. vychody:
     * chodba bufet ucebna
     *
     * @return Dlouhý popis prostoru
     */
    public String getLongDescription() {
        return "You are in room " + name + ": " + description + ".\n"
                + getExitsDescription();
    }

    /**
     * Vrací textový řetězec, který popisuje sousední východy, například:
     * "vychody: hala ".
     *
     * @return Popis východů - názvů sousedních prostorů
     */
    private String getExitsDescription() {
        String returnedText = "exits:";
        for (Room sousedni : exits) {
            returnedText += " " + sousedni.getName();
        }
        return returnedText;
    }

    /**
     * Vrací prostor, který sousedí s aktuálním prostorem a jehož název je zadán
     * jako parametr. Pokud prostor s udaným jménem nesousedí s aktuálním
     * prostorem, vrací se hodnota null.
     *
     * @param siblingName Jméno sousedního prostoru (východu)
     * @return Prostor, který se nachází za příslušným východem, nebo hodnota
     * null, pokud prostor zadaného jména není sousedem.
     */
    public Room getSiblingRoom(String siblingName) {
        List<Room> lookupRooms =
                exits.stream()
                        .filter(sousedni -> sousedni.getName().equals(siblingName))
                        .collect(Collectors.toList());
        if (lookupRooms.isEmpty()) {
            return null;
        } else {
            return lookupRooms.get(0);
        }
    }

    /**
     * Vrací kolekci obsahující prostory, se kterými tento prostor sousedí.
     * Takto získaný seznam sousedních prostor nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Prostor.
     *
     * @return Nemodifikovatelná kolekce prostorů (východů), se kterými tento
     * prostor sousedí.
     */
    public Collection<Room> getExits() {
        return Collections.unmodifiableCollection(exits);
    }

    public void addEntity(Prop prop) {

    }
}
