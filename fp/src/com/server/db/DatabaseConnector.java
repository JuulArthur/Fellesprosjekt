package com.server.db;

import java.io.File;
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

	private String jdbcDriver;
	private Connection conn;
	private String url;
	private Properties properties;
	
	public DatabaseConnector(){
		jdbcDriver=properties.getProperty("jdbcDriver");
		url=properties.getProperty("url");
		
		InputStream in = null;
		
		try{
			in = new FileInputStream("database.properties");
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
		
		Class.forName(jdbcDriver);
		
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String passwd = properties.getProperty("db.passwd");
        
        conn = DriverManager.getConnection(url, user, passwd);
	
	}
	
	
	
	public static void main(String args[]){
		Properties p = new Properties();
		try{
			p.load(new FileInputStream(new File("Properties.properties")));
			DatabaseConnector db = new DatabaseConnector();
			db.initialize();
		}
		catch (FileNotFoundException e ){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
