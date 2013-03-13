package com.server.db;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import com.model.AlarmModel;
import com.model.AppointmentModel;
import com.model.NotificationModel;
import com.model.RoomModel;
import com.model.UserModel;

public class Factory {

	DatabaseConnector db;

	public Factory() {
		db = new DatabaseConnector();
	}

	public void UpdateDatabase(String query) throws SQLException,
			ClassNotFoundException {
		db.initialize();
		db.makeUpdate(query);
		db.close();
	}
	
	/**
	 * Make a single query. Inits the DB. Be a doll and close the connection afterwards
	 * @param query
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ResultSet makeQuery(String query) throws SQLException, ClassNotFoundException{
		db.initialize();
		return db.makeSingleQuery(query);
	}

	public UserModel createUserModel(String username, String password,
			String email, String name, String surname, String phoneNumber,
			int isAdmin) throws SQLException, ClassNotFoundException {
		UserModel um = new UserModel(username, password, email, name, surname,
				phoneNumber, isAdmin);
		String query = String
				.format("insert into User "
						+ "(userName, password, email, name, surName, phoneNumber, isAdmin) values ('%s', '%s','%s','%s','%s','%s',%d)",
						username, password, email, name, surname, phoneNumber,
						isAdmin);
		UpdateDatabase(query);
		return um;
	}
	
	public UserModel createUserModel(UserModel um) throws SQLException, ClassNotFoundException {
		String query = String
				.format("insert into User "
						+ "(userName, password, email, name, surName, phoneNumber, isAdmin) values ('%s', '%s','%s','%s','%s','%s',%b)",
						um.getUsername(), um.getPassword(), um.getEmail(), um.getEmail(), um.getSurname(),
						um.getPhoneNumber(), um.isAdmin());
		UpdateDatabase(query);
		return um;
	}

	public UserModel getUserModel(String username)
			throws ClassNotFoundException, SQLException {

		String query = String
				.format("Select username,email,name,surname,phonenumber,password,isadmin from User where username='%s'",
						username);
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		String password = null;
		String email = null;
		String name = null;
		String surname = null;
		String phoneNumber = null;
		int isAdmin = 0;
		while (rs.next()) {
			email = rs.getString(2);
			name = rs.getString(3);
			surname = rs.getString(4);
			phoneNumber = rs.getString(5);
			password = rs.getString(6);
			isAdmin = rs.getInt(7);
		}

		UserModel um = new UserModel(username, password, email, name, surname,
				phoneNumber, isAdmin);
		rs.close();
		db.close();

		return um;

	}
	
	public UserModel getUserModel(UserModel um)
			throws ClassNotFoundException, SQLException {

		String query = String
				.format("Select username,email,name,surname,phonenumber,password,isadmin from User where username='%s'",
						um.getUsername());
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		String password = null;
		String email = null;
		String name = null;
		String surname = null;
		String phoneNumber = null;
		int isAdmin = 0;
		while (rs.next()) {
			email = rs.getString(2);
			name = rs.getString(3);
			surname = rs.getString(4);
			phoneNumber = rs.getString(5);
			password = rs.getString(6);
			isAdmin = rs.getInt(7);
		}

		UserModel utUm = new UserModel(um.getUsername(), password, email, name, surname,
				phoneNumber, isAdmin);
		rs.close();
		db.close();

		return utUm;

	}

	public void updateUserModel(String username, String password, String email,
			String name, String surname, String phoneNumber, int isAdmin)
			throws SQLException, ClassNotFoundException {
		String query = String.format(
				"UPDATE User SET email='%s',name='%s',surname='%s',phonenumber='%s'"
						+ ",password='%s',isAdmin=%d WHERE username='%s'",
				email, name, surname, phoneNumber, password, isAdmin, username);
		UpdateDatabase(query);
	}
	
	public void updateUserModel(UserModel um)
			throws SQLException, ClassNotFoundException {
		String query = String.format(
				"UPDATE User SET email='%s',name='%s',surname='%s',phonenumber='%s'"
						+ ",password='%s',isAdmin=%d WHERE username='%s'",
				um.getEmail(), um.getName(), um.getSurname(), um.getPhoneNumber(), um.getPassword(), um.isAdmin(), um.getUsername());
		UpdateDatabase(query);
	}

	public void deleteUserModel(String username) throws SQLException,
			ClassNotFoundException {
		String query = String.format("DELETE from User WHERE username='%s'",
				username);
		UpdateDatabase(query);
	}
	
	public void deleteUserModel(UserModel um) throws SQLException,
			ClassNotFoundException {
		String query = String.format("DELETE from User WHERE username='%s'",
				um.getUsername());
		UpdateDatabase(query);
	}

	public boolean checkPassword(String username, String password)
			throws SQLException, ClassNotFoundException {
		String returnPassword = "";
		String query = String.format(
				"SELECT password FROM User WHERE username='%s'", username);
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		rs.beforeFirst();
		if (rs.next()) {
			returnPassword = rs.getString(1);
		}
		rs.close();
		db.close();

		return returnPassword.equals(password) && !returnPassword.equals("");
	}
	
	public boolean checkPassword(UserModel um)
			throws SQLException, ClassNotFoundException {
		String returnPassword = "";
		String query = String.format(
				"SELECT password FROM User WHERE username='%s'", um.getUsername());
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		rs.beforeFirst();
		if (rs.next()) {
			returnPassword = rs.getString(1);
		}
		rs.close();
		db.close();

		return returnPassword.equals(um.getPassword()) && !returnPassword.equals("");
	}

	public AlarmModel createAlarmModel(Date date, String text,
			AppointmentModel ap, UserModel user) throws SQLException,
			ClassNotFoundException {
		AlarmModel am = new AlarmModel(date, text, ap, user);
		String query = String.format("insert into Alarm "
				+ "(date, text, appointmentid, username) values "
				+ "('%s', '%s',%d,'%s')", date, text, ap.getId(),
				user.getUsername());
		UpdateDatabase(query);
		return am;
	}

	public AlarmModel createAlarmModel(AlarmModel am) throws SQLException,
			ClassNotFoundException {
		String query = String.format("insert into Alarm "
				+ "(date, text, appointmentid, username) values "
				+ "('%s', '%s',%d,'%s')", am.getDate(), am.getText(), am
				.getAppointment().getId(), am.getCreator().getUsername());
		UpdateDatabase(query);
		return am;
	}

	public AlarmModel getAlarmModel(AppointmentModel ap, UserModel user)
			throws ClassNotFoundException, SQLException {

		String query = String.format("Select date, text "
				+ "from Alarm WHERE username='%s'AND appointmendid=%d",
				user.getUsername(), ap.getId());
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		Date date = null;
		String text = null;
		while (rs.next()) {
			date = rs.getDate(1);
			text = rs.getString(2);
		}

		AlarmModel am = new AlarmModel(date, text, ap, user);
		rs.close();
		db.close();

		return am;
	}
	
	public AlarmModel getAlarmModel(AlarmModel am)
			throws ClassNotFoundException, SQLException {

		String query = String.format("Select date, text "
				+ "from Alarm WHERE username='%s'AND appointmentid=%d",
				am.getCreator().getUsername(), am.getAppointment().getId());
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		Date date = null;
		String text = null;
		while (rs.next()) {
			date = rs.getDate(1);
			text = rs.getString(2);
		}

		AlarmModel utAm = new AlarmModel(date, text, am.getAppointment(), am.getCreator());
		rs.close();
		db.close();

		return utAm;
	}

	public void updateAlarmModel(Date date, String text, AppointmentModel ap,
			UserModel user) throws SQLException, ClassNotFoundException {
		String query = String.format(
				"UPDATE Alarm SET date='%s',text='%s' WHERE appointmentid=%d AND username='%s'",
				date, text, ap.getId());
		UpdateDatabase(query);
	}

	public void updateAlarmModel(AlarmModel am) throws SQLException, ClassNotFoundException {
		System.out.println(am.getDate());
		String query = String.format(
				"UPDATE Alarm SET date='%s',text='%s' WHERE appointmentid=%d AND username='%s'",
				am.getDate(), am.getText(), am.getAppointment().getId(), am.getCreator().getUsername());
		UpdateDatabase(query);
	}

	public void deleteAlarmModel(AppointmentModel ap, UserModel user)
			throws SQLException, ClassNotFoundException {
		String query = String.format(
				"DELETE from Alarm WHERE username='%s' AND appointmentid=%d",
				user.getUsername(), ap.getId());
		UpdateDatabase(query);
	}
	
	public void deleteAlarmModel(AlarmModel am)
			throws SQLException, ClassNotFoundException {
		String query = String.format(
				"DELETE from Alarm WHERE username='%s' AND appointmentid=%d",
				am.getCreator().getUsername(), am.getAppointment().getId());
		UpdateDatabase(query);
	}
	
	/* APPOINTMENT */
	//GET
	public AppointmentModel getAppointmentModel(int pid) throws SQLException, ClassNotFoundException{
		String query=String.format("Select startTime, EndTime, host, title, text, place, isDeleted, date " +
				"FROM Appointment WHERE id='%s'",pid);
		
		ResultSet rs = makeQuery(query);

		int startTime = 0;
		int endTime = 0;
		UserModel host = null;
		String title = null;
		String text = null;
		String place = null;
		boolean isDeleted; 
		Date date = null;
		ArrayList<UserModel> members = null;
		
		while(rs.next())
		{
			startTime=rs.getInt(1);
			endTime =rs.getInt(2);
			host = getUserModel(rs.getString(3));
			title=rs.getString(4);
			text=rs.getString(5);
			place=rs.getString(6);
			isDeleted=rs.getBoolean(7);
			date = rs.getDate(8);
		}
		
		/* Get members */
//		members = new ArrayList<>();
		
		query=String.format("Select username " +
				"FROM IsSummonedTo WHERE appointmentid='%s'",pid);

		rs=makeQuery(query);

		while(rs.next()){
			members.add(getUserModel(rs.getString(1)));
		}
		
		if(members.size() == 0)
			members = null;
		
		
		rs.close();
		db.close();
		
		return new AppointmentModel(pid, startTime, endTime, host, title, text, place, date, members);
	}
	//UPDATE
	//CREATE
	
