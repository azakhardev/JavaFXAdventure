package cz.vse.adventurefx.main;

import cz.vse.adventurefx.logic.IGame;
import cz.vse.adventurefx.logic.Room;
import cz.vse.adventurefx.logic.commands.CommandCombine;
import cz.vse.adventurefx.logic.commands.CommandDrop;
import cz.vse.adventurefx.logic.commands.CommandGo;
import cz.vse.adventurefx.logic.entities.Player;
import cz.vse.adventurefx.logic.items.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinimapController {
    private IGame game;

    private MainController mainController;

    @FXML
    private ImageView playerPin;

    @FXML
    private ListView<Item> inventoryPanel;

    private ObservableList<Item> items = FXCollections.observableArrayList();

    private Map<String, Point2D> roomsCoordinates = new HashMap<>();

    @FXML
    private Label inventorySpace;

    @FXML
    private void initialize() {
        insertCoordinates();
    }

    private void insertCoordinates() {
        loadInventory();
        inventoryPanel.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        roomsCoordinates.put("barrack", new Point2D(36, 80));
        roomsCoordinates.put("kitchen", new Point2D(112, 104));
        roomsCoordinates.put("storage", new Point2D(100, 164));
        roomsCoordinates.put("office", new Point2D(36, 218));
        roomsCoordinates.put("hallway", new Point2D(212, 104));
        roomsCoordinates.put("greenhouse", new Point2D(362, 115));
        roomsCoordinates.put("outpost", new Point2D(470, 110));
        roomsCoordinates.put("catacombs", new Point2D(471, 30));
        roomsCoordinates.put("shaft", new Point2D(560, 110));
        roomsCoordinates.put("lab", new Point2D(470, 178));
        roomsCoordinates.put("armory", new Point2D(470, 270));
        roomsCoordinates.put("engine_room", new Point2D(336, 188));
        roomsCoordinates.put("server_room", new Point2D(254, 196));
        roomsCoordinates.put("administration", new Point2D(250, 256));
        roomsCoordinates.put("exit", new Point2D(250, 280));
    }

    private void loadInventory() {
        inventoryPanel.setCellFactory(param -> new ListCell<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName() + " (" + item.getVolume() + ")");
            }
        });

        items.addAll(Player.getInstance().getBackpack().getItems());
        inventoryPanel.setItems(items);
        inventorySpace.setText(String.valueOf(
                Player.getInstance().getBackpack().getCapacity()
                        - Player.getInstance().getBackpack().getUsedCapacity()
        ));
    }

    public void setGame(IGame game) {
        this.game = game;
        updatePlayerPosition();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void updatePlayerPosition() {
        Point2D point = roomsCoordinates.get(game.getGamePlan().getCurrentRoom().getName());

        playerPin.setLayoutX(point.getX());
        playerPin.setLayoutY(point.getY());
    }

    @FXML
    private void clickInventoryPanel(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            Item clickedItem = inventoryPanel.getSelectionModel().getSelectedItem();
            if (clickedItem != null) {
                Alert alert = new Alert(Alert.AlertType.NONE, clickedItem.getDescription(), ButtonType.OK);
                alert.setTitle("Description of " + clickedItem.getName());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void useItem(ActionEvent actionEvent) {
        Item selected = inventoryPanel.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        Player.getInstance().getBackpack().setSelectedItem(selected);

        ((Stage) inventoryPanel.getScene().getWindow()).close();
    }

    @FXML
    private void dropItem(ActionEvent actionEvent) {
        Item selected = inventoryPanel.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        mainController.processCommand(CommandDrop.NAME + " " + selected.getName());

        ((Stage) inventoryPanel.getScene().getWindow()).close();
    }

    @FXML
    private void combineItems(ActionEvent actionEvent) {
        List<Item> selectedItems = inventoryPanel.getSelectionModel().getSelectedItems();

        if (selectedItems.size() != 2) {
            return;
        }

        mainController.processCommand(CommandCombine.NAME + " " + selectedItems.get(0).getName() + " " + selectedItems.get(1).getName());

        ((Stage) inventoryPanel.getScene().getWindow()).close();
    }
}
