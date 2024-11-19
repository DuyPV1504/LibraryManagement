package org.example.demo.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class UserController {
    public SplitMenuButton optionU;
    public MenuItem inforU;
    public MenuItem logOutU;
    public Tab bookInfor;
    public TextField bookIDU;
    public TextField bookNameU;
    public TextField authorU;
    public TextField publishedYearU;
    public TextField publisherU;
    public Button searchBookU;
    public TableColumn idUser;
    public TableColumn nameOfBook;
    public TableColumn author;
    public TableColumn publisher;
    public TableColumn publishYear;
    public TableColumn totalBooks;
    public Tab borrownReturnUManage;
    public TextField transactionIDU;
    public TextField transactionAccountU;
    public TextField bookIDUTrans;
    public TextField numberOfBook;
    public TextField borrowDateU;
    public TextField returnDateU;
    public TextField statusU;
    public Button borrowButton;
    public TableColumn availableBooksInBorrow;
    public TableColumn availableBooksInInfoBook;
    public Button returnButton;
    public TableColumn transaction_id;
    public TableColumn userAccount;
    public TableColumn book_id;
    public TableColumn availableBooks;
    public TableColumn returnDate;
    public TableColumn status;
    public Tab bookReview;
    public TextField bookIDReview;
    public TextField bookNameReview;
    public TextField authorReview;
    public TextField publisherReview;
    public TextField publishedYearReview;
    public TextArea comments;
    public Button addCommentButton;
    public Button editCommentButton;
    public Button deleteCommentButton;

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

    public void soLuongConLai(TableColumn.CellEditEvent cellEditEvent) {
    }
}
