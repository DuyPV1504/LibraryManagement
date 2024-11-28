package models;

public class Image {
    String nameBook;
    String author;
    String urlImage;

    public Image(String nameBook, String author, String urlImage) {
        this.nameBook = nameBook;
        this.author = author;
        this.urlImage = urlImage;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
