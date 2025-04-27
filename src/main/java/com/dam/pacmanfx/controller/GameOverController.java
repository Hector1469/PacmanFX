package com.dam.pacmanfx.controller;

import com.dam.pacmanfx.Main;
import com.dam.pacmanfx.db.SQLiteManager;
import com.dam.pacmanfx.model.Score;
import com.dam.pacmanfx.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOverController {

    @FXML
    private TextField nameField;

    private int score;

    public void setScore(int score) {
        this.score = score;
    }

    @FXML
    private void initialize() {
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 14);
    }

    @FXML
    private void onSaveScore(ActionEvent event) {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            name = "Anonymous";
        }

        SQLiteManager.insertScore(new Score(name, score));
        nameField.clear();
    }

    @FXML
    private void onBackToMenu(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        SceneManager.switchScene(stage, "/com/dam/view/menu_view.fxml", "/styles/menu_style.css");
    }





}
