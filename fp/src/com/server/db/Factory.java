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
	
	public UserModel createUser(String username, String password, String email, 
			String name, String surname, String phoneNumber, int isAdmin ) 
					throws SQLException, ClassNotFoundException{
		UserModel um = new UserModel(username, password, email, name, surname, phoneNumber, isAdmin);
		String query=String.format("insert into User " +
				"(userName, password, email, name, surName, phoneNumber, isAdmin) values ('%s', '%s','%s','%s','%s','%s',%d)", username, password, email, name, surname, phoneNumber, isAdmin); 
		db.initialize();
		db.makeUpdate(query);
		db.close();
	
		return um;
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
