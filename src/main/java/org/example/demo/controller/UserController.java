package org.example.demo.controller;

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
import javafx.stage.Stage;
import models.Book;
import models.Loan;
import models.User;
import org.example.demo.GiaoDienChung;
import org.example.demo.HelloApplication;
import org.example.demo.SignUp;
import org.example.demo.data.LoanRepository;
import org.example.demo.database;
import org.example.demo.service.AdminService;
import org.example.demo.service.LoanService;
import org.example.demo.service.UserService;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class UserController extends GiaoDienChung {

    private final UserService userService = new UserService();
    private final LoanService loanService = new LoanService();

    public TableView<Loan> loanTableView;
    public TableView<Book> bookTableView;
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

    private ObservableList<Loan> loanList = FXCollections.observableArrayList();
    private ObservableList<Book> bookList = FXCollections.observableArrayList();

    public void initialize() {

        refreshBookList();
        refreshLoanList();

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
    }

    private void refreshBookList() {
        List<Book> books = userService.getAllBooks();
        bookList.clear();
        bookList.addAll(books);
        bookTableView.setItems(bookList);
        bookTableView.refresh();
    }

    private void refreshLoanList() {
        String currentUserAccount = SignUp.account; // Lấy tài khoản người dùng hiện tại

        if (currentUserAccount == null || currentUserAccount.isEmpty()) {
            loanList.clear();
            loanTableView.setItems(loanList);
            loanTableView.refresh();
            return;
        }

        List<Loan> loans = userService.getUserTransactions(currentUserAccount); // Lấy giao dịch của người dùng hiện tại
        loanList.clear();
        loanList.addAll(loans);
        loanTableView.setItems(loanList);
        loanTableView.refresh();
    }

    public void onOptionMenuUClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Option Menu");
        alert.setHeaderText(null);
        alert.setContentText("Option menu clicked.");
        alert.showAndWait();
    }

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

    public void onLogOutUClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn đăng xuất không?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Xóa dữ liệu người dùng hiện tại
                    SignUp.account = null;

                    // Xóa dữ liệu trong bảng loanTableView
                    loanList.clear();
                    loanTableView.setItems(loanList);
                    loanTableView.refresh();

                    // Chuyển về màn hình đăng nhập
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/signUp.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) logOutU.getParentPopup().getOwnerWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                    // Đóng cửa sổ hiện tại
                    ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();

                    System.out.println("Đăng xuất thành công.");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Không thể tải màn hình đăng nhập.");
                }
            }
        });
    }


    public void onBookInforButtonClick(Event event) {
    }

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

    //nút tab
    public void onBorrowReturnUManageClick(Event event) {
        String currentUserAccount = SignUp.account;

        if (currentUserAccount == null || currentUserAccount.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Không thể xác định tài khoản người dùng hiện tại.");
            alert.showAndWait();
            return;
        }

        List<Loan> loans = userService.getUserTransactions(currentUserAccount);

        if (loans.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Không có giao dịch nào để hiển thị.");
            alert.showAndWait();
        } else {
            loanList.clear();
            loanList.addAll(loans);
            loanTableView.setItems(loanList);
            loanTableView.refresh();
        }
    }

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
        if (avai == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hết sách rồi!");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn sách khác hoặc thử lại sau");
            alert.showAndWait();
            return;
        }

        LocalDate borrowDate = LocalDate.now();
        LocalDate returnDate = borrowDate.plusDays(5);
        Loan newLoan = new Loan(
                SignUp.account,
                searchBook.getId(),
                borrowDate,
                returnDate
        );
        if(loanService.addTransaction(newLoan)) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText("Hãy trả thư viện trong 5 ngày tới nhé");
            alert.showAndWait();
            searchBook.setAvailableBooks(avai - 1);

        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Test loi");
        }
        loanList.add(newLoan);
        bookTableView.refresh();
        loanTableView.refresh();
    }

    public void onReturnButtonClick(ActionEvent actionEvent)  {
        Loan selectedLoan = loanTableView.getSelectionModel().getSelectedItem();
        if (selectedLoan == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Chưa chọn sách");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn sách để trả!");
            alert.showAndWait();
            return;
        }
        boolean success = loanService.deleteTransaction(selectedLoan.getTransactionId());
        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText("Sách được trả lại!");
            alert.showAndWait();
            selectedLoan.setReturnDate(LocalDate.now());
            loanList.remove(selectedLoan);
            loanTableView.setItems(loanList);
            loanTableView.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thất bại");
            alert.setHeaderText(null);
            alert.setContentText("Không thể xóa giao dịch!");
            alert.showAndWait();
        }

        Book book = (Book) userService.searchBook(String.valueOf(selectedLoan.getBookId()),
                null, null, null, null);
        book.setAvailableBooks(book.getAvailableBooks() + 1);
        loanTableView.refresh();
        bookTableView.refresh();
    }

    //nút tab
    public void onBookReviewClick(Event event) {
    }

    public void onAddCommentButtonClick(ActionEvent actionEvent) {
    }

    public void onEditCommentButtonClick(ActionEvent actionEvent) {
    }

    public void onDeleteCommentButtonClick(ActionEvent actionEvent) {
    }


    public void onVolumeClick(ActionEvent actionEvent) {
        if (HelloApplication.isMusic()) {
            HelloApplication.getMediaPlayer().pause();
            HelloApplication.setMusic(false);
        } else {
            HelloApplication.getMediaPlayer().play();
            HelloApplication.setMusic(true);
        }
    }

    public void onExitButton(ActionEvent actionEvent) {
        thoat();
    }

    public void onSearchBookBorrowButtonClick(ActionEvent actionEvent) {
        String transactionId = transactionIDTextField.getText().trim();
        String bookId = book_idTextField.getText().trim();
        String currentUserAccount = SignUp.account;

        if (currentUserAccount == null || currentUserAccount.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Không thể xác định tài khoản người dùng hiện tại.");
            alert.showAndWait();
            return;
        }

        Integer transactionIdInt = transactionId.isEmpty() ? null : Integer.parseInt(transactionId);
        Integer bookIdInt = bookId.isEmpty() ? null : Integer.parseInt(bookId);

        // Gọi UserService để tìm kiếm giao dịch
        List<Loan> loans = userService.searchUserTransactions(transactionIdInt, currentUserAccount, bookIdInt);

        if (loans.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Kết quả tìm kiếm");
            alert.setHeaderText(null);
            alert.setContentText("Không tìm thấy giao dịch nào với thông tin đã nhập.");
            alert.showAndWait();
        } else {
            loanList.clear();
            loanList.addAll(loans);
            loanTableView.setItems(loanList);
            loanTableView.refresh();
        }
    }
}