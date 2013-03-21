package com.server.db;

import java.sql.BatchUpdateException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.model.AlarmModel;
import com.model.AppointmentModel;
import com.model.CalendarModel;
import com.model.GroupModel;
import com.model.NotificationModel;
import com.model.RoomModel;
import com.model.UserModel;

public class Factory {

	DatabaseConnector db;

	public Factory(String databaseProperties) {
		db = new DatabaseConnector(databaseProperties);
	}

	public void updateDatabase(String query) throws SQLException,
			ClassNotFoundException {
		db.initialize();
		db.makeUpdate(query);
		db.close();
	}

	public CalendarModel createCalendarModel(CalendarModel cm)
			throws SQLException, ClassNotFoundException {
		return createCalendarModel(cm.getId(), cm.getName(),
				cm.getAppointments(), cm.getOwner());
	}

	public CalendarModel createCalendarModel(long id, String name,
			ArrayList<AppointmentModel> appointments, String owner)
			throws SQLException, ClassNotFoundException {
		PreparedStatement ps;
		if (appointments == null)
			appointments = new ArrayList<AppointmentModel>();
		CalendarModel cm = new CalendarModel(id, name, appointments, owner);
		String query = String.format("insert into Calendar "
				+ "(id, name) values ('%s', '%s')", id, name);
		updateDatabase(query);
		query = String.format("insert into Follows "
				+ "(isOwner, username, calendarid) values ('%s', '%s', '%s')",
				1, owner, id);
		updateDatabase(query);
		if (appointments.size() > 0) {
			db.initialize();
			ps = db.makeBatchUpdate(String.format("insert into BelongTo "
					+ "(appointmentid, calendarid) values (?, '%s')", name));
			for (int i = 0; i < appointments.size(); i++) {
				ps.setLong(1, appointments.get(i).getId());
				ps.addBatch();
			}
			System.out.println(ps.toString());
			ps.executeBatch();
			ps.close();
			db.close();
		}
		return cm;
	}

	public void updateCalendarModel(CalendarModel cm) throws SQLException,
			ClassNotFoundException {
		updateCalendarModel(cm.getId(), cm.getName(), cm.getAppointments(),
				cm.getOwner());
	}

	public void updateCalendarModel(long id, String name,
			ArrayList<AppointmentModel> appointments, String owner)
			throws SQLException, ClassNotFoundException {
		PreparedStatement ps;
		String query = String.format(
				"update Calendar set name='%s' where id = '%d'", name, id);
		updateDatabase(query);

		/*
		 * query = String .format(
		 * "delete from Follows where isOwner = 1 and calendarid = '%d' and username <> '%s'"
		 * , id, owner); updateDatabase(query);
		 * 
		 * query = String .format(
		 * "insert into Follows (isOwner, username, calendarid) values ('%s', '%s', '%s')"
		 * , 1, owner, id); updateDatabase(query);
		 */

		if (appointments != null && appointments.size() > 0) {
			query = String.format(
					"delete from BelongTo where calendarid = '%d'", id);
			updateDatabase(query);
			db.initialize();
			ps = db.makeBatchUpdate(String.format("insert into BelongTo "
					+ "(appointmentid, calendarid) values (?, '%s')", name));
			for (int i = 0; i < appointments.size(); i++) {
				ps.setLong(1, appointments.get(i).getId());
				ps.addBatch();
			}
			System.out.println(ps.toString());
			ps.executeBatch();
			ps.close();
			db.close();
		}

	}

	public CalendarModel getCalendarModel(CalendarModel cm)
			throws SQLException, ClassNotFoundException {
		return getCalendarModel(cm.getId());
	}

