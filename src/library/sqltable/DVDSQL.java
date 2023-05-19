package library.sqltable;

import library.table.DVD;

import java.sql.*;

public class DVDSQL {

    public static void addDVDs(DVD dvd) {

        String sql = "INSERT INTO dvds (id, duration_minutes, director, rating) VALUES (?, ?, ? ,?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "demo", "secret");
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, dvd.getId());
            ps.setInt(2, dvd.getDuration());
            ps.setString(3, dvd.getDirector());
            ps.setString(4, dvd.getScore().name());


            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int dvdID = generatedKeys.getInt(1);
                dvd.setId(dvdID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateDVD(DVD dvd) {

        String sql = "UPDATE dvds SET duration_minutes = ?, director = ?, rating = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "demo", "secret");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dvd.getDuration());
            ps.setString(2, dvd.getDirector());
            ps.setString(3, dvd.getScore().name());
            ps.setInt(4, dvd.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void displayItems(DVD dvd) {
        System.out.println("Duration: " + dvd.getDuration());
        System.out.println("Director: " + dvd.getDirector());
        System.out.println("Score: " + dvd.getScore().name());
    }
}
