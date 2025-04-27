package com.dam.pacmanfx.db;

import com.dam.pacmanfx.model.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteManager {

    private static final String DB_URL = "jdbc:sqlite:pacman.db";

    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            createTableIfNotExists(conn);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void createTableIfNotExists(Connection conn) {
        String sql = """
            CREATE TABLE IF NOT EXISTS score (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                score INTEGER NOT NULL
            );
            """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertScore(com.dam.pacmanfx.model.Score score) {
        String sql = "INSERT INTO score(name, score) VALUES(?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, score.getName());
            pstmt.setInt(2, score.getScore());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Score> getTopScores(int limit) {
        List<Score> scores = new ArrayList<>();
        String sql = "SELECT name, score FROM score ORDER BY score DESC LIMIT ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                int score = rs.getInt("score");
                scores.add(new Score(name, score));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }

    public static int obtenerHighScore() {
        int highScore = 0;
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:pacman.db");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(score) AS max_score FROM score");
            if (rs.next()) {
                highScore = rs.getInt("max_score");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return highScore;
    }

    public static void deleteAllScores() {
        String sql = "DELETE FROM score";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void close() {

    }
}
