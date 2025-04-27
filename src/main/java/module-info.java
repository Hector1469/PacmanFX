module com.dam.pacmanfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.dam.pacmanfx to javafx.fxml;
    opens com.dam.pacmanfx.controller to javafx.fxml;
    opens com.dam.pacmanfx.model to javafx.base;


    exports com.dam.pacmanfx;
    exports com.dam.pacmanfx.controller;
}
