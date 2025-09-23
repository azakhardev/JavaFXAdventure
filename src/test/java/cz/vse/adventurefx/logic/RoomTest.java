package cz.vse.adventurefx.logic;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*******************************************************************************
 * Testovací třída ProstorTest slouží ke komplexnímu otestování
 * třídy Prostor
 *
 * @author Artem Zacharčenko
 * @version pro skolní rok 2024/2025
 */
public class RoomTest {
    //== Datové atributy (statické i instancí)======================================

    //== Konstruktory a tovární metody =============================================
    //-- Testovací třída vystačí s prázdným implicitním konstruktorem ----------

    //== Příprava a úklid přípravku ================================================

    //== Soukromé metody používané v testovacích metodách ==========================

    //== Vlastní testovací metody ==================================================

    /**
     * Testuje, zda jsou správně nastaveny průchody mezi prostory hry. Prostory
     * nemusí odpovídat vlastní hře,
     */
    @Test
    public void testLzeProjit() {
        Room room1 = new Room("hala", "vstupní hala budovy VŠE na Jižním městě");
        Room room2 = new Room("bufet", "bufet, kam si můžete zajít na svačinku");
        room1.setExit(room2);
        room2.setExit(room1);
        assertEquals(room2, room1.getSiblingRoom("bufet"));
        assertEquals(null, room2.getSiblingRoom("pokoj"));
    }

}
