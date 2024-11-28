package org.example.demo.controller;

import API.RenImage;
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
import models.Image;
import models.Loan;
import models.User;
import models.Book;
import org.example.demo.GiaoDienChung;
import org.example.demo.HelloApplication;
import org.example.demo.service.AdminService;
import org.example.demo.service.BookService;
import org.example.demo.service.ImageService;
import org.example.demo.service.LoanService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminController extends GiaoDienChung {

    private final AdminService adminService = new AdminService();
    private final BookService bookService = new BookService();
    private final LoanService loanService = new LoanService();
    private final ImageService imageService = new ImageService();

    public TableView<User> userTableView;
    public TableView<Book> bookTableView;
    public TableView<Loan> loanTableView;

    public SplitMenuButton option;
    public MenuItem logOut;
    public MenuItem infor;
    public Tab userManageButton;
    public MenuItem volume;
    public Button exitButton;
    public TextField availableBooksInBookManage;
    public TextField bookIDTextField;
    public TextField bookNameTextField;
    public TextField authorTextField;
    public TextField publisherTextField;
    public TextField publishedYearTextField;
    public TextField transactionIDTextField;
    public TextField userAccountTextField;
    public TextField bookIDInBorrowTextField;
    public TextField borrowDateTextField;
    public TextField statusTextField;
    public TextField endDateTextField;
    public Button select;
    public Button selectBook;
    public Button selectTrans;
    public TextField linkAdress;

    private ObservableList<User> userList = FXCollections.observableArrayList();
    private ObservableList<Book> bookList = FXCollections.observableArrayList();
    private ObservableList<Loan> loanList = FXCollections.observableArrayList();


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
    @FXML
    public Tab bookManageButton;
    @FXML
    public TextField totalInBookManage;
    @FXML
    public Button addBookButton;
    @FXML
    public TextField author;
    @FXML
    public Button editBookButton;
    @FXML
    public Button deleteBookButton;
    @FXML
    public Button searchBookButton;
    @FXML
    public TableColumn<Book, Integer> idOfBookInBookManageColum;
    @FXML
    public TableColumn<Book, String> bookNameInBookManageColum;
    @FXML
    public TableColumn<Book, String> authorInBookManageColum;
    @FXML
    public TableColumn<Book, String> publisherInBookManageColum;
    @FXML
    public TableColumn<Book, Integer> publishYearInBookManageColum;
    @FXML
    public TableColumn<Book, Integer> totalBooksInBookManageColum;
    @FXML
    public TableColumn<Book, Integer> availableBooksInBookManageColum;

    @FXML
    public Tab borrowReturnManage;
    @FXML
    public TextField status;
    @FXML
    public Button searchTransactionButton;
    @FXML
    public Button editTransactionButton;
    @FXML
    public Button deleteTransaction;
    @FXML
    public Button addTransaction;
    @FXML
    public TableColumn<Loan, Integer> transaction_idColum;
    @FXML
    public TableColumn<Loan, String> userAccountColum;
    @FXML
    public TableColumn<Loan, Integer> book_idColum;
    @FXML
    public TableColumn<Loan, LocalDate> borrowDateColum;
    @FXML
    public TableColumn<Loan, LocalDate> endDateColum;
    @FXML
    public TableColumn<Loan, LocalDate> returnDateColum;
    @FXML
    public TableColumn<Loan, Loan.LoanStatus> statusColum;


    @FXML
    public void initialize() {

        refreshBookList();
        refreshLoanList();
        refreshUserList();

        bookIDTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchBooksDynamic();
        });
        bookNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchBooksDynamic();
        });
        authorTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchBooksDynamic();
        });
        publisherTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchBooksDynamic();
        });
        publishedYearTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchBooksDynamic();
        });
        availableBooksInBookManage.textProperty().addListener((observable, oldValue, newValue) -> {
            searchBooksDynamic();
        });
        totalInBookManage.textProperty().addListener((observable, oldValue, newValue) -> {
            searchBooksDynamic();
        });

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

        idOfBookInBookManageColum.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookNameInBookManageColum.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        authorInBookManageColum.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherInBookManageColum.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        publishYearInBookManageColum.setCellValueFactory(new PropertyValueFactory<>("publishYear"));
        availableBooksInBookManageColum.setCellValueFactory(new PropertyValueFactory<>("availableBooks"));
        totalBooksInBookManageColum.setCellValueFactory(new PropertyValueFactory<>("totalBooks"));

        transaction_idColum.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        userAccountColum.setCellValueFactory(new PropertyValueFactory<>("userAccount"));
        book_idColum.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        borrowDateColum.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        endDateColum.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        returnDateColum.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        statusColum.setCellValueFactory(new PropertyValueFactory<>("status"));

        userTableView.setItems(userList);
        bookTableView.setItems(bookList);
        loanTableView.setItems(loanList);
    }

    private void searchBooksDynamic() {
        String id = bookIDTextField.getText().trim();
        String bookName = bookNameTextField.getText().trim();
        String author = authorTextField.getText().trim();
        String publisher = publisherTextField.getText().trim();
        String publishedYear = publishedYearTextField.getText().trim();
        String totalBooks = totalInBookManage.getText().trim();
        String availableBooks = availableBooksInBookManage.getText().trim();

        List<Book> books = adminService.searchBook(
                id.isEmpty() ? null : id,
                bookName.isEmpty() ? null : bookName,
                publishedYear.isEmpty() ? null : publishedYear,
                availableBooks.isEmpty() ? null : availableBooks,
                totalBooks.isEmpty() ? null : totalBooks,
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

    private void refreshBookList() {
        List<Book> books = adminService.getAllBooks();
        bookList.clear();
        bookList.addAll(books);
        bookTableView.setItems(bookList);
        bookTableView.refresh();
    }

    private void refreshLoanList() {
        List<Loan> loans = adminService.getAllTransactions();
        loanList.clear();
        loanList.addAll(loans);
        loanTableView.setItems(loanList);
        loanTableView.refresh();
    }

    private void refreshUserList() {
        List<User> users = adminService.getAllUsers();
        userList.clear();
        userList.addAll(users);
        userTableView.setItems(userList);
        userTableView.refresh();
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

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/userInfor.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) option.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
                    Stage stage = (Stage) logOut.getParentPopup().getOwnerWindow();
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

        String id = userID.getText().trim();
        String surname = userSurname.getText().trim();
        String lastname = lastName.getText().trim();
        String dateOfBirth = dateOfBirthInUserMana.getText().trim();
        String gender = genderInuser.getText().trim();
        String email = emailInUser.getText().trim();
        String userAccount = accountNameInUser.getText().trim();

        if (id.isEmpty() && surname.isEmpty() && lastname.isEmpty() &&
                dateOfBirth.isEmpty() && gender.isEmpty() && email.isEmpty() && userAccount.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thiếu thông tin");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập ít nhất một từ khóa để tìm kiếm người dùng!");
            alert.showAndWait();
            return;
        }

        ArrayList<User> users = (ArrayList<User>) adminService.searchUser(
                id.isEmpty() ? null : id,
                surname.isEmpty() ? null : surname,
                lastname.isEmpty() ? null : lastname,
                dateOfBirth.isEmpty() ? null : dateOfBirth,
                gender.isEmpty() ? null : gender,
                email.isEmpty() ? null : email,
                userAccount.isEmpty() ? null : userAccount
        );

        if (users.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Kết quả tìm kiếm");
            alert.setHeaderText(null);
            alert.setContentText("Không tìm thấy người dùng nào với thông tin đã nhập.");
            alert.showAndWait();
        } else {
            userList.clear();
            userList.addAll(users);

            userTableView.setItems(userList);
            userTableView.refresh();
        }
    }


    public void onAddUserButtonClick(ActionEvent actionEvent) {
        if (!validateUserInput()) {
            return;
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

    public void onSelectUserButton(ActionEvent actionEvent) {
        User searchUser = userTableView.getSelectionModel().getSelectedItem();
        if (searchUser == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Chưa chọn người dùng");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn một người dùng để chỉnh sửa!");
            alert.showAndWait();
            return;
        }

        //chua co ham lay link anh

        userID.setText(String.valueOf(searchUser.getId()));
        userSurname.setText(searchUser.getSurname());
        lastName.setText(searchUser.getLastname());
        dateOfBirthInUserMana.setText(searchUser.getDateOfBirth().toString());
        genderInuser.setText(searchUser.getGender());
        emailInUser.setText(searchUser.getEmail());
        accountNameInUser.setText(searchUser.getUserAccount());

    }

    public void onEditInforButtonClick(ActionEvent actionEvent) {
        User searchUser = userTableView.getSelectionModel().getSelectedItem();
        if (searchUser == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Chưa chọn người dùng");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn một người dùng để chỉnh sửa!");
            alert.showAndWait();
            return;
        }
        try {
            int id = Integer.parseInt(userID.getText().trim());
            String surname = userSurname.getText().trim();
            String lastname = lastName.getText().trim();
            LocalDate dateOfBirth = LocalDate.parse(dateOfBirthInUserMana.getText().trim());
            String gender = genderInuser.getText().trim();
            String email = emailInUser.getText().trim();
            String userAccount = accountNameInUser.getText().trim();

            if (id != searchUser.getId()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi đổi id người dùng");
                alert.setHeaderText(null);
                alert.setContentText("ID người dùng được tạo tự động vui lòng không đổi");
                alert.showAndWait();
            }

            boolean checkTrung = !searchUser.getSurname().equals(surname) ||
                    !searchUser.getLastname().equals(lastname) ||
                    !searchUser.getDateOfBirth().equals(dateOfBirth) ||
                    !searchUser.getGender().equals(gender) ||
                    !searchUser.getEmail().equals(email) ||
                    !searchUser.getUserAccount().equals(userAccount);

            if (!checkTrung) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Không có thay đổi");
                alert.setHeaderText(null);
                alert.setContentText("Không có thông tin nào được thay đổi.");
                alert.showAndWait();
                return;
            }

            searchUser.setSurname(surname);
            searchUser.setLastname(lastname);
            searchUser.setDateOfBirth(dateOfBirth);
            searchUser.setGender(gender);
            searchUser.setEmail(email);
            searchUser.setUserAccount(userAccount);

            boolean success = adminService.updateUser(searchUser);

            Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Cập nhật thông tin");
            alert.setHeaderText(null);
            alert.setContentText(success ? "Cập nhật thông tin người dùng thành công!" : "Cập nhật thông tin người dùng thất bại!");
            alert.showAndWait();
            userTableView.refresh();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Dữ liệu nhập vào không hợp lệ. Vui lòng kiểm tra lại!");
            alert.showAndWait();
        }
        ;
    }

    public void onDeleteUserButtonClick(ActionEvent actionEvent) {
        String idText = userID.getText().trim();

        if (idText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Xóa người dùng");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập ID người dùng để xóa!");
            alert.showAndWait();
            return;
        }

        try {
            int userId = Integer.parseInt(idText);
            int rowsDeleted = adminService.deleteUser(userId);

            if (rowsDeleted > 0) {
                refreshUserList();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText(null);
                alert.setContentText("Người dùng đã được xóa thành công!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Thất bại");
                alert.setHeaderText(null);
                alert.setContentText("Không thể xóa người dùng! Hãy kiểm tra ID.");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("ID phải là số hợp lệ!");
            alert.showAndWait();
        }
    }


    public void onBookManageButtonClick(Event event) {
    }

    public void onAddBookButtonClick(ActionEvent actionEvent) {
        String bookName = bookNameTextField.getText().trim();
        String author = authorTextField.getText().trim();
        String publisher = publisherTextField.getText().trim();
        String publishYear = publishedYearTextField.getText().trim();
        String availableBooks = availableBooksInBookManage.getText().trim();
        String totalBooks = totalInBookManage.getText().trim();
        String linkImg = linkAdress.getText().trim();

        if (bookName.isEmpty() || author.isEmpty() || publisher.isEmpty() ||
                publishYear.isEmpty() || availableBooks.isEmpty() || totalBooks.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thiếu thông tin");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập đầy đủ thông tin để thêm sách!");
            alert.showAndWait();
            return;
        }

        try {
            int available = Integer.parseInt(availableBooks);
            int total = Integer.parseInt(totalBooks);
            int year = Integer.parseInt(publishYear);

            if (available > total) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Thông tin không hợp lệ");
                alert.setHeaderText(null);
                alert.setContentText("Số lượng còn lại không được lớn hơn tổng số sách!");
                alert.showAndWait();
                return;
            }

            if (linkImg.isEmpty()) {
                linkAdress.setText("");
            }else{
                if(!imageService.isValidImageUrl(linkImg)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi URL");
                    alert.setHeaderText(null);
                    alert.setContentText("Không tìm thấy URL của ảnh");
                    alert.showAndWait();
                    return;
                }
            }
            Book book = new Book(0, bookName, author, publisher, year, available, total);
            Image img = new Image(bookName, author, linkImg);

            int result = adminService.addBook(book);
            boolean resultImg = imageService.addImg(img);
            RenImage.downImage(linkImg);
            if (!resultImg) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText(null);
                alert.setTitle("Không tạo được ảnh cho sách");
            }

            if (result > 0) {
                refreshBookList();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText(null);
                alert.setContentText("Sách đã được thêm thành công!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Thất bại");
                alert.setHeaderText(null);
                alert.setContentText("Không thể thêm sách! Hãy kiểm tra lại thông tin.");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi định dạng");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập số hợp lệ cho năm xuất bản, tổng số sách và số lượng còn lại.");
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onSelectBook(ActionEvent actionEvent) throws SQLException {
        Book searchBook = bookTableView.getSelectionModel().getSelectedItem();
        if (searchBook == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Chưa chọn sách");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn sách để sửa!");
            alert.showAndWait();
            return;
        }
        bookIDTextField.setText(String.valueOf(searchBook.getId()));
        bookNameTextField.setText(searchBook.getBookName());
        authorTextField.setText(searchBook.getAuthor());
        publisherTextField.setText(searchBook.getPublisher());
        publishedYearTextField.setText(String.valueOf(searchBook.getPublishYear()));
        availableBooksInBookManage.setText(String.valueOf(searchBook.getAvailableBooks()));
        totalInBookManage.setText(String.valueOf(searchBook.getTotalBooks()));
        linkAdress.setText(imageService.getUrlByBookNameAndAuthor(searchBook.getBookName(), searchBook.getAuthor()));

    }


    public void onEditBookButtonClick(ActionEvent actionEvent) {
        Book searchBook = bookTableView.getSelectionModel().getSelectedItem();
        if (searchBook == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Chưa chọn sách");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn sách để sửa!");
            alert.showAndWait();
            return;
        }


        try {
            int id = Integer.parseInt(bookIDTextField.getText().trim());
            String bookString = bookNameTextField.getText().trim();
            String authorString = authorTextField.getText().trim();
            String publisherString = publisherTextField.getText().trim();
            int publishedYear = Integer.parseInt(publishedYearTextField.getText().trim());
            int availableBooksInBookManageInt = Integer.parseInt(availableBooksInBookManage.getText().trim());
            int totalInBookManageInt = Integer.parseInt(totalInBookManage.getText().trim());
            String linkInput = linkAdress.getText().trim();
            String link = imageService.getUrlByBookNameAndAuthor(searchBook.getBookName(), searchBook.getAuthor());

            if (link == null || link.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Lỗi chưa có ảnh sẵn");
                alert.setHeaderText(null);
                alert.setContentText("Chưa có ảnh này trong database.");
                alert.showAndWait();
                link = "";
            }

            if (!imageService.isValidImageUrl(linkInput)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi Link Input");
                alert.setHeaderText(null);
                alert.setContentText("Lỗi không tìm được link ảnh");
                alert.showAndWait();
                return;
            }

            if (!(searchBook.getId() == id)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi đổi id book");
                alert.setHeaderText(null);
                alert.setContentText("ID sách được tạo tự động vui lòng không đổi");
                alert.showAndWait();
                return;
            }

            boolean checkTrung = !(searchBook.getId() == id) ||
                    !searchBook.getBookName().equals(bookString) ||
                    !searchBook.getAuthor().equals(authorString) ||
                    !searchBook.getPublisher().equals(publisherString) ||
                    !(searchBook.getPublishYear() == publishedYear) ||
                    searchBook.getAvailableBooks() != availableBooksInBookManageInt ||
                    searchBook.getTotalBooks() != totalInBookManageInt ||
                    !link.equals(linkInput);

            if (!checkTrung) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Không có thay đổi");
                alert.setHeaderText(null);
                alert.setContentText("Không có thông tin nào được thay đổi.");
                alert.showAndWait();
                return;
            }

            searchBook.setBookName(bookString);
            searchBook.setAuthor(authorString);
            searchBook.setPublisher(publisherString);
            searchBook.setPublishYear(publishedYear);
            searchBook.setAvailableBooks(availableBooksInBookManageInt);
            searchBook.setTotalBooks(totalInBookManageInt);

            boolean checkUpdateLink = false;

            if(link.isEmpty()){
                checkUpdateLink = imageService.addImg(new Image(searchBook.getBookName(),
                        searchBook.getAuthor(),linkInput));
            }else{
                checkUpdateLink = imageService.updateLink(searchBook.getBookName(),
                        searchBook.getAuthor(), linkInput);
            }


            System.out.println(checkUpdateLink);

            boolean success = bookService.updateBook(searchBook) && checkUpdateLink;

            Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Cập nhật thông tin");
            alert.setHeaderText(null);
            alert.setContentText(success ? "Cập nhật thông tin sách thành công!"
                    : "Cập nhật thông tin sách thất bại!");
            alert.showAndWait();

            bookTableView.refresh();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Dữ liệu nhập vào không hợp lệ. Vui lòng kiểm tra lại!");
            alert.showAndWait();
        }
        ;
    }

    public void onDeleteBookButtonClick(ActionEvent actionEvent) throws SQLException {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Xóa sách");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn một sách để xóa!");
            alert.showAndWait();
            return;
        }

        boolean checkXoaAnh = imageService.deleteLink(selectedBook.getBookName(), selectedBook.getAuthor());
        if (!checkXoaAnh) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Xóa hình ảnh thất bại");
            alert.setHeaderText(null);
            alert.setContentText("Không thể xóa hình ảnh liên kết với sách!");
            alert.showAndWait();
            return;
        }

        int rowsDeleted = adminService.deleteBook(selectedBook.getId());
        if (rowsDeleted > 0) {
            refreshBookList();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText("Sách đã được xóa thành công!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thất bại");
            alert.setHeaderText(null);
            alert.setContentText("Không thể xóa sách!");
            alert.showAndWait();
        }

    }


    public void onSearchBookButtonClick(ActionEvent actionEvent) {
        String id = bookIDTextField.getText().trim();
        String bookName = bookNameTextField.getText().trim();
        String author = authorTextField.getText().trim();
        String publisher = publisherTextField.getText().trim();
        String publishedYear = publishedYearTextField.getText().trim();
        String totalBooks = totalInBookManage.getText().trim();
        String availableBooks = availableBooksInBookManage.getText().trim();

        if (id.isEmpty() && bookName.isEmpty() && publishedYear.isEmpty() &&
                availableBooks.isEmpty() && totalBooks.isEmpty() &&
                author.isEmpty() && publisher.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thiếu thông tin");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập ít nhất một từ khóa để tìm kiếm sách!");
            alert.showAndWait();
            refreshBookList();
            return;
        }

        List<Book> books = adminService.searchBook(
                id.isEmpty() ? null : id,
                bookName.isEmpty() ? null : bookName,
                publishedYear.isEmpty() ? null : publishedYear,
                availableBooks.isEmpty() ? null : availableBooks,
                totalBooks.isEmpty() ? null : totalBooks,
                author.isEmpty() ? null : author,
                publisher.isEmpty() ? null : publisher
        );

        if (books.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Kết quả tìm kiếm");
            alert.setHeaderText(null);
            alert.setContentText("Không tìm thấy sách nào với thông tin đã nhập.");
            alert.showAndWait();
            refreshBookList();
        } else {
            bookList.clear();
            bookList.addAll(books);
            bookTableView.setItems(bookList);
            bookTableView.refresh();
        }
    }

    public void onBorrowReturnManageClick(Event event) {
    }

    public void onSearchTransactionButtonClick(ActionEvent actionEvent) {
        String transactionId = transactionIDTextField.getText().trim();
        String userAccount = userAccountTextField.getText().trim();
        String bookId = bookIDInBorrowTextField.getText().trim();
        String borrowDate = borrowDateTextField.getText().trim();
        String endDate = endDateTextField.getText().trim();
        String status = statusTextField.getText().trim();

        if (transactionId.isEmpty() && userAccount.isEmpty() && bookId.isEmpty() &&
                borrowDate.isEmpty() && endDate.isEmpty() && status.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thiếu thông tin");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập ít nhất một từ khóa để tìm kiếm giao dịch!");
            alert.showAndWait();
            return;
        }

        List<Loan> transactions = adminService.searchTransaction(
                transactionId.isEmpty() ? null : transactionId,
                userAccount.isEmpty() ? null : userAccount,
                bookId.isEmpty() ? null : bookId,
                borrowDate.isEmpty() ? null : borrowDate,
                endDate.isEmpty() ? null : endDate,
                status.isEmpty() ? null : status
        );

        if (transactions.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Kết quả tìm kiếm");
            alert.setHeaderText(null);
            alert.setContentText("Không tìm thấy giao dịch nào với thông tin đã nhập.");
            alert.showAndWait();
        } else {
            loanList.clear();
            loanList.addAll(transactions);
            loanTableView.setItems(loanList);
            loanTableView.refresh();
        }
    }

    public void onSelectTrans(ActionEvent actionEvent) {
        Loan searchLoan = loanTableView.getSelectionModel().getSelectedItem();
        if (searchLoan == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Chưa chọn giao dịch");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn giao dịch để sửa!");
            alert.showAndWait();
            return;
        }

        transactionIDTextField.setText(String.valueOf(searchLoan.getBookId()));
        userAccountTextField.setText(searchLoan.getUserAccount());
        bookIDInBorrowTextField.setText(String.valueOf(searchLoan.getBookId()));
        borrowDateTextField.setText(String.valueOf(searchLoan.getBorrowDate()));
        endDateTextField.setText(String.valueOf(searchLoan.getEndDate()));
        statusTextField.setText(String.valueOf(searchLoan.getStatus()));

    }

    public void onEditTransactionButtonClick(ActionEvent actionEvent) {
        Loan searchLoan = loanTableView.getSelectionModel().getSelectedItem();
        if (searchLoan == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Chưa chọn giao dịch");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn giao dịch để sửa!");
            alert.showAndWait();
            return;
        }
        try {
            int transactionId = Integer.parseInt(transactionIDTextField.getText().trim());
            String userAccount = userAccountTextField.getText().trim();
            int bookId = Integer.parseInt(bookIDInBorrowTextField.getText().trim());
            LocalDate borrowDate = LocalDate.parse(borrowDateTextField.getText().trim());
            LocalDate endDate = LocalDate.parse(endDateTextField.getText().trim());
            String status = statusTextField.getText().trim();

            boolean checkTrung = searchLoan.getTransactionId() != transactionId ||
                    !Objects.equals(searchLoan.getUserAccount(), userAccount) ||
                    searchLoan.getBookId() != bookId ||
                    !Objects.equals(searchLoan.getBorrowDate(), borrowDate) ||
                    !Objects.equals(searchLoan.getEndDate(), endDate) ||
                    !Objects.equals(searchLoan.getStatus(), status);
            ;

            if (!checkTrung) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Không có thay đổi");
                alert.setHeaderText(null);
                alert.setContentText("Không có thông tin nào được thay đổi.");
                alert.showAndWait();
                return;
            }

            searchLoan.setTransactionId(transactionId);
            searchLoan.setUserAccount(userAccount);
            searchLoan.setBookId(bookId);
            searchLoan.setBorrowDate(borrowDate);
            searchLoan.setEndDate(endDate);

            boolean success = loanService.updateTransaction(searchLoan);

            Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Cập nhật thông tin");
            alert.setHeaderText(null);
            alert.setContentText(success ? "Cập nhật thông tin gaio dịch thành công!"
                    : "Cập nhật thông tin giao dịch thất bại!");
            alert.showAndWait();

            loanTableView.refresh();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Dữ liệu nhập vào không hợp lệ. Vui lòng kiểm tra lại!");
            alert.showAndWait();
        }
        ;
    }

    public void onAddTransactionButtonClick(ActionEvent actionEvent) {
        String userAccount = userAccountTextField.getText().trim();
        String bookId = bookIDInBorrowTextField.getText().trim();
        String borrowDate = borrowDateTextField.getText().trim();
        String endDate = endDateTextField.getText().trim();
        String status = statusTextField.getText().trim();

        if (userAccount.isEmpty() || bookId.isEmpty() || borrowDate.isEmpty() ||
                endDate.isEmpty() || status.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thiếu thông tin");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin để thêm giao dịch!");
            alert.showAndWait();
            return;
        }

        try {
            Loan newTransaction = new Loan(
                    0,
                    userAccount,
                    Integer.parseInt(bookId),
                    LocalDate.parse(borrowDate),
                    LocalDate.parse(endDate),
                    null,
                    Loan.LoanStatus.valueOf(status.toUpperCase())
            );

            int generatedId = adminService.addTransaction(newTransaction);
            if (generatedId > 0) {
                newTransaction.setTransactionId(generatedId);
                loanList.add(newTransaction);
                loanTableView.setItems(loanList);
                loanTableView.refresh();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText(null);
                alert.setContentText("Giao dịch đã được thêm thành công!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Thất bại");
                alert.setHeaderText(null);
                alert.setContentText("Không thể thêm giao dịch vào cơ sở dữ liệu!");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi dữ liệu");
            alert.setHeaderText(null);
            alert.setContentText("ID sách và các trường ngày phải là dữ liệu hợp lệ!");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi hệ thống");
            alert.setHeaderText(null);
            alert.setContentText("Đã xảy ra lỗi: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void onExitButton(ActionEvent actionEvent) {
        thoat();
    }

    public void onDeleteTransactionButtonCLick(ActionEvent actionEvent) {
        Loan selectedLoan = loanTableView.getSelectionModel().getSelectedItem();

        if (selectedLoan == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thiếu lựa chọn");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn giao dịch để xóa!");
            alert.showAndWait();
            return;
        }

        boolean success = adminService.deleteTransaction(selectedLoan.getTransactionId());
        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText("Giao dịch đã được xóa thành công!");
            alert.showAndWait();
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

}
