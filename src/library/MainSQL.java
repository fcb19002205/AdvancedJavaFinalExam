package library;

import library.sqltable.DVDRating;
import library.sqltable.LibraryItemSQL;
import library.table.Book;
import library.table.DVD;
import library.table.LibraryItems;
import library.table.Magazine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainSQL {

    public static void main(String[] args) {

        //Korištenje lokalizacije i resoruce bundleove
        Locale.setDefault(new Locale("eng"));

        ResourceBundle bundle = ResourceBundle.getBundle("message");

        System.out.println();
        String welcomeMessage = bundle.getString("welcomeMessage");
        String addButtonLabel = bundle.getString("addButtonLabel");
        String removeButtonLabel = bundle.getString("removeButtonLabel");
        String updateButtonLabel = bundle.getString("updateButtonLabel");

        System.out.println(welcomeMessage);
        System.out.println("Add button: " + addButtonLabel);
        System.out.println("Remove button: " + removeButtonLabel);
        System.out.println("Update button: " + updateButtonLabel);
        System.out.println();

        //Dodavanje novih items u mySQL
        Book book1 = new Book(1, "Vlak u snijegu", 1988, "Book", "Mato Lovrak", "Dječja knjiga", 89, "1225896");
        Book book2 = new Book(2, "Rat i mir", 1896, "Book", "Lav Nikolajević Tolstoj", "Roman", 1545, "978030726");
        DVD dvd1 = new DVD(3, "Die hard", 1984, "DVD", 135, "James Cameron", DVDRating.PG);
        DVD dvd2 = new DVD(4, "Die hard 2", 1988, "DVD", 125, "James Cameron", DVDRating.PG);
        Magazine magazine1 = new Magazine(5, "National geographic", 2023, "Magazine", 155, "Adria Media Zagreb");
        Magazine magazine2 = new Magazine(6, "Time", 2022, "Magazine", 97, "Školska knjiga");

        LibraryItemSQL.addItems(book1);
        LibraryItemSQL.addItems(book2);
        System.out.println("Book(s) successfully added");
        System.out.println();
        LibraryItemSQL.addItems(dvd1);
        LibraryItemSQL.addItems(dvd2);
        System.out.println("DVD(s) successfully added");
        System.out.println();
        LibraryItemSQL.addItems(magazine1);
        LibraryItemSQL.addItems(magazine2);
        System.out.println("Magazine(s) successfully added");
        System.out.println();

        //printanje svih stavaka iz mySQL
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "demo", "secret")) {
            System.out.println("Printing all items: ");
            LibraryItemSQL.printAllItems(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //ažuriranje knjige u mySQL
        Book newBook = new Book(1, "Koko u Parizu", 1972, "Book", "Ivan Kušan", "Lektira", 132, "1247588996");
        LibraryItemSQL.updateItem(newBook);
        System.out.println("--------------------------");

        //brisanje knjige u mySQL
        LibraryItemSQL.deleteItem(2);
        System.out.println("--------------------------");

        //Pretraga stavki po naslovu u mySQL
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "demo", "secret")) {
            String title = "Die hard";
            List<LibraryItems> itemByTitle = LibraryItemSQL.searchItems(conn, title, null, 0);
            System.out.println("Print items by Title " + title + ": ");
            LibraryItemSQL.printItems(itemByTitle);
            System.out.println();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Pretraga stavki po autoru u mySQL
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "demo", "secret")) {
            String author = "Ivan Kušan";
            List<LibraryItems> itemByAuthor = LibraryItemSQL.searchItems(conn, null, author, 0);
            System.out.println("Print items by author " + author + ": ");
            LibraryItemSQL.printItems(itemByAuthor);
            System.out.println();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Pretraga po godini u mySQL
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "demo", "secret")) {
            int year = 1988;
            List<LibraryItems> itemByYear = LibraryItemSQL.searchItems(conn, null, null, year);
            System.out.println("Print items by year " + year + ": ");
            LibraryItemSQL.printItems(itemByYear);
            System.out.println();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
