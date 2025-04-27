package com.dam.pacmanfx.controller;

import com.dam.pacmanfx.model.Score;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ScoreController {

    @FXML
    private TableView<Score> scoreTable;

    @FXML
    private TableColumn<Score, String> nameColumn;

    @FXML
    private TableColumn<Score, Integer> scoreColumn;

    @FXML
    private void initialize() {
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 14);
        nameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("name"));
        scoreColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("score"));

        scoreTable.getItems().setAll(com.dam.pacmanfx.db.SQLiteManager.getTopScores(20));

        scoreTable.setSelectionModel(null);
        scoreTable.setFocusTraversable(false);
        nameColumn.setSortable(false);
        scoreColumn.setSortable(false);

    }

    @FXML
    private void onBackToMenu() {
        Stage stage = (Stage) scoreTable.getScene().getWindow();
        com.dam.pacmanfx.util.SceneManager.switchScene(stage, "/com/dam/view/menu_view.fxml", "/styles/menu_style.css");
    }

    @FXML
    private void onClearScores() {
        com.dam.pacmanfx.db.SQLiteManager.deleteAllScores();
        scoreTable.getItems().clear();

    }
}