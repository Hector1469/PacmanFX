package com.dam.pacmanfx.model;

import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ghost {
    public enum Direction { UP, DOWN, LEFT, RIGHT }
    public enum Mode { NORMAL, FLEEING, DEAD }

    private int row;
    private int col;
    private Direction direction;
    private final String color;
    private Mode mode = Mode.NORMAL;
    private int frameCounter = 0;
    private boolean fleeingNearEnd = false;
    private int fleeingTimer = 0;
    private boolean enJuego = false;
    private boolean inteligente = false;

    private final Random random = new Random();

    public Ghost(int startRow, int startCol, String color) {
        this.row = startRow;
        this.col = startCol;
        this.color = color;
        this.direction = Direction.LEFT;

        if (color.equals("red")) {
            this.enJuego = true;
            this.inteligente = true;
        }
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public Direction getDirection() { return direction; }
    public Mode getMode() { return mode; }

    public void liberar() { enJuego = true; }

    public void startFleeing() {
        mode = Mode.FLEEING;
        fleeingTimer = 20;
        fleeingNearEnd = false;
    }

    public void startDead() {
        mode = Mode.DEAD;
    }

    public void updateFrame() {
        frameCounter++;
    }

    public void updateFleeing() {
        if (mode == Mode.FLEEING) {
            fleeingTimer--;

            if (fleeingTimer == 5) {
                fleeingNearEnd = true;
            }

            if (fleeingTimer <= 0) {
                mode = Mode.NORMAL;
                fleeingNearEnd = false;
            }
        }
    }

    public void move(int[][] mapa, Pacman pacman) {
        if (!enJuego) return;

        if (mode == Mode.DEAD) {
            moveDead(mapa);
            return;
        }

        if ((mode == Mode.NORMAL || mode == Mode.FLEEING) && mapa[row][col] == 4) {
            buscarSalidaCarcel(mapa);
            return;
        }

        if (inteligente) {
            movimientoInteligente(mapa, pacman);
        } else {
            movimientoAleatorio(mapa);
        }
    }

    private void buscarSalidaCarcel(int[][] mapa) {
        boolean salidaEncontrada = false;

        for (Direction dir : Direction.values()) {
            int newRow = row + (dir == Direction.UP ? -1 : dir == Direction.DOWN ? 1 : 0);
            int newCol = col + (dir == Direction.LEFT ? -1 : dir == Direction.RIGHT ? 1 : 0);

            if (newRow >= 0 && newRow < mapa.length && newCol >= 0 && newCol < mapa[0].length) {
                int nextCell = mapa[newRow][newCol];
                if (nextCell == 0 || nextCell == 6 || nextCell == 5 || nextCell == 3) {
                    direction = dir;
                    moverEnDireccion();
                    salidaEncontrada = true;
                    break;
                }
            }
        }

        if (!salidaEncontrada) {
            for (Direction dir : Direction.values()) {
                int newRow = row + (dir == Direction.UP ? -1 : dir == Direction.DOWN ? 1 : 0);
                int newCol = col + (dir == Direction.LEFT ? -1 : dir == Direction.RIGHT ? 1 : 0);

                if (newRow >= 0 && newRow < mapa.length && newCol >= 0 && newCol < mapa[0].length) {
                    if (mapa[newRow][newCol] == 4) {
                        direction = dir;
                        moverEnDireccion();
                        break;
                    }
                }
            }
        }
    }

    private void moverEnDireccion() {
        switch (direction) {
            case UP -> row--;
            case DOWN -> row++;
            case LEFT -> col--;
            case RIGHT -> col++;
        }
    }

    private void movimientoAleatorio(int[][] mapa) {
        List<Direction> posiblesDirecciones = new ArrayList<>();

        for (Direction dir : Direction.values()) {
            int newRow = row + (dir == Direction.UP ? -1 : dir == Direction.DOWN ? 1 : 0);
            int newCol = col + (dir == Direction.LEFT ? -1 : dir == Direction.RIGHT ? 1 : 0);

            if (canMove(mapa, newRow, newCol) && dir != getOppositeDirection(direction)) {
                posiblesDirecciones.add(dir);
            }
        }

        if (posiblesDirecciones.isEmpty()) {
            direction = getOppositeDirection(direction);
        } else {
            direction = posiblesDirecciones.get(random.nextInt(posiblesDirecciones.size()));
        }

        moverEnDireccion();
    }

    private void movimientoInteligente(int[][] mapa, Pacman pacman) {
        Direction[] directions = Direction.values();
        int options = 0;

        for (Direction dir : directions) {
            int newRow = row + (dir == Direction.UP ? -1 : dir == Direction.DOWN ? 1 : 0);
            int newCol = col + (dir == Direction.LEFT ? -1 : dir == Direction.RIGHT ? 1 : 0);

            if (canMove(mapa, newRow, newCol)) {
                options++;
            }
        }

        if (options >= 3 || !canMove(mapa, getNextRow(), getNextCol())) {
            Direction bestDir = null;
            double bestDistance = Double.MAX_VALUE;

            for (Direction dir : directions) {
                if (dir == getOppositeDirection(direction)) continue;

                int newRow = row + (dir == Direction.UP ? -1 : dir == Direction.DOWN ? 1 : 0);
                int newCol = col + (dir == Direction.LEFT ? -1 : dir == Direction.RIGHT ? 1 : 0);

                if (canMove(mapa, newRow, newCol)) {
                    double distance = calcularDistancia(newRow, newCol, pacman.getRow(), pacman.getCol());
                    if (mode == Mode.FLEEING) distance = -distance;
                    if (distance < bestDistance) {
                        bestDistance = distance;
                        bestDir = dir;
                    }
                }
            }

            if (bestDir == null) {
                bestDir = getOppositeDirection(direction);
            }

            direction = bestDir;
        }

        moverEnDireccion();
    }

    private void moveDead(int[][] mapa) {
        int targetRow = 14;
        int targetCol = 13;

        if (row == targetRow && col == targetCol) {
            mode = Mode.NORMAL;
            direction = Direction.LEFT;
            return;
        }

        Direction[] directions = Direction.values();
        Direction bestDir = null;
        double bestDistance = Double.MAX_VALUE;

        for (Direction dir : directions) {
            if (dir == getOppositeDirection(direction)) continue;

            int newRow = row + (dir == Direction.UP ? -1 : dir == Direction.DOWN ? 1 : 0);
            int newCol = col + (dir == Direction.LEFT ? -1 : dir == Direction.RIGHT ? 1 : 0);

            if (canMove(mapa, newRow, newCol)) {
                double distance = calcularDistancia(newRow, newCol, targetRow, targetCol);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestDir = dir;
                }
            }
        }

        if (bestDir == null) {
            bestDir = getOppositeDirection(direction);
        }

        direction = bestDir;
        moverEnDireccion();
    }

    private boolean canMove(int[][] mapa, int newRow, int newCol) {
        if (newRow < 0 || newRow >= mapa.length || newCol < 0 || newCol >= mapa[0].length) return false;
        int cell = mapa[newRow][newCol];

        if (mode == Mode.DEAD) {
            return (cell != 1);
        }

        if (mapa[row][col] == 4) {
            return (cell == 0 || cell == 3 || cell == 5 || cell == 6 || cell == 4);
        } else {
            return (cell == 0 || cell == 3 || cell == 5 || cell == 6);
        }
    }

    private double calcularDistancia(int r1, int c1, int r2, int c2) {
        int dr = r1 - r2;
        int dc = c1 - c2;
        return Math.sqrt(dr * dr + dc * dc);
    }

    private int getNextRow() {
        return switch (direction) {
            case UP -> row - 1;
            case DOWN -> row + 1;
            default -> row;
        };
    }

    private int getNextCol() {
        return switch (direction) {
            case LEFT -> col - 1;
            case RIGHT -> col + 1;
            default -> col;
        };
    }

    private Direction getOppositeDirection(Direction dir) {
        return switch (dir) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
        };
    }

    public Image getCurrentImage() {
        if (mode == Mode.DEAD) {
            String dirName = direction.name().toLowerCase();
            return new Image(getClass().getResource("/photo/ghost/spr_ghost_dead_" + dirName + ".png").toExternalForm());
        } else if (mode == Mode.FLEEING) {
            int framesPerSwitch = fleeingNearEnd ? 2 : 5;
            int frame = (frameCounter / framesPerSwitch) % 2;
            return new Image(getClass().getResource("/photo/ghost/spr_afraid_" + frame + ".png").toExternalForm());
        } else {
            String dirName = direction.name().toLowerCase();
            return new Image(getClass().getResource("/photo/ghost/spr_ghost_" + color + "_" + dirName + ".png").toExternalForm());
        }
    }
}
