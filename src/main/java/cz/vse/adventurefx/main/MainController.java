package cz.vse.adventurefx.main;

import cz.vse.adventurefx.logic.Game;
import cz.vse.adventurefx.logic.IGame;
import cz.vse.adventurefx.logic.Room;
import cz.vse.adventurefx.logic.commands.CommandGo;
import cz.vse.adventurefx.logic.commands.CommandInteract;
import cz.vse.adventurefx.logic.commands.CommandPick;
import cz.vse.adventurefx.logic.entities.Prop;
import cz.vse.adventurefx.logic.items.Item;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.Optional;

public class MainController {
    @FXML
    private TextArea outputField;

    @FXML
    private ListView<Room> exitsPanel;

    @FXML
    private ListView<Prop> propsPanel;

    @FXML
    private ListView<Item> itemsPanel;

    @FXML
    private Button sendButton;

    @FXML
    private Button minimapButton;

    @FXML
    private TextField inputField;

    private IGame game = new Game();

    private ObservableList<Room> exitRooms = FXCollections.observableArrayList();

    private ObservableList<Prop> props = FXCollections.observableArrayList();

    private ObservableList<Item> items = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        outputField.appendText(game.getGreeting() + "\n");
        Platform.runLater(() -> inputField.requestFocus());
        exitsPanel.setItems(exitRooms);
        propsPanel.setItems(props);
        itemsPanel.setItems(items);
        updateExitsList();
        updatePropsList();
        updateItemsList();
        game.getGamePlan().addObserver(GameChange.ROOM_CHANGE, this::updateExitsList);
        game.getGamePlan().addObserver(GameChange.ROOM_CHANGE, this::updatePropsList);
        game.getGamePlan().addObserver(GameChange.ROOM_CHANGE, this::updateItemsList);
        game.addObserver(GameChange.GAME_END, this::updateGameEnd);

        setCellsOutput();
    }

    private void setCellsOutput(){
        propsPanel.setCellFactory(param -> new ListCell<Prop>() {
            @Override
            protected void updateItem(Prop prop, boolean empty) {
                super.updateItem(prop, empty);
                setText(empty || prop == null ? null : prop.getName());
            }
        });

        itemsPanel.setCellFactory(param -> new ListCell<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName());
            }
        });
    }

    @FXML
    private void updateExitsList() {
        exitRooms.clear();
        exitRooms.addAll(game.getGamePlan().getCurrentRoom().getExits());
    }

    @FXML
    private void updatePropsList() {
        props.clear();
        props.addAll(game.getGamePlan().getCurrentRoom().getProps().values());
    }

    @FXML
    private void updateItemsList() {
        items.clear();
        items.addAll(game.getGamePlan().getCurrentRoom().getItems().values());
    }

    private void updateGameEnd() {
        if (game.isGameEnded()) {
            outputField.appendText(game.getEpilogue());
        }
        inputField.setDisable(game.isGameEnded());
        sendButton.setDisable(game.isGameEnded());
        minimapButton.setDisable(game.isGameEnded());
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

    @FXML
    private void openMinimap() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("minimap.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Minimap");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
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

    @FXML
    private void clickPropsPanel(MouseEvent mouseEvent) {
        Prop prop = propsPanel.getSelectionModel().getSelectedItem();
        if (prop != null) {
            String command = CommandInteract.NAME + " " + prop.getName();
            processCommand(command);
        }
        updatePropsList();
        updateItemsList();
    }

    @FXML
    private void clickItemsPanel(MouseEvent mouseEvent) {
        Item item = itemsPanel.getSelectionModel().getSelectedItem();
        if (item != null) {
            String command = CommandPick.NAME + " " + item.getName();
            processCommand(command);
        }
        updateItemsList();
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
