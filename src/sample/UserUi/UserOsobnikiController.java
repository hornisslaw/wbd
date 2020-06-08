package sample.UserUi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DBConnection;
import sample.MenuOptions;
import sample.Osobniki;

import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserOsobnikiController implements Initializable {
    private Connection conn;
    private ObservableList<Osobniki> listOsobniki = FXCollections.observableArrayList();

    @FXML
    private TableView<Osobniki> tableOsobniki;
    @FXML
    private TableColumn<Osobniki,String> tableColumnIDOsobnik;
    @FXML
    private TableColumn<Osobniki,String> tableColumnImieOsobnik;
    @FXML
    private TableColumn<Osobniki,String> tableColumnOpisOsobnik;
    @FXML
    private TableColumn<Osobniki,String> tableColumnStanZdrowiaOsobnik;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = DBConnection.getConnection();
        listOsobniki = new Osobniki().getAll(conn);
        //setup table viwe
        tableColumnIDOsobnik.setCellValueFactory(new PropertyValueFactory<>("idOsobnik"));
        tableColumnImieOsobnik.setCellValueFactory(new PropertyValueFactory<>("imie"));
        tableColumnOpisOsobnik.setCellValueFactory(new PropertyValueFactory<>("opis"));
        tableColumnStanZdrowiaOsobnik.setCellValueFactory(new PropertyValueFactory<>("stanZdrowia"));

        tableOsobniki.setItems(listOsobniki);
    }
    private void setTableViewOsobniki(ObservableList<Osobniki> listOsobniki)
    {
        tableColumnIDOsobnik.setCellValueFactory(new PropertyValueFactory<>("idOsobnik"));
        tableColumnImieOsobnik.setCellValueFactory(new PropertyValueFactory<>("imie"));
        tableColumnOpisOsobnik.setCellValueFactory(new PropertyValueFactory<>("opis"));
        tableColumnStanZdrowiaOsobnik.setCellValueFactory(new PropertyValueFactory<>("stanZdrowia"));

        tableOsobniki.setItems(listOsobniki);
    }
    //filtrowanie
    @FXML
    private TextField SearchOsobnikImieText;
    @FXML
    public void SearchOsobnikiOnAction(javafx.event.ActionEvent actionEvent) {
        conn = DBConnection.getConnection();
        String osobnikName;
        osobnikName = SearchOsobnikImieText.getText().trim();
        listOsobniki = new Osobniki().getRestrictedList(conn,osobnikName);
        setTableViewOsobniki(listOsobniki);
    }

@FXML
private Button BackToAnimal;

    public void BackToMenu(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) BackToAnimal.getScene().getWindow();
        stage.close();
        MenuUser menu = new MenuUser();
        Stage stag = new Stage();

        menu.start(stag);

    }
}
