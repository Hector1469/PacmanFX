module com.dam.pacmanfx {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.dam.pacmanfx to javafx.fxml;
    opens com.dam.pacmanfx.controller to javafx.fxml;

    exports com.dam.pacmanfx;
    exports com.dam.pacmanfx.controller;
}
