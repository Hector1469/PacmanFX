package com.dam.pacmanfx.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

public class SceneManager {

    private static final double FIXED_WIDTH = 1200;
    private static final double FIXED_HEIGHT = 1000;

    public static void switchScene(Stage stage, String fxmlPath, String cssPath) {
        switchSceneWithController(stage, fxmlPath, cssPath, null);
    }

    public static <T> void switchSceneWithController(Stage stage, String fxmlPath, String cssPath, Consumer<T> controllerConsumer) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();

            if (controllerConsumer != null) {
                T controller = loader.getController();
                controllerConsumer.accept(controller);
            }

            Scene scene = new Scene(root, FIXED_WIDTH, FIXED_HEIGHT);

            if (cssPath != null && !cssPath.isEmpty()) {
                scene.getStylesheets().add(Objects.requireNonNull(SceneManager.class.getResource(cssPath)).toExternalForm());
            }

            stage.setScene(scene);
            stage.setTitle("PacmanFX");

            stage.setResizable(false);
            stage.setWidth(FIXED_WIDTH);
            stage.setHeight(FIXED_HEIGHT);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

