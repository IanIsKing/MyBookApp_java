public class Book {

    // create a book object
    private int id;
    private String title;
    private String author;
    private int qty;

    // constructor
    public Book(String title, String author, int qty) {
        this.title = title;
        this.author = author;
        this.qty = qty;
    }

    // getters and setters
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getQty() {
        return qty;
    }
}
