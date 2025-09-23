module cz.vse.adventurefx {
    requires javafx.controls;
    requires javafx.fxml;


    opens cz.vse.adventurefx to javafx.fxml;
    exports cz.vse.adventurefx;
}