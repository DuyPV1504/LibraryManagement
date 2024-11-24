module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires java.sql;
    requires mysql.connector.j;
    requires javafx.media;


    opens org.example.demo to javafx.fxml;
    opens models to javafx.base;
    exports org.example.demo;
    exports models;
    exports org.example.demo.controller;
    opens org.example.demo.controller to javafx.fxml;
}