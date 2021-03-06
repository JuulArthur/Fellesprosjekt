package com.server.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.model.AlarmModel;
import com.model.AppointmentModel;
import com.model.GroupModel;
import com.model.CalendarModel;
import com.model.NotificationModel;
import com.model.RoomModel;
import com.model.UserModel;

public class TestDB {

	public void test() throws SQLException, ClassNotFoundException {
		DatabaseConnector db = new DatabaseConnector("database.properties");

		String insert = "insert into Groupp (id, groupName) values (2,'test');";
		db.initialize();
		db.makeUpdate(insert);
		db.close();
	}

	public void readTest() throws SQLException, ClassNotFoundException {
		DatabaseConnector db = new DatabaseConnector("database.properties");

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
		DatabaseConnector db = new DatabaseConnector("database.properties");
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
		Factory f = new Factory("database.properties");
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
		Factory f = new Factory("database.properties");
		AlarmModel am = new AlarmModel(date, "nidda",0, ap, user);
		f.createAlarmModel(am);
	}

	public void testGetAlarm() throws ClassNotFoundException, SQLException {
		Factory f = new Factory("database.properties");
		AppointmentModel apm = new AppointmentModel();
		UserModel user = new UserModel("perok", "hei", "hei", "Juul",
				"Rudihagen", "88888888", 1);
		Date date = new Date(0, 0, 0);
		AppointmentModel ap = new AppointmentModel(2, 4, 5, user, "sdf", "gdf",
				"haa", date, null);
		AlarmModel am = new AlarmModel(date, "",0, ap, user);
		System.out.println(f.getAlarmModel(am));
	}

	public void testUpdateAlarm() throws ClassNotFoundException, SQLException {
		Factory f = new Factory("database.properties");
		AppointmentModel apm = new AppointmentModel();
		UserModel user = new UserModel("perok", "hei", "hei", "Juul",
				"Rudihagen", "88888888", 1);
		Date date = new Date(0, 0, 0);
		AppointmentModel ap = new AppointmentModel(2, 4, 5, user, "sdf", "gdf",
				"haa", date, null);
		AlarmModel am = new AlarmModel(date, "NEI",0, ap, user);
		f.updateAlarmModel(am);
	}

	public void testDeleteAlarm() throws ClassNotFoundException, SQLException {
		Factory f = new Factory("database.properties");

		UserModel user = new UserModel("perok", "hei", "hei", "Juul",
				"Rudihagen", "88888888", 1);
		Date date = new Date(0, 0, 0);
		AppointmentModel ap = new AppointmentModel(2, 4, 5, user, "sdf", "gdf",
				"haa", date, null);
		AlarmModel am = new AlarmModel(date, "NEI",0, ap, user);
		f.deleteAlarmModel(am);
	}

	public void testMakeCal() throws ClassNotFoundException, SQLException {
		Factory f = new Factory("database.properties");
		CalendarModel c = f.createCalendarModel(
				1,
				"chris",

				new ArrayList<AppointmentModel>(Arrays.asList(
						new AppointmentModel(), new AppointmentModel())),

				"cristea");
	}

	public void testCreateNotification() throws SQLException,
			ClassNotFoundException {
		UserModel user = new UserModel("jarudiha", "hei", "hei", "Juul",
				"Rudihagen", "88888888", 1);
		Date date = new Date(2, 3, 4);
		AppointmentModel ap = new AppointmentModel(1, 4, 5, user, "sdf", "gdf",
				"haa", date, null);
		Factory f = new Factory("database.properties");
		NotificationModel nm = new NotificationModel("nidda", ap, user);
		f.createNotificationModel(nm);
	}

	public void testGetNotification() throws ClassNotFoundException,
			SQLException {
		Factory f = new Factory("database.properties");
		UserModel user = new UserModel("jarudiha", "hei", "hei", "Juul",
				"Rudihagen", "88888888", 1);
		Date date = new Date(0, 0, 0);
		AppointmentModel ap = new AppointmentModel(1, 4, 5, user, "sdf", "gdf",
				"haa", date, null);
		NotificationModel nm = new NotificationModel("", ap, user);
		System.out.println(f.getNotificationModel(nm));
	}

