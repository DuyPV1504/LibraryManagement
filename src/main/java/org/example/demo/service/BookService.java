package org.example.demo.service;

import models.Book;
import org.example.demo.data.BookRepository;

import java.util.List;

public class BookService {
    private BookRepository bookRepository;

    public BookService() {
        bookRepository = new BookRepository();
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public Book getBookById(int bookId) {
        return bookRepository.getBookById(bookId);
    }

    public boolean addBook(Book book) {
        return bookRepository.addBook(book);
    }

    public boolean updateBook(Book book) {
        return bookRepository.updateBook(book);
    }

    public boolean deleteBook(int bookId) {
        return bookRepository.deleteBookById(bookId);
    }

    public List<Book> searchBookByKeyword(String keyword) {
        return bookRepository.searchBookByKeyword(keyword);
    }

    public int getAvailableBooks(int bookId) {
        Book book = bookRepository.getBookById(bookId);
        if (book != null) {
            return book.getAvailableBooks();
        } else {
            return 0;
        }
    }

    public boolean decreaseAvailableBooks(int bookId) {
        Book book = bookRepository.getBookById(bookId);
        if (book != null && book.getAvailableBooks() > 0) {
            book.setAvailableBooks(book.getAvailableBooks() - 1);
            return bookRepository.updateBook(book);
        } else {
            return false;
        }
    }

    public boolean increaseAvailableBooks(int bookId) {
        Book book = bookRepository.getBookById(bookId);
        if (book != null && book.getAvailableBooks() < book.getTotalBooks()) {
            book.setAvailableBooks(book.getAvailableBooks() + 1);
            return bookRepository.updateBook(book);
        } else {
            return false;
        }
    }
}

