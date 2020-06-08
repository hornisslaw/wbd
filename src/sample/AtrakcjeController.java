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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AtrakcjeController implements Initializable {



    private Connection conn;
    private ObservableList<Atrakcje> atrakcjelist = FXCollections.observableArrayList();

    @FXML
    private TableView<Atrakcje> TableAtrakcje;

    @FXML
    private TableColumn<?, ?> TableAtrakcjeID;

    @FXML
    private TableColumn<?, ?> TableAtrakcjeDzien;

    @FXML
    private TableColumn<?, ?> TableAtrakcjeOd;

    @FXML
    private TableColumn<?, ?> TableAtrakcjeDo;

    @FXML
    private TableColumn<?, ?> TableAtrakcjeNazwa;

    @FXML
    private TableColumn<?, ?> TableAtrakcjeOpis;

    @FXML
    private TableColumn<?, ?> TableAtrakcjeStrefa;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = DBConnection.getConnection();
        atrakcjelist = new Atrakcje().getAll(conn);
        //setup table viwe
        TableAtrakcjeID.setCellValueFactory(new PropertyValueFactory<>("idAtrakcja"));
        TableAtrakcjeDzien.setCellValueFactory(new PropertyValueFactory<>("dzienAtrakcja"));
        TableAtrakcjeOd.setCellValueFactory(new PropertyValueFactory<>("odAtrakcja"));
        TableAtrakcjeDo.setCellValueFactory(new PropertyValueFactory<>("doAtrakcja"));
        TableAtrakcjeNazwa.setCellValueFactory(new PropertyValueFactory<>("nazwaAtrakcja"));
        TableAtrakcjeOpis.setCellValueFactory(new PropertyValueFactory<>("opisAtrakcja"));
        TableAtrakcjeStrefa.setCellValueFactory(new PropertyValueFactory<>("strefaAtrakcja"));
        TableAtrakcje.setItems(atrakcjelist);
    }

    private void setTableViewAtrakcje(ObservableList<Atrakcje> atrakcjelist)
    {
        TableAtrakcjeID.setCellValueFactory(new PropertyValueFactory<>("idAtrakcja"));
        TableAtrakcjeDzien.setCellValueFactory(new PropertyValueFactory<>("dzienAtrakcja"));
        TableAtrakcjeOd.setCellValueFactory(new PropertyValueFactory<>("odAtrakcja"));
        TableAtrakcjeDo.setCellValueFactory(new PropertyValueFactory<>("doAtrakcja"));
        TableAtrakcjeNazwa.setCellValueFactory(new PropertyValueFactory<>("nazwaAtrakcja"));
        TableAtrakcjeOpis.setCellValueFactory(new PropertyValueFactory<>("opisAtrakcja"));
        TableAtrakcjeStrefa.setCellValueFactory(new PropertyValueFactory<>("strefaAtrakcja"));
        TableAtrakcje.setItems(atrakcjelist);
    }


    @FXML
    private TextField TextAtrakcjaID;

    @FXML
    private TextField TextAtrakcjaDate;

    @FXML
    private TextField TextAtrakcjaOd;

    @FXML
    private TextField TextAtrakcjaDo;

    @FXML
    private TextField TextAtrakcjaOpis;

    @FXML
    private TextField TextAtrakcjaNazwa;

    @FXML
    private TextField TextAtrakcjaStrefa;




    @FXML
    public void AddAtrakcja(javafx.event.ActionEvent actionEvent) {
        Integer idAtrakcja;
        String dzienAtrakcja;
        String odAtrakcja;
        String doAtrakcja;
        String nazwaAtrakcja;
        String opisAtrakcja;
        String strefaAtrakcja;

        Integer result;
        dzienAtrakcja= TextAtrakcjaDate.getText().trim();
        try{
            LocalDate.parse(dzienAtrakcja);
        }catch (DateTimeParseException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Problem with data format");
            alert.setContentText("Details: Wrong format. Try YYYY-MM-DD");
            alert.showAndWait();
        }

        odAtrakcja= TextAtrakcjaOd.getText().trim();
        if( odAtrakcja.length()>4){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("czas typu 1200");
            alert.showAndWait();
            return;
        }
        doAtrakcja= TextAtrakcjaDo.getText().trim();
        if( doAtrakcja.length()>4){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("czas typu 1300");
            alert.showAndWait();
            return;
        }
        nazwaAtrakcja= TextAtrakcjaNazwa.getText().trim();
        if(nazwaAtrakcja.length()==0 || nazwaAtrakcja.length()>60){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("nie moze byc empty ani >60");
            alert.showAndWait();
            return;
        }
        opisAtrakcja= TextAtrakcjaOpis.getText().trim();
        if(opisAtrakcja.length()==0 || opisAtrakcja.length()>250){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("nie moze byc empty ani >250");
            alert.showAndWait();
            return;
        }
        strefaAtrakcja = TextAtrakcjaStrefa.getText().trim();
        if(!strefaAtrakcja.equals("A")&&!strefaAtrakcja.equals("B")&&!strefaAtrakcja.equals("C")&&!strefaAtrakcja.equals("D")&&!strefaAtrakcja.equals("E")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("A/B/C/D/E tylko");
            alert.showAndWait();
            return;
        }

        idAtrakcja =Integer.parseInt( TextAtrakcjaID.getText().trim()); //DOPRACOWAC


        conn =DBConnection.getConnection();
        result = new Atrakcje().insertAtrakcja(conn,idAtrakcja,dzienAtrakcja,odAtrakcja,doAtrakcja,nazwaAtrakcja,opisAtrakcja,strefaAtrakcja,"T");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Udalo sie dodac rekord");
        alert.showAndWait();
    }


    @FXML
    public void DeleteAtrakcja(javafx.event.ActionEvent actionEvent) {
        Integer rowIndex = TableAtrakcje.getSelectionModel().getSelectedIndex();
        if(rowIndex<0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nie zaznaczono ani jedengo rekordu do usuniecia");
            alert.showAndWait();
            return;
        }
        Integer atrakcjaID= TableAtrakcje.getSelectionModel().getSelectedItem().getIdAtrakcja();
        conn = DBConnection.getConnection();
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> res1=alert1.showAndWait();
        if(res1.get()==ButtonType.OK){
            Integer result = new Atrakcje().removeAtrakcje(conn,atrakcjaID);
            if(result>0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Record removed");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private TextField SearchFieldData;
    @FXML
    public void FindEvent(javafx.event.ActionEvent actionEvent) {
        conn = DBConnection.getConnection();
        String atrakcjaName;
        atrakcjaName = SearchFieldData.getText().trim();
        atrakcjelist = new Atrakcje().getRestrictedList(conn,atrakcjaName);
        setTableViewAtrakcje(atrakcjelist);
    }




    @FXML
    private Button ButToMenu;
    //Backto Animals
    @FXML
    public void ReturnToMenu(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) ButToMenu.getScene().getWindow();
        stage.close();
        MenuOptions menu = new MenuOptions();
        Stage stag = new Stage();

        menu.start(stag);
    }


}
