package org.example.demo.controller;

import API.RenImage;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Book;
import models.Comment;
import models.Loan;
import models.User;
import org.example.demo.GiaoDienChung;
import org.example.demo.HelloApplication;
import org.example.demo.LogIn;
import org.example.demo.data.LoanRepository;
import org.example.demo.database;
import org.example.demo.service.AdminService;
import org.example.demo.service.ImageService;
import org.example.demo.service.LoanService;
import org.example.demo.service.UserService;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class UserController extends GiaoDienChung {

    private final UserService userService = new UserService();
    private final LoanService loanService = new LoanService();
    private final ImageService imageService = new ImageService();


    public TableView<Loan> loanTableView;
    public TableView<Book> bookTableView;
    public TableView commentTableView;

    @FXML
    public SplitMenuButton optionU;
    @FXML
    public MenuItem inforU;
    @FXML
    public MenuItem logOutU;
    @FXML
    public Tab bookInfor;
    @FXML
    public Button searchBookU;
    @FXML
    public TableColumn author;
    @FXML
    public Tab borrownReturnUManage;
    @FXML
    public Button borrowButton;
    @FXML
    public Button returnButton;
    @FXML
    public TableColumn userAccount;
    @FXML
    public TableColumn status;
    @FXML
    public Tab bookReview;
    @FXML
    public TextField publishedYearReview;
    @FXML
    public TextArea comments;
    @FXML
    public Button addCommentButton;
    @FXML
    public Button editCommentButton;
    @FXML
    public Button deleteCommentButton;
    @FXML
    public MenuItem volume;
    @FXML
    public Button exitButton;
    public TextField availableBooksTextFields;
    public TextField bookIDInForTextField;
    public TextField bookNameInForTextFiled;
    public TextField authorInForTextFiled;
    public TextField publisherInForTextFiled;
    public TextField publishedYearInForTextFiled;
    public TableColumn<Book, Integer> bookIDInForCollumn;
    public TableColumn<Book, String> bookNameCollumn;
    public TableColumn<Book, String> authorCollumn;
    public TableColumn<Book, String> publisherCollumn;
    public TableColumn<Book, Integer> availableBooksInInfoBookCollumn;
    public TableColumn<Book, Integer> publishYearCollumn;
    public TableColumn<Book, Integer> totalBooksCollumn;

    public TextField transactionIDTextField;
    public TextField userAccountTextField;
    public TextField book_idTextField;
    public TextField borrowDateTextField;
    public TextField returnDateTextField;
    public TextField statusTextField;
    public TableColumn<Loan, Integer> transaction_idCollumn;
    public TableColumn<Loan, String> userAccountCollumn;
    public TableColumn<Loan, Integer> book_idCollumn;
    public TableColumn<Loan, Integer> availableBooksInBorrowCollum;
    public TableColumn<Loan, LocalDate> borrowDateCollum;
    public TableColumn<Loan, LocalDate> returnDateCollumn;
    public TableColumn<Loan, Loan.LoanStatus> statusCollumn;

    public TextField bookIDReviewField;
    public TextField bookNameReviewField;
    public TextField authorReviewField;
    public TextField publisherReviewField;
    public TextField publishedYearReviewField;
    public Button searchBookBorrow;
    public TableColumn commentColumn;
    public Button searchBookInComment;
    public Button selectBook;
    public TableColumn nameUser;
    public ImageView renImageBook;

    private ObservableList<Loan> loanList = FXCollections.observableArrayList();
    private ObservableList<Book> bookList = FXCollections.observableArrayList();
    private ObservableList<Comment> commentsList = FXCollections.observableArrayList();

    public void initialize() {

        refreshBookList();
        refreshLoanList();

        bookNameInForTextFiled.textProperty().addListener((observable, oldValue, newValue) -> {
            searchBooksDynamic();
        });
        authorInForTextFiled.textProperty().addListener((observable, oldValue, newValue) -> {
            searchBooksDynamic();
        });
        publisherInForTextFiled.textProperty().addListener((observable, oldValue, newValue) -> {
            searchBooksDynamic();
        });
        publishedYearInForTextFiled.textProperty().addListener((observable, oldValue, newValue) -> {
            searchBooksDynamic();
        });

        bookIDInForCollumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookNameCollumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        authorCollumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCollumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        publishYearCollumn.setCellValueFactory(new PropertyValueFactory<>("publishYear"));
        totalBooksCollumn.setCellValueFactory(new PropertyValueFactory<>("totalBooks"));
        availableBooksInInfoBookCollumn.setCellValueFactory(new PropertyValueFactory<>("availableBooks"));

        transaction_idCollumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        userAccountCollumn.setCellValueFactory(new PropertyValueFactory<>("userAccount"));
        book_idCollumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        borrowDateCollum.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        statusCollumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        bookTableView.setItems(bookList);
        loanTableView.setItems(loanList);

        borrownReturnUManage.setOnSelectionChanged(event -> {
            if (borrownReturnUManage.isSelected()) {
                refreshLoanList();
            }
        });
    }

    /**
     * tim sach.
     */
    private void searchBooksDynamic() {
        String id = bookIDInForTextField.getText().trim();
        String bookName = bookNameInForTextFiled.getText().trim();
        String author = authorInForTextFiled.getText().trim();
        String publisher = publisherInForTextFiled.getText().trim();
        String publishedYear = publishedYearInForTextFiled.getText().trim();

        List<Book> books = userService.searchBook(
                id.isEmpty() ? null : id,
                bookName.isEmpty() ? null : bookName,
                publishedYear.isEmpty() ? null : publishedYear,
                author.isEmpty() ? null : author,
                publisher.isEmpty() ? null : publisher
        );

        if (books.isEmpty()) {
            bookList.clear();
            bookTableView.setItems(bookList);
        } else {
            bookList.clear();
            bookList.addAll(books);
            bookTableView.setItems(bookList);
            bookTableView.refresh();
        }
    }


    /**
     * refresh sach.
     */
    private void refreshBookList() {
        List<Book> books = userService.getAllBooks();
        bookList.clear();
        bookList.addAll(books);
        bookTableView.setItems(bookList);
        bookTableView.refresh();
    }

    /**
     * comment.
     */
    private void refreshComment() {
        int idBook = Integer.parseInt(bookIDInForTextField.getText());
        List<Comment> coms = userService.getCommentByIdBook(idBook);
        commentsList.clear();
        commentsList.addAll(coms);
        commentTableView.setItems(commentsList);
        commentTableView.refresh();
    }

    /**
     * giao dich.
     */
    private void refreshLoanList() {
        String currentUserAccount = LogIn.account;
        List<Loan> loans = userService.getTransactionsByUser(currentUserAccount);
        loanList.clear();
        loanList.addAll(loans);
        loanTableView.setItems(loanList);
        loanTableView.refresh();
    }

    /**
     * lua chon.
     *
     * @param actionEvent click
     */
    public void onOptionMenuUClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Option Menu");
        alert.setHeaderText(null);
        alert.setContentText("Option menu clicked.");
        alert.showAndWait();
    }

    /**
     * info nguoi dung.
     *
     * @param actionEvent click
     */
    public void onInforUClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Personal Information");
        alert.setHeaderText(null);
        alert.setContentText("Displaying personal information.");
        alert.showAndWait();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/userInfor.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) optionU.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * dang xuat.
     *
     * @param actionEvent click
     */
    public void onLogOutUClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn đăng xuất không?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {

                    LogIn.account = null;

                    loanList.clear();
                    loanTableView.setItems(loanList);
                    loanTableView.refresh();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/logIn.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) logOutU.getParentPopup().getOwnerWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                    ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();

                    System.out.println("Đăng xuất thành công.");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Không thể tải màn hình đăng nhập.");
                }
            }
        });
    }


    /**
     * tab.
     *
     * @param event click
     */
    public void onBookInforButtonClick(Event event) {
    }

    /**
     * chon sach.
     *
     * @param actionEvent click
     */
    public void onSelectBookButton(ActionEvent actionEvent) {
        Book searchBook = bookTableView.getSelectionModel().getSelectedItem();
        if (searchBook == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Chưa chọn sách");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn sách!");
            alert.showAndWait();
            return;
        }
        bookIDInForTextField.setText(String.valueOf(searchBook.getId()));
        bookNameInForTextFiled.setText(searchBook.getBookName());
        authorInForTextFiled.setText(searchBook.getAuthor());
        publisherInForTextFiled.setText(searchBook.getPublisher());
        publishedYearInForTextFiled.setText(String.valueOf(searchBook.getPublishYear()));

        bookIDReviewField.setText(String.valueOf(searchBook.getId()));
        bookNameReviewField.setText(searchBook.getBookName());
        authorReviewField.setText(searchBook.getAuthor());
        publisherReviewField.setText(searchBook.getPublisher());
        publishedYearReviewField.setText(String.valueOf(searchBook.getPublishYear()));

        refreshComment();

        nameUser.setCellValueFactory(new PropertyValueFactory<>("userAccount"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));

        commentTableView.setItems(commentsList);
        String bookName = searchBook.getBookName();
        String author = searchBook.getAuthor();
        try {
            String imageUrl;
            if (imageService.getUrlByBookNameAndAuthor(bookName, author) == null) {
                imageUrl = "";
            } else {
                imageUrl = imageService.getImagePath(imageService.getUrlByBookNameAndAuthor(bookName, author));
            }
            if (imageUrl != null && !imageUrl.isEmpty()) {
                File imageFile = new File(imageUrl);
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    renImageBook.setImage(image);
                } else {
                    renImageBook.setImage(new Image("F:\\btlOOP\\demo\\src\\main\\resources\\image\\document.jpg"));
                }
            } else {
                renImageBook.setImage(new Image("F:\\btlOOP\\demo\\src\\main\\resources\\image\\document.jpg"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Hiển thị ảnh mặc định nếu có lỗi
            renImageBook.setImage(new Image("F:\\btlOOP\\demo\\src\\main\\resources\\image\\document.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * tim sach.
     *
     * @param actionEvent click.
     */
    public void onSearchBookUButtonClick(ActionEvent actionEvent) {
        String id = bookIDInForTextField.getText().trim();
        String bookName = bookNameInForTextFiled.getText().trim();
        String author = authorInForTextFiled.getText().trim();
        String publisher = publisherInForTextFiled.getText().trim();
        String publishedYear = publishedYearInForTextFiled.getText().trim();

        if (id.isEmpty() && bookName.isEmpty() && publishedYear.isEmpty() &&
                author.isEmpty() && publisher.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thiếu thông tin");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập ít nhất một từ khóa để tìm kiếm sách!");
            alert.showAndWait();
            return;
        }

        List<Book> books = userService.searchBook(
                id.isEmpty() ? null : id,
                bookName.isEmpty() ? null : bookName,
                publishedYear.isEmpty() ? null : publishedYear,
                author.isEmpty() ? null : author,
                publisher.isEmpty() ? null : publisher
        );

        if (books.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Kết quả tìm kiếm");
            alert.setHeaderText(null);
            alert.setContentText("Không tìm thấy sách nào với thông tin đã nhập.");
            alert.showAndWait();
        } else {
            bookList.clear();
            bookList.addAll(books);
            bookTableView.setItems(bookList);
            bookTableView.refresh();
        }
    }

    /**
     * nut tab.
     *
     * @param event clcik
     */
    public void onBorrowReturnUManageClick(Event event) {
    }

    /**
     * quey update book.
     *
     * @param bookId            id
     * @param newAvailableBooks sach con lai
     * @return logic
     */
    public boolean updateAvailableBooks(int bookId, int newAvailableBooks) {
        String sql = "UPDATE Books SET availableBooks = ? WHERE id = ?";
        try (Connection con = database.connectDB()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, newAvailableBooks);
                ps.setInt(2, bookId);
                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * muon sach.
     *
     * @param actionEvent click
     * @throws SQLException loi sql
     */
    public void onBorrowButtonClick(ActionEvent actionEvent) throws SQLException {
        Book searchBook = bookTableView.getSelectionModel().getSelectedItem();
        if (searchBook == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Chưa chọn sách");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn sách để mượn!");
            alert.showAndWait();
            return;
        }
        bookIDInForTextField.setText(String.valueOf(searchBook.getId()));
        bookNameInForTextFiled.setText(searchBook.getBookName());
        authorInForTextFiled.setText(searchBook.getAuthor());
        publishedYearInForTextFiled.setText(searchBook.getPublisher());
        publisherInForTextFiled.setText(String.valueOf(searchBook.getPublishYear()));
        int avai = searchBook.getAvailableBooks();

        Alert alert;
        if (avai <= 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hết sách rồi!");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn sách khác hoặc thử lại sau.");
            alert.showAndWait();
            return;
        }

        LocalDate borrowDate = LocalDate.now();
        LocalDate returnDate = borrowDate.plusDays(5);
        Loan newLoan = new Loan(
                LogIn.account,
                searchBook.getId(),
                borrowDate,
                returnDate
        );

        if (!updateAvailableBooks(searchBook.getId(), avai - 1)) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi hệ thống");
            alert.setHeaderText(null);
            alert.setContentText("Không thể cập nhật số lượng sách. Vui lòng thử lại.");
            alert.showAndWait();
            return;
        }

        if (loanService.addTransaction(newLoan)) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText("Hãy trả sách trong 5 ngày tới nhé!");
            alert.showAndWait();
            searchBook.setAvailableBooks(avai - 1);
            loanList.add(newLoan);
            bookTableView.refresh();
            loanTableView.refresh();
        } else {
            updateAvailableBooks(searchBook.getId(), avai);
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi giao dịch");
            alert.setHeaderText(null);
            alert.setContentText("Không thể hoàn tất giao dịch. Vui lòng thử lại.");
            alert.showAndWait();
        }
    }

    /**
     * tra sach.
     *
     * @param actionEvent click
     */
    public void onReturnButtonClick(ActionEvent actionEvent) {
        Loan selectedLoan = loanTableView.getSelectionModel().getSelectedItem();
        if (selectedLoan == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Chưa chọn sách");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn sách để trả!");
            alert.showAndWait();
            return;
        }

        Book selectedBook = bookList.stream()
                .filter(book -> book.getId() == selectedLoan.getBookId())
                .findFirst()
                .orElse(null);

        if (selectedBook == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Không tìm thấy sách");
            alert.setHeaderText(null);
            alert.setContentText("Không tìm thấy sách trong danh sách. Vui lòng thử lại!");
            alert.showAndWait();
            return;
        }
        int currentAvailableBooks = selectedBook.getAvailableBooks();
        boolean success = loanService.deleteTransaction(selectedLoan.getTransactionId());

        boolean update = updateAvailableBooks(selectedBook.getId(), currentAvailableBooks + 1);

        if (success && update) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText("Sách được trả lại thành công!");
            alert.showAndWait();

            loanList.remove(selectedLoan);

            selectedBook.setAvailableBooks(currentAvailableBooks + 1);

            loanTableView.refresh();
            bookTableView.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thất bại");
            alert.setHeaderText(null);
            alert.setContentText("Không thể trả sách. Vui lòng thử lại!");
            alert.showAndWait();
        }

    }

    /**
     * nut tab.
     *
     * @param event click
     * @throws SQLException sql
     */
    public void onBookReviewClick(Event event) throws SQLException {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Chưa chọn sách");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn sách để xem bình luận!");
            alert.showAndWait();
            return;
        }

        int bookId = selectedBook.getId();
        String query = "SELECT comment,userName FROM Comments WHERE bookId = ?;";
        connection = database.connectDB();
        assert connection != null;
        preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, bookId);

        resultSet = preparedStatement.executeQuery();
        commentTableView.getItems().clear();
        while (resultSet.next()) {

            String userName = resultSet.getString("userName");
            String content = resultSet.getString("comment");
            Comment comment = new Comment(bookId, content, userName);

            commentTableView.getItems().add(comment);
        }
        if (commentTableView.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Không có bình luận");
            alert.setHeaderText(null);
            alert.setContentText("Không tìm thấy bình luận nào cho sách này!");
            alert.showAndWait();
        }
        commentTableView.refresh();

    }

    /**
     * ham them comment.
     *
     * @param actionEvent click
     * @throws SQLException loi sql
     */
    public void onAddCommentButtonClick(ActionEvent actionEvent) throws SQLException {
        Book searchBook = bookTableView.getSelectionModel().getSelectedItem();
        if (searchBook == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Chưa chọn sách");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn sách để bình luận!");
            alert.showAndWait();
            return;
        }

        String comment = comments.getText().trim();
        if (comment.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thiếu thông tin");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập bình luận trước khi thêm!");
            alert.showAndWait();
            return;
        }

        String query = "INSERT INTO Comments (bookId, comment,userName) VALUES (?, ?,?)";
        connection = database.connectDB();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, searchBook.getId());
        preparedStatement.setString(2, comment);
        preparedStatement.setString(3, LogIn.nameString);

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText("Bình luận đã được thêm thành công!");
            alert.showAndWait();

            comments.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Lỗi ở đâu đó trong hàm addComments");
            alert.showAndWait();
        }
        Comment cmt = new Comment(searchBook.getId(), comment, LogIn.nameString);
        commentsList.add(cmt);
        commentTableView.setItems(commentsList);
        commentTableView.refresh();

    }

    /**
     * check update
     *
     * @param comment cmt
     * @return logic
     */
    public boolean updateCom(Comment comment) {
        try {
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * sua comment.
     *
     * @param actionEvent click
     */
    public void onEditCommentButtonClick(ActionEvent actionEvent) {
        Comment searchCom = (Comment) commentTableView.getSelectionModel().getSelectedItem();
        if (searchCom == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Chưa chọn Comment");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn Comment để sửa!");
            alert.showAndWait();
            return;
        }
        //sua
        try {

            String comment = comments.getText().trim();

            if (comment.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Lỗi comment");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng không để trống Comment");
                alert.showAndWait();
                return;
            }

            boolean checkTrung = !Objects.equals(searchCom.getContent(), comment);

            if (!checkTrung) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Không có thay đổi");
                alert.setHeaderText(null);
                alert.setContentText("Không có Comment nào được thay đổi.");
                alert.showAndWait();
                return;
            }

            searchCom.setContent(comment);

            boolean success = updateCom(searchCom);

            Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Cập nhật thông tin");
            alert.setHeaderText(null);
            alert.setContentText(success ? "Cập nhật thông tin Comment thành công!"
                    : "Cập nhật thông tin giao dịch thất bại!");
            alert.showAndWait();

            searchCom.setContent(comment);
            commentTableView.refresh();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Dữ liệu nhập vào không hợp lệ. Vui lòng kiểm tra lại!");
            alert.showAndWait();
        }
    }

    /**
     * xoa cmt.
     *
     * @param actionEvent click
     */
    public void onDeleteCommentButtonClick(ActionEvent actionEvent) {
        Comment selectedComt = (Comment) commentTableView.getSelectionModel().getSelectedItem();

        if (selectedComt == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thiếu lựa chọn");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn giao dịch để xóa!");
            alert.showAndWait();
            return;
        }

        if (!LogIn.nameString.equals(selectedComt.getUserAccount())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi xóa comment người khác");
            alert.setHeaderText(null);
            alert.setContentText("Bạn không có quyền xóa comment người khác");
        }

        boolean success = userService.deleteComment(selectedComt.getContent(), selectedComt.getUserAccount());
        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText("Comment đã được xóa thành công!");
            alert.showAndWait();
            commentsList.remove(selectedComt);
            commentTableView.setItems(commentsList);
            commentTableView.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thất bại");
            alert.setHeaderText(null);
            alert.setContentText("Không thể xóa Comment!");
            alert.showAndWait();
        }
    }


    /**
     * tat bat nhac.
     *
     * @param actionEvent click
     */
    public void onVolumeClick(ActionEvent actionEvent) {
        if (HelloApplication.isMusic()) {
            HelloApplication.getMediaPlayer().pause();
            HelloApplication.setMusic(false);
        } else {
            HelloApplication.getMediaPlayer().play();
            HelloApplication.setMusic(true);
        }
    }

    /**
     * thaot.
     *
     * @param actionEvent click
     */
    public void onExitButton(ActionEvent actionEvent) {
        thoat();
    }

    /**
     * tim sach.
     *
     * @param actionEvent click
     */
    public void onSearchBookBorrowButtonClick(ActionEvent actionEvent) {
        String currentUserAccount = LogIn.account;

        if (currentUserAccount == null || currentUserAccount.isEmpty()) {
            System.out.println("User is not logged in.");
            return;
        }

        String transactionId = transactionIDTextField.getText().trim();
        String userAccount = userAccountTextField.getText().trim();
        String bookId = book_idTextField.getText().trim();
        String borrowDate = borrowDateTextField.getText().trim();
        String returnDate = returnDateTextField.getText().trim();
        String status = statusTextField.getText().trim();

        List<Loan> results = userService.searchTransactions(
                transactionId.isEmpty() ? null : Integer.parseInt(transactionId),
                userAccount.isEmpty() ? currentUserAccount : userAccount,
                bookId.isEmpty() ? null : Integer.parseInt(bookId),
                borrowDate.isEmpty() ? null : LocalDate.parse(borrowDate),
                returnDate.isEmpty() ? null : LocalDate.parse(returnDate),
                status.isEmpty() ? null : Loan.LoanStatus.valueOf(status.toUpperCase())
        );

        loanList.clear();
        loanList.addAll(results);
        loanTableView.setItems(loanList);
        loanTableView.refresh();
    }

}