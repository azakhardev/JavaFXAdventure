/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adventurefx.main;


import cz.vse.adventurefx.logic.Game;
import cz.vse.adventurefx.logic.IGame;
import cz.vse.adventurefx.uiText.TextInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*******************************************************************************
 * Třída  Start je hlavní třídou projektu,
 * který představuje jednoduchou textovou adventuru určenou k dalším úpravám a rozšiřování
 *
 * @author Jarmila Pavlíčková
 * @version ZS 2016/2017
 */
public class Start extends Application {
    /***************************************************************************
     * Metoda, prostřednictvím níž se spouští celá aplikace.
     *
     * @param args Parametry příkazového řádku
     */
    public static void main(String[] args) {

        if (args.length > 0 && args[0].equals("text")) {
            IGame hra = new Game();
            TextInterface ui = new TextInterface(hra);
            ui.play();
            Platform.exit();
        } else {
            launch();
        }

    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Start.class.getResource("main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Adventure");
    }
}
