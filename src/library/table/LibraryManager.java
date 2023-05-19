package library.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryManager {

    private Map<String, Integer> borrowedBooks;
    private List<LibraryItems> inventory;

    public LibraryManager() {
        borrowedBooks = new HashMap<>();
    }

    public synchronized void borrowBook(String username, String bookTitle) {
        System.out.println(username + " was borrowed book " + bookTitle);
        borrowedBooks.put(bookTitle, borrowedBooks.getOrDefault(bookTitle, 0) + 1);
    }

    public synchronized void returnBook(String username, String bookTitle) {
        System.out.println(username + " returned book " + bookTitle);
        int numBorrowed = borrowedBooks.getOrDefault(bookTitle, 0);
        if (numBorrowed > 0) {
            borrowedBooks.put(bookTitle, numBorrowed);
        }
    }

}
