import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    // Database credentials
    private Connection connection;

    // Constructor for BookDAO
    public BookDAO(Connection connection) {
        this.connection = connection;
    }

    // Add a book to the database
    public void addBook(Book book) throws SQLException {
        // The ? is a placeholder for a value that we will set later
        String sql = "INSERT INTO books (title, author, qty) VALUES (?, ?, ?)";
        // add the book to the database and get the id back using try-with-resources
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getQty());
            int affectedRows = statement.executeUpdate();

            // check to see if the book was added
            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }

            // get the id of the book we just added
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating book failed, no ID obtained.");
                }
            }
        }
    }

    // Get a book by its id
    public Book getBookById(int id) throws SQLException {
        // The ? is a placeholder for a value that we will set later
        String sql = "SELECT id, title, author, qty FROM books WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // set the value of the placeholder to the id we are looking for
            statement.setInt(1, id);
            // execute the query and return the book
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    int qty = resultSet.getInt("qty");
                    Book book = new Book(title, author, qty);
                    book.setId(id);
                    return book;
                }
            }
        }
        return null;
    }

    // Get all books from the database
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT id, title, author, qty FROM books";
        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int qty = resultSet.getInt("qty");
                Book book = new Book(title, author, qty);
                book.setId(id);
                books.add(book);
            }
        }
        return books;
    }

    // Update a book in the database
    public void updateBook(Book book) throws SQLException {
        String sql = "UPDATE books SET title=?, author=?, qty=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getQty());
            statement.setInt(4, book.getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Update failed, no rows affected.");
            }
        }
    }

    // Delete a book from the database
    public void deleteBook(int deleteId) {
        String sql = "DELETE FROM books WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, deleteId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Delete failed, no rows affected.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
