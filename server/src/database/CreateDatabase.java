package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabase {
  public static void main(String[] args) {
    Connection connection = null;
    Statement statement = null;
    try {
      Class.forName("org.gjt.mm.mysql.Driver").newInstance();
      String url = "jdbc:mysql://localhost/mysql";
      connection = DriverManager.getConnection(url, "username", "password");

      statement = connection.createStatement();
      String hrappSQL = "CREATE DATABASE hrapp";
      statement.executeUpdate(hrappSQL);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (statement != null) {
        try {
          statement.close();
        } catch (SQLException e) {
        } // nothing we can do
      }
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
        } // nothing we can do
      }
    }
  }

}

  