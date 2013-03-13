package com.server.db;

import java.sql.BatchUpdateException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;


import com.model.AlarmModel;
import com.model.AppointmentModel;
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
						+ "(userName, password, email, name, surName, phoneNumber, isAdmin) values ('%s', '%s','%s','%s','%s','%s',%d)",
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
	
	/* IsSummonedTo */
	/**
	 * Gets all the usernames for the attending people of an appointment
	 * @param aid AppointmentId
	 * @return ArrayLisnt<String> attending peeps
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public ArrayList<String> getIsSummonedTo(int aid) throws ClassNotFoundException, SQLException{
		ArrayList<String> summoned = new ArrayList<>();
		
		String query=String.format("Select username " +
				"FROM IsSummonedTo WHERE appointmentid='%s'",aid);

		ResultSet rs=makeQuery(query);

		while(rs.next()){
			summoned.add(rs.getString(1));
		}
		
		return summoned;
	}
	
	public void createIsSummonedTo(ArrayList<UserModel> users, int aid) throws ClassNotFoundException {
		String query = "INSERT INTO IsSummonedTo "
				+ "(appointmentid, username) VALUES "
				+ "(?, ?)";
		
		PreparedStatement pst;
		try {
			db.initialize();
			pst = db.makeBatchUpdate(query);

		
		/* insert data */
	    for (int i = 0; i < users.size(); i++) {
	    	pst.setInt(1, aid);
	    	pst.setString(2, users.get(i).getUsername());
	    	pst.addBatch();
	    }
	    
	    // Execute the batch
	    int [] updateCounts = pst.executeBatch();
	    
	    db.close();
	    
		} catch (BatchUpdateException e) {
		    // Not all of the statements were successfully executed
		    int[] updateCounts = e.getUpdateCounts();

		    // Some databases will continue to execute after one fails.
		    // If so, updateCounts.length will equal the number of batched statements.
		    // If not, updateCounts.length will equal the number of successfully executed statements
		    processUpdateCounts(updateCounts);

		    // Either commit the successfully executed statements or rollback the entire batch
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		//UpdateDatabase(query);
	}
	
	public static void processUpdateCounts(int[] updateCounts) {
	    for (int i=0; i<updateCounts.length; i++) {
	    	if (updateCounts[i] >= 0) {
	    		// Successfully executed; the number represents number of affected rows
	    	} else if (updateCounts[i] == Statement.SUCCESS_NO_INFO) {
	    		// Successfully executed; number of affected rows not available
	    	} else if (updateCounts[i] == Statement.EXECUTE_FAILED) {
	    		// Failed to execute
	    		System.err.println("[Factory] ProcessUpdateCounts: Batch updating failed");
	    		System.err.println("[Factory] ProcessUpdateCounts: " + i);
	    	}
	    }
	}
	
	/* APPOINTMENT */
	//GET
	public AppointmentModel getAppointmentModel(int aid) throws SQLException, ClassNotFoundException{
		String query=String.format("Select startTime, EndTime, host, title, text, place, isDeleted, date " +
				"FROM Appointment WHERE id='%s'",aid);
		
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
		ArrayList<String> summoned = getIsSummonedTo(aid);
		if(summoned.size() != 0){
			members = new ArrayList<>();
			
			for (int i = 0; i < summoned.size(); i++) {
				members.add(getUserModel(summoned.get(i)));
			}
		}		
		
		rs.close();
		db.close();
		
		return new AppointmentModel(aid, startTime, endTime, host, title, text, place, date, members);
	}
	//UPDATE
	
	public void updateAppointmentModel(AppointmentModel am) throws ClassNotFoundException, SQLException{
		/*Update appointment*/
		String query = String.format(
				"UPDATE Appointment " +
				"SET startTime='%d', EndTime='%d', host='%d', title='%d', text='%d', place='%d', isDeleted='%d', date='%d' " +
				"WHERE id=%d",
				am.getStartTime(), am.getEndTime(), am.getHost(), am.getTitle(), am.getText(), am.getPlace(), am.isDeleted(), am.getDate(),
				am.getId());
		UpdateDatabase(query);
		
		/* Update IsSummonedTo. We need to send notification as well */
		//1. Who was invited
		
		ArrayList<String> summoned = getIsSummonedTo(am.getId());
		
		//2. Empty IsSummonedTo rows for that appointment
		query = String.format("DELETE FROM IsSummonedTo WHERE appointmentid='%s'",
				am.getId());
		UpdateDatabase(query);
		
		//3. Add all the attends to the appointment
		if(summoned.size() != 0){
			createIsSummonedTo(am.getMembers(), am.getId());
			
			//4. We need to notify the new guys that they are invited
			//TODO:
			
		}
		
		
		
	}
	//CREATE
	
	public void createAppointmentModel(AppointmentModel apModel){
		
	}
	//DELETE
}
