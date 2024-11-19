package org.example.demo.data;

import models.Book;
import models.Loan;
import models.User;
import org.example.demo.database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanRepository {

    private Connection connection;
    private UserRepository userRepository;
    private BookRepository bookRepository;

    public LoanRepository() {
        connection = database.connectDB();
        userRepository = new UserRepository();
        bookRepository = new BookRepository();
    }

    public List<Loan> getAllTransactions() {
        List<Loan> loanList = new ArrayList<>();
        String sql = "SELECT * FROM Loans";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Loan loan = mapResultSetToLoan(rs);
                loanList.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanList;
    }

    public List<Loan> searchTransaction(String keyword) {
        String query = "SELECT * FROM Loans WHERE LOWER(userAccount) LIKE LOWER(?) OR LOWER(book_id) LIKE LOWER(?)";
        List<Loan> loanList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            String likeKeyword = "%" + keyword + "%";
            preparedStatement.setString(1, likeKeyword);
            preparedStatement.setString(2, likeKeyword);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Loan loan = mapResultSetToLoan(resultSet);
                loanList.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanList;
    }

    public Loan getTransaction(int transactionId) {
        String sql = "SELECT * FROM Loans WHERE transaction_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToLoan(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addTransaction(Loan loan) {
        String query = "INSERT INTO Loans (userAccount, book_id, borrowDate, endDate, returnDate, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, loan.getUserAccount());
            preparedStatement.setInt(2, loan.getBookId());
            preparedStatement.setDate(3, java.sql.Date.valueOf(loan.getBorrowDate()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(loan.getEndDate()));
            preparedStatement.setDate(5, loan.getReturnDate() != null ? java.sql.Date.valueOf(loan.getReturnDate()) : null);
            preparedStatement.setString(6, loan.getStatus().name());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Adding loan failed, no rows affected.");
            }

            // Lấy transaction_id tự động tạo ra từ cơ sở dữ liệu
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    loan.setTransactionId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Adding loan failed, no ID obtained.");
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTransaction(Loan loan) {
        String query = "UPDATE Loans SET userAccount = ?, book_id = ?, borrowDate = ?, endDate = ?, returnDate = ?, status = ? WHERE transaction_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, loan.getUserAccount());
            preparedStatement.setInt(2, loan.getBookId());
            preparedStatement.setDate(3, java.sql.Date.valueOf(loan.getBorrowDate()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(loan.getEndDate()));
            preparedStatement.setDate(5, loan.getReturnDate() != null ? java.sql.Date.valueOf(loan.getReturnDate()) : null);
            preparedStatement.setString(6, loan.getStatus().name());
            preparedStatement.setInt(7, loan.getTransactionId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa giao dịch theo transaction_id
    public boolean deleteTransaction(int transactionId) {
        String query = "DELETE FROM Loans WHERE transaction_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, transactionId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Loan mapResultSetToLoan(ResultSet rs) throws SQLException {
        int transactionId = rs.getInt("transaction_id");
        String userAccount = rs.getString("userAccount");
        int bookId = rs.getInt("book_id");
        LocalDate borrowDate = rs.getDate("borrowDate").toLocalDate();
        LocalDate endDate = rs.getDate("endDate").toLocalDate();
        LocalDate returnDate = rs.getDate("returnDate") != null ? rs.getDate("returnDate").toLocalDate() : null;
        Loan.LoanStatus status = Loan.LoanStatus.valueOf(rs.getString("status").toUpperCase());

        // Lấy đối tượng User và Book từ UserRepository và BookRepository
        User user = userRepository.getUserByAccount(userAccount);
        Book book = bookRepository.getBookById(bookId);

        // Tạo đối tượng Loan với các thông tin lấy từ ResultSet
        return new Loan(transactionId, userAccount, bookId, borrowDate, endDate, returnDate, status);
    }
}

