package com.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/javafx/Vue/Revue/menuGeneralRevue.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 450);
        stage.setTitle("Gestion de revues");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}