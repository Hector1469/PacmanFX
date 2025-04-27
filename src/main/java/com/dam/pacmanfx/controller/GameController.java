package com.dam.pacmanfx.controller;

import com.dam.pacmanfx.db.SQLiteManager;
import com.dam.pacmanfx.model.Ghost;
import com.dam.pacmanfx.model.Pacman;
import com.dam.pacmanfx.util.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
public class GameController {

    @FXML
    private Canvas gameCanvas;

    @FXML
    private Label labelScore1UP;

    @FXML
    private Label labelHighScore;

    @FXML
    private Label labelLives;

    @FXML
    private Label labelLevel;


    private Image fondoMapa;
    private Pacman pacman = new Pacman();
    private Ghost redGhost = new Ghost(11, 13, "red");
    private Ghost pinkGhost = new Ghost(14, 13, "pink");
    private Ghost blueGhost = new Ghost(13, 13, "blue");
    private Ghost orangeGhost = new Ghost(13, 14, "orange");

    private int nivel = 1;
    private int ghostSpeed = 300;


    private int pillsEaten = 0;


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

    private boolean frutaGenerada = false;
    private boolean frutaVisible = false;
    private double frutaX;
    private double frutaY;
    private int frutaTimer = 0;
    private Image frutaImage;
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

    private final int[][] mapaOriginal = new int[mapa.length][mapa[0].length];

    @FXML
    public void initialize() {
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 14);
        labelScore1UP.setText("0");
        labelHighScore.setText(String.valueOf(SQLiteManager.obtenerHighScore()));
        actualizarLabels();
        fondoMapa = new Image(getClass().getResource("/photo/Map.png").toExternalForm());

        dibujarMapa();

        gameCanvas.widthProperty().addListener((obs, oldVal, newVal) -> dibujarMapa());
        gameCanvas.heightProperty().addListener((obs, oldVal, newVal) -> dibujarMapa());

