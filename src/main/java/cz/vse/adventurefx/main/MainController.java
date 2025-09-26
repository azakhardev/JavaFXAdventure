package cz.vse.adventurefx.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainController {
    @FXML
    public TextArea outputField;

    @FXML
    private TextField inputField;

    @FXML
    private void sendInput(ActionEvent actionEvent) {
        outputField.appendText(inputField.getText() + "\n");
        inputField.clear();
    }
}
