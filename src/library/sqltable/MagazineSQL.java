package library.sqltable;

import library.table.Magazine;

import java.sql.*;

public class MagazineSQL {

    public static void addMagazine (Magazine magazine) {

        String sql = "INSERT INTO magazines (id, issue_number, publisher) VALUES (?, ?, ?)";;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "demo", "secret");
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, magazine.getId());
            ps.setInt(2, magazine.getIssueNumber());
            ps.setString(3, magazine.getPublisher());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int magazineID = generatedKeys.getInt(1);
                magazine.setId(magazineID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateMagazine (Magazine magazine) {

        String sql = "UPDATE magazines SET issue_number = ?, publisher = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "demo", "secret");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, magazine.getIssueNumber());
            ps.setString(2, magazine.getPublisher());
            ps.setInt(3, magazine.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
