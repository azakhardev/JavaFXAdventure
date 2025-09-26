package cz.vse.adventurefx.main;

import cz.vse.adventurefx.logic.Game;
import cz.vse.adventurefx.logic.IGame;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainController {
    @FXML
    public TextArea outputField;

    @FXML
    private Button sendButton;

    @FXML
    private TextField inputField;

    private IGame game = new Game();

    @FXML
    private void initialize() {
        outputField.appendText(game.getGreeting() + "\n");
        Platform.runLater(()-> inputField.requestFocus());
    }

    @FXML
    private void sendInput(ActionEvent actionEvent) {
        String command = inputField.getText();
        outputField.appendText("> " + command + "\n");

        String result = game.processCommand(command);
        outputField.appendText(result + "\n\n");

        inputField.clear();

        if(game.isGameEnded()){
            outputField.appendText(game.getEpilogue());
            inputField.setDisable(true);
            sendButton.setDisable(true);
        }
    }
}
