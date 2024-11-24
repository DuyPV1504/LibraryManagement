package org.example.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccount extends GiaoDienChung implements Initializable {
    public TextField firstNameText;
    public TextField lastNameText;
    public TextField onDayDateText;
    public TextField monthDateText;
    public TextField yearDateText;
    public Button createButton;
    public TextField onGenderText;
    public TextField accountCreateUserNameText;
    public TextField emailCreateText;
    public PasswordField userCreatePasswordText;
    public TextField onUserName;
    public PasswordField userCreatePasswordTextPart;
    public Button backToLogginButton;
    public Button exitButton;

    /**
     * check xem email co hop ly khong.
     *
     * @return sai/ dung
     */
    public boolean checkEmailHopLy() {
        System.out.println(emailCreateText.getText());
        Pattern p = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)" +
                "*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
        Matcher M = p.matcher(emailCreateText.getText());
        if (M.find() && M.group().equals(emailCreateText.getText())) {
            System.out.println("Valid email");
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid email");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid email address");
            emailCreateText.clear();
            alert.showAndWait();
            return false;
        }
    }

    public void onCreateButton() {

        String queryCreateAccount = "INSERT INTO Users ( surname, lastname, dateOfBirth, "
                + "gender, email, userName, userAccount, userPassword, roles,\n"
                + "warning) VALUES (?,?,?,?,?,?,?,?,?,?)";

        connection = database.connectDB();

        try {
            assert connection != null;/*khang dinh connection khong
            bi null neu la null thi tra ket qua AssertionError*/
            Alert alert;
            PreparedStatement ps = connection.prepareStatement(queryCreateAccount);
            ps.setString(1, firstNameText.getText());
            ps.setString(2, lastNameText.getText());
            ps.setString(3, yearDateText.getText()
                    + '-' + monthDateText.getText() + '-' + onDayDateText.getText());
            ps.setString(4, onGenderText.getText());
            ps.setString(5, emailCreateText.getText());
            ps.setString(6, onUserName.getText());
            ps.setString(7, accountCreateUserNameText.getText());
            ps.setString(8, userCreatePasswordText.getText());
            ps.setString(9, "User");
            ps.setInt(10, 0);


            if (checkAllFieldIsFill()) {
                if (checkValidPassword() && checkEmailHopLy() && checkTrungAccountVaEmail()
                        && checkValidGender() && checkDateValid() && checkTrungPassword() && checkTrungUserName()) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Account created successfully");
                    alert.showAndWait();
                    ps.executeUpdate();
                    chuyenCanh(createButton, "signUp.fxml");
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Try again");
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkTrungUserName() throws SQLException {
        connection = database.connectDB();
        String check = "SELECT Count(*) FROM Users WHERE userName = ?";
        assert connection != null;
        PreparedStatement pre = connection.prepareStatement(check);
        ResultSet rs = null;
        try {
            assert connection != null;
            pre.setString(1, onUserName.getText());

            rs = pre.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("User Name that already exists");
                alert.showAndWait();
                onUserName.clear();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    public boolean checkTrungAccountVaEmail() throws SQLException {
        connection = database.connectDB();
        String check = "SELECT Count(*) FROM Users WHERE userAccount = ? or email = ?";

        assert connection != null;
        PreparedStatement pre = connection.prepareStatement(check);
        ResultSet rs = null;
        try {
            assert connection != null;
            pre.setString(1, accountCreateUserNameText.getText());
            pre.setString(2, emailCreateText.getText());

            rs = pre.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Email or account that already exists");
                alert.showAndWait();
                accountCreateUserNameText.clear();
                emailCreateText.clear();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean checkDateValid() {
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate date = LocalDate.parse(yearDateText.getText() + '/' + monthDateText.getText() + '/'
                    + onDayDateText.getText(), dateFormatter);
            return true;
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid date");
            alert.showAndWait();
            onDayDateText.clear();
            monthDateText.clear();
            yearDateText.clear();
            return false;
        }

    }

    public boolean checkAllFieldIsFill() {
        return !(firstNameText.getText().isEmpty() || lastNameText.getText().isEmpty() || monthDateText.getText().isEmpty()
                || onDayDateText.getText().isEmpty() || onGenderText.getText().isEmpty()
                || emailCreateText.getText().isEmpty() || onUserName.getText().isEmpty()
                || accountCreateUserNameText.getText().isEmpty()
                || userCreatePasswordText.getText().isEmpty());
    }

    public boolean checkValidPassword() {
        if (userCreatePasswordText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid password");
            alert.showAndWait();
            userCreatePasswordText.clear();
            return false;
        }
        System.out.println(userCreatePasswordText.getText());
        return true;
    }

    public boolean checkValidGender() {
        if (onGenderText.getText().equals("Male") || onGenderText.getText().equals("Female")) {
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid gender");
        alert.showAndWait();
        onGenderText.clear();
        return false;
    }


    boolean checkTrungPassword() {
        if (userCreatePasswordText.getText().equals(userCreatePasswordTextPart.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Passwords do not match");
            alert.showAndWait();
            userCreatePasswordText.clear();
            userCreatePasswordTextPart.clear();
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void onBackToLogginButton(ActionEvent actionEvent) {
        firstNameText.clear();
        lastNameText.clear();
        monthDateText.clear();
        onDayDateText.clear();
        onGenderText.clear();
        emailCreateText.clear();
        onUserName.clear();
        accountCreateUserNameText.clear();
        userCreatePasswordText.clear();
        userCreatePasswordTextPart.clear();
        chuyenCanh(backToLogginButton, "signUp.fxml");
    }

    public void onExitButton(ActionEvent actionEvent) {
        thoat();
    }
}