        frutaImage = new Image(getClass().getResource("/photo/spr_cherry_0.png").toExternalForm());


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
                                comprobarFruta();
                            }

                            if (frutaVisible) {
                                frutaTimer--;
                                if (frutaTimer <= 0) {
                                    frutaVisible = false;
                                    dibujarMapa();
                                }
                            }
                            dibujarMapa();
                        });
                    }
                }).start();

                new Thread(() -> {
                    while (true) {
                        try {
                            Thread.sleep(redGhost.getMode() == Ghost.Mode.DEAD ? 100 : ghostSpeed);
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

                // Mover pinkGhost
                new Thread(() -> {
                    while (true) {
                        try { Thread.sleep(pinkGhost.getMode() == Ghost.Mode.DEAD ? 100 : ghostSpeed);
                        } catch (InterruptedException ignored) {}
                        Platform.runLater(() -> {
                            if (!isPacmanDying) {
                                pinkGhost.updateFleeing();
                                pinkGhost.move(mapa, pacman);
                            }
                            dibujarMapa();
                        });
                    }
                }).start();

                // Mover blueGhost
                new Thread(() -> {
                    while (true) {
                        try { Thread.sleep(blueGhost.getMode() == Ghost.Mode.DEAD ? 100 : ghostSpeed);
                        } catch (InterruptedException ignored) {}
                        Platform.runLater(() -> {
                            if (!isPacmanDying) {
                                blueGhost.updateFleeing();
                                blueGhost.move(mapa, pacman);
                            }
                            dibujarMapa();
                        });
                    }
                }).start();

                // Mover orangeGhost
                new Thread(() -> {
                    while (true) {
                        try { Thread.sleep(orangeGhost.getMode() == Ghost.Mode.DEAD ? 100 : ghostSpeed);
                        } catch (InterruptedException ignored) {}
                        Platform.runLater(() -> {
                            if (!isPacmanDying) {
                                orangeGhost.updateFleeing();
                                orangeGhost.move(mapa, pacman);
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
                                pinkGhost.updateFrame();
                                blueGhost.updateFrame();
                                orangeGhost.updateFrame();
                            }
                            dibujarMapa();
                        });
                    }
                }).start();

            }
        });

        for (int i = 0; i < mapa.length; i++) {
            System.arraycopy(mapa[i], 0, mapaOriginal[i], 0, mapa[i].length);
        }
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
            double redX = offsetX + redGhost.getCol() * tileSize;
            double redY = offsetY + redGhost.getRow() * tileSize;
            gc.drawImage(redGhostImage, redX, redY, tileSize, tileSize);

            Image pinkGhostImage = pinkGhost.getCurrentImage();
            double pinkX = offsetX + pinkGhost.getCol() * tileSize;
            double pinkY = offsetY + pinkGhost.getRow() * tileSize;
            gc.drawImage(pinkGhostImage, pinkX, pinkY, tileSize, tileSize);

            Image blueGhostImage = blueGhost.getCurrentImage();
            double blueX = offsetX + blueGhost.getCol() * tileSize;
            double blueY = offsetY + blueGhost.getRow() * tileSize;
            gc.drawImage(blueGhostImage, blueX, blueY, tileSize, tileSize);

            Image orangeGhostImage = orangeGhost.getCurrentImage();
            double orangeX = offsetX + orangeGhost.getCol() * tileSize;
            double orangeY = offsetY + orangeGhost.getRow() * tileSize;
            gc.drawImage(orangeGhostImage, orangeX, orangeY, tileSize, tileSize);
        }

        if (frutaVisible) {
            double x = offsetX + frutaX * tileSize;
            double y = offsetY + frutaY * tileSize;
            gameCanvas.getGraphicsContext2D().drawImage(frutaImage, x, y, tileSize, tileSize);
        }

        if (floatingTimer > 0) {
            gc.setFill(Color.WHITE);
            gc.setFont(new javafx.scene.text.Font(20));
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
            pillsEaten++;

            if (pillsEaten == 10) pinkGhost.liberar();
            if (pillsEaten == 20) blueGhost.liberar();
            if (pillsEaten == 30) orangeGhost.liberar();

        } else if (mapa[row][col] == 3) {
            mapa[row][col] = 0;
            score += 50;
            activarFugaTodos();
        }
        labelScore1UP.setText(String.valueOf(score));

        if (!frutaVisible && !frutaGenerada && pillsEaten >= contarPildoras(mapaOriginal) / 2) {
            frutaVisible = true;
            frutaGenerada = true;
            frutaTimer = 150;
            frutaX = 13;
            frutaY = 17;
        }

        if (contarPildoras(mapa) == 0) {
            siguienteNivel();
        }
    }


    private void activarFugaTodos() {
        redGhost.startFleeing();
        pinkGhost.startFleeing();
        blueGhost.startFleeing();
        orangeGhost.startFleeing();
        ghostComboCounter = 0;
    }


    private void comprobarColisiones() {
        if (isPacmanDying) return;

        comprobarColisionConFantasma(redGhost);
        comprobarColisionConFantasma(pinkGhost);
        comprobarColisionConFantasma(blueGhost);
        comprobarColisionConFantasma(orangeGhost);
    }
    private void comprobarColisionConFantasma(Ghost ghost) {
        if (pacman.getRow() == ghost.getRow() && pacman.getCol() == ghost.getCol()) {
            if (ghost.getMode() == Ghost.Mode.FLEEING) {
                ghostComboCounter++;
                int pointsEarned = 200 * (int) Math.pow(2, ghostComboCounter - 1);
                score += pointsEarned;
                labelScore1UP.setText(String.valueOf(score));

                ghost.startDead();

                floatingScore = pointsEarned;
                floatingX = offsetX + ghost.getCol() * tileSize;
                floatingY = offsetY + ghost.getRow() * tileSize;
                floatingTimer = 20;
            } else if (ghost.getMode() == Ghost.Mode.NORMAL) {
                pacmanMuere();
            }
        }
    }

    private void comprobarFruta() {
        if (frutaVisible && pacman.getRow() == (int) frutaY && pacman.getCol() == (int) frutaX) {
            frutaVisible = false;
            score += nivel * 200;
            labelScore1UP.setText(String.valueOf(score));

            floatingScore = nivel * 200;
            floatingX = offsetX + frutaX * tileSize;
            floatingY = offsetY + frutaY * tileSize;
            floatingTimer = 20;

            dibujarMapa();
        }
    }




    private void pacmanMuere() {
        isPacmanDying = true;
        mostrarPacman = false;
        mostrarFantasmas = false;
        mostrandoMuerte = true;

        new Thread(() -> {
            try {
                Thread.sleep(2000);

                Platform.runLater(() -> {
                    vidas--;
                    actualizarLabels();

                    if (vidas <= 0) {
                        Stage stage = (Stage) gameCanvas.getScene().getWindow();
                        SceneManager.switchSceneWithController(
                                stage,
                                "/com/dam/view/gameOver_view.fxml",
                                "/styles/gameOver_style.css",
                                (GameOverController controller) -> controller.setScore(score)
                        );
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

        dibujarMapa();

        gc.setFill(Color.WHITE);
        gc.setFont(new javafx.scene.text.Font(50));
        gc.fillText("GO!", gameCanvas.getWidth() / 2 - 50, gameCanvas.getHeight() / 2);

        // Resetear Pacman
        pacman.setRow(17);
        pacman.setCol(13);
        pacman.setDirection(Pacman.Direction.RIGHT);

        // Resetear fantasmas
        redGhost = new Ghost(11, 13, "red");
        redGhost.liberar();
        pinkGhost = new Ghost(14, 13, "pink");
        blueGhost = new Ghost(13, 13, "blue");
        orangeGhost = new Ghost(13, 14, "orange");

        pillsEaten = 0;
        frutaGenerada = false;

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


    private int contarPildoras(int[][] mapa) {
        int total = 0;
        for (int[] fila : mapa) {
            for (int celda : fila) {
                if (celda == 6 || celda == 3) total++;
            }
        }
        return total;
    }


    private void siguienteNivel() {
        isPacmanDying = true;
        mostrandoMuerte = true;

        new Thread(() -> {
            try {
                Thread.sleep(2000);

                Platform.runLater(() -> {
                    for (int i = 0; i < mapa.length; i++) {
                        System.arraycopy(mapaOriginal[i], 0, mapa[i], 0, mapa[i].length);
                    }

                    pacman.setRow(17);
                    pacman.setCol(13);
                    pacman.setDirection(Pacman.Direction.RIGHT);

                    redGhost = new Ghost(11, 13, "red");
                    redGhost.liberar();
                    pinkGhost = new Ghost(14, 13, "pink");
                    blueGhost = new Ghost(13, 13, "blue");
                    orangeGhost = new Ghost(13, 14, "orange");

                    pillsEaten = 0;
                    frutaGenerada = false;

                    nivel++;
                    ghostSpeed = Math.max(100, ghostSpeed - 20);
                    actualizarLabels();

                    dibujarMapa();
                    GraphicsContext gc = gameCanvas.getGraphicsContext2D();
                    gc.setFill(Color.WHITE);
                    gc.setFont(new javafx.scene.text.Font(50));
                    gc.fillText("Next level", gameCanvas.getWidth() / 2 - 50, gameCanvas.getHeight() / 2);
                });

                Thread.sleep(2000);

                Platform.runLater(() -> {
                    isPacmanDying = false;
                    mostrandoMuerte = false;
                    dibujarMapa();
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }



    private void actualizarLabels() {
        labelLives.setText(String.valueOf(vidas));
        labelLevel.setText(String.valueOf(nivel));
    }


}
