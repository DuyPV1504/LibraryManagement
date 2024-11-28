package org.example.demo.service;

import models.Image;
import models.User;
import org.example.demo.data.AdminRepository;
import org.example.demo.data.ImageRespository;

import java.io.IOException;
import java.sql.SQLException;

public class ImageService {
    private final ImageRespository imgRepository = new ImageRespository();

    public boolean addImg(Image img) {
        try {
            imgRepository.addImage(img);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUrlByBookNameAndAuthor(String bookName, String author) throws SQLException {
        try {
            return imgRepository.getImageUrl(bookName, author);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateLink(String bookName, String author, String url) throws SQLException {
        try {
            imgRepository.updateImageUrl(bookName, author, url);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteLink(String bookName, String author) throws SQLException {
        try {
            imgRepository.deleteImage(bookName, author);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isValidImageUrl(String imageUrl) {
        return ImageRespository.isValidImageUrl(imageUrl);
    }

    public String getImagePath(String imageUrl) throws IOException {
        return ImageRespository.getImagePath(imageUrl);
    }
}
