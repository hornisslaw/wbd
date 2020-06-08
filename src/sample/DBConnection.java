package sample;
import javafx.scene.control.Alert;

import java.sql.*;

public class DBConnection {
    private static Connection conn;
    public static Connection getConnection()  {
        String DB_URL = "jdbc:oracle:thin:@localhost:1521:ORCL1"; //ta sciezka jest tak jak na wykladzie
        String DB_USER = "RKACZMAR3"; //twoj usser nie system
        String DB_PASS = "amatorobson";  //twoje haslo usera
        //String DB_URL = "jdbc:oracle:thin:@localhost:1521:Fanso98"; //ta sciezka jest tak jak na wykladzie
        //String DB_USER = "KHARBASZ"; //twoj usser nie system
        //String DB_PASS = "hujhujs";  //twoje haslo usera
        try{
        conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);

        }
        catch(SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error to database connection...");
            alert.setContentText("Details: "+ exc.getMessage());
            alert.show();

        }

        return conn;
    }


}
