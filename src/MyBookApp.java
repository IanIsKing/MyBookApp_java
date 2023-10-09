// Note to Lecturer - please read my README.md file for more information about this project

import java.util.List;
import java.util.Scanner;
import java.sql.*;

public class MyBookApp {
    // Database credentials
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/ebookstore?useSSL=false";
    private static final String DATABASE_USER = "otheruser";
    private static final String DATABASE_PASSWORD = "swordfish";

    public static void main(String[] args) {
        System.out.println("Welcome to the Book Manager Application\n");
        // Load the JDBC driver
        try (Scanner scanner = new Scanner(System.in);
                Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {
            BookDAO bookDAO = new BookDAO(connection);
            int option;
            do {
                // Display menu graphics
                printMainMenu();
                option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        addBook(scanner, bookDAO);
                        break;
                    case 2:
                        updateBook(scanner, bookDAO);
                        break;
                    case 3:
                        deleteBook(scanner, bookDAO);
                        break;
                    case 4:
                        searchBook(scanner, bookDAO);
                        break;
                    case 5:
                        listBooks(bookDAO);
                        break;
                    case 0:
                        System.out.println("Bye!");
                        break;
                    default:
                        System.out.println("Error! Not a valid command.\n");
                        break;
                }
            } while (option != 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Prints the main menu
    private static void printMainMenu() {
        System.out.println("1 - Add a book");
        System.out.println("2 - Update a book");
        System.out.println("3 - Delete a book");
        System.out.println("4 - Search for a book");
        System.out.println("5 - List books");
        System.out.println("0 - Exit\n");
        System.out.print("Enter a command: ");
    }

    // Prints the add book menu
    private static void addBook(Scanner scanner, BookDAO bookDAO) throws SQLException {
        System.out.println("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.println("Enter Book Author: ");
        String author = scanner.nextLine();
        System.out.println("Enter Book Quantity: ");
        int qty = scanner.nextInt();

        // Create a new book object from the users input
        Book book = new Book(title, author, qty);
        // Add the book to the database
        bookDAO.addBook(book);
        System.out.println("Book added successfully with ID: " + book.getId());
    }

    // Prints the update book menu
    private static void updateBook(Scanner scanner, BookDAO bookDAO) throws SQLException {
        System.out.println("Enter the ID of the book you want to update: ");
        int updateId = scanner.nextInt();
        scanner.nextLine();

        // Check if the book exists
        Book existingBook = bookDAO.getBookById(updateId);
        if (existingBook == null) {
            System.out.println("No book found with the given ID!");
            return;
        }

        // Get the new book details from the user
        System.out.println("Enter new title for " + existingBook.getTitle() + " or press enter to keep the same: ");
        String updateTitle = scanner.nextLine();
        System.out.println("Enter new author for " + existingBook.getAuthor() + " or press enter to keep the same: ");
        String updateAuthor = scanner.nextLine();
        System.out.println("Enter new quantity for " + existingBook.getQty() + " or -1 to keep the same: ");
        int updateQty = scanner.nextInt();

        if (updateTitle.isEmpty())
            updateTitle = existingBook.getTitle();
        if (updateAuthor.isEmpty())
            updateAuthor = existingBook.getAuthor();
        if (updateQty == -1)
            updateQty = existingBook.getQty();

        // Create a new book object with the updated details
        Book updatedBook = new Book(updateTitle, updateAuthor, updateQty);
        // Set the ID of the book to be updated
        updatedBook.setId(updateId);
        // Update the book in the database
        bookDAO.updateBook(updatedBook);
        System.out.println("Book updated successfully!");
    }

    // Prints the delete book menu
    private static void deleteBook(Scanner scanner, BookDAO bookDAO) throws SQLException {
        System.out.println("Enter the ID of the book you want to delete: ");
        int deleteId = scanner.nextInt();
        scanner.nextLine();

        // Check if the book exists
        Book existingBook = bookDAO.getBookById(deleteId);
        if (existingBook == null) {
            System.out.println("No book found with the given ID!");
            return;
        }

        // Delete the book from the database
        bookDAO.deleteBook(deleteId);
        System.out.println("Book deleted successfully!");
    }

    // Prints the search book menu
    private static void searchBook(Scanner scanner, BookDAO bookDAO) throws SQLException {
        System.out.println("Search by: 1 - ID, 2 - Title,");
        int searchOption = scanner.nextInt();
        scanner.nextLine();

        Book existingBook = null;

        switch (searchOption) {
            // Search by ID
            case 1:
                System.out.println("Enter the ID of the book you want to search: ");
                int searchId = scanner.nextInt();
                scanner.nextLine();

                // Check if the book exists
                existingBook = bookDAO.getBookById(searchId);
                if (existingBook == null) {
                    System.out.println("No book found with the given ID!");
                    return;
                }
                break;
            // Search by Title
            case 2:
                System.out.println("Enter the Title of the book you want to search: ");
                String searchTitle = scanner.nextLine();
                // Check if the book exists
                existingBook = bookDAO.getBookByTitle(searchTitle);
                if (existingBook == null) {
                    System.out.println("No book found with the given Title!");
                    return;
                }
                break;
            default:
                System.out.println("Error! Not a valid command.\n");
                break;
        }

        // Print the book details
        System.out.println("Book found: " + existingBook.getTitle() + ", " + existingBook.getAuthor() + " - "
                + existingBook.getQty());
    }

    // Prints the list books menu
    private static void listBooks(BookDAO bookDAO) throws SQLException {
        List<Book> books = bookDAO.getAllBooks();
        System.out.println("\n------Table----------");
        for (Book b : books) {
            System.out.println(b.getId() + " - " + b.getTitle() + ", " + b.getAuthor() + " - " + b.getQty());
        }
        System.out.println("---------------------\n");
    }
}
