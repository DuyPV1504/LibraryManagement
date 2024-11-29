package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserInforController extends GiaoDienChung implements Initializable {
    public Button changeNameButton;
    public Button changeDateButton;
    public Button changeGenderButton;
    public Button changeEmailButton;
    public Button changePasswordButton;
    public Button changeAccountNameButton;
    public TextField surName;
    public TextField day;
    public TextField gender;
    public TextField accountName;
    public TextField email;
    public TextField userPassword;
    public TextField month;
    public TextField year;
    public TextField lastName;
    public Button exitButton;
    public Button backTo;
    public TextField tenDangNhap;

    public String dayString = null;
    public String monthString = null;
    public String yearString = null;

    /**
     * thay ten.
     *
     * @param actionEvent click
     * @throws SQLException sql
     */
    public void onChangeNameButtonClick(ActionEvent actionEvent) throws SQLException {
        if (!surName.getText().equals(LogIn.surName) || !lastName.getText().equals(LogIn.lastName)) {
            String querySurName = "UPDATE Users SET surname = ? WHERE userAccount = ?;";
            String queryLastName = "UPDATE Users SET lastName = ? WHERE userAccount = ?";
            connection = database.connectDB();
            assert connection != null;
            preparedStatement = connection.prepareStatement(querySurName);
            preparedStatement.setString(1, surName.getText());
            preparedStatement.setString(2, LogIn.account);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(queryLastName);
            preparedStatement.setString(1, lastName.getText());
            preparedStatement.setString(2, LogIn.account);
            preparedStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Successful");
            alert.setHeaderText(null);
            alert.setContentText("Your name has been successfully updated.");
            alert.showAndWait();

            LogIn.surName = surName.getText();
            LogIn.lastName = lastName.getText();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Nothing to update");
            alert.showAndWait();
        }
    }

    /**
     * thay sn.
     *
     * @param actionEvent click
     * @throws SQLException
     */
    public void onChangeDateButtonClick(ActionEvent actionEvent) throws SQLException {
        if (!dayString.equals(day.getText()) || !monthString.equals(month.getText())
                || !yearString.equals(year.getText())) {

            String queryDay = "UPDATE Users SET dateOfBirth = ? WHERE userAccount = ?;";
            connection = database.connectDB();
            assert connection != null;

            preparedStatement = connection.prepareStatement(queryDay);
            preparedStatement.setString(1, year.getText() + '-'
                    + month.getText() + '-' + day.getText());
            preparedStatement.setString(2, LogIn.account);
            preparedStatement.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Successful");
            alert.setHeaderText(null);
            alert.setContentText("Your birth day has been successfully updated.");
            alert.showAndWait();

            dayString = day.getText();
            monthString = month.getText();
            yearString = year.getText();
        }

    }

    /**
     * thay gioi tinh.
     *
     * @param actionEvent click
     * @throws SQLException sql
     */
    public void onChangeGenderButtonClick(ActionEvent actionEvent) throws SQLException {
        if (!gender.getText().equals(LogIn.genderString)) {
            String queryGender = "UPDATE Users SET gender = ? WHERE userAccount = ?;";
            connection = database.connectDB();
            assert connection != null;
            preparedStatement = connection.prepareStatement(queryGender);
            preparedStatement.setString(1, gender.getText());
            preparedStatement.setString(2, LogIn.account);
            preparedStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Successful");
            alert.setHeaderText(null);
            alert.setContentText("Your gender has been successfully updated.");
            alert.showAndWait();

            LogIn.genderString = gender.getText();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Nothing to update");
            alert.showAndWait();
        }
    }

    /**
     * doi mail.
     *
     * @param actionEvent click
     * @throws SQLException sql
     */
    public void onChangeEmailButtonClick(ActionEvent actionEvent) throws SQLException {
        if (!email.getText().equals(LogIn.emailString)) {
            String queryEmail = "UPDATE Users SET email = ? WHERE userAccount = ?;";
            connection = database.connectDB();
            assert connection != null;
            preparedStatement = connection.prepareStatement(queryEmail);
            preparedStatement.setString(1, email.getText());
            preparedStatement.setString(2, LogIn.account);
            preparedStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Successful");
            alert.setHeaderText(null);
            alert.setContentText("Your email has been successfully updated.");
            alert.showAndWait();

            LogIn.emailString = email.getText();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Nothing to update");
            alert.showAndWait();
        }
    }

    /**
     * doi pass.
     *
     * @param actionEvent click
     * @throws SQLException sql
     */
    public void onChangePasswordButtonClick(ActionEvent actionEvent) throws SQLException {
        if (!userPassword.getText().equals(LogIn.passWordString)) {
            String queryPassword = "UPDATE Users SET userPassword = ? WHERE userAccount = ?;";
            connection = database.connectDB();
            assert connection != null;
            preparedStatement = connection.prepareStatement(queryPassword);
            preparedStatement.setString(1, userPassword.getText());
            preparedStatement.setString(2, LogIn.account);
            preparedStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Successful");
            alert.setHeaderText(null);
            alert.setContentText("Your password has been successfully updated.");
            alert.showAndWait();

            LogIn.passWordString = userPassword.getText();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Nothing to update");
            alert.showAndWait();

        }
    }

    /**
     * doi ten acc.
     *
     * @param actionEvent click
     * @throws SQLException sql
     */
    public void onChangeAccountNameButton(ActionEvent actionEvent) throws SQLException {
        if (!accountName.getText().equals(LogIn.nameString)) {
            String queryAccountName = "UPDATE Users SET userName = ? WHERE userAccount = ?;";
            connection = database.connectDB();
            assert connection != null;
            preparedStatement = connection.prepareStatement(queryAccountName);
            preparedStatement.setString(1, accountName.getText());
            preparedStatement.setString(2, LogIn.account);
            preparedStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Successful");
            alert.setHeaderText(null);
            alert.setContentText("Your name account has been successfully updated.");
            alert.showAndWait();

            LogIn.nameString = accountName.getText();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Nothing to update");
            alert.showAndWait();
        }
    }

    /**
     * ham thoat.
     *
     * @param actionEvent click
     */
    public void onExitButton(ActionEvent actionEvent) {
        thoat();
    }

    /**
     * quay lai.
     *
     * @param actionEvent click
     * @throws SQLException sql
     */
    public void onBackToButton(ActionEvent actionEvent) throws SQLException {
        String query = "SELECT * FROM Users WHERE userAccount = ?";
        String roles = null;
        connection = database.connectDB();
        assert connection != null;
        preparedStatement = connection.prepareStatement(query);
        try {
            preparedStatement.setString(1, LogIn.account);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                roles = resultSet.getString("roles");
                if (roles.equals("Admin")) {
                    chuyenCanh(backTo, "Admin.fxml");
                } else {
                    chuyenCanh(backTo, "User.fxml");
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Not found roles");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String query = "SELECT surName, lastName, day(dateOfBirth) as day1 , month(dateOfBirth) as month1, "
                + "EXTRACT(year FROM dateOfBirth) as year1, gender, userAccount, email, "
                + "userPassword,userName FROM Users WHERE userAccount = ?";
        connection = database.connectDB();

        try {
            assert connection != null;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, LogIn.account);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                surName.setText(resultSet.getString("surName"));
                lastName.setText(resultSet.getString("lastName"));
                day.setText(resultSet.getString("day1"));
                month.setText(resultSet.getString("month1"));
                year.setText(resultSet.getString("year1"));
                gender.setText(resultSet.getString("gender"));
                accountName.setText(resultSet.getString("userName"));
                email.setText(resultSet.getString("email"));
                userPassword.setText(resultSet.getString("userPassword"));
                tenDangNhap.setText(resultSet.getString("userAccount"));

                dayString = resultSet.getString("day1");
                monthString = resultSet.getString("month1");
                yearString = resultSet.getString("year1");

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Not found account");
                alert.setHeaderText(null);
                alert.showAndWait();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
