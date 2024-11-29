package org.example.demo.service;

import models.Book;
import org.example.demo.data.BookRepository;

import java.util.List;

public class BookService {
    private BookRepository bookRepository;

    /**
     * khoi tao.
     */
    public BookService() {
        bookRepository = new BookRepository();
    }

    /**
     * lay sach.
     *
     * @return list
     */
    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    /**
     * lay sach = id.
     *
     * @param bookId id
     * @return sach
     */
    public Book getBookById(int bookId) {
        return bookRepository.getBookById(bookId);
    }

    /**
     * them sach.
     *
     * @param book sach
     * @return logic
     */
    public boolean addBook(Book book) {
        return bookRepository.addBook(book);
    }

    /**
     * update sach.
     *
     * @param book sach
     * @return logic
     */
    public boolean updateBook(Book book) {
        return bookRepository.updateBook(book);
    }

    /**
     * xoa sach.
     *
     * @param bookId id
     * @return logic
     */
    public boolean deleteBook(int bookId) {
        return bookRepository.deleteBookById(bookId);
    }

    /**
     * lay sach.
     *
     * @param keyword tso
     * @return list
     */
    public List<Book> searchBookByKeyword(String keyword) {
        return bookRepository.searchBookByKeyword(keyword);
    }

    /**
     * lay ton tai.
     *
     * @param bookId id
     * @return so
     */
    public int getAvailableBooks(int bookId) {
        Book book = bookRepository.getBookById(bookId);
        if (book != null) {
            return book.getAvailableBooks();
        } else {
            return 0;
        }
    }

    /**
     * giao avai.
     *
     * @param bookId id
     * @return logic
     */
    public boolean decreaseAvailableBooks(int bookId) {
        Book book = bookRepository.getBookById(bookId);
        if (book != null && book.getAvailableBooks() > 0) {
            book.setAvailableBooks(book.getAvailableBooks() - 1);
            return bookRepository.updateBook(book);
        } else {
            return false;
        }
    }

    /**
     * tang avai.
     *
     * @param bookId id
     * @return logic
     */
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

