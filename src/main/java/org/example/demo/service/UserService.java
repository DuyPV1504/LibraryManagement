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

    /**
     * lay sach.
     *
     * @return list
     */
    public List<Book> getAllBooks() {
        return userRepository.getAllBooks();
    }

    /**
     * tim sach.
     *
     * @param id            id
     * @param bookName      ten
     * @param publishedYear nam xb
     * @param author        tgia
     * @param publisher     nxb
     * @return list
     */
    public List<Book> searchBook(String id, String bookName, String publishedYear,
                                 String author, String publisher) {
        return userRepository.searchBookByKeyword(id, bookName, publishedYear, author, publisher);
    }

    /**
     * lay loan.
     *
     * @param userAccount userAcc
     * @return list
     */
    public List<Loan> getTransactionsByUser(String userAccount) {
        return userRepository.getTransactionsByUser(userAccount);
    }

    /**
     * lay comment.
     *
     * @param idBook id
     * @return list
     */
    public List<Comment> getCommentByIdBook(int idBook) {
        return userRepository.getCommentByIdBook(idBook);
    }

    /**
     * xoa cmt.
     *
     * @param comment     cmt
     * @param userAccount acc
     * @return logic
     */
    public boolean deleteComment(String comment, String userAccount) {
        String query = "DELETE FROM Comments WHERE comment = ? AND userName = ?";
        System.out.println(userAccount);
        return delete(query, comment, userAccount);
    }

    /**
     * tim loan.
     *
     * @param transactionId id
     * @param userAccount   acc
     * @param bookId        id
     * @param borrowDate    muon
     * @param returnDate    tra
     * @param status        tinh trang
     * @return list
     */
    public List<Loan> searchTransactions(Integer transactionId, String userAccount, Integer bookId, LocalDate borrowDate,
                                         LocalDate returnDate, Loan.LoanStatus status) {
        return userRepository.searchTransactions(transactionId, userAccount, bookId, borrowDate, returnDate, status);
    }
}