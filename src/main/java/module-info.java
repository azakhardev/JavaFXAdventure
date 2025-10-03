module cz.vse.adventurefx {
    requires javafx.fxml;
    requires javafx.web;


    exports cz.vse.adventurefx.main;
    opens cz.vse.adventurefx.main to javafx.fxml;
}