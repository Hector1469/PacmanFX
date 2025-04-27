package com.dam.pacmanfx.controller;

import com.dam.pacmanfx.Main;
import com.dam.pacmanfx.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class MenuController {

    @FXML
    private Button btnPlay;
    @FXML
    private Button btnScore;
    @FXML
    private Button btnSkins;
    @FXML
    private Button btnExit;
    @FXML
    private BorderPane root;

    @FXML
    private void initialize() {
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 14);
    }

    @FXML
    private void onPlayClicked(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        SceneManager.switchScene(stage, "/com/dam/view/game_view.fxml", "/styles/game_style.css");
    }

    @FXML
    void onScoreClicked(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        SceneManager.switchScene(stage, "/com/dam/view/score_view.fxml", "/styles/score_style.css");
    }

    @FXML
    void onSkinClicked(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        SceneManager.switchScene(stage, "/com/dam/view/selectorSkins_view.fxml", "/styles/selectorSkins_style.css");
    }
    @FXML
    private void onExitClicked() {
        System.exit(0);
    }
}
