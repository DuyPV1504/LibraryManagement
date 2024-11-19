package org.example.demo.data;

import models.Admin;
import models.Book;
import models.Loan;
import models.User;
import models.Loan.LoanStatus;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdminRepository {
    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/library_management";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "Liver1890_";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }

    // 1. Lấy thông tin Admin hiện tại
    public Admin getCurrentAdmin(int adminId) {
        String query = "SELECT * FROM admin WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Admin(
                        resultSet.getInt("id"),
                        resultSet.getString("surname"),
                        resultSet.getString("lastname"),
                        resultSet.getDate("dateOfBirth").toLocalDate(),
                        resultSet.getString("gender"),
                        resultSet.getString("email"),
                        resultSet.getString("userName"),
                        resultSet.getString("userAccount"),
                        resultSet.getString("roles"),
                        resultSet.getInt("warning")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clearSession(int adminId) {
        String query = "DELETE FROM sessions WHERE user_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, adminId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Session của admin đã được xóa.");
            } else {
                System.out.println("Không tìm thấy session nào để xóa.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int userId) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("surname"),
                        resultSet.getString("lastname"),
                        resultSet.getDate("dateOfBirth").toLocalDate(),
                        resultSet.getString("gender"),
                        resultSet.getString("email"),
                        resultSet.getString("userName"),
                        resultSet.getString("userAccount"),
                        resultSet.getString("roles"),
                        resultSet.getInt("warning")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Book getBookById(int bookId) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("bookName"),
                        resultSet.getString("author"),
                        resultSet.getString("publisher"),
                        resultSet.getInt("publishYear"),
                        resultSet.getInt("availableBooks"),
                        resultSet.getInt("totalBooks")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<User> searchUserByKeyword(String keyword) {
        String query = "SELECT * FROM Users WHERE LOWER(surname) LIKE LOWER(?) OR LOWER(lastname) LIKE LOWER(?) OR LOWER(email) LIKE LOWER(?)";
        List<User> userList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            String likeKeyword = "%" + keyword + "%";
            preparedStatement.setString(1, likeKeyword);
            preparedStatement.setString(2, likeKeyword);
            preparedStatement.setString(3, likeKeyword);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("surname"),
                        resultSet.getString("lastname"),
                        resultSet.getDate("dateOfBirth").toLocalDate(),
                        resultSet.getString("gender"),
                        resultSet.getString("email"),
                        resultSet.getString("userName"),
                        resultSet.getString("userAccount"),
                        resultSet.getString("roles"),
                        resultSet.getInt("warning")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public boolean addUser(User user) {
        String query = "INSERT INTO Users (surname, lastname, dateOfBirth, gender, email, userName, userAccount, roles, warning) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getSurname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setDate(3, java.sql.Date.valueOf(user.getDateOfBirth()));
            preparedStatement.setString(4, user.getGender());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getUserName());
            preparedStatement.setString(7, user.getUserAccount());
            preparedStatement.setString(8, user.getRoles());
            preparedStatement.setInt(9, user.getWarning());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Adding user failed, no rows affected.");
            }

            // Lấy id tự động tạo ra từ cơ sở dữ liệu
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Adding user failed, no ID obtained.");
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user) {
        String query = "UPDATE Users SET surname = ?, lastname = ?, dateOfBirth = ?, gender = ?, email = ?, userName = ?, userAccount = ?, roles = ?, warning = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getSurname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setDate(3, java.sql.Date.valueOf(user.getDateOfBirth()));
            preparedStatement.setString(4, user.getGender());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getUserName());
            preparedStatement.setString(7, user.getUserAccount());
            preparedStatement.setString(8, user.getRoles());
            preparedStatement.setInt(9, user.getWarning());
            preparedStatement.setInt(10, user.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUserById(int userId) {
        String query = "DELETE FROM Users WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Book> searchBookByKeyword(String keyword) {
        String query = "SELECT * FROM Books WHERE LOWER(bookName) LIKE LOWER(?) OR LOWER(author) LIKE LOWER(?)";
        List<Book> bookList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            String likeKeyword = "%" + keyword + "%";
            preparedStatement.setString(1, likeKeyword);
            preparedStatement.setString(2, likeKeyword);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("bookName"),
                        resultSet.getString("author"),
                        resultSet.getString("publisher"),
                        resultSet.getInt("publishYear"),
                        resultSet.getInt("totalBooks"),
                        resultSet.getInt("availableBooks")
                );
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    // 8. Thêm sách mới
    public boolean addBook(Book book) {
        String query = "INSERT INTO Books (bookName, author, publisher, publishYear, totalBooks, availableBooks) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, book.getBookName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getPublisher());
            preparedStatement.setInt(4, book.getPublishYear());
            preparedStatement.setInt(5, book.getTotalBooks());
            preparedStatement.setInt(6, book.getAvailableBooks());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Adding book failed, no rows affected.");
            }

            // Lấy id tự động tạo ra từ cơ sở dữ liệu
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Adding book failed, no ID obtained.");
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 9. Cập nhật thông tin sách
    public boolean updateBook(Book book) {
        String query = "UPDATE Books SET bookName = ?, author = ?, publisher = ?, publishYear = ?, totalBooks = ?, availableBooks = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, book.getBookName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getPublisher());
            preparedStatement.setInt(4, book.getPublishYear());
            preparedStatement.setInt(5, book.getTotalBooks());
            preparedStatement.setInt(6, book.getAvailableBooks());
            preparedStatement.setInt(7, book.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBookById(int bookId) {
        String query = "DELETE FROM Books WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, bookId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Loan> searchTransaction(String keyword) {
        String query = "SELECT * FROM Loans WHERE userAccount LIKE ? OR CAST(book_id AS CHAR) LIKE ?";
        List<Loan> loanList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            String likeKeyword = "%" + keyword + "%";
            preparedStatement.setString(1, likeKeyword);
            preparedStatement.setString(2, likeKeyword);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int transactionId = resultSet.getInt("transaction_id");
                String userAccount = resultSet.getString("userAccount");
                int bookId = resultSet.getInt("book_id");
                LocalDate borrowDate = resultSet.getDate("borrowDate").toLocalDate();
                LocalDate endDate = resultSet.getDate("endDate").toLocalDate();
                LocalDate returnDate = resultSet.getDate("returnDate") != null ? resultSet.getDate("returnDate").toLocalDate() : null;
                LoanStatus status = LoanStatus.valueOf(resultSet.getString("status").toUpperCase());

                Loan loan = new Loan(transactionId, userAccount, bookId, borrowDate, endDate, returnDate, status);
                loanList.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanList;
    }

    public boolean addTransaction(Loan loan) {
        String query = "INSERT INTO Loans (userAccount, book_id, borrowDate, endDate, returnDate, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

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
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

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

    public boolean deleteTransaction(int transactionId) {
        String query = "DELETE FROM Loans WHERE transaction_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, transactionId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
