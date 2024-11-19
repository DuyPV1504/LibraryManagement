package models;

public class Book {
    private int id;
    private String bookName;
    private String author;
    private String publisher;
    private int publishYear;
    private int totalBooks;
    private int availableBooks;

    public Book() {

    }
    public Book(String bookName, String author, String publisher, int publishYear, int totalBooks, int availableBooks) {
        if (totalBooks < 0 || availableBooks < 0) {
            throw new IllegalArgumentException("Total books and available books cannot be negative.");
        }
        if (availableBooks > totalBooks) {
            throw new IllegalArgumentException("Available books cannot exceed total books.");
        }
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.totalBooks = totalBooks;
        this.availableBooks = availableBooks;
    }

    public Book(int id, String bookName, String author, String publisher, int publishYear, int totalBooks, int availableBooks) {
        this(bookName, author, publisher, publishYear, totalBooks, availableBooks);
        this.id = id;
    }

    public boolean borrowBook() {
        if (availableBooks > 0) {
            availableBooks--;
            return true;
        }
        throw new IllegalStateException("Cannot borrow book: No available copies.");
    }

    public void returnBook() {
        if (availableBooks < totalBooks) {
            availableBooks++;
        } else {
            throw new IllegalStateException("All copies are already returned. No changes made.");
        }
    }

    public boolean isAvailable() {
        return availableBooks > 0;
    }

    public double getBorrowedPercentage() {
        if (totalBooks == 0) return 0.0;
        return ((totalBooks - availableBooks) / (double) totalBooks) * 100;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() { return bookName; }

    public void setBookName(String bookName) { this.bookName = bookName; }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public String getPublisher() { return publisher; }

    public void setPublisher(String publisher) { this.publisher = publisher; }

    public int getPublishYear() { return publishYear; }

    public void setPublishYear(int publishYear) { this.publishYear = publishYear; }

    public int getTotalBooks() { return totalBooks; }

    public void setTotalBooks(int totalBooks) { this.totalBooks = totalBooks; }

    public int getAvailableBooks() { return availableBooks; }

    public void setAvailableBooks(int availableBooks) { this.availableBooks = availableBooks; }

    // Override toString for debugging
    @Override
    public String toString() {
        return String.format(
                "Book[id=%d, name=%s, author=%s, publisher=%s, year=%d, available=%d/%d]",
                id, bookName, author, publisher, publishYear, availableBooks, totalBooks
        );
    }
}