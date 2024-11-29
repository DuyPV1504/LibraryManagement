package org.example.demo.data;

import models.Comment;
import models.Loan;
import models.User;
import models.Book;
import org.example.demo.database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;
import static org.example.demo.database.connectDB;

public class UserRepository extends BaseRepository {

    /**
     * lay list sach.
     *
     * @return book
     */
    public List<Book> getAllBooks() {
        String query = "SELECT id, bookName, author, publisher, publishYear, availableBooks, totalBooks FROM Books";
        List<Book> books = new ArrayList<>();

        try (Connection connection = connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("bookName"),
                        resultSet.getString("author"),
                        resultSet.getString("publisher"),
                        resultSet.getInt("publishYear"),
                        resultSet.getInt("availableBooks"),
                        resultSet.getInt("totalBooks")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    /**
     * tim list sach.
     *
     * @param id            id
     * @param bookName      ten
     * @param publishedYear nam xb
     * @param author        tac gia
     * @param publisher     nxb
     * @return list
     */
    public List<Book> searchBookByKeyword(String id, String bookName, String publishedYear, String author, String publisher) {
        String query = "SELECT * FROM Books WHERE " +
                "(? IS NULL OR id = ?) AND " +
                "(? IS NULL OR bookName LIKE ?) AND " +
                "(? IS NULL OR publishYear LIKE ?) AND " +
                "(? IS NULL OR author LIKE ?) AND " +
                "(? IS NULL OR publisher LIKE ?)";

        List<Book> bookList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, id);
            preparedStatement.setString(3, bookName);
            preparedStatement.setString(4, bookName != null ? bookName + "%" : null);
            preparedStatement.setString(5, publishedYear);
            preparedStatement.setString(6, publishedYear != null ? "%" + publishedYear + "%" : null);
            preparedStatement.setString(7, author);
            preparedStatement.setString(8, author != null ? "%" + author + "%" : null);
            preparedStatement.setString(9, publisher);
            preparedStatement.setString(10, publisher != null ? "%" + publisher + "%" : null);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("bookName"),
                        resultSet.getString("author"),
                        resultSet.getString("publisher"),
                        resultSet.getInt("publishYear"),
                        resultSet.getInt("availableBooks"),
                        resultSet.getInt("totalBooks")
                );
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    /**
     * lay list loan.
     *
     * @param userAccount ten
     * @return list
     */
    public List<Loan> getTransactionsByUser(String userAccount) {
        String query = "SELECT * FROM Loans WHERE userAccount = ?";
        List<Loan> loanList = new ArrayList<>();

        try (Connection connection = connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userAccount);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Loan loan = new Loan(
                        resultSet.getInt("transaction_id"),
                        resultSet.getString("userAccount"),
                        resultSet.getInt("book_id"),
                        resultSet.getDate("borrowDate").toLocalDate(),
                        resultSet.getDate("endDate").toLocalDate(),
                        resultSet.getDate("returnDate") == null ? null : resultSet.getDate("returnDate").toLocalDate(),
                        Loan.LoanStatus.valueOf(resultSet.getString("status").toUpperCase())
                );
                loanList.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanList;
    }

    /**
     * list comment.
     *
     * @param idBook id
     * @return list
     */
    public List<Comment> getCommentByIdBook(int idBook) {
        String query = "SELECT * FROM Loans WHERE userAccount = ?";
        List<Comment> commentList = new ArrayList<>();

        try (Connection connection = connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, String.valueOf(idBook));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Comment loan = new Comment(
                        resultSet.getInt("book_id"),
                        resultSet.getString("comment"),
                        resultSet.getString("userAccount")
                );
                commentList.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentList;
    }

    /**
     * tim list giao dich.
     *
     * @param transactionId id
     * @param userAccount   userAcc
     * @param bookId        id
     * @param borrowDate    ngay muon
     * @param returnDate    tra
     * @param status        tinh trang
     * @return list
     */
    public List<Loan> searchTransactions(Integer transactionId, String userAccount, Integer bookId, LocalDate borrowDate,
                                         LocalDate returnDate, Loan.LoanStatus status) {
        String query = "SELECT * FROM Loans WHERE " +
                "(? IS NULL OR transaction_id = ?) AND " +
                "(? IS NULL OR userAccount = ?) AND " +
                "(? IS NULL OR book_id = ?) AND " +
                "(? IS NULL OR borrowDate = ?) AND " +
                "(? IS NULL OR returnDate = ?) AND " +
                "(? IS NULL OR status = ?)";
        List<Loan> loanList = new ArrayList<>();

        try (Connection connection = connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, transactionId);
            preparedStatement.setObject(2, transactionId);
            preparedStatement.setObject(3, userAccount);
            preparedStatement.setObject(4, userAccount);
            preparedStatement.setObject(5, bookId);
            preparedStatement.setObject(6, bookId);
            preparedStatement.setObject(7, borrowDate);
            preparedStatement.setObject(8, borrowDate);
            preparedStatement.setObject(9, returnDate);
            preparedStatement.setObject(10, returnDate);
            preparedStatement.setObject(11, status == null ? null : status.name());
            preparedStatement.setObject(12, status == null ? null : status.name());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Loan loan = new Loan(
                        resultSet.getInt("transaction_id"),
                        resultSet.getString("userAccount"),
                        resultSet.getInt("book_id"),
                        resultSet.getDate("borrowDate").toLocalDate(),
                        resultSet.getDate("endDate").toLocalDate(),
                        resultSet.getDate("returnDate") == null ? null : resultSet.getDate("returnDate").toLocalDate(),
                        Loan.LoanStatus.valueOf(resultSet.getString("status").toUpperCase())
                );
                loanList.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanList;
    }
}
