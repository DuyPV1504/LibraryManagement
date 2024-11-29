package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class Admin extends User {

    private ObservableList<User> userList;
    private ObservableList<Book> bookList;
    private ObservableList<Loan> loanList;

    /**
     * Tạo admin.
     *
     * @param id          id
     * @param surname     họ
     * @param lastname    tên
     * @param dateOfBirth sn
     * @param gender      giới tính
     * @param email       mail
     * @param userName    tên dùng trong app
     * @param userAccount tên đăng nhập
     * @param roles       vai trò
     * @param warning     mức cảnh báo
     */
    public Admin(int id, String surname, String lastname,
                 LocalDate dateOfBirth, String gender, String email,
                 String userName, String userAccount, String roles, int warning) {
        super(id, surname, lastname, dateOfBirth, gender, email, userName, userAccount, roles, warning);
        this.userList = FXCollections.observableArrayList();
        this.bookList = FXCollections.observableArrayList();
        this.loanList = FXCollections.observableArrayList();
    }

    /**
     * Thêm người dùng.
     *
     * @param newUser user
     */
    public void addUser(User newUser) {
        userList.add(newUser);
        System.out.println("User added: " + newUser);
    }

    /**
     * xoa nguoi dung.
     *
     * @param user user
     */
    public void removeUser(User user) {
        userList.remove(user);
        System.out.println("User removed: " + user);
    }

    /**
     * update nguoi dung.
     *
     * @param updatedUser update
     */
    public void updateUser(User updatedUser) {
        for (User user : userList) {
            if (user.getId() == updatedUser.getId()) {
                user.setSurname(updatedUser.getSurname());
                user.setLastname(updatedUser.getLastname());
                user.setDateOfBirth(updatedUser.getDateOfBirth());
                user.setGender(updatedUser.getGender());
                user.setEmail(updatedUser.getEmail());
                user.setUserAccount(updatedUser.getUserAccount());
                user.setWarning(updatedUser.getWarning());
                System.out.println("User information updated: " + user);
                break;
            }
        }
    }

    /**
     * them sach.
     *
     * @param newBook sach
     */
    public void addBook(Book newBook) {
        bookList.add(newBook);
        System.out.println("Book added: " + newBook);
    }

    /**
     * them sach.
     *
     * @param book sach
     */
    public void removeBook(Book book) {
        bookList.remove(book);
        System.out.println("Book removed: " + book);
    }

    /**
     * update sach.
     *
     * @param updatedBook sach
     */
    public void updateBook(Book updatedBook) {
        for (Book book : bookList) {
            if (book.getId() == updatedBook.getId()) { // Compare the ID of the book
                book.setBookName(updatedBook.getBookName());
                book.setAuthor(updatedBook.getAuthor());
                book.setPublisher(updatedBook.getPublisher());
                book.setPublishYear(updatedBook.getPublishYear());
                book.setTotalBooks(updatedBook.getTotalBooks());
                System.out.println("Book information updated: " + book);
                break;
            }
        }
    }

    /**
     * them giao dich.
     *
     * @param newTransaction giao dich
     */
    public void addTransaction(Loan newTransaction) {
        loanList.add(newTransaction);
        System.out.println("Loan added: " + newTransaction);
    }

    /**
     * xoa gd.
     *
     * @param transaction gd
     */
    public void removeTransaction(Loan transaction) {
        loanList.remove(transaction);
        System.out.println("Loan removed: " + transaction);
    }

    /**
     * update gd.
     *
     * @param updatedTransaction gd
     */
    public void updateTransaction(Loan updatedTransaction) {
        for (Loan loan : loanList) {
            if (loan.getTransactionId() == updatedTransaction.getTransactionId()) { // Compare transaction ID
                loan.setBorrowDate(updatedTransaction.getBorrowDate());
                loan.setEndDate(updatedTransaction.getEndDate());
                loan.setReturnDate(updatedTransaction.getReturnDate());
                loan.setStatus(updatedTransaction.getStatus());
                System.out.println("Loan information updated: " + loan);
                break;
            }
        }
    }

    /**
     * list nguoi.
     *
     * @return nguoi
     */
    public ObservableList<User> getUserList() {
        return userList;
    }

    /**
     * list sach.
     *
     * @return sach
     */
    public ObservableList<Book> getBookList() {
        return bookList;
    }

    /**
     * list gd.
     *
     * @return gd
     */
    public ObservableList<Loan> getLoanList() {
        return loanList;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id='" + getId() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", lastname='" + getLastname() + '\'' +
                ", dateOfBirth=" + getDateOfBirth() +
                ", gender='" + getGender() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", userName='" + getUserName() + '\'' +
                ", userAccount='" + getUserAccount() + '\'' +
                ", roles='" + getRoles() + '\'' +
                ", warning=" + getWarning() +
                '}';
    }
}
