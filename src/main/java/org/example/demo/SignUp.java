package org.example.demo;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUp extends giaoDienChung implements Initializable {

    public Button onCreateAccButton;
    public TextField onUserNameButton;
    public TextField onPasswordButton;
    public Button onSignInButton;

    public void onSignInClick(ActionEvent actionEvent) {

        String userAccount = onUserNameButton.getText();
        String password = onPasswordButton.getText();

        String mySQL = "Select * from users where userAccount = ? and userPassword = ?";
        connection = database.connectDB();

        try {
            preparedStatement = connection.prepareStatement(mySQL);
            preparedStatement.setString(1, userAccount);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            Alert alert;
            if (userAccount.isEmpty() || password.isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Notification");
                alert.setHeaderText(null);
                alert.setContentText("Please enter your username and password");
                alert.showAndWait();
            } else {
                if (resultSet.next()) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Notification");
                    alert.setHeaderText(null);
                    alert.setContentText("Success");
                    alert.showAndWait();
                    String role = resultSet.getString("roles");
                    if (role.equals("Admin")) {
                        super.chuyenCanh(onSignInButton,"Admin.fxml");
                    } else {
                        super.chuyenCanh(onSignInButton,"User.fxml");
                    }
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Notification");
                    alert.setHeaderText(null);
                    alert.setContentText("Username or password is wrong");
                    alert.showAndWait();
                }
            }
            resultSet = preparedStatement.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onCreateAccClick(ActionEvent actionEvent) {
        chuyenCanh(onCreateAccButton,"createAccount.fxml");
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
