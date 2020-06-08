package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.*;

public class Atrakcje {
    private Integer idAtrakcja;
    private String dzienAtrakcja;
    private String odAtrakcja;
    private String doAtrakcja;
    private String nazwaAtrakcja;
    private String opisAtrakcja;
    private String strefaAtrakcja;


    public Integer getIdAtrakcja() {
        return idAtrakcja;
    }

    public void setIdAtrakcja(Integer idAtrakcja) {
        this.idAtrakcja = idAtrakcja;
    }

    public String getDzienAtrakcja() {
        return dzienAtrakcja;
    }

    public void setDzienAtrakcja(String dzienAtrakcja) {
        this.dzienAtrakcja = dzienAtrakcja;
    }

    public String getOdAtrakcja() {
        return odAtrakcja;
    }

    public void setOdAtrakcja(String odAtrakcja) {
        this.odAtrakcja = odAtrakcja;
    }

    public String getDoAtrakcja() {
        return doAtrakcja;
    }

    public void setDoAtrakcja(String doAtrakcja) {
        this.doAtrakcja = doAtrakcja;
    }

    public String getNazwaAtrakcja() {
        return nazwaAtrakcja;
    }

    public void setNazwaAtrakcja(String nazwaAtrakcja) {
        this.nazwaAtrakcja = nazwaAtrakcja;
    }

    public String getOpisAtrakcja() {
        return opisAtrakcja;
    }

    public void setOpisAtrakcja(String opisAtrakcja) {
        this.opisAtrakcja = opisAtrakcja;
    }

    public String getStrefaAtrakcja() {
        return strefaAtrakcja;
    }

    public void setStrefaAtrakcja(String strefaAtrakcja) {
        this.strefaAtrakcja = strefaAtrakcja;
    }

    public ObservableList<Atrakcje> getAll(Connection conn){
        ObservableList<Atrakcje> listAtrakcje = FXCollections.observableArrayList();

        String sql = "SELECT IDATRAKCJA, DZIEN, GODZINAOD, GODZINADO, NAZWA, OPIS, STREFAA FROM ATRAKCJE ORDER BY IDATRAKCJA";
        Statement stmt;
        ResultSet rs;
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                Atrakcje atrakcje = new Atrakcje();
                atrakcje.setIdAtrakcja(rs.getInt(1));
                atrakcje.setDzienAtrakcja(rs.getString(2));
                atrakcje.setOdAtrakcja(rs.getString(3));
                atrakcje.setDoAtrakcja(rs.getString(4));
                atrakcje.setNazwaAtrakcja(rs.getString(5));
                atrakcje.setOpisAtrakcja(rs.getString(6));
                atrakcje.setStrefaAtrakcja(rs.getString(7));
                listAtrakcje.add(atrakcje);
            }
        }

        catch (SQLException exc)
        {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data retrival.");
            alert.setContentText("Details: "+ exc.getMessage());
            alert.show();
        }
        return listAtrakcje;
    }




    public ObservableList<Atrakcje> getRestrictedList ( Connection conn, String atrakjaName){
        ObservableList<Atrakcje> listAtrakcje = FXCollections.observableArrayList();
        String sql = "SELECT IDATRAKCJA, DZIEN, GODZINAOD, GODZINADO, NAZWA, OPIS, STREFAA "+ " FROM ATRAKCJE WHERE UPPER(NAZWA) LIKE ? ORDER BY IDATRAKCJA";
        PreparedStatement stmt;
        ResultSet rs;
        try{
            stmt=conn.prepareStatement(sql);
            stmt.setString(1,atrakjaName+"%");
            rs=stmt.executeQuery();
            while(rs.next()){
                Atrakcje atrakcje = new Atrakcje();
                atrakcje.setIdAtrakcja(rs.getInt(1));
                atrakcje.setDzienAtrakcja(rs.getString(2));
                atrakcje.setOdAtrakcja(rs.getString(3));
                atrakcje.setDoAtrakcja(rs.getString(4));
                atrakcje.setNazwaAtrakcja(rs.getString(5));
                atrakcje.setOpisAtrakcja(rs.getString(6));
                atrakcje.setStrefaAtrakcja(rs.getString(7));
                listAtrakcje.add(atrakcje);
            }

        }
        catch (SQLException exc)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data retrival.");
            alert.setContentText("Detailsd: "+ exc.getMessage());
            alert.show();
        }

        return listAtrakcje;
    }

    public int insertAtrakcja (Connection conn, Integer idAtrakcja, String dzienAtrakcja,String odAtrakcja,
                               String doAtrakcja, String nazwaAtrakcja,String opisAtrakcja,
                               String strefaAtrakcja,String Czyog ){
        String sql = "INSERT INTO ATRAKCJE (IDATRAKCJA,DZIEN,GODZINAOD,GODZINADO,NAZWA,OPIS,STREFAA,IDOCEANARIUM,CZYOG) values (?,?,?,?,?,?,?,1,?)";
        PreparedStatement stmt;
        Integer res = null;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,idAtrakcja);
            stmt.setString(2,dzienAtrakcja);
            stmt.setString(3,odAtrakcja);
            stmt.setString(4,doAtrakcja);
            stmt.setString(5,nazwaAtrakcja);
            stmt.setString(6,opisAtrakcja);
            stmt.setString(7,strefaAtrakcja);
            stmt.setString(8,Czyog);
            res= stmt.executeUpdate();
        }catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data retrival.");
            alert.setContentText("Details: "+ exc.getMessage());
            alert.show();
        }
        return  res;
    }


    public int removeAtrakcje (Connection conn, Integer aktrakcjaID){
        String sql = "DELETE from ATRAKCJE WHERE IDATRAKCJA=?";
        PreparedStatement stmt;
        Integer res=0;
        try{
            stmt= conn.prepareStatement(sql);
            stmt.setInt(1,aktrakcjaID);
            res=stmt.executeUpdate();
        }
        catch(SQLException exc){Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Problem with deleting");
            alert.setContentText("Details: "+ exc.getMessage());
            alert.showAndWait();

        }
        return res;
    }


    public void start(Stage primaryStage) throws Exception {


        Stage window = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("Atrakcje.fxml"));
        window.setTitle("Oceanarium");
        window.setScene(new Scene(root, 600, 500));
        window.show();

    }



}