	public void createAppointmentModel(AppointmentModel apModel){
		
	}
	//DELETE
	
	public NotificationModel createNotificationModel(NotificationModel nm) throws SQLException, ClassNotFoundException {
		String query = String
				.format("insert into Notification "
						+ "(text, appointmentid, username) values ('%s', %d, '%s')",
						nm.getText(), nm.getAppointment().getId(), nm.getCreator().getUsername());
		UpdateDatabase(query);
		return nm;
	}
	
	public NotificationModel getNotificationModel(NotificationModel nm)
			throws ClassNotFoundException, SQLException {

		String query = String.format("Select text "
				+ "from Notification WHERE username='%s'AND appointmentid=%d",
				nm.getCreator().getUsername(), nm.getAppointment().getId());
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		Date date = null;
		String text = null;
		while (rs.next()) {
			text = rs.getString(1);
		}

		NotificationModel utNm = new NotificationModel(text, nm.getAppointment(), nm.getCreator());
		rs.close();
		db.close();

		return utNm;
	}
	
	public void updateNotificationModel(NotificationModel nm) throws SQLException, ClassNotFoundException {
		String query = String.format(
				"UPDATE Notification SET text='%s' WHERE appointmentid=%d AND username='%s'",
				nm.getText(), nm.getAppointment().getId(), nm.getCreator().getUsername());
		UpdateDatabase(query);
	}
	
