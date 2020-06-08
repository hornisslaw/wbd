package sample.UserUi;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.Bilety;
import sample.Login;
import sample.Osobniki;

public class MenuController {

    @FXML
    private Button ButMenuZwierzeta;
    //Backto Animals
    @FXML
    public void GoToOsobniki(ActionEvent actionEvent) throws Exception {

        Stage stage = (Stage) ButMenuZwierzeta.getScene().getWindow();
        stage.close();
        UserOsobniki menu = new UserOsobniki();
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
        UserBilety menu = new UserBilety();
        Stage stag = new Stage();

        menu.start(stag);

    }
    @FXML
    private Button butPowrot;

    public void GoToPowrot(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) butPowrot.getScene().getWindow();
        stage.close();
        Login menu = new Login();
        Stage stag = new Stage();

        menu.start(stag);

    }
    public void exitToDesktop(ActionEvent actionEvent) {
        Platform.exit();
    }
    @FXML
    private Button butAtrakcja;
    public void GoToAtrakcje(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) butAtrakcja.getScene().getWindow();
        stage.close();
        UserAtrakcje menu = new UserAtrakcje();
        Stage stag = new Stage();

        menu.start(stag);
    }
}
