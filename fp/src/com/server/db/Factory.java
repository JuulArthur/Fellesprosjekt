package com.server.db;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.bind.annotation.XmlElement;

import com.model.AlarmModel;
import com.model.AppointmentModel;
import com.model.UserModel;
import com.model.CalendarModel;

public class Factory {

	DatabaseConnector db;

	public Factory() {
		db = new DatabaseConnector();
	}

	public void updateDatabase(String query) throws SQLException,
			ClassNotFoundException {
		db.initialize();
		db.makeUpdate(query);
		db.close();
	}
	
	public CalendarModel createCalendarModel(CalendarModel cm) throws SQLException, ClassNotFoundException {
		return createCalendarModel(cm.getId(), cm.getName(), cm.getAppointments(), cm.getOwner(), cm.getFollowers());
	}

	public CalendarModel createCalendarModel(int id, String name,
			ArrayList<AppointmentModel> appointments, UserModel owner,
			ArrayList<UserModel> followers) throws SQLException,
			ClassNotFoundException {
		PreparedStatement ps;
		if (appointments == null)
			appointments = new ArrayList<AppointmentModel>() {
			};
		if (followers == null)
			followers = new ArrayList<UserModel>() {
			};
		CalendarModel cm = new CalendarModel(id, name, appointments, owner,
				followers);
		String query = String.format("insert into Calendar "
				+ "(id, name) values ('%s', '%s')", id, name);
		updateDatabase(query);
		query = String.format("insert into Follows "
				+ "(isOwner, username, calendarid) values ('%s', '%s', '%s')",
				1, owner.getUsername(), id);
		updateDatabase(query);
		if (appointments.size() > 0) {
			db.initialize();
			ps = db.makeBatchUpdate(String.format("insert into BelongTo "
					+ "(appointmentid, calendarid) values (?, '%s')", name));
			for (int i = 0; i < appointments.size(); i++) {
				ps.setInt(1, appointments.get(i).getId());
				ps.addBatch();
			}
			System.out.println(ps.toString());
			ps.executeBatch();
			ps.close();
			db.close();
		}
		if (followers.size() > 0) {
			db.initialize();
			ps = db.makeBatchUpdate(String.format("insert into Follows"
					+ "(isOwner, username, calendarid) values ('%s', ?, '%s')",
					0, name));
			for (int i = 0; i < followers.size(); i++) {
				ps.setString(1, followers.get(i).getName());
				ps.addBatch();
			}
			System.out.println(ps.toString());
			ps.executeBatch();
			ps.close();
			db.close();
		}
		return cm;
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
		updateDatabase(query);
		return um;
	}