	public void deleteNotificationModel(NotificationModel nm)
			throws SQLException, ClassNotFoundException {
		String query = String.format(
				"DELETE from Notification WHERE username='%s' AND appointmentid=%d",
				nm.getCreator().getUsername(), nm.getAppointment().getId());
		UpdateDatabase(query);
	}
	
	public RoomModel createRoomModel(RoomModel rm) throws SQLException, ClassNotFoundException {
		String query = String
				.format("insert into Room "
						+ "(roomnumber, roomname, capacity, location) values (%d, '%s', %d, '%s')",
						rm.getRoomNumber(), rm.getRoomName(), rm.getCapacity(), rm.getLocation());
		UpdateDatabase(query);
		return rm;
	}
	
	public RoomModel getRoomModel(RoomModel rm)
			throws ClassNotFoundException, SQLException {
		String query = String.format("Select roomname, capacity, location "
				+ "from Room WHERE roomnumber=%d", rm.getRoomNumber());
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		String roomName = null;
		int capacity = 0;
		String location = null;
		while (rs.next()) {
			roomName = rs.getString(1);
			capacity = rs.getInt(2);
			location = rs.getString(3);
		}

		RoomModel utRm = new RoomModel(rm.getRoomNumber(), roomName, capacity, location);
		rs.close();
		db.close();

		return utRm;
	}
	
	public void updateRoomModel(RoomModel rm) throws SQLException, ClassNotFoundException {
		String query = String.format(
				"UPDATE Room SET text='%s' WHERE roomnumber=%d",
				rm.getRoomNumber());
		UpdateDatabase(query);
	}
	
	public void deleteRoomModel(RoomModel rm)
			throws SQLException, ClassNotFoundException {
		String query = String.format(
				"DELETE from Room WHERE roomnumber=%d",
				rm.getRoomNumber());
		UpdateDatabase(query);
	}
}
