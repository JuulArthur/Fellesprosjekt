package com.server.db;

import java.sql.SQLException;
import java.util.Properties;
import com.model.UserModel;

public class Factory {

	DatabaseConnector db;
	
	public Factory(){
		db = new DatabaseConnector();
	}
	
	public UserModel createUser(String userName, String password, String email, String name, String surName, String phoneNumber, int isAdmin ) throws SQLException, ClassNotFoundException{
		//lage en person
		//Last ned apache cayenne eller jbose hibernate
		//Employee e = new Employee(name,birthyear);
		UserModel um = new UserModel();
		String query=String.format("insert into User " +
				"(userName, email, name, surName, phoneNumber, password, isAdmin) values ('%s', '%s','%s','%s','%s','%s',%d)", userName, password, email, name, surName, phoneNumber, isAdmin); 
		db.initialize();
		db.makeUpdate(query);
		db.close();
	
		return um;
	}
}