	public UserModel createUserModel(UserModel um) throws SQLException,
			ClassNotFoundException {
		String query = String
				.format("insert into User "
						+ "(userName, password, email, name, surName, phoneNumber, isAdmin) values ('%s', '%s','%s','%s','%s','%s',%d)",
						um.getUsername(), um.getPassword(), um.getEmail(),
						um.getEmail(), um.getSurname(), um.getPhoneNumber(),
						um.isAdmin());
		updateDatabase(query);
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

	public UserModel getUserModel(UserModel um) throws ClassNotFoundException,
			SQLException {

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

		UserModel utUm = new UserModel(um.getUsername(), password, email, name,
				surname, phoneNumber, isAdmin);
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
		updateDatabase(query);
	}

	public void updateUserModel(UserModel um) throws SQLException,
			ClassNotFoundException {
		String query = String.format(
				"UPDATE User SET email='%s',name='%s',surname='%s',phonenumber='%s'"
						+ ",password='%s',isAdmin=%d WHERE username='%s'",
				um.getEmail(), um.getName(), um.getSurname(),
				um.getPhoneNumber(), um.getPassword(), um.isAdmin(),
				um.getUsername());
		updateDatabase(query);
	}

	public void deleteUserModel(String username) throws SQLException,
			ClassNotFoundException {
		String query = String.format("DELETE from User WHERE username='%s'",
				username);
		updateDatabase(query);
	}

	public void deleteUserModel(UserModel um) throws SQLException,
			ClassNotFoundException {
		String query = String.format("DELETE from User WHERE username='%s'",
				um.getUsername());
		updateDatabase(query);
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

	public boolean checkPassword(UserModel um) throws SQLException,
			ClassNotFoundException {
		String returnPassword = "";
		String query = String.format(
				"SELECT password FROM User WHERE username='%s'",
				um.getUsername());
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		rs.beforeFirst();
		if (rs.next()) {
			returnPassword = rs.getString(1);
		}
		rs.close();
		db.close();

		return returnPassword.equals(um.getPassword())
				&& !returnPassword.equals("");
	}

	public AlarmModel createAlarmModel(Date date, String text,
			AppointmentModel ap, UserModel user) throws SQLException,
			ClassNotFoundException {
		AlarmModel am = new AlarmModel(date, text, ap, user);
		String query = String.format("insert into Alarm "
				+ "(date, text, appointmentid, username) values "
				+ "('%s', '%s',%d,'%s')", date, text, ap.getId(),
				user.getUsername());
		updateDatabase(query);
		return am;
	}

	public AlarmModel createAlarmModel(AlarmModel am) throws SQLException,
			ClassNotFoundException {
		String query = String.format("insert into Alarm "
				+ "(date, text, appointmentid, username) values "
				+ "('%s', '%s',%d,'%s')", am.getDate(), am.getText(), am
				.getAppointment().getId(), am.getCreator().getUsername());
		updateDatabase(query);
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
				+ "from Alarm WHERE username='%s'AND appointmentid=%d", am
				.getCreator().getUsername(), am.getAppointment().getId());
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		Date date = null;
		String text = null;
		while (rs.next()) {
			date = rs.getDate(1);
			text = rs.getString(2);
		}

		AlarmModel utAm = new AlarmModel(date, text, am.getAppointment(),
				am.getCreator());
		rs.close();
		db.close();

		return utAm;
	}

	public void updateAlarmModel(Date date, String text, AppointmentModel ap,
			UserModel user) throws SQLException, ClassNotFoundException {
		String query = String
				.format("UPDATE Alarm SET date='%s',text='%s' WHERE appointmentid=%d AND username='%s'",
						date, text, ap.getId());
		updateDatabase(query);

	}

	public void updateAlarmModel(AlarmModel am) throws SQLException,
			ClassNotFoundException {
		System.out.println(am.getDate());
		String query = String
				.format("UPDATE Alarm SET date='%s',text='%s' WHERE appointmentid=%d AND username='%s'",
						am.getDate(), am.getText(),
						am.getAppointment().getId(), am.getCreator()
								.getUsername());
		updateDatabase(query);

	}

	public void deleteAlarmModel(AppointmentModel ap, UserModel user)
			throws SQLException, ClassNotFoundException {
		String query = String.format(
				"DELETE from Alarm WHERE username='%s' AND appointmentid=%d",
				user.getUsername(), ap.getId());
		updateDatabase(query);
	}

	public void deleteAlarmModel(AlarmModel am) throws SQLException,
			ClassNotFoundException {
		String query = String.format(
				"DELETE from Alarm WHERE username='%s' AND appointmentid=%d",
				am.getCreator().getUsername(), am.getAppointment().getId());
		updateDatabase(query);
	}

	/* APPOINTMENT */
	// GET
	public AppointmentModel getAppointmentModel(int pid) throws SQLException,
			ClassNotFoundException {

		String query = String.format(
				"Select startTime, EndTime, host, title, text, place, isDeleted, date"
						+ "from Appointment WHERE id='%s'", pid);

		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);

		int startTime = 0;
		int endTime = 0;
		UserModel host = null;
		String title = null;
		String text = null;
		String place = null;
		boolean isDeleted;
		Date date = null;
		ArrayList<UserModel> members = null;

		while (rs.next()) {
			startTime = rs.getInt(2);
			endTime = rs.getInt(3);
			host = getUserModel(rs.getString(4));
			title = rs.getString(5);
			text = rs.getString(6);
			place = rs.getString(7);
			isDeleted = rs.getBoolean(8);
			date = rs.getDate(9);
		}

		/* Get members */
		// members = new ArrayList<>();

		query = String.format("Select username"
				+ "from IsSummonedTo WHERE appointmentid='%s'", pid);
		rs = db.makeSingleQuery(query);

		while (rs.next()) {
			members.add(getUserModel(rs.getString(1)));
		}

		if (members.size() == 0)
			members = null;

		rs.close();
		db.close();

		return new AppointmentModel(pid, startTime, endTime, host, title, text,
				place, date, members);
	}

	// UPDATE
	// CREATE

	public void createAppointmentModel(AppointmentModel apModel) {

	}
	// DELETE
}
