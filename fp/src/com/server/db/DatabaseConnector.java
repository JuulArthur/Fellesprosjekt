package com.server.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
/*
 * Database101
 * 
 * $ mysql -u root -p //Skriv ditt rotpassord
 * $ create database test3;
 * $ CREATE USER 'testuser'@'localhost' IDENTIFIED BY 'test623';
 * $ GRANT ALL PRIVILEGES ON * . * TO 'testuser'@'localhost';
 * $ grant all privileges on test3.* to testuser@localhost; //Ikke nødvendig??
 * $ FLUSH PRIVILEGES; //Reload privileges
 * Login
 * $ mysql -u testuser -p test3 //Skriv passord til testuser
 * $ use test3;
 * 
 * Here you can learn more about mysql: http://zetcode.com/db/mysqljava/
 */
public class DatabaseConnector {

	private Connection conn;
	private Properties properties = new Properties();
	
	public DatabaseConnector(String propertiesFile){		
		InputStream in = null;
		
		try{
			in = new FileInputStream(propertiesFile);
			properties.load(in);
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet makeSingleQuery(String sql) throws SQLException{
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		return rs;
	}
	
	public int makeUpdate(String sql) throws SQLException{
		Statement st =conn.createStatement();
		return st.executeUpdate(sql);
	}
	
	public PreparedStatement makeBatchUpdate(String sql) throws SQLException{
		PreparedStatement st =conn.prepareStatement(sql);
		return st;
		
	}
	
	public void close() throws SQLException{
		conn.close();
	}
	
	
	public void initialize() throws SQLException, ClassNotFoundException{
		
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String passwd = properties.getProperty("db.passwd");
        
        conn = DriverManager.getConnection(url, user, passwd);
	}
	
	
	
	public static void main(String args[]){
		Properties p = new Properties();
		try{
			DatabaseConnector db = new DatabaseConnector("database.properties");
			db.initialize();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
