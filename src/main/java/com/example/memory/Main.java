package com.example.memory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

//Application è una classe astratta fornita da JavaFX che serve come punto di partenza per creare applicazioni
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Carica il file tramite FXMLLoader utilizzando il layout definito nel file per creare una nuova scena
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("memory-game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Memory");
        stage.getIcons().add(new Image("com/example/memory/images/icons8-asso-di-fiori-64.png"));
        stage.setScene(scene);
        stage.show();
    }

    //Lancia l'applicazione Java
    public static void main(String[] args) {
        launch();
    }
}