	public CalendarModel getCalendarModel(long idIn) throws SQLException,
			ClassNotFoundException {
		db.initialize();
		String query = String.format(
				"Select name from Calendar WHERE id = '%s'", idIn);
		ResultSet rs = db.makeSingleQuery(query);
		// long id = -1L;
		String name = null;
		String owner = null;
		ArrayList<AppointmentModel> appointments = new ArrayList<AppointmentModel>();

		// Fetch Calendar name
		while (rs.next()) {
			// id = rs.getLong(1);
			name = rs.getString(1);
		}

		// Fetch owner thePwner
		query = String
				.format("select username from Follows where isOwner=1 and calendarid='%s'",
						idIn);
		rs = db.makeSingleQuery(query);
		while (rs.next()) {
			owner = rs.getString(1);
		}

		// FEtch all appointments
		query = String.format(
				"select appointmentid from BelongTo where calendarid = '%d'",
				idIn);
		rs = db.makeSingleQuery(query);
		while (rs.next()) {
			appointments.add(getAppointmentModel(rs.getLong(1), owner)); //TODO IF IS NULL LOOK AT THIS!!
		}

		CalendarModel cm = new CalendarModel(idIn, name, appointments, owner);
		rs.close();
		db.close();

		return cm;

	}

	public void deleteCalendarModel(CalendarModel cm) throws SQLException,
			ClassNotFoundException {
		deleteCalendarModel(cm.getId());
	}

	public void deleteCalendarModel(long id) throws SQLException,
			ClassNotFoundException {
		String query = String
				.format("delete from Calendar where id = '%d'", id);
		updateDatabase(query);
	}

	public ResultSet makeQuery(String query) throws SQLException,
			ClassNotFoundException {
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
		updateDatabase(query);
		return um;
	}

	public UserModel createUserModel(UserModel um) throws SQLException,
			ClassNotFoundException {
		return createUserModel(um.getUsername(), um.getPassword(),
				um.getEmail(), um.getName(), um.getSurname(),
				um.getPhoneNumber(), (um.isAdmin() ? 1 : 0));
	}

	public UserModel getUserModel(String username)
			throws ClassNotFoundException, SQLException {

		String query = String
				.format("Select email,name,surname,phonenumber,password,isadmin from User where username='%s'",
						username);
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		String password = null;
		String email = null;
		String name = null;
		String surname = null;
		String phoneNumber = null;
		int isAdmin = 0;

		// Fetch dat data
		while (rs.next()) {
			email = rs.getString(1);
			name = rs.getString(2);
			surname = rs.getString(3);
			phoneNumber = rs.getString(4);
			password = rs.getString(5);
			isAdmin = rs.getInt(6);
		}

		// Create the model
		UserModel um = new UserModel(username, password, email, name, surname,
				phoneNumber, isAdmin);

		// Check if the user exists
		if (um.getPassword().equals(null))
			um = null;
		else {

			// Get the calendars
			query = String
					.format("Select calendarid, isOwner from Follows Where username='%s'",
							username);
			ResultSet crs = db.makeSingleQuery(query);

			long calendarId = 0;
			boolean isOwner = false;
			while (crs.next()) {
				calendarId = crs.getLong(1);
				isOwner = crs.getBoolean(2);

				CalendarModel tempCal = getCalendarModel(calendarId);

				if (isOwner) {
					um.addCalendar(tempCal);
				} else {
					um.addSubscribedCalendars(tempCal);
				}

			}
			crs.close();

			// Get the IsSummonedTo appointments

			ArrayList<AppointmentModel> summoned = new ArrayList<AppointmentModel>();

			query = String.format("SELECT appointmentid, isAccepted, isSeen "
					+ "FROM IsSummonedTo WHERE username='%s'", username);

			crs = makeQuery(query);

			while (rs.next()) {
				AppointmentModel cake = getAppointmentModel(rs.getLong(1));
				cake.setAccepted(rs.getBoolean(2));
				cake.setSeen(rs.getBoolean(3));
				summoned.add(cake);
			}

			crs.close();

			CalendarModel cm = new CalendarModel();
			cm.setOwner(username);
			cm.setName("Summoned To");
			cm.setAppointments(summoned);

			um.setSummonedTo(cm);
		}
		rs.close();
		db.close();

		return um;

	}

