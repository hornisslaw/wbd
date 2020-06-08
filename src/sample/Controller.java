package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {


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
    //dodawanie

    @FXML
    private TextField ImieInsertOsobnik;

    @FXML
    private TextField OpisInsertOsobnik;

    @FXML
    private TextField StanZdrowiaInsertOsobnik;


    @FXML
    private TextField IDinsertOsobnik;

    @FXML
    public void InserOsobnikOnAction(javafx.event.ActionEvent actionEvent) {
        String osobnikImie;
        String osobnikStanZdrowia;
        String osobnikOpis;
        Integer osobnikID;
        Integer result;
        osobnikImie= ImieInsertOsobnik.getText().trim();
        if(osobnikImie.length()==0 || osobnikImie.length()>30){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty or to long name");
            alert.showAndWait();
            return;
        }
        osobnikImie= ImieInsertOsobnik.getText().trim();
        if(osobnikImie.length()==0 || osobnikImie.length()>30){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty or to long name");
            alert.showAndWait();
            return;
        }
        osobnikOpis = OpisInsertOsobnik.getText().trim();
        if(osobnikOpis.length()==0 || osobnikOpis.length()>250){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty or to long description");
            alert.showAndWait();
            return;
        }
        osobnikStanZdrowia = StanZdrowiaInsertOsobnik.getText().trim();
        if(!osobnikStanZdrowia.equals("ZDROWY")&&!osobnikStanZdrowia.equals("CHORY")&&!osobnikStanZdrowia.equals("DO SPRAWDZENIA")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Osobnik moze byc tylko ZDROWY,CHORY lub DO SPRAWDZENIA");
            alert.showAndWait();
            return;
        }
        osobnikID = Integer.parseInt(IDinsertOsobnik.getText().trim());

        conn =DBConnection.getConnection();
        result = new Osobniki().insertOsobnik(conn,osobnikImie,osobnikID,osobnikStanZdrowia,osobnikOpis);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Udalo sie dodac rekord");
        alert.showAndWait();
    }
    //delete

    @FXML
    public void buttonDeleteOsobnik(javafx.event.ActionEvent actionEvent) {
        Integer rowIndex = tableOsobniki.getSelectionModel().getSelectedIndex();
        if(rowIndex<0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nie zaznaczono ani jedengo rekordu do usuniecia");
            alert.showAndWait();
            return;
        }
        Integer osobnikID= tableOsobniki.getSelectionModel().getSelectedItem().getIdOsobnik();
        conn = DBConnection.getConnection();
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> res1=alert1.showAndWait();
        if(res1.get()==ButtonType.OK){
            Integer result = new Osobniki().removeOsobnik(conn,osobnikID);
            if(result>0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Record removed");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private Button BackToAnimal;
    //Backto Animals
    @FXML
    public void BackToAnimalFromOsosbniki(ActionEvent actionEvent) throws Exception {

        Stage stage = (Stage) BackToAnimal.getScene().getWindow();
        stage.close();
        MenuOptions menu = new MenuOptions();
        Stage stag = new Stage();

        menu.start(stag);

    }

}




