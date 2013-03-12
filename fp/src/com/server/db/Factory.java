package com.server.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import com.model.UserModel;

public class Factory {

	DatabaseConnector db;
	
	public Factory(){
		db = new DatabaseConnector();
	}
	
	public void UpdateDatabase(String query) throws SQLException, ClassNotFoundException{
		db.initialize();
		db.makeUpdate(query);
		db.close();
	}
	
	public UserModel createUser(String username, String password, String email, 
			String name, String surname, String phoneNumber, int isAdmin ) 
					throws SQLException, ClassNotFoundException{
		UserModel um = new UserModel(username, password, email, name, surname, phoneNumber, isAdmin);
		String query=String.format("insert into User " +
				"(userName, password, email, name, surName, phoneNumber, isAdmin) values ('%s', '%s','%s','%s','%s','%s',%d)", username, password, email, name, surname, phoneNumber, isAdmin); 
		UpdateDatabase(query);
		return um;
	}
	
	public UserModel getUserModel(String username) throws ClassNotFoundException, SQLException
	{
		
		
		String query=String.format("Select username,email,name,surname,phonenumber,password,isadmin from User where username='%s'",username);
		db.initialize();
		ResultSet rs=db.makeSingleQuery(query);
		String password=null;
		String email=null;
		String name=null;
		String surname=null;
		String phoneNumber=null;
		int isAdmin=0;
		while(rs.next())
		{
			email=rs.getString(2);
			name=rs.getString(3);
			surname=rs.getString(4);
			phoneNumber=rs.getString(5);
			password=rs.getString(6);
			isAdmin=rs.getInt(7);
		}
		
		UserModel um = new UserModel(username, password, email, name, surname, phoneNumber, isAdmin);
		rs.close();
		db.close();
		
		return um ;
	
		
	}
	
	public boolean checkPassword(String username, String password) 
					throws SQLException, ClassNotFoundException{
		String returnPassword = "";		
		String query=String.format("SELECT password FROM User WHERE username='%s'" ,username); 
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		rs.beforeFirst();
		if(rs.next()){
			returnPassword = rs.getString(1);
		}
		rs.close();
		db.close();
	
		return returnPassword.equals(password)&& !returnPassword.equals("");
	}
}
