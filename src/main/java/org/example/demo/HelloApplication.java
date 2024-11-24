package org.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    public static boolean music = false;
    public static MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("start.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        String musicFile = "src/main/resources/music/saoTruc.mp3";
        Media media = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(music);
    }

    public static boolean isMusic() {
        return music;
    }

    public static void setMusic(boolean music) {
        HelloApplication.music = music;
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static void setMediaPlayer(MediaPlayer mediaPlayer) {
        HelloApplication.mediaPlayer = mediaPlayer;
    }

    public static void main(String[] args) {
        launch();
    }
}
