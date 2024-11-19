package org.example.demo.controller;

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
import models.User;
import org.example.demo.service.AdminService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AdminController {

    private final AdminService adminService = new AdminService();

    public TableView<User> userTableView;
    public SplitMenuButton option;
    public MenuItem logOut;
    public MenuItem infor;
    public Tab userManageButton;
    public MenuItem volume;
    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    public TextField userID;
    @FXML
    public TextField userSurname;
    @FXML
    public TextField lastName;
    @FXML
    public TextField dateOfBirthInUserMana;
    @FXML
    public TextField genderInuser;
    @FXML
    public TextField emailInUser;
    @FXML
    public TextField accountNameInUser;
    @FXML
    public TextField rolesInUser;
    @FXML
    public TextField warningInUser;
    @FXML
    public Button searchUserButton;
    @FXML
    public Button addUserButton;
    @FXML
    public Button editInforButton;
    @FXML
    public Button deleteUserButton;
    @FXML
    public TableColumn<User, String> idUserColum;
    @FXML
    public TableColumn<User, String> surnameColum;
    @FXML
    public TableColumn<User, String> lastnameColum;
    @FXML
    public TableColumn<User, String> dateOfBirthColum;
    @FXML
    public TableColumn<User, String> genderColum;
    @FXML
    public TableColumn<User, String> emailColum;
    @FXML
    public TableColumn<User, String> userNameColum;
    @FXML
    public TableColumn<User, String> accountNameColum;
    @FXML
    public TableColumn<User, String> userPasswordColum;
    @FXML
    public TableColumn<User, Integer> warningColum;
    public Tab bookManageButton;
    public TextField bookIDManage;
    public TextField nameOfBookInBokkManage;
    public TextField publishedYearInBookManage;
    public TextField quantityInBookManage;
    public TextField totalInBookManage;
    public Button addBookButton;
    public TextField author;
    public TextField publisher;
    public Button editBookButton;
    public Button deleteBookButton;
    public Button searchBookButton;
    public TableColumn idOfBookInBookManageColum;
    public TableColumn bookNameInBookManageColum;
    public TableColumn authorInBookManageColum;
    public TableColumn publisherInBookManageColum;
    public TableColumn publishYearInBookManageColum;
    public TableColumn availableBooksInBookManageColum;
    public TableColumn totalBooksInBookManageColum;
    public Tab borrowReturnManage;
    public TextField transactionID;
    public TextField transactionAccount;
    public TextField bookIDTrans;
    public TextField soLuong;
    public TextField borrowDate;
    public TextField returnDate;
    public TextField status;
    public Button searchTransactionButton;
    public Button editTransactionButton;
    public Button deleteTransaction;
    public Button addTransaction;
    public TableColumn transaction_idColum;
    public TableColumn userAccountColum;
    public TableColumn book_idColum;
    public TableColumn borrowDateColum;
    public TableColumn endDateColum;
    public TableColumn returnDateColum;
    public TableColumn statusColum;

    @FXML
    public void initialize() {
        // Liên kết các cột của bảng quản lý người dùng với thuộc tính của lớp User
        idUserColum.setCellValueFactory(new PropertyValueFactory<>("id"));
        surnameColum.setCellValueFactory(new PropertyValueFactory<>("surname"));
        lastnameColum.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        dateOfBirthColum.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        genderColum.setCellValueFactory(new PropertyValueFactory<>("gender"));
        emailColum.setCellValueFactory(new PropertyValueFactory<>("email"));
        userNameColum.setCellValueFactory(new PropertyValueFactory<>("userName"));
        accountNameColum.setCellValueFactory(new PropertyValueFactory<>("userAccount"));
        userPasswordColum.setCellValueFactory(new PropertyValueFactory<>("password"));
        warningColum.setCellValueFactory(new PropertyValueFactory<>("warning"));

        // Liên kết ObservableList với TableView
        userTableView.setItems(userList);
    }

    public void onOptionMenuClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Option Menu");
        alert.setHeaderText(null);
        alert.setContentText("Option menu clicked.");
        alert.showAndWait();
    }

    public void onInforClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Personal Information");
        alert.setHeaderText(null);
        alert.setContentText("Displaying personal information.");
        alert.showAndWait();
    }

    public void onLogOutClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/signUp.fxml"));
                    Parent root = loader.load();
                    Stage stage =(Stage) logOut.getParentPopup().getOwnerWindow();
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

    public void onUserManageButtonClick(Event event) {
    }

    public void onSearchUserButtonClick(ActionEvent actionEvent) {
        String keyword = userSurname.getText().trim();

        if (keyword.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thiếu thông tin");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập từ khóa để tìm kiếm người dùng!");
            alert.showAndWait();
        } else {
            ArrayList<User> users = (ArrayList<User>) adminService.searchUser(keyword);
            if (users.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Kết quả tìm kiếm");
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thấy người dùng nào với từ khóa đã nhập.");
                alert.showAndWait();
            } else {

                userList.clear();

                userList.addAll(users);

                userTableView.setItems(userList);
                userTableView.refresh();
            }
        }
    }

    public void onAddUserButtonClick(ActionEvent actionEvent) {
        if (!validateUserInput()) {
            return; // Nếu không hợp lệ, kết thúc hàm
        }

        try {
            int id = Integer.parseInt(userID.getText().trim());
            String surname = userSurname.getText().trim();
            String lastname = lastName.getText().trim();
            LocalDate dateOfBirth = LocalDate.parse(dateOfBirthInUserMana.getText().trim());
            String gender = genderInuser.getText().trim();
            String email = emailInUser.getText().trim();
            String userName = accountNameInUser.getText().trim();
            String userAccount = accountNameInUser.getText().trim();
            String roles = rolesInUser.getText().trim();
            int warning = Integer.parseInt(warningInUser.getText().trim());

            User newUser = new User(id, surname, lastname, dateOfBirth, gender, email, userName, userAccount, roles, warning);
            boolean success = adminService.addUser(newUser);

            Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Thêm người dùng");
            alert.setHeaderText(null);
            alert.setContentText(success ? "Người dùng đã được thêm thành công!" : "Thêm người dùng thất bại!");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi định dạng ngày");
            alert.setHeaderText(null);
            alert.setContentText("Ngày sinh không đúng định dạng. Vui lòng nhập đúng định dạng yyyy-MM-dd!");
            alert.showAndWait();
        }
    }

    public boolean validateUserInput() {
        String id = userID.getText().trim();
        String surname = userSurname.getText().trim();
        String lastname = lastName.getText().trim();
        String dateOfBirthString = dateOfBirthInUserMana.getText().trim();
        String gender = genderInuser.getText().trim();
        String email = emailInUser.getText().trim();
        String userName = accountNameInUser.getText().trim();
        String roles = rolesInUser.getText().trim();
        String warningString = warningInUser.getText().trim();

        if (id.isEmpty() || surname.isEmpty() || lastname.isEmpty() || dateOfBirthString.isEmpty() ||
                gender.isEmpty() || email.isEmpty() || userName.isEmpty() || roles.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thiếu thông tin");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin!");
            alert.showAndWait();
            return false;
        }

        try {
            LocalDate.parse(dateOfBirthString);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi định dạng ngày");
            alert.setHeaderText(null);
            alert.setContentText("Ngày sinh không đúng định dạng. Vui lòng nhập đúng định dạng yyyy-MM-dd!");
            alert.showAndWait();
            return false;
        }

        if (!warningString.isEmpty()) {
            try {
                Integer.parseInt(warningString);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi định dạng cảnh báo");
                alert.setHeaderText(null);
                alert.setContentText("Giá trị cảnh báo không hợp lệ. Vui lòng nhập số nguyên!");
                alert.showAndWait();
                return false;
            }
        }

        // Kiểm tra định dạng email
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi định dạng email");
            alert.setHeaderText(null);
            alert.setContentText("Địa chỉ email không hợp lệ. Vui lòng nhập lại!");
            alert.showAndWait();
            return false;
        }

        return true;
    }


    public void onEditInforButtonClick(ActionEvent actionEvent) {
    }

    public void onDeleteUserButtonClick(ActionEvent actionEvent) {
    }

    public void onBookManageButtonClick(Event event) {
    }

    public void onAddBookButtonClick(ActionEvent actionEvent) {
    }

    public void onEditBookButtonClick(ActionEvent actionEvent) {
    }

    public void onDeleteBookButtonClick(ActionEvent actionEvent) {
    }

    public void onSearchBookButtonClick(ActionEvent actionEvent) {
    }

    public void onBorrowReturnManageClick(Event event) {
    }

    public void onSearchTransactionButtonClick(ActionEvent actionEvent) {
    }

    public void onEditTransactionButtonClick(ActionEvent actionEvent) {
    }

    public void onDeleteTransactionButtonCLick(ActionEvent actionEvent) {
    }

    public void onAddTransactionButtonClick(ActionEvent actionEvent) {
    }

    public void onVolumeClick(ActionEvent actionEvent) {
    }


}
