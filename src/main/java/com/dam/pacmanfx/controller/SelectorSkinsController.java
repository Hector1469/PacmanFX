package com.dam.pacmanfx.controller;

import com.dam.pacmanfx.util.SkinManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import com.dam.pacmanfx.util.SceneManager;

import java.util.Arrays;
import java.util.List;

public class SelectorSkinsController {

    @FXML
    private ImageView imagePreview;

    @FXML
    private Button buttonPrev;

    @FXML
    private Button buttonNext;

    @FXML
    private Button buttonConfirm;

    private List<String> skins = Arrays.asList("og", "green");
    private int currentSkinIndex = 0;

    @FXML
    public void initialize() {
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 14);
        mostrarSkinActual();

        buttonPrev.setOnAction(e -> {
            currentSkinIndex = (currentSkinIndex - 1 + skins.size()) % skins.size();
            mostrarSkinActual();
        });

        buttonNext.setOnAction(e -> {
            currentSkinIndex = (currentSkinIndex + 1) % skins.size();
            mostrarSkinActual();
        });

        buttonConfirm.setOnAction(e -> {
            SkinManager.setSelectedSkin(skins.get(currentSkinIndex));
            Stage stage = (Stage) buttonConfirm.getScene().getWindow();
            SceneManager.switchScene(stage, "/com/dam/view/menu_view.fxml", "/styles/menu_style.css");
        });
    }

    private void mostrarSkinActual() {
        String skinFolder = skins.get(currentSkinIndex);
        Image image = new Image(getClass().getResourceAsStream("/photo/pacman/" + skinFolder + "/pacman_right_open.png"));
        imagePreview.setImage(image);
    }
}