	public UserModel getUserModel(UserModel um) throws ClassNotFoundException,
			SQLException {
		return getUserModel(um.getUsername());
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
		updateUserModel(um.getUsername(), um.getPassword(), um.getEmail(),
				um.getName(), um.getSurname(), um.getPhoneNumber(),
				(um.isAdmin() ? 1 : 0));
	}

	public void deleteUserModel(String username) throws SQLException,
			ClassNotFoundException {
		String query = String.format("DELETE from User WHERE username='%s'",
				username);
		updateDatabase(query);
	}

	public void deleteUserModel(UserModel um) throws SQLException,
			ClassNotFoundException {
		deleteUserModel(um.getUsername());
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
		return checkPassword(um.getUsername(), um.getPassword());
	}

	public AlarmModel createAlarmModel(Date date, String text, int time,
			AppointmentModel ap, UserModel user) throws SQLException,
			ClassNotFoundException {
		AlarmModel am = new AlarmModel(date, text, time, ap, user);
		String hours = Integer.toString(time % 60);
		String minutes = Integer.toString(time - Integer.parseInt(hours));
		String textDate = date.toString();
		textDate += " " + hours + ":" + minutes + ":00";
		String query = String.format("insert into Alarm "
				+ "(date, text, time, appointmentid, username) values "
				+ "('%s', '%s',%d ,%d,'%s')", date, text, time, ap.getId(),
				user.getUsername());
		updateDatabase(query);
		return am;
	}

	public AlarmModel createAlarmModel(AlarmModel am) throws SQLException,
			ClassNotFoundException {
		return createAlarmModel(am.getDate(), am.getText(), am.getTime(),
				am.getAppointment(), am.getCreator());
	}

	public AlarmModel getAlarmModel(String user, long aid)
			throws ClassNotFoundException, SQLException {

		String query = String.format("Select date, text "
				+ "from Alarm WHERE username='%s'AND appointmentid=%d", user,
				aid);
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		Date date = null;
		String text = null;
		int time = 0;
		while (rs.next()) {
			date = rs.getDate(1);
			text = rs.getString(2);
			time = rs.getInt(3);
		}

		AlarmModel am;
		if (date == null)
			am = null;
		else
			am = new AlarmModel(date, text, time, null, null);
		rs.close();
		db.close();

		return am;
	}

	public AlarmModel getAlarmModel(String user, long aid, AppointmentModel ap,
			UserModel creator) throws ClassNotFoundException, SQLException {

		String query = String.format("Select date, text "
				+ "from Alarm WHERE username='%s'AND appointmentid=%d", user,
				aid);
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		Date date = null;
		String text = null;
		int time = 0;
		while (rs.next()) {
			date = rs.getDate(1);
			text = rs.getString(2);
			time = rs.getInt(3);
		}

		AlarmModel am;
		if (text == null || date == null)
			am = null;
		else
			am = new AlarmModel(date, text, time, null, null);

		rs.close();
		db.close();

		return am;
	}

	public AlarmModel getAlarmModel(AlarmModel am)
			throws ClassNotFoundException, SQLException {
		return getAlarmModel(am.getCreator().getUsername(), am.getAppointment()
				.getId(), am.getAppointment(), am.getCreator());
	}

	public void updateAlarmModel(Date date, String text, int time,
			AppointmentModel ap, UserModel user) throws SQLException,
			ClassNotFoundException {
		updateAlarmModel(new AlarmModel(date, text, time, ap, user));
	}

	public void updateAlarmModel(AlarmModel am) throws SQLException,
			ClassNotFoundException {
		System.out.println(am.getDate());
		String query = String
				.format("UPDATE Alarm SET date='%s',text='%s',time=%d WHERE appointmentid=%d AND username='%s'",
						am.getDate(), am.getText(), am.getTime(), am
								.getAppointment().getId(), am.getCreator()
								.getUsername());
		updateDatabase(query);
	}

