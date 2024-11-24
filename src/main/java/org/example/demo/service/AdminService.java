package org.example.demo.service;

import models.Book;
import models.Loan;
import models.User;
import org.example.demo.data.AdminRepository;

import java.util.List;

public class AdminService {
    private final AdminRepository adminRepository = new AdminRepository();
    public List<User> searchUser(String id, String surname, String lastname, String dateOfBirth, String gender, String email, String userAccount) {
        return adminRepository.searchUserByKeyword(id, surname, lastname, dateOfBirth, gender, email, userAccount);
    }

    public List<Book> searchBook(String id, String bookName, String publishedYear, String availableBooks, String totalBooks, String author, String publisher) {
        return adminRepository.searchBookByKeyword(id, bookName, publishedYear, availableBooks, totalBooks, author, publisher);
    }

    public List<Book> getAllBooks() {
        return adminRepository.getAllBooks();
    }

    public List<Loan> getAllTransactions() {
        return adminRepository.getAllTransactions();
    }

    public List<User> getAllUsers() {
        return adminRepository.getAllUsers();
    }


    public int addBook(Book book) {
        return adminRepository.addBook(book);
    }

    public int deleteBook(int bookId) {
        return adminRepository.deleteBook(bookId);
    }

    public List<Loan> searchTransaction(String transactionId, String userAccount, String bookId, String borrowDate, String endDate, String status) {
        return adminRepository.searchTransactionByKeyword(transactionId, userAccount, bookId, borrowDate, endDate, status);
    }

    public int addTransaction(Loan loan) {
        return adminRepository.addTransaction(loan);
    }

    public boolean deleteTransaction(int transactionId) {
        return adminRepository.deleteTransactionById(transactionId);
    }

    public boolean addUser(User user) {
        try {
            adminRepository.addUser(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user) {
        try {
            adminRepository.updateUser(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUserById(int userId) {
        try {
            adminRepository.deleteUserById(userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBook(Book book) {
        try {
            adminRepository.updateBook(book);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTransaction(Loan loan) {
        try {
            adminRepository.updateTransaction(loan);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
