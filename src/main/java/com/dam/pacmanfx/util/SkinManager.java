package com.dam.pacmanfx.util;

public class SkinManager {

    private static String selectedSkin = "og";
    public static String getSelectedSkin() {
        return selectedSkin;
    }

    public static void setSelectedSkin(String skin) {
        selectedSkin = skin;
    }
}
