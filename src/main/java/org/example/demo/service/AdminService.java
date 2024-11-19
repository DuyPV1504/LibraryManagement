package org.example.demo.service;

import models.Book;
import models.Loan;
import models.User;
import org.example.demo.data.AdminRepository;

import java.util.List;

public class AdminService {
    private final AdminRepository adminRepository = new AdminRepository();

    public List<User> searchUser(String keyword) {
        return adminRepository.searchUserByKeyword(keyword);
    }

    public List<Book> searchBook(String keyword) {
        return adminRepository.searchBookByKeyword(keyword);
    }

    public List<Loan> searchTransaction(String keyword) {
        return adminRepository.searchTransaction(keyword);
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

    public boolean addBook(Book book) {
        try {
            adminRepository.addBook(book);
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

    public boolean deleteBookById(int bookId) {
        try {
            adminRepository.deleteBookById(bookId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addTransaction(Loan loan) {
        try {
            adminRepository.addTransaction(loan);
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

    public boolean deleteTransaction(int transactionId) {
        try {
            adminRepository.deleteTransaction(transactionId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
