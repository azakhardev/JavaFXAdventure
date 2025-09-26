package cz.vse.adventurefx.main;

import cz.vse.adventurefx.logic.Game;
import cz.vse.adventurefx.logic.IGame;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

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
        Platform.runLater(() -> inputField.requestFocus());
    }

    @FXML
    private void sendInput(ActionEvent actionEvent) {
        String command = inputField.getText();
        outputField.appendText("> " + command + "\n");

        String result = game.processCommand(command);
        outputField.appendText(result + "\n\n");

        inputField.clear();

        if (game.isGameEnded()) {
            outputField.appendText(game.getEpilogue());
            inputField.setDisable(true);
            sendButton.setDisable(true);
        }
    }

    public void closeGame(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to close the game?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    public void newGame(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to start a new game?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            outputField.clear();
            outputField.appendText(game.getGreeting() + "\n");
            this.game = new Game();

        }
    }
}
