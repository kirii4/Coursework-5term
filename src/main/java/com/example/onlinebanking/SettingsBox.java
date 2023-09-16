package com.example.onlinebanking;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SettingsBox {

    public static void display(Stage primaryStage) {
        Stage settingsStage = new Stage();
        settingsStage.initOwner(primaryStage);
        settingsStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.setOnCloseRequest(event -> {
            // Закрываем дочернее окно при закрытии родительского окна
            settingsStage.close();
        });

        BorderPane settingsRoot = new BorderPane();
        settingsRoot.setPadding(new Insets(10));
        BorderPane.setAlignment(settingsRoot, Pos.CENTER);

        // Создаем рамку вокруг окна
        BorderStroke borderStroke = new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, null, new BorderWidths(2));
        Border border = new Border(borderStroke);
        settingsRoot.setBorder(border);

        // Устанавливаем размер окна 60% от размера родительского окна
        // Создаем DoubleProperty для отслеживания ширины и высоты родительского окна
        DoubleProperty parentWidth = new SimpleDoubleProperty(primaryStage.getWidth());
        DoubleProperty parentHeight = new SimpleDoubleProperty(primaryStage.getHeight());

    // Создаем DoubleProperty для отслеживания ширины и высоты дочернего окна
        DoubleProperty settingsWidth = new SimpleDoubleProperty(parentWidth.get() * 0.6);
        DoubleProperty settingsHeight = new SimpleDoubleProperty(parentHeight.get() * 0.6);

    // Настройка начального размера дочернего окна
        settingsStage.setWidth(settingsWidth.get());
        settingsStage.setHeight(settingsHeight.get());

    // Вычисляем положение окна относительно центра родительского окна
        double _parentX = primaryStage.getX();
        double _parentY = primaryStage.getY();
        double _centerX = _parentX + (parentWidth.get() - settingsWidth.get()) / 2;
        double _centerY = _parentY + (parentHeight.get() - settingsHeight.get()) / 2;
        settingsStage.setX(_centerX);
        settingsStage.setY(_centerY);

    // Слушатели для изменения размеров родительского окна
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            parentWidth.set(newVal.doubleValue());
            settingsWidth.set(parentWidth.get() * 0.6);
            double parentX = primaryStage.getX();
            double centerX = parentX + (parentWidth.get() - settingsWidth.get()) / 2;
            settingsStage.setX(centerX);
            settingsStage.setWidth(settingsWidth.get());
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            parentHeight.set(newVal.doubleValue());
            settingsHeight.set(parentHeight.get() * 0.6);
            double parentY = primaryStage.getY();
            double centerY = parentY + (parentHeight.get() - settingsHeight.get()) / 2;
            settingsStage.setY(centerY);
            settingsStage.setHeight(settingsHeight.get());
        });


        // Создаем VBox для организации содержимого окна
        VBox contentBox = new VBox(10); // 10 - расстояние между элементами
        contentBox.setAlignment(Pos.CENTER);

        // Добавляем имя и фамилию пользователя и его аватар
        HBox userBox = new HBox(10); // 10 - расстояние между элементами
        userBox.setAlignment(Pos.CENTER);

        // Создаем и добавляем аватар пользователя
        String workingDir = System.getProperty("user.dir");
        String relativePath = "src/main/resources/path/Images/monkey.jpg";
        String absolutePath = workingDir + "/" + relativePath;
        Image avatarImage = new Image(absolutePath); // Укажите путь к изображению аватара
        ImageView avatarImageView = new ImageView(avatarImage);
        avatarImageView.setFitWidth(64); // Ширина аватара
        avatarImageView.setFitHeight(64); // Высота аватара

        // Создаем и добавляем имя и фамилию пользователя
        Label userNameLabel = new Label("Имя Фамилия"); // Замените на реальное имя и фамилию пользователя
        userNameLabel.setStyle("-fx-font-weight: bold;");

        userBox.getChildren().addAll(avatarImageView, userNameLabel);
        contentBox.getChildren().add(userBox);

        // Добавляем кнопки "Мой аккаунт", "Расширенные настройки" и "Безопасность"
        Button myAccountButton = new Button("Мой аккаунт");
        Button advancedSettingsButton = new Button("Расширенные настройки");
        Button securityButton = new Button("Безопасность");

        contentBox.getChildren().addAll(myAccountButton, advancedSettingsButton, securityButton);

        // Создаем кнопку "X" справа
        Button closeButton = new Button("X");
        closeButton.setOnAction(e -> settingsStage.close());
        settingsRoot.setRight(closeButton);

        settingsRoot.setCenter(contentBox);

        Scene settingsScene = new Scene(settingsRoot);
        settingsStage.setScene(settingsScene);
        settingsStage.showAndWait();
    }
}
