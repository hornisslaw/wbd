package sample.UserUi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Bilety;
import sample.DBConnection;
import sample.MenuOptions;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class UserBiletyController implements Initializable {


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
    private Button ButToMenu;
    //Backto Animals
    @FXML
    public void ReturnToMenu(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) ButToMenu.getScene().getWindow();
        stage.close();
        MenuUser menu = new MenuUser();
        Stage stag = new Stage();

        menu.start(stag);
    }
@FXML
private TextField SearchFieldData;
    public void FindEventDate(ActionEvent actionEvent) {
        conn = DBConnection.getConnection();
        String biletData;
        biletData = SearchFieldData.getText().trim();
        biletylist = new Bilety().getRestrictedList(conn,biletData);
        setTableViewBilety(biletylist);

    }
}
