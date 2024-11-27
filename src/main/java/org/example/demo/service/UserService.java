package org.example.demo.service;

import models.Book;
import models.Comment;
import models.Loan;
import models.User;
import org.example.demo.data.BaseRepository;
import org.example.demo.data.UserRepository;

import java.time.LocalDate;
import java.util.List;

public class UserService extends BaseRepository {

    private final UserRepository userRepository = new UserRepository();

    public List<Book> getAllBooks() {
        return userRepository.getAllBooks();
    }

    public List<Book> searchBook(String id, String bookName, String publishedYear, String author, String publisher) {
        return userRepository.searchBookByKeyword(id, bookName, publishedYear, author, publisher);
    }

    public List<Loan> getTransactionsByUser(String userAccount) {
        return userRepository.getTransactionsByUser(userAccount);
    }

    public List<Comment> getCommentByIdBook(int idBook) {
        return userRepository.getCommentByIdBook(idBook);
    }

    public boolean deleteComment(String comment,String userAccount) {
        String query = "DELETE FROM Comments WHERE comment = ? AND userName = ?";
        System.out.println(userAccount);
        return delete(query, comment,userAccount);
    }

    public List<Loan> searchTransactions(Integer transactionId, String userAccount, Integer bookId, LocalDate borrowDate,
                                         LocalDate returnDate, Loan.LoanStatus status) {
        return userRepository.searchTransactions(transactionId, userAccount, bookId, borrowDate, returnDate, status);
    }
}