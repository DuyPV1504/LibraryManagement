package models;

public class Book {
    private int id;
    private String bookName;
    private String author;
    private String publisher;
    private int publishYear;
    private int availableBooks;
    private int totalBooks;

    public Book() {
    }

    /**
     * tao sach.
     *
     * @param bookName       ten
     * @param author         tgia
     * @param publisher      nxs
     * @param publishYear    nam xb
     * @param availableBooks ton kho
     * @param totalBooks     tong sach
     */
    public Book(String bookName, String author, String publisher, int publishYear,
                int availableBooks, int totalBooks) {
        if (totalBooks < 0 || availableBooks < 0) {
            throw new IllegalArgumentException("Total books and available books cannot be negative.");
        }
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.availableBooks = availableBooks;
        this.totalBooks = totalBooks;
    }

    /**
     * them sach voi id.
     *
     * @param id             id
     * @param bookName       ten
     * @param author         tgia
     * @param publisher      nxs
     * @param publishYear    nam xb
     * @param availableBooks sach con lai
     * @param totalBooks     tong sach
     */
    public Book(int id, String bookName, String author, String publisher,
                int publishYear, int availableBooks, int totalBooks) {
        this(bookName, author, publisher, publishYear, availableBooks, totalBooks);
        this.id = id;
    }

    /**
     * muon sach.
     *
     * @return logic
     */
    public boolean borrowBook() {
        if (availableBooks > 0) {
            availableBooks--;
            return true;
        }
        throw new IllegalStateException("Cannot borrow book: No available copies.");
    }

    /**
     * tra sach.
     */
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public int getAvailableBooks() {
        return availableBooks;
    }

    public void setAvailableBooks(int availableBooks) {
        this.availableBooks = availableBooks;
    }

    public int getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(int totalBooks) {
        this.totalBooks = totalBooks;
    }


    // Override toString for debugging
    @Override
    public String toString() {
        return String.format(
                "Book[id=%d, name=%s, author=%s, publisher=%s, year=%d, available=%d/%d]",
                id, bookName, author, publisher, publishYear, availableBooks, totalBooks
        );
    }
}
