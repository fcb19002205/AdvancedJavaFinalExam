package library;

import library.sqltable.DVDRating;
import library.table.*;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        //Korištenje lokalizacije i resoruce bundleove
        Locale.setDefault(new Locale("hrv"));

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

        //Dodavanje novih stavki u kontejner
        ItemContainer<LibraryItems> items = new ItemContainer<>();

        Book book1 = new Book(1, "Vlak u snijegu", 1988, "Book", "Mato Lovrak", "Dječja knjiga", 89, "1225896");
        Book book2 = new Book(2, "Rat i mir", 1896, "Book", "Lav Nikolajević Tolstoj", "Roman", 1545, "978030726");
        DVD dvd1 = new DVD(3, "Die hard", 1984, "DVD", 135, "James Cameron", DVDRating.PG);
        DVD dvd2 = new DVD(4, "Die hard 2", 1988, "DVD", 125, "James Cameron", DVDRating.PG);
        Magazine magazine1 = new Magazine(5, "National geographic", 2023, "Magazine", 155, "Adria Media Zagreb");
        Magazine magazine2 = new Magazine(6, "Time", 2022, "Magazine", 97, "Školska knjiga");

        items.addItem(book1);
        items.addItem(book2);
        items.addItem(dvd1);
        items.addItem(dvd2);
        items.addItem(magazine1);
        items.addItem(magazine2);

        //Printanje svih stavki
        System.out.println("All items: ");
        for (LibraryItems item : items.getAllItems()) {
            System.out.println(item.toString());
        }
        System.out.println("-----------------------------------");
        System.out.println();

        //Brisanje stavke pod ID brojem 6
        System.out.println("Removing item with ID 6...");
        items.remove(6);
        System.out.println("Item with ID 6 successfully deleted");
        System.out.println();

        //Ponovno printanje nakon brisanja stavke pod ID brojem
        System.out.println("All items after removing item with ID 6: ");
        for (LibraryItems item : items.getAllItems()) {
            System.out.println(item);
        }
        System.out.println("-----------------------------------");
        System.out.println();

        //Filtriranje stavki po određenoj riječi u stupcu "Title"
        List<LibraryItems> filteredItems = LibraryOperations.searchItemsByTitle(items.getAllItems(), "Vlak");
        System.out.println("Filtered items: ");
        for (LibraryItems item : filteredItems) {
            System.out.println(item);
        }
        System.out.println("-----------------------------------");
        System.out.println();

        //Izračun prosječne godine
        System.out.println("Average year of publication: " + LibraryOperations.getAveragePublicationYear(items.getAllItems()).orElse(0));
        System.out.println("-----------------------------------");
        System.out.println();

        //Ispis najstarije stavke
        System.out.println("Oldest item: ");
        System.out.println(LibraryOperations.getOldestItem(items.getAllItems()));
        System.out.println("-----------------------------------");
        System.out.println();

        //Sortiranje po imenu
        List<LibraryItems> sortTitle = LibraryUtils.sortByTitle(items.getAllItems());
        System.out.println("Sort by title: ");
        for (LibraryItems item : sortTitle) {
            System.out.println(item);
        }
        System.out.println("-----------------------------------");
        System.out.println();

        //Sortiranje po godini
        List<LibraryItems> sortByYear = LibraryUtils.sortByYearPublished(items.getAllItems());
        System.out.println("Sort by year published: ");
        for (LibraryItems item : sortByYear) {
            System.out.println(item);
        }
        System.out.println("-----------------------------------");
        System.out.println();

        //Ispis samo stavki koje su izdane nakon 1990 godine
        int year = 1990;
        List<LibraryItems> filteredItems1 = LibraryUtils.filterByYearPublished(items.getAllItems(), 1990);
        System.out.println("Items published after " + year + " su sljedeće: ");
        for (LibraryItems item : filteredItems1) {
            System.out.println(item);
        }
        System.out.println("-----------------------------------");
        System.out.println();


        //Simuliranje više korisnika koji pristupaju sustavu
        LibraryManager libraryManager = new LibraryManager();

        int numThreads = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        for (int i = 1; i <= numThreads; i++) {
            executorService.execute(() -> {
                String username = "User " + Thread.currentThread().getId();
                System.out.println(username + " has entered in system");

                libraryManager.borrowBook(username, "Vlak u snijegu");
                libraryManager.returnBook(username, "Rat i mir");
                System.out.println(username + " is finished with work in system.");
            });
        }

            executorService.shutdown();

    }

}
