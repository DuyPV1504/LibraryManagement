package org.example.demo.data;
import API.RenImage;
import models.Image;
import models.User;
import org.example.demo.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;


public class ImageRespository extends BaseRepository {
    public String getImageUrl(String bookName, String author) throws SQLException {
        String query = "SELECT urlImage FROM Images WHERE bookName = ? AND author = ?";

        try (Connection conn = database.connectDB()) {
            assert conn != null;
            try (PreparedStatement statement = conn.prepareStatement(query)) {

                statement.setString(1, bookName);
                statement.setString(2, author);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("urlImage");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }


    public boolean addImage(Image image) throws SQLException {
        String query = "INSERT INTO Images (bookName, author, urlImage) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, image.getNameBook());
            preparedStatement.setString(2, image.getAuthor());
            preparedStatement.setString(3, image.getUrlImage());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateImageUrl(String bookName, String author, String newUrl) throws SQLException {
        if (newUrl == null || newUrl.trim().isEmpty()) {
            newUrl = null;
        }
        String query = "UPDATE Images SET urlImage = ? WHERE bookName = ? AND author = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newUrl);
            preparedStatement.setString(2, bookName);
            preparedStatement.setString(3, author);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteImage(String bookName, String author) throws SQLException {
        String query = "DELETE FROM Images WHERE bookName = ? AND author = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bookName);
            preparedStatement.setString(2, author);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isValidImageUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.out.println("Mã phản hồi không phải 200 OK: " + responseCode);
                return false;
            }

            String contentType = connection.getContentType();

            if (contentType != null && contentType.startsWith("image/")) {
                System.out.println("URL chứa hình ảnh: " + imageUrl);
                return true;
            } else {
                System.out.println("URL không phải là hình ảnh: " + imageUrl);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getImagePath(String imageUrl) throws IOException {
        String saveDir = "F:\\btlOOP\\demo\\src\\main\\resources\\image";

        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

        File saveFile = new File(saveDir, fileName);

        if (saveFile.exists()) {
            return saveFile.getAbsolutePath();
        } else {
            RenImage.downImage(imageUrl);
            throw new FileNotFoundException("Ảnh không tồn tại trong thư mục: " + saveFile.getAbsolutePath());
        }
    }



}
