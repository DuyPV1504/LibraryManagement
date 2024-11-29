package org.example.demo.service;

import models.Image;
import models.User;
import org.example.demo.data.AdminRepository;
import org.example.demo.data.ImageRespository;

import java.io.IOException;
import java.sql.SQLException;

public class ImageService {
    private final ImageRespository imgRepository = new ImageRespository();

    /**
     * them anh.
     *
     * @param img img
     * @return logic
     */
    public boolean addImg(Image img) {
        try {
            imgRepository.addImage(img);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * lay url.
     *
     * @param bookName ten
     * @param author   tgia
     * @return link
     * @throws SQLException sql
     */
    public String getUrlByBookNameAndAuthor(String bookName, String author) throws SQLException {
        try {
            return imgRepository.getImageUrl(bookName, author);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * update link.
     *
     * @param bookName ten sach
     * @param author   tgia
     * @param url      url
     * @return link
     * @throws SQLException sql
     */
    public boolean updateLink(String bookName, String author, String url) throws SQLException {
        try {
            imgRepository.updateImageUrl(bookName, author, url);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * xoa link.
     *
     * @param bookName ten sach
     * @param author   tgia
     * @return
     * @throws SQLException
     */
    public boolean deleteLink(String bookName, String author) throws SQLException {
        try {
            imgRepository.deleteImage(bookName, author);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * check link.
     *
     * @param imageUrl url
     * @return logic
     */
    public boolean isValidImageUrl(String imageUrl) {
        return ImageRespository.isValidImageUrl(imageUrl);
    }

    /**
     * lay path.
     * @param imageUrl url
     * @return path
     * @throws IOException file
     */
    public String getImagePath(String imageUrl) throws IOException {
        return ImageRespository.getImagePath(imageUrl);
    }
}
