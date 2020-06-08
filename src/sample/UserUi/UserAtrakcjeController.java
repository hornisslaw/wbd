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
import sample.Atrakcje;
import sample.DBConnection;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class UserAtrakcjeController implements Initializable {

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
    private Button ButToMenu;

    public void ReturnToMenuUser(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) ButToMenu.getScene().getWindow();
        stage.close();
        MenuUser menu = new MenuUser();
        Stage stag = new Stage();

        menu.start(stag);
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
}
