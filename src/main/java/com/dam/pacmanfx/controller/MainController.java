package com.dam.pacmanfx.controller;

import com.dam.pacmanfx.model.Ghost;
import com.dam.pacmanfx.model.Pacman;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class MainController {

    @FXML
    private Canvas gameCanvas;

    @FXML
    private Label labelScore1UP;

    @FXML
    private Label labelHighScore;

    private Image fondoMapa;
    private Pacman pacman = new Pacman();
    private Ghost redGhost = new Ghost(11, 13, "red");

    private double tileSize;
    private double offsetX;
    private double offsetY;

    private int score = 0;
    private int ghostComboCounter = 0;
    private int floatingScore = 0;
    private double floatingX = 0;
    private double floatingY = 0;
    private int floatingTimer = 0;
    private int vidas = 3;
    private boolean isPacmanDying = false;
    private boolean mostrarFantasmas = true;

    private boolean mostrarPacman = true;
    private boolean mostrandoMuerte = false;

    private final int[][] mapa = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,1},
            {1,6,1,1,1,1,1,6,1,1,6,1,1,1,1,1,1,6,1,1,6,1,1,1,1,1,6,1},
            {1,3,1,1,1,1,1,6,1,1,6,1,1,1,1,1,1,6,1,1,6,1,1,1,1,1,3,1},
            {1,6,6,6,6,1,1,6,1,1,6,1,1,1,1,1,1,6,1,1,6,1,1,6,6,6,6,1},
            {1,1,1,1,6,1,1,6,1,1,6,6,6,6,6,6,6,6,1,1,6,1,1,6,1,1,1,1},
            {1,1,1,1,6,1,1,6,1,1,6,1,1,1,1,1,1,6,1,1,6,1,1,6,1,1,1,1},
            {1,1,1,1,6,1,1,6,1,1,6,1,1,1,1,1,1,6,1,1,6,1,1,6,1,1,1,1},
            {1,6,6,6,6,6,6,6,1,1,6,6,6,1,1,6,6,6,1,1,6,6,6,6,6,6,6,1},
            {1,6,1,1,6,1,1,1,1,1,1,1,6,1,1,6,1,1,1,1,1,1,1,6,1,1,6,1},
            {1,6,1,1,6,1,1,1,1,1,1,1,6,1,1,6,1,1,1,1,1,1,1,6,1,1,6,1},
            {1,6,1,1,6,6,6,6,6,0,0,0,0,0,0,0,0,0,0,6,6,6,6,6,1,1,6,1},
            {1,6,1,1,1,1,6,1,1,0,1,1,1,4,4,1,1,1,0,1,1,6,1,1,1,1,6,1},
            {1,6,1,1,1,1,6,1,1,0,1,4,4,4,4,4,4,1,0,1,1,6,1,1,1,1,6,1},
            {1,6,1,1,6,6,6,1,1,0,1,4,4,4,4,4,4,1,0,1,1,6,6,6,1,1,6,1},
            {1,6,1,1,6,1,1,1,1,0,1,4,4,4,4,4,4,1,0,1,1,1,1,6,1,1,6,1},
            {1,6,1,1,6,1,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,6,1,1,6,1},
            {1,6,6,6,6,6,6,6,6,0,0,0,0,0,0,0,0,0,0,6,6,6,6,6,6,6,6,1},
            {1,6,1,1,1,1,6,1,1,6,1,1,1,1,1,1,1,1,6,1,1,6,1,1,1,1,6,1},
            {1,6,1,1,1,1,6,1,1,6,1,1,1,1,1,1,1,1,6,1,1,6,1,1,1,1,6,1},
            {5,6,6,6,6,6,6,1,1,6,6,6,6,1,1,6,6,6,6,1,1,6,6,6,6,6,6,5},
            {1,6,1,1,6,1,1,1,1,1,1,1,6,1,1,6,1,1,1,1,1,1,1,6,1,1,6,1},
            {1,6,1,1,6,1,1,1,1,1,1,1,6,1,1,6,1,1,1,1,1,1,1,6,1,1,6,1},
            {1,6,1,1,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,1,1,6,1},
            {1,6,1,1,1,1,1,6,1,1,6,1,1,1,1,1,1,6,1,1,6,1,1,1,1,1,6,1},
            {1,6,1,1,1,1,1,6,1,1,6,1,1,1,1,1,1,6,1,1,6,1,1,1,1,1,6,1},
            {1,6,1,1,6,6,6,6,1,1,6,6,6,1,1,6,6,6,1,1,6,6,6,6,1,1,6,1},
            {1,3,1,1,6,1,1,1,1,1,1,1,6,1,1,6,1,1,1,1,1,1,1,6,1,1,3,1},
            {1,6,1,1,6,1,1,1,1,1,1,1,6,1,1,6,1,1,1,1,1,1,1,6,1,1,6,1},
            {1,6,6,6,6,6,6,6,6,6,6,6,6,1,1,6,6,6,6,6,6,6,6,6,6,6,6,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    @FXML
    public void initialize() {
        labelScore1UP.setText("0");
        labelHighScore.setText("0");
        fondoMapa = new Image(getClass().getResource("/photo/Map.png").toExternalForm());

        dibujarMapa();

        gameCanvas.widthProperty().addListener((obs, oldVal, newVal) -> dibujarMapa());
        gameCanvas.heightProperty().addListener((obs, oldVal, newVal) -> dibujarMapa());

        Platform.runLater(() -> {
            Scene scene = gameCanvas.getScene();
            if (scene != null) {
                double topHeight = 70;
                double bottomHeight = 40;
                gameCanvas.widthProperty().bind(scene.widthProperty());
                gameCanvas.heightProperty().bind(scene.heightProperty().subtract(topHeight + bottomHeight));

                scene.setOnKeyPressed(e -> {
                    if (!isPacmanDying) {
                        switch (e.getCode()) {
                            case W -> pacman.setDirection(Pacman.Direction.UP);
                            case A -> pacman.setDirection(Pacman.Direction.LEFT);
                            case S -> pacman.setDirection(Pacman.Direction.DOWN);
                            case D -> pacman.setDirection(Pacman.Direction.RIGHT);
                        }
                    }
                });

                new Thread(() -> {
                    while (true) {
                        try { Thread.sleep(200); } catch (InterruptedException ignored) {}
                        Platform.runLater(() -> {
                            if (!isPacmanDying) {
                                pacman.move(mapa);
                                pacman.updateFrame();
                                comprobarPildoras();
                                comprobarColisiones();
                            }
                            dibujarMapa();
                        });
                    }
                }).start();

                new Thread(() -> {
                    while (true) {
                        try {
                            Thread.sleep(redGhost.getMode() == Ghost.Mode.DEAD ? 100 : 300);
                        } catch (InterruptedException ignored) {}
                        Platform.runLater(() -> {
                            if (!isPacmanDying) {
                                redGhost.updateFleeing();
                                redGhost.move(mapa, pacman);
                            }
                            dibujarMapa();
                        });
                    }
                }).start();

                new Thread(() -> {
                    while (true) {
                        try { Thread.sleep(50); } catch (InterruptedException ignored) {}
                        Platform.runLater(() -> {
                            if (!isPacmanDying) {
                                redGhost.updateFrame();
                            }
                            dibujarMapa();
                        });
                    }
                }).start();
            }
        });
    }

    private void dibujarMapa() {
        if (mostrandoMuerte) return;
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        Canvas canvas = gameCanvas;

        int filas = mapa.length;
        int columnas = mapa[0].length;

        double tileWidth = canvas.getWidth() / columnas;
        double tileHeight = canvas.getHeight() / filas;
        tileSize = Math.min(tileWidth, tileHeight);

        offsetX = (canvas.getWidth() - (tileSize * columnas)) / 2;
        offsetY = (canvas.getHeight() - (tileSize * filas)) / 2;

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int row = 0; row < filas; row++) {
            for (int col = 0; col < columnas; col++) {
                double x = offsetX + col * tileSize;
                double y = offsetY + row * tileSize;

                switch (mapa[row][col]) {
                    case 0,1,3,4,5,6 -> gc.setFill(Color.BLACK);
                    default -> gc.setFill(Color.GRAY);
                }
                gc.fillRect(x, y, tileSize, tileSize);

                if (mapa[row][col] == 6) {
                    gc.setFill(Color.WHITE);
                    double pillSize = tileSize * 0.15;
                    gc.fillOval(x + (tileSize - pillSize) / 2, y + (tileSize - pillSize) / 2, pillSize, pillSize);
                } else if (mapa[row][col] == 3) {
                    gc.setFill(Color.GOLD);
                    double bigPillSize = tileSize * 0.4;
                    gc.fillOval(x + (tileSize - bigPillSize) / 2, y + (tileSize - bigPillSize) / 2, bigPillSize, bigPillSize);
                }
            }
        }

        double imgWidth = fondoMapa.getWidth();
        double imgHeight = fondoMapa.getHeight();
        double scale = Math.min(canvas.getWidth() / imgWidth, canvas.getHeight() / imgHeight);
        double drawWidth = imgWidth * scale;
        double drawHeight = imgHeight * scale;
        double imgOffsetX = (canvas.getWidth() - drawWidth) / 2;
        double imgOffsetY = (canvas.getHeight() - drawHeight) / 2;

        gc.drawImage(fondoMapa, imgOffsetX, imgOffsetY, drawWidth, drawHeight);

        if (mostrarPacman) {
            Image pacmanImage = pacman.getCurrentImage();
            double pacX = offsetX + pacman.getCol() * tileSize;
            double pacY = offsetY + pacman.getRow() * tileSize;
            gc.drawImage(pacmanImage, pacX, pacY, tileSize, tileSize);
        }

        if (mostrarFantasmas) {
            Image redGhostImage = redGhost.getCurrentImage();
            double ghostX = offsetX + redGhost.getCol() * tileSize;
            double ghostY = offsetY + redGhost.getRow() * tileSize;
            gc.drawImage(redGhostImage, ghostX, ghostY, tileSize, tileSize);
        }

        if (floatingTimer > 0) {
            gc.setFill(Color.WHITE);
            gc.fillText(String.valueOf(floatingScore), floatingX, floatingY);
            floatingTimer--;
        }
    }


    private void comprobarPildoras() {
        int row = pacman.getRow();
        int col = pacman.getCol();
        if (mapa[row][col] == 6) {
            mapa[row][col] = 0;
            score += 10;
        } else if (mapa[row][col] == 3) {
            mapa[row][col] = 0;
            score += 50;
            activarFugaTodos();
        }
        labelScore1UP.setText(String.valueOf(score));
    }

    private void activarFugaTodos() {
        redGhost.startFleeing();
    }

    private void comprobarColisiones() {
        if (isPacmanDying) return;

        if (pacman.getRow() == redGhost.getRow() && pacman.getCol() == redGhost.getCol()) {
            if (redGhost.getMode() == Ghost.Mode.FLEEING) {
                ghostComboCounter++;
                int pointsEarned = 200 * (int) Math.pow(2, ghostComboCounter - 1);
                score += pointsEarned;
                labelScore1UP.setText(String.valueOf(score));

                redGhost.startDead();

                floatingScore = pointsEarned;
                floatingX = offsetX + redGhost.getCol() * tileSize;
                floatingY = offsetY + redGhost.getRow() * tileSize;
                floatingTimer = 20;
            } else if (redGhost.getMode() == Ghost.Mode.NORMAL) {
                pacmanMuere();
            }
        }
    }

    private void pacmanMuere() {
        isPacmanDying = true;
        mostrarPacman = false;
        mostrarFantasmas = false;
        mostrandoMuerte = true;

        new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    // Primero cambiar flags y redibujar
                    dibujarMapa(); // Dibuja el mapa sin Pacman ni Fantasmas

                    // Ahora encima dibujar pacman_dead.png
                    GraphicsContext gc = gameCanvas.getGraphicsContext2D();
                    Image deadPacmanImage = new Image(getClass().getResource("/photo/pacman/pac_man_dead.png").toExternalForm());
                    double x = offsetX + pacman.getCol() * tileSize + (tileSize - 32) / 2;
                    double y = offsetY + pacman.getRow() * tileSize + (tileSize - 32) / 2;
                    gc.drawImage(deadPacmanImage, x, y, 32, 32);
                });

                // Espera 5 segundos
                Thread.sleep(5000);

                Platform.runLater(() -> {
                    vidas--;

                    if (vidas <= 0) {
                        System.exit(0);
                    } else {
                        mostrarGoYResetear();
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }







    private void mostrarGoYResetear() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        gc.drawImage(fondoMapa, offsetX, offsetY, tileSize * mapa[0].length, tileSize * mapa.length);

        gc.setFill(Color.WHITE);
        gc.setFont(new javafx.scene.text.Font(50));
        gc.fillText("GO!", gameCanvas.getWidth() / 2 - 50, gameCanvas.getHeight() / 2);

        pacman.setRow(17);
        pacman.setCol(13);
        pacman.setDirection(Pacman.Direction.RIGHT);
        redGhost = new Ghost(11, 13, "red");

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                Platform.runLater(() -> {
                    isPacmanDying = false;
                    mostrarPacman = true;
                    mostrarFantasmas = true;
                    mostrandoMuerte = false;
                    dibujarMapa();
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


}
