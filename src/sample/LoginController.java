package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.UserUi.MenuUser;

public class LoginController {
    @FXML
    private Button ButClientContinue;
    //Backto Animals
    public void GoToClientMenu(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) ButClientContinue.getScene().getWindow();
        stage.close();
        MenuUser menu = new MenuUser();
        Stage stag = new Stage();
        menu.start(stag);
    }
    @FXML
    private Button ZalogPrac;

    public void GoToPracMenu(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) ZalogPrac.getScene().getWindow();
        stage.close();
        MenuOptions menu = new MenuOptions();
        Stage stag = new Stage();
        menu.start(stag);

    }
}
