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

public class BiletyController implements Initializable {


    public TextField TextBiletIDAtrakcji;
    private Connection conn;
    private ObservableList<Bilety> biletylist = FXCollections.observableArrayList();

    @FXML
    private TableView<Bilety> TableBilety;

    @FXML
    private TableColumn<Bilety, String> TableBiletyID;

    @FXML
    private TableColumn<Bilety, String> TableBiletyCena;

    @FXML
    private TableColumn<Bilety, String> TableBiletyUlg;

    @FXML
    private TableColumn<Bilety, String> TableBiletyGrup;

    @FXML
    private TableColumn<Bilety, String> TableBiletyData;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = DBConnection.getConnection();
        biletylist = new Bilety().getAll(conn);
        //setup table viwe
        TableBiletyID.setCellValueFactory(new PropertyValueFactory<>("idBilet"));
        TableBiletyCena.setCellValueFactory(new PropertyValueFactory<>("cenaBilet"));
        TableBiletyUlg.setCellValueFactory(new PropertyValueFactory<>("czUlgBilet"));
        TableBiletyGrup.setCellValueFactory(new PropertyValueFactory<>("czyGruBilet"));
        TableBiletyData.setCellValueFactory(new PropertyValueFactory<>("dataBilet"));

        TableBilety.setItems(biletylist);
    }
    private void setTableViewBilety(ObservableList<Bilety> biletylist)
    {
        TableBiletyID.setCellValueFactory(new PropertyValueFactory<>("idBilet"));
        TableBiletyCena.setCellValueFactory(new PropertyValueFactory<>("cenaBilet"));
        TableBiletyUlg.setCellValueFactory(new PropertyValueFactory<>("czUlgBilet"));
        TableBiletyGrup.setCellValueFactory(new PropertyValueFactory<>("czyGruBilet"));
        TableBiletyData.setCellValueFactory(new PropertyValueFactory<>("dataBilet"));

        TableBilety.setItems(biletylist);
    }

    @FXML
    private TextField TextBiletID;

    @FXML
    private TextField TextBiletCena;

    @FXML
    private TextField TextBiletUlg;

    @FXML
    private TextField TextBiletGrup;

    @FXML
    private TextField TextBiletData;

    @FXML
    public void AddBilet(javafx.event.ActionEvent actionEvent) {
        String BiletUlg;
        String BiletGru;
        String BiletData;
        Integer BiletID;
        Double BiletCena;
        Integer result;
        BiletUlg= TextBiletUlg.getText().trim();
        if(!BiletUlg.equals("T")&&!BiletUlg.equals("N")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("T/N tylko");
            alert.showAndWait();
            return;
        }
        BiletGru = TextBiletGrup.getText().trim();
        if(!BiletGru.equals("T")&&!BiletGru.equals("N")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("T/N tylko");
            alert.showAndWait();
            return;
        }
        BiletData = TextBiletData.getText().trim();
        if(BiletData.length()==0 || BiletData.length()>250){     //dopracowac
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty or to long description");
            alert.showAndWait();
            return;
        }


        BiletID =Integer.parseInt( TextBiletID.getText().trim()); //DOPRACOWAC
        BiletCena = Double.parseDouble(TextBiletCena.getText().trim()); //DOPRACOWAC OGRANICZENIA I FORME



        conn =DBConnection.getConnection();
        result = new Bilety().insertBilet(conn,BiletCena,BiletID,BiletUlg,BiletGru,BiletData);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Udalo sie dodac rekord");
        alert.showAndWait();
    }



    @FXML
    public void DeleteBilet(javafx.event.ActionEvent actionEvent) {
        Integer rowIndex = TableBilety.getSelectionModel().getSelectedIndex();
        if(rowIndex<0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nie zaznaczono ani jedengo rekordu do usuniecia");
            alert.showAndWait();
            return;
        }
        Integer biletID= TableBilety.getSelectionModel().getSelectedItem().getIdBilet();
        conn = DBConnection.getConnection();
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> res1=alert1.showAndWait();
        if(res1.get()==ButtonType.OK){
            Integer result = new Bilety().removeBilet(conn,biletID);
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
    public void FindEventDate(javafx.event.ActionEvent actionEvent) {
        conn = DBConnection.getConnection();
        String biletData;
        biletData = SearchFieldData.getText().trim();
        biletylist = new Bilety().getRestrictedList(conn,biletData);
        setTableViewBilety(biletylist);
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
