package org.example.demo;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class Start extends GiaoDienChung {


    public Button startButton;
    public Button exitButton;

    /**
     * nut bat dau.
     *
     * @param actionEvent click
     */
    public void onStartClick(ActionEvent actionEvent) {
        super.chuyenCanh(startButton, "logIn.fxml");
    }

    /**
     * thoat.
     *
     * @param actionEvent click
     */
    public void onExitButton(ActionEvent actionEvent) {
        thoat();
    }
}