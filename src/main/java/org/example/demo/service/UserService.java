package org.example.demo.service;

import models.Book;
import models.Loan;
import models.User;
import org.example.demo.data.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository userRepository = new UserRepository();

    public List<Book> getAllBooks() {
        return userRepository.getAllBooks();
    }

    public List<Book> searchBook(String id, String bookName, String publishedYear, String author, String publisher) {
        return userRepository.searchBookByKeyword(id, bookName, publishedYear, author, publisher);
    }

    public List<Loan> getAllTransactions() {
        return userRepository.getAllTransactions();
    }

}