module cz.vse.adventurefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    exports cz.vse.adventurefx.main;
    opens cz.vse.adventurefx.main to javafx.fxml;
}