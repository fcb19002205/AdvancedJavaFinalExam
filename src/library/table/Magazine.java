package library.table;

public class Magazine extends LibraryItems {

    int issueNumber;
    String publisher;

    public Magazine(int id, String title, int yearPublished, String typeOfItem, int issueNumber, String publisher) {
        super(id, title, yearPublished, typeOfItem);
        this.issueNumber = issueNumber;
        this.publisher = publisher;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return super.toString() +
                "issueNumber:  " + issueNumber + "\n" +
                "publisher: " + publisher;
    }

}
