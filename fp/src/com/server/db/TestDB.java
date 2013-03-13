package com.server.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.model.AlarmModel;
import com.model.AppointmentModel;
import com.model.CalendarModel;
import com.model.UserModel;

public class TestDB {

	public void test() throws SQLException, ClassNotFoundException {
		DatabaseConnector db = new DatabaseConnector();

		String insert = "insert into Groupp (id, groupName) values (2,'test');";
		db.initialize();
		db.makeUpdate(insert);
		db.close();
	}

	public void readTest() throws SQLException, ClassNotFoundException {
		DatabaseConnector db = new DatabaseConnector();

		String sql = "select username,password from User WHERE username='perok'";
		db.initialize();
		ResultSet rs = db.makeSingleQuery(sql);
		rs.beforeFirst();
		while (rs.next()) {
			String name = rs.getString(1);
			String password = rs.getString(2);
			System.out.println(String.format("%S %S\n", name, password));
		}
		rs.close();
	}

	public void batchTest() throws SQLException, ClassNotFoundException {
		DatabaseConnector db = new DatabaseConnector();
		db.initialize();
		String insert = "insert into Groupp (id, groupName) values (?,?);";
		PreparedStatement ps = db.makeBatchUpdate(insert);
		String id = null;
		String groupName = null;
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		while (line.trim().length() > 2) {
			StringTokenizer t = new StringTokenizer(line);
			id = t.nextToken();
			groupName = t.nextToken();
			ps.setInt(1, Integer.parseInt(id));
			ps.setString(2, groupName);
			ps.addBatch();
			line = sc.nextLine();
		}
		System.out.println(ps.toString());
		ps.executeBatch();
		ps.close();
		db.close();
	}

	public UserModel testCreateUser(String userName, String password,
			String email, String name, String surname, String phoneNumber,
			int isAdmin) throws SQLException, ClassNotFoundException {
		Factory f = new Factory();
		UserModel um = f.createUserModel(userName, password, email, name,
				surname, phoneNumber, isAdmin);
		return um;

	}

	public void testCreateAlarm() throws SQLException, ClassNotFoundException {
		UserModel user = new UserModel("jarudiha", "hei", "hei", "Juul",
				"Rudihagen", "88888888", 1);
		Date date = new Date(2, 3, 4);
		AppointmentModel ap = new AppointmentModel(1, 4, 5, user, "sdf", "gdf",
				"haa", date, null);
		Factory f = new Factory();
		AlarmModel am = new AlarmModel(date, "nidda", ap, user);
		f.createAlarmModel(am);
	}

	public void testGetAlarm() throws ClassNotFoundException, SQLException {
		Factory f = new Factory();
		AppointmentModel apm = new AppointmentModel();
		UserModel user = new UserModel("perok", "hei", "hei", "Juul",
				"Rudihagen", "88888888", 1);
		Date date = new Date(0, 0, 0);
		AppointmentModel ap = new AppointmentModel(2, 4, 5, user, "sdf", "gdf",
				"haa", date, null);
		AlarmModel am = new AlarmModel(date, "", ap, user);
		System.out.println(f.getAlarmModel(am));
	}

	public void testUpdateAlarm() throws ClassNotFoundException, SQLException {
		Factory f = new Factory();
		AppointmentModel apm = new AppointmentModel();
		UserModel user = new UserModel("perok", "hei", "hei", "Juul",
				"Rudihagen", "88888888", 1);
		Date date = new Date(0, 0, 0);
		AppointmentModel ap = new AppointmentModel(2, 4, 5, user, "sdf", "gdf",
				"haa", date, null);
		AlarmModel am = new AlarmModel(date, "NEI", ap, user);
		f.updateAlarmModel(am);
	}

	public void testDeleteAlarm() throws ClassNotFoundException, SQLException {
		Factory f = new Factory();
		AppointmentModel apm = new AppointmentModel();
		UserModel user = new UserModel("perok", "hei", "hei", "Juul",
				"Rudihagen", "88888888", 1);
		Date date = new Date(0, 0, 0);
		AppointmentModel ap = new AppointmentModel(2, 4, 5, user, "sdf", "gdf",
				"haa", date, null);
		AlarmModel am = new AlarmModel(date, "NEI", ap, user);
		f.deleteAlarmModel(am);
	}

	public void testMakeCal() throws ClassNotFoundException, SQLException {
		Factory f = new Factory();
		CalendarModel c = f.createCalendarModel(1, "chris",

		new ArrayList<AppointmentModel>( Arrays.asList(
			new AppointmentModel(),
			new AppointmentModel()
		)),

		new UserModel("cristea", "ep", "chris", "tonn", "88888888",
				"hei123", 1),
						
		new ArrayList<UserModel>( Arrays.asList(
			new UserModel(),
			new UserModel(),
			new UserModel()
		))
		);
	}

	public static void main(String args[]) {
		TestDB t = new TestDB();
		try {
			t.testMakeCal();
			// }
			// catch(FileNotFoundException e){
			// e.getStackTrace();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
