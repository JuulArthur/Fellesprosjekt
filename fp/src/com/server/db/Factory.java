package com.server.db;

import java.sql.SQLException;
import java.util.Properties;

public class Factory {

	DatabaseConnector db;
	
	public Factory(){
		db = new DatabaseConnector();
	}
	
	public void createEmployee(String name, int birthyear) throws SQLException, ClassNotFoundException{
		//lage en person
		//Last ned apache cayenne eller jbose hibernate
		//Employee e = new Employee(name,birthyear);
		String query = "";
		db.initialize();
		db.makeSingleQuery(query);
		db.close();
	}
}
