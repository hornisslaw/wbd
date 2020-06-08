package sample.UserUi;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuUser {

    public void start(Stage primaryStage) throws Exception {


        Stage window = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("../UserUi/Menu.fxml"));
        window.setTitle("Oceanarium");
        window.setScene(new Scene(root, 600, 500));
        window.show();

    }
}
