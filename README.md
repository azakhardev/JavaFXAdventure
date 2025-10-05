# Facility X – JavaFX Edition
### 📖 Popis

Pokračování původní textové adventury Facility X, tentokrát s plnohodnotným grafickým uživatelským rozhraním v JavaFX.
Hráč se opět probouzí v opuštěném podzemním komplexu a jeho cílem je najít cestu ven. Místo psaní textových příkazů může nyní svět prozkoumávat intuitivně – klikáním, tlačítky a interaktivními panely.

Projekt představuje vizuální nadstavbu nad původní logikou hry a zachovává všechny mechaniky – inventář, používání předmětů, interakce s objekty i řešení hádanek – tentokrát s přehledným a plynulým GUI.

### 💻 Technologie
- Java 17+
- JavaFX
- FXML
- Observer pattern pro aktualizaci herního stavu v reálném čase
- Modulární architektura – logika hry oddělena od grafického rozhraní

### 🕹️ Ovládání hry
Hra už nevyžaduje psaní příkazů do konzole – všechno probíhá pomocí grafického rozhraní:

  | Akce                                  | Popis                                                |
| ------------------------------------- | ---------------------------------------------------- |
| **Klik na místnost** v panelu „Exits“ | Přesune hráče do sousední místnosti                  |
| **Klik na objekt** v panelu „Props“  | Interakce s objektem nebo použití vybraného předmětu |
| **Klik na předmět** v panelu „Items“  | Seber předmět z místnosti                            |
| **Tlačítko Map**                  | Otevře přehled místností a inventáře                 |
| **Dvojklik** na předmět v inventáři   | Zobrazí jeho popis                                   |
| **Use / Drop / Combine** v minimapě   | Použije, odhodí nebo zkombinuje předměty             |
| **New Game / Exit / Help** v menu     | Restartuje hru, ukončí ji, nebo otevře nápovědu      |

Textové pole ve spodní části obrazovky stále umožňuje zadávat příkazy ručně, pokud hráč preferuje původní styl hraní.

### 🧭 Herní prvky
- Minimapa – zobrazuje aktuální pozici hráče a umožňuje správu inventáře
- Dynamické aktualizace GUI – seznamy předmětů, objektů a východů se mění podle aktuální místnosti
- Obrázky místností – každé prostředí má vlastní ilustraci (roomImage)
- Automatické sledování změn – Observer systém sleduje ROOM_CHANGE a GAME_END eventy
- Nápověda v HTML – zabudovaná příručka s vysvětlením mechanik (vzhled vygenerován AI)

### 🧠 Herní princip
Stejně jako v původní hře je cílem uniknout z komplexu Facility X hráč musí:
- prozkoumávat místnosti,
- sbírat a používat předměty,
- kombinovat objekty,
- číst poznámky a řešit hádanky,
- sledovat děj prostřednictvím prostředí.

### 🧩 Možnosti rozšíření
- Přidání hudby nebo zvukových efektů
- Implementace systému ukládání hry
- Animace přechodů mezi místnostmi
- Vylepšené UI s ikonami pro předměty
