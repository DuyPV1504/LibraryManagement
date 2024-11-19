package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public abstract class giaoDienChung {
    public Connection connection;
    public PreparedStatement preparedStatement;
    public ResultSet resultSet;

    /**
     * chuyen canh.
     * @param button nut click de chuyen
     * @param fileName ten file fxml
     */

    public void chuyenCanh(Button button,String fileName) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
            Parent root = loader.load();
            Stage stage = (Stage) button.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
