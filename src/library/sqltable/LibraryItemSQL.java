package library.sqltable;

import library.table.Book;
import library.table.DVD;
import library.table.LibraryItems;
import library.table.Magazine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryItemSQL {

    public static void addItems(LibraryItems item) {

        String sql = "INSERT INTO library_items (title, publication_year, item_type) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "demo", "secret");
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, item.getTitle());
            ps.setInt(2, item.getYearPublished());
            ps.setString(3, item.getTypeOfItem());

            ps.executeUpdate();


            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int itemID = generatedKeys.getInt(1);
                item.setId(itemID);
            }

            if (item instanceof Book) {
                BookSQL.addBooks((Book) item);
            } else if (item instanceof Magazine) {
                MagazineSQL.addMagazine((Magazine) item);
            } else if (item instanceof DVD) {
                DVDSQL.addDVDs((DVD) item);
            } else {
                System.out.println("Unsupported item type");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void deleteItem(int itemID) {

        String sql = "DELETE FROM library_items WHERE id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "demo", "secret");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, itemID);

            ps.executeUpdate();

            System.out.println("Deleted item with itemID: " + itemID);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateItem(LibraryItems item) {

        String sql = "UPDATE library_items SET title = ?, publication_year = ?, item_type = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "demo", "secret");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getTitle());
            ps.setInt(2, item.getYearPublished());
            ps.setString(3, item.getTypeOfItem());
            ps.setInt(4, item.getId());

            ps.executeUpdate();

            if (item instanceof Book) {
                BookSQL.updateBook((Book) item);
                System.out.println("Successfully updated: " + item);
            } else if (item instanceof Magazine) {
                MagazineSQL.updateMagazine((Magazine) item);
                System.out.println("Successfully updated: " + item);
            } else if (item instanceof DVD) {
                DVDSQL.updateDVD((DVD) item);
                System.out.println("Successfully updated: " + item);
            } else {
                System.out.println("Unsupported item type");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void printAllItems(Connection conn) {

        String sql = "SELECT * FROM library_items";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int itemID = rs.getInt("id");
                String itemTitle = rs.getString("title");
                int itemYear = rs.getInt("publication_year");
                String itemType = rs.getString("item_type");

                System.out.println("ID: " + itemID);
                System.out.println("Title: " + itemTitle);
                System.out.println("Year: " + itemYear);
                System.out.println("Type: " + itemType);

                String bookSql = "SELECT * FROM books WHERE id = ?";
                try (PreparedStatement ps1 = conn.prepareStatement(bookSql)) {
                    ps1.setInt(1, itemID);

                    try (ResultSet booksRs = ps1.executeQuery()) {
                        if (booksRs.next()) {
                            String bookAuthor = booksRs.getString("author");
                            String bookGenre = booksRs.getString("genre");
                            int bookPages = booksRs.getInt("pages");
                            int bookISBN = booksRs.getInt("isbn");

                            System.out.println("Author: " + bookAuthor);
                            System.out.println("Genre: " + bookGenre);
                            System.out.println("Pages: " + bookPages);
                            System.out.println("ISBN: " + bookISBN);
                        }
                    }

                }

                String magazineSql = "SELECT * FROM magazines WHERE id = ?";

                try (PreparedStatement ps2 = conn.prepareStatement(magazineSql)) {
                    ps2.setInt(1, itemID);

                    try (ResultSet magazinesRs = ps2.executeQuery()) {
                        if (magazinesRs.next()) {
                            int magazinesIssueNumber = magazinesRs.getInt("issue_number");
                            String magazinesPublisher = magazinesRs.getString("publisher");

                            System.out.println("Issue number: " + magazinesIssueNumber);
                            System.out.println("Publisher: " + magazinesPublisher);
                        }
                    }

                }

                String dvdSql = "SELECT * FROM dvds WHERE id = ?";

                try (PreparedStatement ps3 = conn.prepareStatement(dvdSql)) {
                    ps3.setInt(1, itemID);

                    try (ResultSet dvdRs = ps3.executeQuery()) {
                        if (dvdRs.next()) {
                            int dvdDuration = dvdRs.getInt("duration_minutes");
                            String dvdDirector = dvdRs.getString("director");
                            String dvdRating = dvdRs.getString("rating");

                            System.out.println("Duration: " + dvdDuration);
                            System.out.println("Director: " + dvdDirector);
                            System.out.println("Rating: " + dvdRating);
                        }
                    }

                }
                System.out.println("--------------------------------");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<LibraryItems> searchItems(Connection conn, String title, String author, int year) {
        List<LibraryItems> items = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM library_items li");
        sql.append(" LEFT JOIN books b ON li.id = b.id");
        sql.append(" LEFT JOIN dvds d ON li.id = d.id");
        sql.append(" LEFT JOIN magazines m ON li.id = m.id");
        sql.append(" WHERE 1=1");

        if (title != null && !title.isEmpty()) {
            sql.append(" AND li.title LIKE ?");
        }
        if (author != null && !author.isEmpty()) {
            sql.append(" AND b.author LIKE ?");
        }
        if (year > 0) {
            sql.append(" AND li.publication_year = ?");
        }

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int parameterIndex = 1;

            if (title != null && !title.isEmpty()) {
                ps.setString(parameterIndex++, "%" + title + "%");
            }
            if (author != null && !author.isEmpty()) {
                ps.setString(parameterIndex++, "%" + author + "%");
            }
            if (year > 0) {
                ps.setInt(parameterIndex, year);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int itemId = rs.getInt("li.id");
                    String itemTitle = rs.getString("li.title");
                    int itemYear = rs.getInt("li.publication_year");
                    String itemType = rs.getString("li.item_type");

                    LibraryItems item;
                    switch (itemType) {
                        case "Book":
                            String authorName = rs.getString("b.author");
                            String genre = rs.getString("b.genre");
                            int pages = rs.getInt("b.pages");
                            String isbnNumber = rs.getString("b.isbn");
                            item = new Book(itemId, itemTitle, itemYear, itemType, authorName, genre, pages, isbnNumber);
                            break;
                        case "DVD":
                            int duration = rs.getInt("d.duration_minutes");
                            String director = rs.getString("d.director");
                            String ratingStr = rs.getString("d.rating");
                            DVDRating rating = DVDRating.valueOf(ratingStr);
                            item = new DVD(itemId, itemTitle, itemYear, itemType, duration, director, rating);
                            break;
                        case "Magazine":
                            int issueNumber = rs.getInt("m.issue_number");
                            String publisher = rs.getString("m.publisher");
                            item = new Magazine(itemId, itemTitle,itemYear, itemType, issueNumber, publisher);
                            break;
                        default:
                            item = new LibraryItems(itemId, itemTitle, itemYear, itemType);
                            break;
                    }

                    items.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

        public static void printItems (List<LibraryItems> items) {
            if (items.isEmpty()) {
                System.out.println("No items found.");
            } else {
                for (LibraryItems item : items) {
                    System.out.println("ID: " + item.getId());
                    System.out.println("Title: " + item.getTitle());
                    System.out.println("Year: " + item.getYearPublished());
                    System.out.println("Item type: " + item.getTypeOfItem());

                    if (item.getClass() == Book.class) {
                        Book book = (Book) item;
                        System.out.println("Author: " + book.getAuthor());
                        System.out.println("Genre: " + book.getGenre());
                        System.out.println("Pages: " + book.getPages());
                        System.out.println("ISBN: " + book.getISBNNumber());
                    } else if (item.getClass() == DVD.class) {
                        DVD dvd = (DVD) item;
                        System.out.println("Duration: " + dvd.getDuration());
                        System.out.println("Director: " + dvd.getDirector());
                        System.out.println("Rating: " + dvd.getScore());
                    } else if (item.getClass() == Magazine.class) {
                        Magazine magazine = (Magazine) item;
                        System.out.println("Issue Number: " + magazine.getIssueNumber());
                        System.out.println("Publisher: " + magazine.getPublisher());
                    }

                    System.out.println("----------------------");
                }
            }
        }
    }
