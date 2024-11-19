package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;

public class Admin extends User {

    private ObservableList<User> userList;
    private ObservableList<Book> bookList;
    private ObservableList<Loan> loanList;

    public Admin(int id, String surname, String lastname, LocalDate dateOfBirth, String gender, String email,
                 String userName, String userAccount, String roles, int warning) {
        super(id, surname, lastname, dateOfBirth, gender, email, userName, userAccount, roles, warning);
        this.userList = FXCollections.observableArrayList();
        this.bookList = FXCollections.observableArrayList();
        this.loanList = FXCollections.observableArrayList();
    }

    public void addUser(User newUser) {
        userList.add(newUser);
        System.out.println("User added: " + newUser);
    }

    public void removeUser(User user) {
        userList.remove(user);
        System.out.println("User removed: " + user);
    }

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

    public void addBook(Book newBook) {
        bookList.add(newBook);
        System.out.println("Book added: " + newBook);
    }

    public void removeBook(Book book) {
        bookList.remove(book);
        System.out.println("Book removed: " + book);
    }

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

    public void addTransaction(Loan newTransaction) {
        loanList.add(newTransaction);
        System.out.println("Loan added: " + newTransaction);
    }

    public void removeTransaction(Loan transaction) {
        loanList.remove(transaction);
        System.out.println("Loan removed: " + transaction);
    }

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

    public ObservableList<User> getUserList() {
        return userList;
    }

    public ObservableList<Book> getBookList() {
        return bookList;
    }

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
