package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.lang.management.PlatformLoggingMXBean;

public class MenuOptionsController {
    @FXML
    private Button ButMenuZwierzeta;
    //Backto Animals
    @FXML
    public void GoToOsobniki(ActionEvent actionEvent) throws Exception {

        Stage stage = (Stage) ButMenuZwierzeta.getScene().getWindow();
        stage.close();
        Osobniki menu = new Osobniki();
        Stage stag = new Stage();

        menu.start(stag);

    }
    @FXML
    private Button ButMenuBilety;
    //Backto Animals
    @FXML
    public void GoToBilety(ActionEvent actionEvent) throws Exception {

        Stage stage = (Stage) ButMenuBilety.getScene().getWindow();
        stage.close();
        Bilety menu = new Bilety();
        Stage stag = new Stage();

        menu.start(stag);

    }


    public void exitToDesktop(ActionEvent actionEvent) {
        Platform.exit();
    }

    @FXML
    private Button butPowrot;

    public void GoToAtrakcje(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) butPowrot.getScene().getWindow();
        stage.close();
        Atrakcje menu = new Atrakcje();
        Stage stag = new Stage();

        menu.start(stag);
    }

    public void GoToPowrot(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) butPowrot.getScene().getWindow();
        stage.close();
        Login menu = new Login();
        Stage stag = new Stage();

        menu.start(stag);

    }
    //Backto Animals
   
}
