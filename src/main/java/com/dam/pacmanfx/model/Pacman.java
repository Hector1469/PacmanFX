package com.dam.pacmanfx.model;

import javafx.scene.image.Image;

public class Pacman {
    public enum Direction { UP, DOWN, LEFT, RIGHT }

    private int row = 17;
    private int col = 13;
    private Direction direction = Direction.RIGHT;
    private int animationFrame = 0;

    public int getRow() { return row; }
    public int getCol() { return col; }
    public void setRow(int row) { this.row = row; }
    public void setCol(int col) { this.col = col; }
    public Direction getDirection() { return direction; }
    public void setDirection(Direction direction) { this.direction = direction; }
    public void move(int[][] mapa) {
        int nextRow = row;
        int nextCol = col;

        switch (direction) {
            case UP -> nextRow--;
            case DOWN -> nextRow++;
            case LEFT -> nextCol--;
            case RIGHT -> nextCol++;
        }

        // Si se va a mover fuera del mapa, abortar
        if (nextRow < 0 || nextRow >= mapa.length || nextCol < 0 || nextCol >= mapa[0].length) {
            return;
        }

        int targetCell = mapa[nextRow][nextCol];

        // Teletransporte entre túneles
        if (targetCell == 5) {
            if (nextCol == 0) {
                col = mapa[0].length - 1;
            } else if (nextCol == mapa[0].length - 1) {
                col = 0;
            }

            return;
        }

        if (targetCell == 0 || targetCell == 3 || targetCell == 6) {
            row = nextRow;
            col = nextCol;

        }
    }


    public Image getCurrentImage() {
        String frameType = switch (animationFrame % 3) {
            case 0 -> "open";
            case 1 -> "semi";
            default -> "closed";
        };
        String dir = direction.name().toLowerCase();
        String skinFolder = com.dam.pacmanfx.util.SkinManager.getSelectedSkin();
        return new Image(getClass().getResource(
                "/photo/pacman/" + skinFolder + "/pacman_" + dir + "_" + frameType + ".png"
        ).toExternalForm());
    }


    public void updateFrame() {
        animationFrame++;
    }

    public int getNextRow() {
        return switch (direction) {
            case UP -> row - 1;
            case DOWN -> row + 1;
            default -> row;
        };
    }

    public int getNextCol() {
        return switch (direction) {
            case LEFT -> col - 1;
            case RIGHT -> col + 1;
            default -> col;
        };
    }

}
