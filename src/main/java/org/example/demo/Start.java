package org.example.demo;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class Start extends GiaoDienChung {


    public Button startButton;
    public Button exitButton;

    public void onStartClick(ActionEvent actionEvent) {
        super.chuyenCanh(startButton,"signUp.fxml");
    }

    public void onExitButton(ActionEvent actionEvent) {
        thoat();
    }
}