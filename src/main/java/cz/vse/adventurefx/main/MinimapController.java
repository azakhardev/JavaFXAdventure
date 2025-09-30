package cz.vse.adventurefx.main;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class MinimapController {
    @FXML
    private ImageView playerPin;

    @FXML
    private ImageView minimap;

    @FXML
    private void initialize() {
        playerPin.setLayoutX(20);
        playerPin.setLayoutY(20);
    }
}
