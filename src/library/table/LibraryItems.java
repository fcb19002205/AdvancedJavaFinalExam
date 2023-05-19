package library.table;

public class LibraryItems {

    private int id;
    private String title;
    private int yearPublished;
    private String typeOfItem;

    public LibraryItems(int id, String title, int yearPublished, String typeOfItem) {
        this.id = id;
        this.title = title;
        this.yearPublished = yearPublished;
        this.typeOfItem = typeOfItem;
    }

    public LibraryItems() {
    }

    public LibraryItems(String title, int yearPublished, String typeOfItem) {
        this.title = title;
        this.yearPublished = yearPublished;
        this.typeOfItem = typeOfItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {

        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getTypeOfItem() {
        return typeOfItem;
    }

    public void setTypeOfItem(String typeOfItem) {
        this.typeOfItem = typeOfItem;
    }

    public void display() {
        System.out.println("ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Year: " + yearPublished);
        System.out.println("Item type: " + typeOfItem);
    }

    @Override
    public String toString() {
        return  "\n" +
                "id: " + id + "\n" +
                "title: " + title + "\n" +
                "year: " + yearPublished + "\n" +
                "typeOfItem: " + typeOfItem + "\n";
    }

}
