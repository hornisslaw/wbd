package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.text.TableView;
import java.io.IOException;
import java.sql.Connection;

public class Main extends Application {
    Stage window;
    private Stage primaryStage;
    Scene Login,Ticets,sample,Animals,Atractions;
    @Override
    public void start(Stage primaryStage) throws Exception{
        window=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        window.setTitle("Oceanarium");

        window.setScene(new Scene(root, 600, 500));
        window.show();

    }




    public static void main(String[] args) {
        launch(args);
    }
}
