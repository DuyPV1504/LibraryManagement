package org.example.demo.data;

import models.Book;
import models.Loan;
import models.User;
import models.Loan.LoanStatus;
import org.example.demo.database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.example.demo.database.connectDB;

public class AdminRepository extends BaseRepository {

    /**
     * query update role.
     *
     * @param userId id
     */
    public void updateUserRoleToAdmin(int userId) {
        String query = "UPDATE Users SET roles = 'Admin' WHERE id = ?";

        try (Connection connection = connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User role updated to Admin successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * query lay user.
     *
     * @return list
     */
    public List<User> getAllUsers() {
        String query = "SELECT * FROM Users";
        List<User> users = new ArrayList<>();

        try (Connection connection = connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

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
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * tim sach.
     *
     * @param id          id
     * @param surname     ho
     * @param lastname    ten
     * @param dateOfBirth sn
     * @param gender      gioi tinh
     * @param email       mail
     * @param userAccount ten
     * @return list
     */
    public List<User> searchUserByKeyword(String id, String surname, String lastname,
                                          String dateOfBirth, String gender,
                                          String email, String userAccount) {
        String query = "SELECT * FROM Users WHERE " + "(? IS NULL OR id = ?) AND "
                + "(? IS NULL OR LOWER(surname) LIKE LOWER(?)) AND "
                + "(? IS NULL OR LOWER(lastname) LIKE LOWER(?)) AND "
                + "(? IS NULL OR dateOfBirth = ?) AND "
                + "(? IS NULL OR LOWER(gender) = LOWER(?)) AND "
                + "(? IS NULL OR LOWER(email) LIKE LOWER(?)) AND "
                + "(? IS NULL OR LOWER(userAccount) LIKE LOWER(?))";

        List<User> userList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, id);
            preparedStatement.setString(3, surname);
            preparedStatement.setString(4, surname != null ? "%" + surname.toLowerCase() + "%" : null);
            preparedStatement.setString(5, lastname);
            preparedStatement.setString(6, lastname != null ? "%" + lastname.toLowerCase() + "%" : null);
            preparedStatement.setString(7, dateOfBirth);
            preparedStatement.setString(8, dateOfBirth);
            preparedStatement.setString(9, gender);
            preparedStatement.setString(10, gender);
            preparedStatement.setString(11, email);
            preparedStatement.setString(12, email != null ? "%" + email.toLowerCase() + "%" : null);
            preparedStatement.setString(13, userAccount);
            preparedStatement.setString(14, userAccount != null ? "%" + userAccount.toLowerCase() + "%" : null);


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

    /**
     * them nguoi.
     *
     * @param user user
     * @return logic
     */
    public boolean addUser(User user) {
        String query = "INSERT INTO Users (surname, lastname, dateOfBirth, "
                + "gender, email, userName, userAccount, roles, "
                + "warning) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

    /**
     * update nguoi.
     *
     * @param user
     * @return
     */
    public boolean updateUser(User user) {
        String query = "UPDATE Users SET surname = ?, lastname = ?, dateOfBirth = ?, "
                + "gender = ?, email = ?, userName = ?, userAccount = ?, "
                + "roles = ?, warning = ? WHERE id = ?";
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

    /**
     * xoa nguoi.
     *
     * @param userId id
     * @return so
     */
    public int deleteUser(int userId) {
        String query = "DELETE FROM Users WHERE id = ?";
        try (Connection connection = connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * lay sach.
     *
     * @return list
     */
    public List<Book> getAllBooks() {
        String query = "SELECT * FROM Books";
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
     * tim sach.
     *
     * @param id             id
     * @param bookName       ten
     * @param publishedYear  nam xb
     * @param availableBooks con
     * @param totalBooks     tong
     * @param author         tac gia
     * @param publisher      nxb
     * @return list
     */
    public List<Book> searchBookByKeyword(String id, String bookName, String publishedYear, String availableBooks,
                                          String totalBooks, String author, String publisher) {
        String query = "SELECT * FROM Books WHERE "
                + "(? IS NULL OR id = ?) AND "
                + "(? IS NULL OR bookName LIKE ?) AND "
                + "(? IS NULL OR publishYear LIKE ?) AND "
                + "(? IS NULL OR availableBooks = ?) AND "
                + "(? IS NULL OR totalBooks = ?) AND "
                + "(? IS NULL OR author LIKE ?) AND "
                + "(? IS NULL OR publisher LIKE ?)";

        List<Book> bookList = new ArrayList<>();

        try (Connection connection = connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, id);
            preparedStatement.setString(3, bookName);
            preparedStatement.setString(4, bookName != null ? bookName + "%" : null);
            preparedStatement.setString(5, publishedYear);
            preparedStatement.setString(6, publishedYear != null ? "%" + publishedYear + "%" : null);
            preparedStatement.setString(7, availableBooks);
            preparedStatement.setString(8, availableBooks);
            preparedStatement.setString(9, totalBooks);
            preparedStatement.setString(10, totalBooks);
            preparedStatement.setString(11, author);
            preparedStatement.setString(12, author != null ? "%" + author + "%" : null);
            preparedStatement.setString(13, publisher);
            preparedStatement.setString(14, publisher != null ? "%" + publisher + "%" : null);

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
     * them sach.
     *
     * @param book book
     * @return so
     */
    public int addBook(Book book) {
        String query = "INSERT INTO Books (bookName, author, publisher, "
                + "publishYear, availableBooks, totalBooks) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(query,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, book.getBookName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getPublisher());
            preparedStatement.setInt(4, book.getPublishYear());
            preparedStatement.setInt(5, book.getAvailableBooks());
            preparedStatement.setInt(6, book.getTotalBooks());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * update sach.
     *
     * @param book sach
     * @return logic
     */
    public boolean updateBook(Book book) {
        String query = "UPDATE Books SET bookName = ?, author = ?, publisher = ?, "
                + "publishYear = ?, totalBooks = ?, availableBooks = ? WHERE id = ?";
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

    /**
     * xoa sach.
     *
     * @param bookId id
     * @return so
     */
    public int deleteBook(int bookId) {
        String query = "DELETE FROM Books WHERE id = ?";
        try (Connection connection = connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, bookId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * danh sach loan.
     *
     * @param transactionId id
     * @param userAccount   tenAcc
     * @param bookId        idBook
     * @param borrowDate    ngay muon
     * @param endDate       han
     * @param status        tinh trang
     * @return list
     */
    public List<Loan> searchTransactionByKeyword(String transactionId, String userAccount,
                                                 String bookId, String borrowDate, String endDate, String status) {
        String query = "SELECT * FROM Loans WHERE "
                + "(? IS NULL OR transaction_id = ?) AND "
                + "(? IS NULL OR userAccount LIKE ?) AND "
                + "(? IS NULL OR book_id = ?) AND "
                + "(? IS NULL OR borrowDate = ?) AND "
                + "(? IS NULL OR endDate = ?) AND "
                + "(? IS NULL OR status = ?)";

        List<Loan> transactionList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, transactionId);
            preparedStatement.setString(2, transactionId);
            preparedStatement.setString(3, userAccount);
            preparedStatement.setString(4, userAccount != null ? "%" + userAccount + "%" : null);
            preparedStatement.setString(5, bookId);
            preparedStatement.setString(6, bookId);
            preparedStatement.setString(7, borrowDate);
            preparedStatement.setString(8, borrowDate);
            preparedStatement.setString(9, endDate);
            preparedStatement.setString(10, endDate);
            preparedStatement.setString(11, status);
            preparedStatement.setString(12, status);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Loan loan = new Loan(
                        resultSet.getInt("transaction_id"),
                        resultSet.getString("userAccount"),
                        resultSet.getInt("book_id"),
                        resultSet.getDate("borrowDate").toLocalDate(),
                        resultSet.getDate("endDate").toLocalDate(),
                        resultSet.getDate("returnDate") != null
                                ? resultSet.getDate("returnDate").toLocalDate() : null,
                        Loan.LoanStatus.valueOf(resultSet.getString("status").toUpperCase())
                );
                transactionList.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    /**
     * them sach.
     *
     * @param loan loan
     * @return so
     */
    public int addTransaction(Loan loan) {
        String query = "INSERT INTO Loans (userAccount, book_id, borrowDate, endDate, returnDate, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        return add(query,
                loan.getUserAccount(),
                loan.getBookId(),
                java.sql.Date.valueOf(loan.getBorrowDate()),
                java.sql.Date.valueOf(loan.getEndDate()),
                loan.getReturnDate() != null ? java.sql.Date.valueOf(loan.getReturnDate()) : null,
                loan.getStatus().toString());
    }

    /**
     * xoa gd.
     *
     * @param transactionId gd
     * @return logic
     */
    public boolean deleteTransactionById(int transactionId) {
        String query = "DELETE FROM Loans WHERE transaction_id = ?";
        return delete(query, transactionId);
    }

    /**
     * lay trans.
     *
     * @return list
     */
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
                        resultSet.getDate("returnDate") == null
                                ? null : resultSet.getDate("returnDate").toLocalDate(),
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
     * update trans
     *
     * @param loan loan
     * @return logic
     */
    public boolean updateTransaction(Loan loan) {
        String query = "UPDATE Loans SET userAccount = ?, book_id = ?, borrowDate = ?, "
                + "endDate = ?, returnDate = ?, status = ? WHERE transaction_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, loan.getUserAccount());
            preparedStatement.setInt(2, loan.getBookId());
            preparedStatement.setDate(3, java.sql.Date.valueOf(loan.getBorrowDate()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(loan.getEndDate()));
            preparedStatement.setDate(5,
                    loan.getReturnDate() != null ? java.sql.Date.valueOf(loan.getReturnDate()) : null);
            preparedStatement.setString(6, loan.getStatus().name());
            preparedStatement.setInt(7, loan.getTransactionId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
