package models;

import java.time.LocalDate;

public class Loan {
    private int transactionId;
    private String userAccount;
    private int bookId;
    private LocalDate borrowDate;
    private LocalDate endDate;
    private LocalDate returnDate;
    private LoanStatus status;

    public enum LoanStatus {
        BORROWED,
        RETURNED
    }

    public Loan() {

    }

    /**
     * tao giao dich.
     *
     * @param userAccount ten
     * @param bookId      id
     * @param borrowDate  ngay
     * @param endDate     ngay tra
     */
    public Loan(String userAccount, int bookId, LocalDate borrowDate, LocalDate endDate) {
        this.userAccount = userAccount;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.endDate = endDate;
        this.status = LoanStatus.BORROWED;
    }

    /**
     * tao gd bang id.
     *
     * @param transactionId id
     * @param userAccount   ten nguoi dung
     * @param bookId        id sach
     * @param borrowDate    ngay muon
     * @param returnDate    ngay tra
     * @param status        tinh trang
     */
    public Loan(int transactionId, String userAccount, int bookId, LocalDate borrowDate, LocalDate returnDate, LoanStatus status) {
        this.transactionId = transactionId;
        this.userAccount = userAccount;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    /**
     * tao loan 3.
     *
     * @param transactionId id
     * @param userAccount   ten
     * @param bookId        id
     * @param borrowDate    ngay
     * @param endDate       han
     * @param returnDate    tra
     * @param status        tinh trang
     */
    public Loan(int transactionId, String userAccount, int bookId, LocalDate borrowDate, LocalDate endDate, LocalDate returnDate, LoanStatus status) {
        this.transactionId = transactionId;
        this.userAccount = userAccount;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.endDate = endDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public boolean isOverdue() {
        return LocalDate.now().isAfter(endDate) && status == LoanStatus.BORROWED;
    }

    // Phương thức toString
    @Override
    public String toString() {
        return "Loan{" +
                "transactionId=" + transactionId +
                ", userAccount='" + userAccount + '\'' +
                ", bookId=" + bookId +
                ", borrowDate=" + borrowDate +
                ", endDate=" + endDate +
                ", returnDate=" + (returnDate != null ? returnDate : "Chưa trả") +
                ", status=" + status +
                '}';
    }
}

