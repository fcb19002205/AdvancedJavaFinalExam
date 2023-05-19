package library.table;

public class Book extends LibraryItems {

    private String author;
    private String genre;
    private int pages;
    private String ISBNNumber;

    public Book(int id, String title, int yearPublished, String typeOfItem, String author, String genre, int pages, String ISBNNumber) {
        super(id, title, yearPublished, typeOfItem);
        this.author = author;
        this.genre = genre;
        this.pages = pages;
        this.ISBNNumber = ISBNNumber;
    }

    public Book() {
    }

    public Book(String author, String genre, int pages, String ISBNNumber) {
        this.author = author;
        this.genre = genre;
        this.pages = pages;
        this.ISBNNumber = ISBNNumber;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getISBNNumber() {
        return ISBNNumber;
    }

    public void setISBNNumber(String ISBNNumber) {
        this.ISBNNumber = ISBNNumber;
    }

    @Override
    public String toString() {
        return super.toString() + "Author: " + author + "\n" +
                "Genre: " + genre + "\n" +
                "number of pages: " + pages + "\n" +
                "ISBN Number: " + ISBNNumber;
    }

}