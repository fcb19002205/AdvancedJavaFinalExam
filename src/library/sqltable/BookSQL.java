package library.sqltable;

import library.table.Book;

import java.sql.*;

public class BookSQL {

    public static void addBooks(Book book) {

        String sql = "INSERT INTO books (id, author, genre, pages, isbn) VALUES (?, ?, ? ,?, ?)";
        ;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "demo", "secret");
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, book.getId());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getGenre());
            ps.setInt(4, book.getPages());
            ps.setString(5, book.getISBNNumber());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int bookID = generatedKeys.getInt(1);
                book.setId(bookID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBook(Book book) {

        String sql = "UPDATE books SET author = ?, genre = ?, pages = ?, isbn = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "demo", "secret");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getAuthor());
            ps.setString(2, book.getGenre());
            ps.setInt(3, book.getPages());
            ps.setString(4, book.getISBNNumber());
            ps.setInt(5, book.getId());

            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