	public void deleteAlarmModel(AlarmModel am) throws SQLException,
			ClassNotFoundException {
		deleteAlarmModel(am.getAppointment().getId(), am.getCreator()
				.getUsername());
	}

	public void deleteAlarmModel(long id, String userName) throws SQLException,
			ClassNotFoundException {
		String query = String.format(
				"DELETE from Alarm WHERE username='%s' AND appointmentid=%d",
				userName, id);
		updateDatabase(query);
	}

	/**
	 * fetches all rooms over a certan capacity
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	private ArrayList<Object> getRoomsFromCapacity(int capacity)
			throws SQLException, ClassNotFoundException {
		ArrayList<Object> rooms = new ArrayList<Object>();

		String query = String.format(
				"select * from Room where capacity >= '%d'", capacity);

		ResultSet rs = makeQuery(query);

		while (rs.next()) {
			rooms.add(getRoomModel(rs.getLong(1)));
		}
		rs.close();

		if (rooms.size() > 0)
			return rooms;
		else
			return null;
	}

	/**
	 * suppose to check weather the rooms fed to it are available or not
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private ArrayList<Object> filterOutTakenRooms(ArrayList<Object> rooms, int startTime, int endTime)
			throws SQLException, ClassNotFoundException {
		
		//Don't even ask.. should work
		
		ArrayList<Object> takenRooms = new ArrayList<Object>();
		String query = String
				.format("SELECT Reserved.RoomNumber From Reserved INNER JOIN (select * from Appointment where startTime >= '%d' and endTime <= '%d') AS app ON app.id = Reserved.appointmentid",
						startTime, endTime);
		System.out.println(query);
		
		//"SELECT Reserved.roomnumber From Reserved INNER JOIN \"Appointment where startTime >= '%d' and endTime <= '%d'\" ON Appointment.id = Reserved.appointmentid"
		ResultSet rs = makeQuery(query);

		while (rs.next()) {
			takenRooms.add(getRoomModel(rs.getLong(1)));
		}
		rs.close();

		rooms.removeAll(takenRooms);
		
		return rooms;
	}

	/**
	 * used to get all available rooms given a capacity
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<Object> getAvailableRooms(int capacity, int startTime,	int endTime) throws SQLException, ClassNotFoundException {
		return filterOutTakenRooms(getRoomsFromCapacity(capacity), startTime,endTime);
	}

	/**
	 * Gets all the usernames for the attending people of an appointment
	 * 
	 * @param aid
	 *            AppointmentId
	 * @return ArrayLisnt<String> attending peeps
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<Object> getIsSummonedTo(long aid, boolean accepted)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> summoned = new ArrayList<Object>();

		String query = String.format("Select username "
				+ "FROM IsSummonedTo " +
				"WHERE appointmentid='%s' AND isAccepted='%b'", aid, accepted);

		ResultSet rs = makeQuery(query);

		while (rs.next()) {
			summoned.add((Object)rs.getString(1));
		}

		return summoned;
	}
	
	public void updateIscomming(UserModel butthurt, AppointmentModel ap) throws SQLException, ClassNotFoundException{
		
		String query = String
				.format("UPDATE ISSUMMONED SET isAcepted=%b,WHERE appointmentid=%d AND username='%s'",
						true, ap.getId() , butthurt.getUsername());
		updateDatabase(query);
		
	}
	
	
	public void updateIsNotComming(UserModel butthurt, AppointmentModel ap) throws SQLException, ClassNotFoundException{
			String query = String
					.format("UPDATE ISSUMMONED SET isAcepted=%b,WHERE appointmentid=%d AND username='%s'",
							false, ap.getId() , butthurt.getUsername());
			updateDatabase(query);
			
	}
	
	
	public void updateIsSummonedTo(ArrayList<UserModel> users, long l)
			throws ClassNotFoundException, SQLException {

		deleteIsSummonedTo(l);
		createIsSummonedTo(users, l);
	}

	public void createIsSummonedTo(ArrayList<UserModel> users, long l)
			throws ClassNotFoundException {
		String query = "INSERT INTO IsSummonedTo "
				+ "(appointmentid, username) VALUES " + "(?, ?)";

		PreparedStatement pst;
		try {
			db.initialize();
			pst = db.makeBatchUpdate(query);

			/* insert data */
			for (int i = 0; i < users.size(); i++) {
				pst.setLong(1, l);
				pst.setString(2, users.get(i).getUsername());
				pst.addBatch();
			}

