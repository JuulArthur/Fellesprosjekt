package database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Retrieve2 {

    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        Properties props = new Properties();
        FileInputStream in = null;

        try {
            //in = new FileInputStream("/Users/Juul/workspace/Fellesprosjekt/Fellesprosjekt/server/src/database/database.properties");
        	in = new FileInputStream("src/database/database.properties");
        	props.load(in);

        } catch (FileNotFoundException ex) {

            Logger lgr = Logger.getLogger(Retrieve2.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } catch (IOException ex) {

            Logger lgr = Logger.getLogger(Retrieve2.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            
            try {
                 if (in != null) {
                     in.close();
                 }
            } catch (IOException ex) {
                Logger lgr = Logger.getLogger(Retrieve2.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String passwd = props.getProperty("db.passwd");

        try {

            con = DriverManager.getConnection(url, user, passwd);
            pst = con.prepareStatement("SELECT * FROM Avtale");
            rs = pst.executeQuery();

            while (rs.next()) {
                System.out.print(rs.getInt(1));
                System.out.print(": ");
                System.out.println(rs.getString(5));
            }


        } catch (Exception ex) {
            Logger lgr = Logger.getLogger(Retrieve2.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Retrieve2.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
}