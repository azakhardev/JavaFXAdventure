package cz.vse.adventurefx.main;

import cz.vse.adventurefx.logic.IGame;
import cz.vse.adventurefx.logic.Room;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class MinimapController {

    private IGame game;

    @FXML
    private ImageView playerPin;

    @FXML
    private ImageView minimap;

    private Map<String, Point2D> roomsCoordinates = new HashMap<>();

    @FXML
    private void initialize() {
        insertCoordinates();
    }

    private void insertCoordinates() {
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
    }

    public void setGame(IGame game) {
        this.game = game;
        updatePlayerPosition();
    }

    private void updatePlayerPosition() {
        Point2D point = roomsCoordinates.get(game.getGamePlan().getCurrentRoom().getName());

        playerPin.setLayoutX(point.getX());
        playerPin.setLayoutY(point.getY());
    }
}
