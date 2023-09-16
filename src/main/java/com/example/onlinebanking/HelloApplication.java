package com.example.onlinebanking;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application{

    Button button;

    @Override
    public void start(Stage stage){
        stage.setTitle("Online banking");
        button = new Button();
        button.setText("Settings");
        button.setOnAction(e -> SettingsBox.display(stage));

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}