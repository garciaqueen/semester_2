package org.example.game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GameCanvas gc = new GameCanvas(800, 800);
        Group root = new Group(gc);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Breakout game");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
