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

public class DatabaseConnector {

	private Connection conn;
	private Properties properties = new Properties();
	
	public DatabaseConnector(){		
		InputStream in = null;
		
		try{
			in = new FileInputStream("database.properties");
			System.out.println(in);
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
			DatabaseConnector db = new DatabaseConnector();
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