package org.example.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;


public abstract class GiaoDienChung {
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

    public static void chuyenCanhStatic(Button button, String fileName) {
        try{
            FXMLLoader loader = new FXMLLoader(GiaoDienChung.class.getResource(fileName));
            Parent root = loader.load();
            Stage stage = (Stage) button.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void thoat(){
        thoatStatic();
    }

    public static void thoatStatic(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận thoát");
        alert.setHeaderText("Bạn có chắc chắn muốn thoát?");
        alert.setContentText("Nhấn OK để thoát, hoặc Cancel để hủy.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
