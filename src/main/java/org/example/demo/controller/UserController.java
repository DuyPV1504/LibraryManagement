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
import org.example.demo.GiaoDienChung;
import org.example.demo.HelloApplication;
import org.example.demo.service.AdminService;
import org.example.demo.service.UserService;

import java.io.IOException;
import java.util.List;

public class UserController extends GiaoDienChung {

    private final UserService userService = new UserService();

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
    public TableColumn availableBooksInBorrowCollum;
    public TableColumn borrowDateCollum;
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
    public TableColumn transaction_idCollumn;
    public TableColumn userAccountCollumn;
    public TableColumn book_idCollumn;
    public TableColumn returnDateCollumn;
    public TableColumn statusCollumn;
    public TextField bookIDReviewField;
    public TextField bookNameReviewField;
    public TextField authorReviewField;
    public TextField publisherReviewField;
    public TextField publishedYearReviewField;

    private ObservableList<Book> bookList = FXCollections.observableArrayList();

    public void initialize() {

        refreshBookList();

        bookIDInForCollumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookNameCollumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        authorCollumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCollumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        publishYearCollumn.setCellValueFactory(new PropertyValueFactory<>("publishYear"));
        totalBooksCollumn.setCellValueFactory(new PropertyValueFactory<>("totalBooks"));
        availableBooksInInfoBookCollumn.setCellValueFactory(new PropertyValueFactory<>("availableBooks"));

        bookTableView.setItems(bookList);
    }

    private void refreshBookList() {
        List<Book> books = userService.getAllBooks();
        bookList.clear();
        bookList.addAll(books);
        bookTableView.setItems(bookList);
        bookTableView.refresh();
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
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/signUp.fxml"));
                    Parent root = loader.load();
                    Stage stage =(Stage) logOutU.getParentPopup().getOwnerWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();

                    System.out.println("Logged out successfully.");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Failed to load the login screen.");
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


    public void onBorrowReturnUManageClick(Event event) {
    }

    public void onBorrowButtonClick(ActionEvent actionEvent) {
    }

    public void onReturnButtonClick(ActionEvent actionEvent) {
    }

    public void onBookReviewClick(Event event) {
    }

    public void onAddCommentButtonClick(ActionEvent actionEvent) {
    }

    public void onEditCommentButtonClick(ActionEvent actionEvent) {
    }

    public void onDeleteCommentButtonClick(ActionEvent actionEvent) {
    }


    public void onVolumeClick(ActionEvent actionEvent) {
        if(HelloApplication.isMusic()){
            HelloApplication.getMediaPlayer().pause();
            HelloApplication.setMusic(false);
        }else{
            HelloApplication.getMediaPlayer().play();
            HelloApplication.setMusic(true);
        }
    }

    public void onExitButton(ActionEvent actionEvent) {
        thoat();
    }
}
