package models;

public class Comment {
    private int bookId;
    private String content;
    private String userAccount;

    /**
     * ham tao.
     *
     * @param bookId      idSach
     * @param content     text
     * @param userAccount ten nguoi dung
     */
    public Comment(int bookId, String content, String userAccount) {
        this.bookId = bookId;
        this.content = content;
        this.userAccount = userAccount;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
