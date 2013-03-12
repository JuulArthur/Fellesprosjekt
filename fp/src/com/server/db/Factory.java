package com.server.db;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.model.AlarmModel;
import com.model.AppointmentModel;
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
	
	public UserModel createUserModel(String username, String password, String email, 
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
	
	public void updateUserModel(String username, String password, String email, 
			String name, String surname, String phoneNumber, int isAdmin ) 
					throws SQLException, ClassNotFoundException{
		String query = String.format("UPDATE User SET email='%s',name='%s',surname='%s',phonenumber='%s'" +
				",password='%s',isAdmin=%d WHERE username='%s'", email, name, surname, phoneNumber, password, isAdmin, username);
		UpdateDatabase(query);
		
	}
	
	public void deleteUserModel(String username) throws SQLException, ClassNotFoundException{
		String query=String.format("DELETE from User WHERE username='%s'" ,username); 
		UpdateDatabase(query);
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
	
	public AlarmModel createAlarmModel(Date date, String text, AppointmentModel ap, UserModel um) 
			throws SQLException, ClassNotFoundException{
		AlarmModel am = new AlarmModel(date, text, ap, um);
		String query=String.format("insert into Alarm " +
				"(date, text, appointmentid, id, username) values " +
				"('%s', '%s',%d,'%s')", date, text, ap.getId(), um.getUsername()); 
		UpdateDatabase(query);
		return am;
	}
	
	public AlarmModel getAlarmModel(String username, int id) throws ClassNotFoundException, SQLException
	{
		
		
		String query=String.format("Select date, text " +
				"from Alarm WHERE username='%s'AND appointmendid=%d",username,id);
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
	
	public void updateAlarmModel(String username, String password, String email, 
			String name, String surname, String phoneNumber, int isAdmin ) 
					throws SQLException, ClassNotFoundException{
		String query = String.format("UPDATE User SET email='%s',name='%s',surname='%s',phonenumber='%s',password='%s',isAdmin=%d WHERE username='%s'", email, name, surname, phoneNumber, password, isAdmin, username);
		UpdateDatabase(query);
		
	}
	
	public void deleteAlarmModel(String username) throws SQLException, ClassNotFoundException{
		String query=String.format("DELETE from User WHERE username='%s'" ,username); 
		UpdateDatabase(query);
	}
}
