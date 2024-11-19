package org.example.demo;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class Start extends giaoDienChung {


    public Button startButton;

    public void onStartClick(ActionEvent actionEvent) {
        super.chuyenCanh(startButton,"signUp.fxml");
    }
}