package org.example.demo.data;

import models.Loan;
import models.User;
import models.Book;
import org.example.demo.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;
import static org.example.demo.database.connectDB;

public class UserRepository extends BaseRepository {

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

    public List<Book> searchBookByKeyword(String id, String bookName, String publishedYear, String author, String publisher) {
        String query = "SELECT * FROM Books WHERE " +
                "(? IS NULL OR id = ?) AND " +
                "(? IS NULL OR bookName = ?) AND " +
                "(? IS NULL OR publishYear = ?) AND " +
                "(? IS NULL OR author = ?) AND " +
                "(? IS NULL OR publisher = ?)";

        List<Book> bookList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, id);
            preparedStatement.setString(3, bookName);
            preparedStatement.setString(4, bookName);
            preparedStatement.setString(5, publishedYear);
            preparedStatement.setString(6, publishedYear);
            preparedStatement.setString(7, author);
            preparedStatement.setString(8, author);
            preparedStatement.setString(9, publisher);
            preparedStatement.setString(10, publisher);

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

    public List<Loan> getAllTransactions() {
        String query = "SELECT * FROM Loans";
        List<Loan> loanList = new ArrayList<>();

        try (Connection connection = connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

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