	public void testUpdateNotification() throws ClassNotFoundException,
			SQLException {
		Factory f = new Factory("database.properties");
		UserModel user = new UserModel("jarudiha", "hei", "hei", "Juul",
				"Rudihagen", "88888888", 1);
		Date date = new Date(0, 0, 0);
		AppointmentModel ap = new AppointmentModel(1, 4, 5, user, "sdf", "gdf",
				"haa", date, null);
		NotificationModel nm = new NotificationModel("NEI", ap, user);
		f.updateNotificationModel(nm);
	}

	public void testDeleteNotification() throws ClassNotFoundException,
			SQLException {
		Factory f = new Factory("database.properties");
		UserModel user = new UserModel("jarudiha", "hei", "hei", "Juul",
				"Rudihagen", "88888888", 1);
		Date date = new Date(0, 0, 0);
		AppointmentModel ap = new AppointmentModel(1, 4, 5, user, "sdf", "gdf",
				"haa", date, null);
		NotificationModel nm = new NotificationModel("NEI", ap, user);
		f.deleteNotificationModel(nm);
	}

	public void testCreateRoom() throws SQLException, ClassNotFoundException {
		Factory f = new Factory("database.properties");
		RoomModel rm = new RoomModel(1, "Soverom", 6, "Hjemme");
		f.createRoomModel(rm);
	}

	public void testGetRoom() throws ClassNotFoundException, SQLException {
		Factory f = new Factory("database.properties");
		RoomModel nm = new RoomModel(1, "", 1, "asd");
		System.out.println(f.getRoomModel(nm));
	}

	public void testUpdateRoom() throws ClassNotFoundException, SQLException {
		Factory f = new Factory("database.properties");
		RoomModel rm = new RoomModel(1, "updated", 1, "jahman!");
		f.updateRoomModel(rm);
	}

	public void testDeleteRoom() throws ClassNotFoundException, SQLException {
		Factory f = new Factory("database.properties");
		RoomModel rm = new RoomModel(1, "", 1, "");
		f.deleteRoomModel(rm);
	}

	public void testCreateAppointment() throws ClassNotFoundException,
			SQLException {
		AppointmentModel am = new AppointmentModel(1337, 2343, 23432,
				new UserModel("perok", "hei", null, null, null, null, 1),
				"SEXEHTIME", "best time of the day", "room", new Date(0, 0, 0),
				null);

		Factory f = new Factory("database.properties");

		f.createAppointmentModel(am);

		am.setTitle("NOT SO SEXEHTIME :(");

		f.updateAppointmentModel(am);

		AppointmentModel kake = f.getAppointmentModel(am.getId());

		System.out.println(kake);

		f.deleteAppointmentModel(am.getId());
	}

	public void testGetGroup(int id) throws SQLException,
			ClassNotFoundException {
		Factory f = new Factory("database.properties");
		ArrayList<UserModel> member = new ArrayList<UserModel>();
		UserModel user = f.getUserModel("jarudiha");
		UserModel usere = f.getUserModel("perok");
		member.add(user);
		member.add(usere);

		GroupModel gm = new GroupModel(id, "soup", member);
		f.UpdateGroupModel(gm);
		System.out.println(gm == f.getGroupModel(id));
		System.out.println(f.getGroupModel(id));
		System.out.println(gm);
	}

	public void testAddMember(GroupModel gm) throws SQLException,
			ClassNotFoundException {
		Factory f = new Factory("database.properties");
		f.addMemberOf(gm);
	}

	public void testDeleteGroup(int id) throws SQLException,
			ClassNotFoundException {
		Factory f = new Factory("database.properties");
		f.deleteGroupModel(id);
		f.getGroupModel(id);
	}

	public void testCreategroupp() throws SQLException, ClassNotFoundException {
		Factory f = new Factory("database.properties");
		GroupModel gm = new GroupModel(11, "community", null);

		f.createGroupModel(gm);
	}

	public void testCreateTestDatabase() throws SQLException,
			ClassNotFoundException {
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost:306/?user=root&password=hei123");
		Statement s = conn.createStatement();
		s.executeUpdate("create databse factorytest;");
		s.executeUpdate("use databse factorytest;");
	}

	public static void main(String args[]) {
		TestDB t = new TestDB();

		// t.testCreateUser("christea", "enkelt", "jada", "chrisboy",
		// "t�nnemann", "32234890", 0);

		try {
			t.testGetAlarm();

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
