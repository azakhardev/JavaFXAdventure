module cz.vse.adventurefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;


    exports cz.vse.adventurefx.main;
    opens cz.vse.adventurefx.main to javafx.fxml;
}