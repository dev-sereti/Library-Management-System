import java.util.*;

// Book Class
class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
    private boolean isAvailable;

    public Book(int id, String title, String author, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = true;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}

// User Class (Base)
abstract class User {
    private int id;
    private String name;
    private String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

// Member Class
class Member extends User {
    private List<Book> borrowedBooks;

    public Member(int id, String name, String email) {
        super(id, name, email);
        this.borrowedBooks = new ArrayList<>();
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            borrowedBooks.add(book);
            book.setAvailable(false);
            System.out.println(getName() + " borrowed the book: " + book.getTitle());
        } else {
            System.out.println("Book is not available for borrowing.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            book.setAvailable(true);
            System.out.println(getName() + " returned the book: " + book.getTitle());
        } else {
            System.out.println("This book was not borrowed by the member.");
        }
    }
}

// Librarian Class
class Librarian extends User {

    public Librarian(int id, String name, String email) {
        super(id, name, email);
    }

    public void addBook(List<Book> catalog, Book book) {
        catalog.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    public void removeBook(List<Book> catalog, Book book) {
        if (catalog.remove(book)) {
            System.out.println("Book removed: " + book.getTitle());
        } else {
            System.out.println("Book not found in catalog.");
        }
    }
}

// Library Management System Class
public class LibraryManagementSystem {
    private List<Book> catalog;
    private List<User> users;

    public LibraryManagementSystem() {
        catalog = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void searchBooks(String query) {
        for (Book book : catalog) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(query.toLowerCase()) ||
                    book.getGenre().toLowerCase().contains(query.toLowerCase())) {
                System.out.println(book);
            }
        }
    }

    public void addUser(User user) {
        users.add(user);
        System.out.println("User added: " + user.getName());
    }

    public List<Book> getCatalog() {
        return catalog;
    }

    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Librarian");
            System.out.println("2. Add Member");
            System.out.println("3. Add Book");
            System.out.println("4. Search Books");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Librarian ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Librarian Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Librarian Email: ");
                    String email = scanner.nextLine();
                    library.addUser(new Librarian(id, name, email));
                }
                case 2 -> {
                    System.out.print("Enter Member ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Member Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Member Email: ");
                    String email = scanner.nextLine();
                    library.addUser(new Member(id, name, email));
                }
                case 3 -> {
                    System.out.print("Enter Book ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Book Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Book Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter Book Genre: ");
                    String genre = scanner.nextLine();
                    library.getCatalog().add(new Book(id, title, author, genre));
                    System.out.println("Book added to catalog.");
                }
                case 4 -> {
                    System.out.print("Enter search query: ");
                    String query = scanner.nextLine();
                    library.searchBooks(query);
                }
                case 5 -> {
                    System.out.print("Enter Member ID: ");
                    int memberId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Book ID: ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine();

                    User user = library.users.stream().filter(u -> u instanceof Member && u.getId() == memberId).findFirst().orElse(null);
                    Book book = library.getCatalog().stream().filter(b -> b.getId() == bookId).findFirst().orElse(null);

                    if (user instanceof Member && book != null) {
                        ((Member) user).borrowBook(book);
                    } else {
                        System.out.println("Invalid Member ID or Book ID.");
                    }
                }
                case 6 -> {
                    System.out.print("Enter Member ID: ");
                    int memberId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Book ID: ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine();

                    User user = library.users.stream().filter(u -> u instanceof Member && u.getId() == memberId).findFirst().orElse(null);
                    Book book = library.getCatalog().stream().filter(b -> b.getId() == bookId).findFirst().orElse(null);

                    if (user instanceof Member && book != null) {
                        ((Member) user).returnBook(book);
                    } else {
                        System.out.println("Invalid Member ID or Book ID.");
                    }
                }
                case 7 -> {
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
