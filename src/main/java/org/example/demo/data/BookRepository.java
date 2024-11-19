package org.example.demo.data;

import models.Book;
import org.example.demo.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private Connection connection;

    public BookRepository() {
        connection = database.connectDB();
    }

    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM Books";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = mapResultSetToBook(rs);
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public Book getBookById(int bookId) {
        String sql = "SELECT * FROM Books WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToBook(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addBook(Book book) {
        String sql = "INSERT INTO Books (bookName, author, publisher, publishYear, totalBooks, availableBooks) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setBookPreparedStatement(stmt, book);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Adding book failed, no rows affected.");
            }

            // Lấy id tự động tạo ra từ cơ sở dữ liệu
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
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

    public boolean updateBook(Book book) {
        String sql = "UPDATE Books SET bookName = ?, author = ?, publisher = ?, publishYear = ?, totalBooks = ?, availableBooks = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setBookPreparedStatement(stmt, book);
            stmt.setInt(7, book.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBookById(int bookId) {
        String sql = "DELETE FROM Books WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Book> searchBookByKeyword(String keyword) {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM Books WHERE LOWER(bookName) LIKE LOWER(?) OR LOWER(author) LIKE LOWER(?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String likeKeyword = "%" + keyword + "%";
            stmt.setString(1, likeKeyword);
            stmt.setString(2, likeKeyword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = mapResultSetToBook(rs);
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    private void setBookPreparedStatement(PreparedStatement stmt, Book book) throws SQLException {
        stmt.setString(1, book.getBookName());
        stmt.setString(2, book.getAuthor());
        stmt.setString(3, book.getPublisher());
        stmt.setInt(4, book.getPublishYear());
        stmt.setInt(5, book.getTotalBooks());
        stmt.setInt(6, book.getAvailableBooks());
    }

    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        return new Book(
                rs.getInt("id"),
                rs.getString("bookName"),
                rs.getString("author"),
                rs.getString("publisher"),
                rs.getInt("publishYear"),
                rs.getInt("totalBooks"),
                rs.getInt("availableBooks")
        );
    }
}

