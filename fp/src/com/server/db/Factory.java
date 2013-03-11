package com.server.db;

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
		//lage en person
		//Last ned apache cayenne eller jbose hibernate
		//Employee e = new Employee(name,birthyear);
		UserModel um = new UserModel(username, password, email, name, surname, phoneNumber, isAdmin);
		String query=String.format("insert into User " +
				"(userName, password, email, name, surName, phoneNumber, isAdmin) values ('%s', '%s','%s','%s','%s','%s',%d)", username, password, email, name, surname, phoneNumber, isAdmin); 
		db.initialize();
		db.makeUpdate(query);
		db.close();
	
		return um;
	}
}
