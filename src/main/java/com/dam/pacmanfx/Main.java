package com.dam.pacmanfx;

import com.dam.pacmanfx.db.SQLiteManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/view/menu_view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 1000);
        scene.getStylesheets().add(getClass().getResource("/styles/menu_style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("PacmanFX");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/photo/pacman/og/pacman_right_open.png")));

        stage.setResizable(false);
        stage.setWidth(1200);
        stage.setHeight(1000);

        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() {
        SQLiteManager.close();
    }
}
