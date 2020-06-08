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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AtrakcjeController implements Initializable {

    private Connection conn = DBConnection.getConnection();
    private ObservableList<Atrakcje> atrakcjeList = FXCollections.observableArrayList();

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
        atrakcjeList = new Atrakcje().getAll(conn);
        //setup table viwe
        TableAtrakcjeID.setCellValueFactory(new PropertyValueFactory<>("idAtrakcja"));
        TableAtrakcjeDzien.setCellValueFactory(new PropertyValueFactory<>("dzienAtrakcja"));
        TableAtrakcjeOd.setCellValueFactory(new PropertyValueFactory<>("odAtrakcja"));
        TableAtrakcjeDo.setCellValueFactory(new PropertyValueFactory<>("doAtrakcja"));
        TableAtrakcjeNazwa.setCellValueFactory(new PropertyValueFactory<>("nazwaAtrakcja"));
        TableAtrakcjeOpis.setCellValueFactory(new PropertyValueFactory<>("opisAtrakcja"));
        TableAtrakcjeStrefa.setCellValueFactory(new PropertyValueFactory<>("strefaAtrakcja"));
        TableAtrakcje.setItems(atrakcjeList);
    }

    private void setTableViewAtrakcje(ObservableList<Atrakcje> atrakcjelist) {
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
    public void addAtrakcja(javafx.event.ActionEvent actionEvent) {
        Atrakcje nowaAtrakcja = Atrakcje.AtrakcjeBuilder.Atrakcje()
                .withIdAtrakcja(Integer.parseInt(TextAtrakcjaID.getText().trim()))
                .withDzienAtrakcja(TextAtrakcjaDate.getText().trim())
                .withOdAtrakcja(TextAtrakcjaOd.getText().trim())
                .withDoAtrakcja(TextAtrakcjaDo.getText().trim())
                .withNazwaAtrakcja(TextAtrakcjaNazwa.getText().trim())
                .withOpisAtrakcja(TextAtrakcjaOpis.getText().trim())
                .withStrefaAtrakcja(TextAtrakcjaStrefa.getText().trim())
                .build();

        if (validateAtrakcja(nowaAtrakcja)) {
            Atrakcje.insertAtrakcja(conn, nowaAtrakcja, "T");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Udalo sie dodac rekord");
            alert.showAndWait();
        }
    }

    private boolean validateAtrakcja(Atrakcje atrakcja) {
        return isValidDzienAtrakcja(atrakcja.getDzienAtrakcja())
                && isValidTimeFormat(atrakcja.getOdAtrakcja())
                && isValidTimeFormat(atrakcja.getDoAtrakcja())
                && isValidNazwaAtrakcja(atrakcja.getNazwaAtrakcja())
                && isValidOpisAtrakcja(atrakcja.getOpisAtrakcja())
                && isValidStrefaAtrakcja(atrakcja.getStrefaAtrakcja());
    }

    private boolean isValidDzienAtrakcja(String dzienAtrakcja) {
        try {
            LocalDate.parse(dzienAtrakcja);
        } catch (DateTimeParseException exc) {
            displayError("Details: Wrong format. Try YYYY-MM-DD");
            return false;
        }
        return true;
    }

    private boolean isValidTimeFormat(String odAtrakcja) {
        int timeFormatLength = 4;
        if (odAtrakcja.length() != timeFormatLength) {
            displayError("czas typu 1200");
            return false;
        }
        return true;
    }

    private boolean isValidNazwaAtrakcja(String nazwaAtrakcja) {
        if (nazwaAtrakcja.length() == 0 || nazwaAtrakcja.length() > 60) {
            displayError("nie moze byc empty ani >60");
            return false;
        }
        return true;
    }

    private boolean isValidOpisAtrakcja(String opisAtrakcja) {
        if (opisAtrakcja.length() == 0 || opisAtrakcja.length() > 250) {
            displayError("nie moze byc empty ani >250");
            return false;
        }
        return true;
    }

    private boolean isValidStrefaAtrakcja(String strefaAtrakcja) {
        List<String> mozliweStrefy = List.of("A", "B", "C", "D", "E");
        if (!mozliweStrefy.contains(strefaAtrakcja)) {
            displayError("A/B/C/D/E tylko");
            return false;
        }
        return true;
    }

    private void displayError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }


    @FXML
    public void deleteAtrakcja(javafx.event.ActionEvent actionEvent) {
        Integer rowIndex = TableAtrakcje.getSelectionModel().getSelectedIndex();
        if (rowIndex < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nie zaznaczono ani jedengo rekordu do usuniecia");
            alert.showAndWait();
            return;
        }
        Integer atrakcjaID = TableAtrakcje.getSelectionModel().getSelectedItem().getIdAtrakcja();
        conn = DBConnection.getConnection();
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> res1 = alert1.showAndWait();
        if (res1.get() == ButtonType.OK) {
            Integer result = new Atrakcje().removeAtrakcje(conn, atrakcjaID);
            if (result > 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Record removed");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private TextField SearchFieldData;

    @FXML
    public void findEvent(javafx.event.ActionEvent actionEvent) {
        conn = DBConnection.getConnection();
        String atrakcjaName;
        atrakcjaName = SearchFieldData.getText().trim();
        atrakcjeList = new Atrakcje().getRestrictedList(conn, atrakcjaName);
        setTableViewAtrakcje(atrakcjeList);
    }


    @FXML
    private Button ButToMenu;

    //Backto Animals
    @FXML
    public void returnToMenu(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) ButToMenu.getScene().getWindow();
        stage.close();
        MenuOptions menu = new MenuOptions();
        Stage stag = new Stage();

        menu.start(stag);
    }


}