			// Execute the batch
			int[] updateCounts = pst.executeBatch();

			db.close();

		} catch (BatchUpdateException e) {
			// Not all of the statements were successfully executed
			int[] updateCounts = e.getUpdateCounts();

			// Some databases will continue to execute after one fails.
			// If so, updateCounts.length will equal the number of batched
			// statements.
			// If not, updateCounts.length will equal the number of successfully
			// executed statements
			processUpdateCounts(updateCounts);

			// Either commit the successfully executed statements or rollback
			// the entire batch
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// UpdateDatabase(query);
	}

	/**
	 * Checks the errors from a batch update
	 * 
	 * @param updateCounts
	 */
	public static void processUpdateCounts(int[] updateCounts) {
		for (int i = 0; i < updateCounts.length; i++) {
			if (updateCounts[i] >= 0) {
				// Successfully executed; the number represents number of
				// affected rows
			} else if (updateCounts[i] == Statement.SUCCESS_NO_INFO) {
				// Successfully executed; number of affected rows not available
			} else if (updateCounts[i] == Statement.EXECUTE_FAILED) {
				// Failed to execute
				System.err
						.println("[Factory] ProcessUpdateCounts: Batch updating failed");
				System.err.println("[Factory] ProcessUpdateCounts: " + i);
			}
		}
	}

	public void deleteIsSummonedToPeople(ArrayList<String> users, long aid)
			throws ClassNotFoundException, SQLException {
		String query = "DELETE FROM IsSummonedTo "
				+ "WHERE appointmentid='?' AND username='?'";

		PreparedStatement pst;
		try {
			db.initialize();
			pst = db.makeBatchUpdate(query);

			/* insert data */
			for (int i = 0; i < users.size(); i++) {
				pst.setLong(1, aid);
				pst.setString(2, users.get(i));
				pst.addBatch();
			}

			// Execute the batch
			int[] updateCounts = pst.executeBatch();

			db.close();

		} catch (BatchUpdateException e) {
			// Not all of the statements were successfully executed
			int[] updateCounts = e.getUpdateCounts();

			// Some databases will continue to execute after one fails.
			// If so, updateCounts.length will equal the number of batched
			// statements.
			// If not, updateCounts.length will equal the number of successfully
			// executed statements
			processUpdateCounts(updateCounts);

			// Either commit the successfully executed statements or rollback
			// the entire batch
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteIsSummonedTo(long l) throws ClassNotFoundException,
			SQLException {
		String query = String.format(
				"DELETE FROM IsSummonedTo WHERE appointmentid='%s'", l);
		updateDatabase(query);
	}

	/* APPOINTMENT */
	// GET
	public AppointmentModel getAppointmentModel(AppointmentModel am)
			throws SQLException, ClassNotFoundException {
		return getAppointmentModel(am.getId());
	}

	public AppointmentModel getAppointmentModel(long l) throws SQLException,
			ClassNotFoundException {
		String query = String.format(
				"Select startTime, EndTime, host, title, text, place, isDeleted, date "
						+ "FROM Appointment WHERE id='%s'", l);

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

		while (rs.next()) {
			startTime = rs.getInt(1);
			endTime = rs.getInt(2);
			host = getUserModel(rs.getString(3));
			title = rs.getString(4);
			text = rs.getString(5);
			place = rs.getString(6);
			isDeleted = rs.getBoolean(7);
			date = rs.getDate(8);
		}

		/* Get members */
		// members = new ArrayList<>();

		query = String.format("Select username"
				+ "from IsSummonedTo WHERE appointmentid='%s'", l);
		rs = db.makeSingleQuery(query);

		while (rs.next()) {
			members.add(getUserModel(rs.getString(1)));
		}

		if (members.size() == 0)
			members = null;

		rs.close();
		db.close();

		if (title != null)
			return new AppointmentModel(l, startTime, endTime, host, title,
					text, place, date, members);
		else
			return null;
	}
	
	

	public AppointmentModel getAppointmentModel(long l, String owner) throws SQLException,
			ClassNotFoundException {
		String query = String.format(
				"Select startTime, EndTime, host, title, text, place, isDeleted, date "
						+ "FROM Appointment WHERE id='%s'", l);

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

		while (rs.next()) {
			startTime = rs.getInt(1);
			endTime = rs.getInt(2);
			host = new UserModel(owner);
			title = rs.getString(4);
			text = rs.getString(5);
			place = rs.getString(6);
			isDeleted = rs.getBoolean(7);
			date = rs.getDate(8);
		}

		/* Get members */
		// members = new ArrayList<>();

		query = String.format("Select username "
				+ "from IsSummonedTo WHERE appointmentid='%s'", l);
		rs = db.makeSingleQuery(query);

		while (rs.next()) {
			members.add(getUserModel(rs.getString(1)));
		}

		if(members != null)
			if (members.size() == 0)
				members = null;

		rs.close();
		db.close();

		if (title != null)
			return new AppointmentModel(l, startTime, endTime, host, title,
					text, place, date, members);
		else
			return null;
	}
	
	

	// UPDATE
	// CREATE

	public void updateAppointmentModel(AppointmentModel am)
			throws ClassNotFoundException, SQLException {
		/* Update appointment */
		String query = String
				.format("UPDATE Appointment "
						+ "SET startTime='%s', EndTime='%s', host='%s', title='%s', text='%s', place='%s', isDeleted=%b, date='%s' "
						+ "WHERE id=%d", am.getStartTime(), am.getEndTime(), am
						.getHost().getUsername(), am.getTitle(), am.getText(),
						am.getPlace(), am.isDeleted(), am.getDate(), am.getId());
		updateDatabase(query);

		/* Update IsSummonedTo. We need to send notification as well */
		// 1. Who was invited

		// ArrayList<String> summoned = getIsSummonedTo(am.getId());

		// 2. Empty IsSummonedTo rows for that appointment
		deleteIsSummonedTo(am.getId());
		if (am.getMembers() != null)
			createIsSummonedTo(am.getMembers(), am.getId());

	}

	// CREATE

	public AppointmentModel createAppointmentModel(AppointmentModel am)
			throws ClassNotFoundException, SQLException {
		String query = String
				.format("INSERT INTO Appointment "
						+ "(id, startTime, EndTime, host, title, text, place, isDeleted, date) "
						+ "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', %b, '%s')",
						am.getId(), am.getStartTime(), am.getEndTime(), am
								.getHost().getUsername(), am.getTitle(), am
								.getText(), am.getPlace(), am.isDeleted(), am
								.getDate());

		updateDatabase(query);

		// Add all the attends to the appointment
		if (am.getMembers() != null && am.getMembers().size() != 0) {
			createIsSummonedTo(am.getMembers(), am.getId());
		}

		return am;
	}

	public void deleteAppointmentModel(AppointmentModel am)
			throws SQLException, ClassNotFoundException {
		deleteAppointmentModel(am.getId());
	}

	// DELETE
	public void deleteAppointmentModel(long l) throws SQLException,
			ClassNotFoundException {
		String query = String
				.format("DELETE FROM Appointment WHERE id='%s'", l);
		updateDatabase(query);
	}

	@Deprecated
	public void createNotificationModel(String text, int aid, String username)
			throws SQLException, ClassNotFoundException {
		String query = String.format("insert into Notification "
				+ "(text, appointmentid, username) values ('%s', %d, '%s')",
				text, aid, username);
		updateDatabase(query);
	}

	public NotificationModel createNotificationModel(NotificationModel nm)
			throws SQLException, ClassNotFoundException {
		String query = String.format("insert into Notification "
				+ "(text, appointmentid, username) values ('%s', %d, '%s')",
		// TODO nm.getCreator? brukeren som skal inviteres?
				nm.getText(), nm.getAppointment().getId(), nm.getCreator()
						.getUsername());
		updateDatabase(query);
		return nm;
	}

	@Deprecated
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

		NotificationModel utNm = new NotificationModel(text,
				nm.getAppointment(), nm.getCreator());
		rs.close();
		db.close();

		return utNm;
	}

	public NotificationModel getNotificationModel(String userName, int id)
			throws ClassNotFoundException, SQLException {

		String query = String.format("Select text "
				+ "from Notification WHERE username='%s'AND appointmentid=%d",
				userName, id);
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		Date date = null;
		String text = null;
		while (rs.next()) {
			text = rs.getString(1);
		}

		NotificationModel utNm = new NotificationModel(text, null, null);
		rs.close();
		db.close();

		return utNm;
	}

	public void updateNotificationModel(NotificationModel nm)
			throws SQLException, ClassNotFoundException {
		String query = String
				.format("UPDATE Notification SET text='%s' WHERE appointmentid=%d AND username='%s'",
						nm.getText(), nm.getAppointment().getId(), nm
								.getCreator().getUsername());
		updateDatabase(query);
	}

	@Deprecated
	public void deleteNotificationModel(NotificationModel nm)
			throws SQLException, ClassNotFoundException {
		String query = String
				.format("DELETE from Notification WHERE username='%s' AND appointmentid=%d",
						nm.getCreator().getUsername(), nm.getAppointment()
								.getId());
		updateDatabase(query);
	}

	public void deleteNotificationModel(String userName, int id)
			throws SQLException, ClassNotFoundException {
		String query = String
				.format("DELETE from Notification WHERE username='%s' AND appointmentid=%d",
						userName, id);
		updateDatabase(query);
	}

	public RoomModel createRoomModel(RoomModel rm) throws SQLException,
			ClassNotFoundException {
		String query = String
				.format("insert into Room "
						+ "(roomnumber, roomname, capacity, location) values (%d, '%s', %d, '%s')",
						rm.getRoomNumber(), rm.getRoomName(), rm.getCapacity(),
						rm.getLocation());
		updateDatabase(query);
		return rm;
	}

	@Deprecated
	public void createRoomModel(int roomNumber, String roomName, int capacity,
			String location) throws SQLException, ClassNotFoundException {
		String query = String
				.format("insert into Room "
						+ "(roomnumber, roomname, capacity, location) values (%d, '%s', %d, '%s')",
						roomNumber, roomName, capacity, location);
		updateDatabase(query);
	}

	public RoomModel getRoomModel(RoomModel rm) throws ClassNotFoundException,
			SQLException {
		return getRoomModel(rm.getRoomNumber());
	}

	public RoomModel getRoomModel(long roomNumber)
			throws ClassNotFoundException, SQLException {
		String query = String.format("Select roomname, capacity, location "
				+ "from Room WHERE roomnumber=%d", roomNumber);
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

		RoomModel utRm = new RoomModel(roomNumber, roomName, capacity, location);

		if (utRm.getRoomName() == null)
			utRm = null;

		rs.close();
		db.close();

		return utRm;
	}

	public void updateRoomModel(RoomModel rm) throws SQLException,
			ClassNotFoundException {
		String query = String
				.format("UPDATE Room SET roomName='%s', capacity=%d, location='%s' WHERE roomnumber=%d",
						rm.getRoomName(), rm.getCapacity(), rm.getLocation(),
						rm.getRoomNumber());
		updateDatabase(query);
	}

	public void deleteRoomModel(RoomModel rm) throws SQLException,
			ClassNotFoundException {
		deleteRoomModel(rm.getRoomNumber());
	}

	public void deleteRoomModel(long roomNumber) throws SQLException,
			ClassNotFoundException {
		String query = String.format("DELETE from Room WHERE roomnumber=%d",
				roomNumber);
		updateDatabase(query);
	}

	public GroupModel createGroupModel(GroupModel gm) throws SQLException,
			ClassNotFoundException {
		String query = String
				.format("insert into groupp " + "(id, name) values "
						+ "(%d, '%s')", gm.getId(), gm.getName());
		updateDatabase(query);
		addMemberOf(gm);
		return gm;
	}

	public void deleteGroupModel(int id) throws SQLException,
			ClassNotFoundException {
		String query = String.format("DELETE from groupp WHERE id=%d", id);
		updateDatabase(query);
	}

	public void UpdateGroupModel(GroupModel gm) throws SQLException,
			ClassNotFoundException {
		String query = String.format("UPDATE Groupp "
				+ "SET name='%s' WHERE id= %d ", gm.getName(), gm.getId());
		updateDatabase(query);
		addMemberOf(gm);

	}

	public void addMemberOf(GroupModel gm) throws SQLException,
			ClassNotFoundException {
		ArrayList<UserModel> memberList = gm.getMembers();
		String query = String.format("DELETE from MemberOf WHERE groupid=%d",
				gm.getId());
		updateDatabase(query);
		System.out.println("deleted");

		for (int i = 0; i < memberList.size(); i++) {
			String memberQuery = String.format("insert into MemberOf "
					+ "(username, groupid) values " + "('%s', %d)", memberList
					.get(i).getUsername(), gm.getId());
			updateDatabase(memberQuery);

		}

	}

	public GroupModel getGroupModel(int id) throws ClassNotFoundException,
			SQLException {

		String query = String.format("Select id, name from Groupp where id=%d",
				id);
		String MEMBERquery = String.format(
				"Select username from Memberof where groupid=%d", id);
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		ResultSet nrs = db.makeSingleQuery(MEMBERquery);
		int newId = 0;
		String name = null;
		ArrayList<UserModel> members = new ArrayList<UserModel>();

		while (rs.next()) {
			newId = rs.getInt(1);
			name = rs.getString(2);
		}
		String username;
		while (nrs.next()) {
			username = nrs.getString(1);
			members.add(getUserModel(username));
		}

		GroupModel gm = new GroupModel(newId, name, members);
		rs.close();
		db.close();

		return gm;

	}

	public ArrayList<Object> getEveryUser(String user) throws ClassNotFoundException, SQLException {
		ArrayList<Object> everyUser = new ArrayList<Object>();
		
		String query = String.format("Select username from User where username <> '%s'", user);

		ResultSet rs = makeQuery(query);
		while (rs.next()) {
			everyUser.add(getUserModel(rs.getString(1)));
		}
		return everyUser;

	}
	
	public ArrayList<Object> getEveryGroup() throws ClassNotFoundException, SQLException {
		ArrayList<Object> everyGroup = new ArrayList<Object>();
		
		String query = String.format("Select name from Groupp");

		ResultSet rs = makeQuery(query);
		while (rs.next()) {
			everyGroup.add(rs.getString(1));
		}
		return everyGroup;

	}

	public void deleteBelongTo(long appointmentid, long calId)
			throws ClassNotFoundException, SQLException {
		String query = String
				.format("DELETE from BelongTo WHERE appointmentid='%s' AND calendarid=%d",
						appointmentid, calId);
		updateDatabase(query);
	}

	public void createBelongTo(long appointmentid, long calId)
			throws ClassNotFoundException, SQLException {
		String query = String.format("insert into BelongTo "
				+ "(appointmentid, calendarid) VALUES " + "(%d, '%s')",
				appointmentid, calId);
		updateDatabase(query);
	}

	public void updateBelongTo(long appointmentid, long newCalID, long oldCalId)
			throws ClassNotFoundException, SQLException {
		deleteBelongTo(appointmentid, oldCalId);
		createBelongTo(appointmentid, newCalID);
	}

}
