package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.*;

public class Bilety {
    private Integer idBilet;
    private Double cenaBilet;
    private String czUlgBilet;
    private String czyGruBilet;
    private String dataBilet;

    public Integer getIdBilet() {
        return idBilet;
    }

    public void setIdBilet(Integer idBilet) {
        this.idBilet = idBilet;
    }

    public Double getCenaBilet() {
        return cenaBilet;
    }

    public void setCenaBilet(Double cenaBilet) {
        this.cenaBilet = cenaBilet;
    }

    public String getCzUlgBilet() {
        return czUlgBilet;
    }

    public void setCzUlgBilet(String czUlgBilet) {
        this.czUlgBilet = czUlgBilet;
    }

    public String getCzyGruBilet() {
        return czyGruBilet;
    }

    public void setCzyGruBilet(String czyGruBilet) {
        this.czyGruBilet = czyGruBilet;
    }

    public String getDataBilet() {
        return dataBilet;
    }

    public void setDataBilet(String dataBilet) {
        this.dataBilet = dataBilet;
    }

    public String getAtrakcjaBilet() {
        return atrakcjaBilet;
    }

    public void setAtrakcjaBilet(String atrakcjaBilet) {
        this.atrakcjaBilet = atrakcjaBilet;
    }

    private String atrakcjaBilet;

    public ObservableList<Bilety> getAll(Connection conn){
        ObservableList<Bilety> listBilety = FXCollections.observableArrayList();

        String sql = "SELECT IDBILET, CENA, CZYG, CZYU, DATAWYDANIA FROM BILETY ORDER BY IDBILET";
        Statement stmt;
        ResultSet rs;
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                Bilety bilety = new Bilety();
                bilety.setIdBilet(rs.getInt(1));
                bilety.setCenaBilet(rs.getDouble(2));
                bilety.setCzUlgBilet(rs.getString(3));
                bilety.setCzyGruBilet(rs.getString(4));
                bilety.setDataBilet(rs.getString(5));
                listBilety.add(bilety);
            }
        }
        catch (SQLException exc){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data retrival.");
            alert.setContentText("Details: "+ exc.getMessage());
            alert.show();
        }

        return listBilety;
    }

    public ObservableList<Bilety> getRestrictedList ( Connection conn, String biletyDate){
        ObservableList<Bilety> listBilety = FXCollections.observableArrayList();
        String sql = "SELECT IDBILET, CENA, CZYG, CZYU,  DATAWYDANIA "+ " FROM BILETY WHERE DATAWYDANIA LIKE ? ORDER BY IDBILET";
        PreparedStatement stmt;
        ResultSet rs;
        try{
            stmt=conn.prepareStatement(sql);
            stmt.setString(1,biletyDate+"%");
            rs=stmt.executeQuery();
            while(rs.next()){
                Bilety bilety = new Bilety();
                bilety.setIdBilet(rs.getInt(1));
                bilety.setCenaBilet(rs.getDouble(2));
                bilety.setCzUlgBilet(rs.getString(3));
                bilety.setCzyGruBilet(rs.getString(4));
                bilety.setDataBilet(rs.getString(5));
                listBilety.add(bilety);
            }


        }
        catch (SQLException exc)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data retrival.");
            alert.setContentText("Detailsd: "+ exc.getMessage());
            alert.show();
        }

        return listBilety;
    }

    public int insertBilet (Connection conn, Double Cena, Integer BiletID, String CzyUlg,String CzyGru, String dataBilet ){
        String sql = "INSERT into BILETY (IDBILET, CENA, CZYG, CZYU, DATAWYDANIA, IDATRAKCJA ,IDKLIENT) values (?,?,?,?,?,1,1)";

        PreparedStatement stmt;
        Integer res = null;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,BiletID);
            stmt.setDouble(2,Cena);
            stmt.setString(3,CzyGru);
            stmt.setString(4,CzyUlg);
            stmt.setString(5,dataBilet);
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
    public int removeBilet (Connection conn, Integer biletID){
        String sql = "DELETE from BILETY WHERE IDBILET=?";
        PreparedStatement stmt;
        Integer res=0;
        try{
            stmt= conn.prepareStatement(sql);
            stmt.setInt(1,biletID);
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

        Parent root = FXMLLoader.load(getClass().getResource("Bilety.fxml"));
        window.setTitle("Oceanarium");
        window.setScene(new Scene(root, 600, 500));
        window.show();

    }


}
