package org.example.demo.data;

import models.User;
import org.example.demo.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private Connection connection;

    public UserRepository() {
        connection = database.connectDB();
    }

    // Lấy tất cả người dùng
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = mapResultSetToUser(rs);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM Users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByAccount(String userAccount) {
        String sql = "SELECT * FROM Users WHERE userAccount = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userAccount);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addUser(User user) {
        String sql = "INSERT INTO Users (surname, lastname, dateOfBirth, gender, email, userName, userAccount, roles, warning) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setUserPreparedStatement(stmt, user);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Adding user failed, no rows affected.");
            }

            // Lấy id tự động tạo ra từ cơ sở dữ liệu
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
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
        String sql = "UPDATE Users SET surname = ?, lastname = ?, dateOfBirth = ?, gender = ?, email = ?, userName = ?, userAccount = ?, roles = ?, warning = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setUserPreparedStatement(stmt, user);
            stmt.setInt(10, user.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM Users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> searchUserByKeyword(String keyword) {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE LOWER(surname) LIKE LOWER(?) OR LOWER(lastname) LIKE LOWER(?) OR LOWER(email) LIKE LOWER(?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String likeKeyword = "%" + keyword + "%";
            stmt.setString(1, likeKeyword);
            stmt.setString(2, likeKeyword);
            stmt.setString(3, likeKeyword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = mapResultSetToUser(rs);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    private void setUserPreparedStatement(PreparedStatement stmt, User user) throws SQLException {
        stmt.setString(1, user.getSurname());
        stmt.setString(2, user.getLastname());
        stmt.setDate(3, Date.valueOf(user.getDateOfBirth()));
        stmt.setString(4, user.getGender());
        stmt.setString(5, user.getEmail());
        stmt.setString(6, user.getUserName());
        stmt.setString(7, user.getUserAccount());
        stmt.setString(8, user.getRoles());
        stmt.setInt(9, user.getWarning());
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("surname"),
                rs.getString("lastname"),
                rs.getDate("dateOfBirth").toLocalDate(),
                rs.getString("gender"),
                rs.getString("email"),
                rs.getString("userName"),
                rs.getString("userAccount"),
                rs.getString("roles"),
                rs.getInt("warning")
        );
    }
}

