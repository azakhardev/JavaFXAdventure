package cz.vse.adventurefx.main;

import cz.vse.adventurefx.logic.Game;
import cz.vse.adventurefx.logic.IGame;
import cz.vse.adventurefx.logic.Room;
import cz.vse.adventurefx.logic.commands.CommandGo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.util.Optional;

public class MainController {
    @FXML
    private TextArea outputField;

    @FXML
    private ListView<Room> exitsPanel;

    @FXML
    private Button sendButton;

    @FXML
    private TextField inputField;

    private IGame game = new Game();

    private ObservableList<Room> exitRooms = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        outputField.appendText(game.getGreeting() + "\n");
        Platform.runLater(() -> inputField.requestFocus());
        exitsPanel.setItems(exitRooms);
        updateExitsList();
        game.addObserver(GameChange.GAME_END, this::updateExitsList);
        game.getGamePlan().addObserver(GameChange.ROOM_CHANGE, this::updateGameEnd);
    }

    @FXML
    private void updateExitsList() {
        exitRooms.clear();
        exitRooms.addAll(game.getGamePlan().getCurrentRoom().getExits());
    }

    private void updateGameEnd() {
        if (game.isGameEnded()) {
            outputField.appendText(game.getEpilogue());
        }
        inputField.setDisable(game.isGameEnded());
        sendButton.setDisable(game.isGameEnded());
        exitsPanel.setDisable(game.isGameEnded());
    }

    @FXML
    private void sendInput(ActionEvent actionEvent) {
        String command = inputField.getText();
        inputField.clear();

        if (command.equals("speedrun")) {
            playFromFile("speedrun.txt");
        }
        processCommand(command);
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
            exitsPanel.setDisable(true);
        }
    }


    @FXML
    public void clickExitsPanel(MouseEvent mouseEvent) {
        Room exit = exitsPanel.getSelectionModel().getSelectedItem();
        if (exit != null) {
            String command = CommandGo.NAME + " " + exit;
            processCommand(command);
        }
    }

    private void processCommand(String command) {
        outputField.appendText("> " + command + "\n");

        String result = game.processCommand(command);
        outputField.appendText(result + "\n\n");
    }

    public void playFromFile(String nazevSouboru) {
        try (
                BufferedReader cteni = new BufferedReader(new FileReader(nazevSouboru));
        ) {
            System.out.println(game.getGreeting());

            for (String radek = cteni.readLine(); radek != null && !game.isGameEnded(); radek = cteni.readLine()) {
                processCommand(radek);
            }

        } catch (FileNotFoundException e) {
            File file = new File(nazevSouboru);
            System.out.println("Soubor nebyl nalezen!\nProhledávaná cesta byla: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Nelze hrát hru ze souboru, něco se pokazilo: " + e.getMessage());
        }
    }
}
