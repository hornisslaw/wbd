package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.*;

public class Osobniki {
    private  Integer idOsobnik;
    private String imie;
    private String opis;
    private String stanZdrowia;

    public Integer getIdOsobnik() {
        return idOsobnik;
    }

    public void setIdOsobnik(Integer idOsobnik) {
        this.idOsobnik = idOsobnik;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getStanZdrowia() {
        return stanZdrowia;
    }

    public void setStanZdrowia(String stanZdrowia) {
        this.stanZdrowia = stanZdrowia;
    }
    public ObservableList<Osobniki> getAll(Connection conn){
        ObservableList<Osobniki> listOsobniki = FXCollections.observableArrayList();

        String sql = "SELECT IDOSOBNIK, IMIE, OPIS, STANZDROWIA FROM OSOBONIKI ORDER BY IDOSOBNIK";
        Statement stmt;
        ResultSet rs;
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                Osobniki osobniki = new Osobniki();
                osobniki.setIdOsobnik(rs.getInt(1));
                osobniki.setImie(rs.getString(2));
                osobniki.setOpis(rs.getString(3));
                osobniki.setStanZdrowia(rs.getString(4));
                listOsobniki.add(osobniki);
            }
        }
        catch (SQLException exc){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data retrival.");
            alert.setContentText("Details: "+ exc.getMessage());
            alert.show();
        }

        return listOsobniki;
    }
    public ObservableList<Osobniki> getRestrictedList ( Connection conn, String osobnikiName){
        ObservableList<Osobniki> listOsobniki = FXCollections.observableArrayList();
        String sql = "SELECT IDOSOBNIK, IMIE, OPIS, STANZDROWIA "+ " FROM OSOBONIKI WHERE UPPER(IMIE) LIKE ? ORDER BY IDOSOBNIK";
        PreparedStatement stmt;
        ResultSet rs;
        try{
            stmt=conn.prepareStatement(sql);
            stmt.setString(1,osobnikiName.toUpperCase()+"%");
            rs=stmt.executeQuery();
            while(rs.next()){
                Osobniki osobniki= new Osobniki();
                osobniki.idOsobnik=rs.getInt(1);
                osobniki.imie=rs.getString(2);
                osobniki.opis=rs.getString(3);
                osobniki.stanZdrowia=rs.getString(4);
                listOsobniki.add(osobniki);
            }


        }
        catch (SQLException exc)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data retrival.");
            alert.setContentText("Detailsd: "+ exc.getMessage());
            alert.show();
        }

        return listOsobniki;
    }
    public int insertOsobnik (Connection conn, String osobnikImie, Integer osobnikID, String osobnikStanZdrowia,String osobnikOpis ){
    String sql = "INSERT into OSOBONIKI (IDOSOBNIK, IMIE, OPIS, STANZDROWIA, IDAKWARIUM, IDZWIERZE) values (?,?,?,?,1,1)";
    PreparedStatement stmt;
    Integer res = null;
    try{
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1,osobnikID);
        stmt.setString(2,osobnikImie);
        stmt.setString(3,osobnikOpis);
        stmt.setString(4,osobnikStanZdrowia);
        res= stmt.executeUpdate();
    }catch (SQLException exc) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error with data retrival.");
        alert.setContentText("Details: "+ exc.getMessage());
        alert.show();
    }
    return  res;
    }
    // usuwanie
    public int removeOsobnik (Connection conn, Integer osobnikID){
        String sql = "DELETE from OSOBONIKI WHERE IDOSOBNIK=?";
        PreparedStatement stmt;
        Integer res=0;
        try{
            stmt= conn.prepareStatement(sql);
            stmt.setInt(1,osobnikID);
            res=stmt.executeUpdate();
        }
        catch(SQLException exc){Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Problem with deleting");
            alert.setContentText("Details: "+ exc.getMessage());
            alert.showAndWait();

        }
        return res;
    }

    //powrot do osobnikow

        public void start(Stage primaryStage) throws Exception {


            Stage window = primaryStage;

            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            window.setTitle("Oceanarium");
            window.setScene(new Scene(root, 600, 500));
            window.show();

        }

    }



