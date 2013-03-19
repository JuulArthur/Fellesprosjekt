package tests;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.model.AppointmentModel;
import com.model.UserModel;
import com.server.db.Factory;

public class SystemTests {

	static Factory f;

	@BeforeClass
	public static void init() throws SQLException, ClassNotFoundException {
		f = new Factory("testdatabase.properties");
		f.updateDatabase("CREATE TABLE Room (roomnumber BIGINT NOT NULL,roomname VARCHAR(40),capacity INT NOT NULL,location VARCHAR (50),PRIMARY KEY (roomnumber));");
		f.updateDatabase("CREATE TABLE User (username VARCHAR(30) NOT NULL,email VARCHAR(60) NOT NULL,name VARCHAR(40),surname VARCHAR(40),phonenumber CHAR(8) NOT NULL,password VARCHAR(40) NOT NULL,isAdmin Bool DEFAULT FALSE NOT NULL,PRIMARY KEY (username));");
		f.updateDatabase("CREATE TABLE Appointment (" + "id BIGINT NOT NULL,"
				+ "startTime INT NOT NULL," + "endTime INT NOT NULL,"
				+ "host VARCHAR(30) NOT NULL," + "title VARCHAR(50) NOT NULL,"
				+ "text VARCHAR(100) NOT NULL," + "place TEXT,"
				+ "isSendtOutNoti BOOL DEFAULT 0 NOT NULL,"
				+ "isDeleted BOOL DEFAULT 0 NOT NULL," + "date DATE,"
				+ "PRIMARY KEY (id),"
				+ "FOREIGN KEY (host) REFERENCES User(username) "
				+ "ON UPDATE CASCADE " + "ON DELETE CASCADE " + ");");
		f.updateDatabase("CREATE TABLE Calendar (id BIGINT NOT NULL,name VARCHAR(40),PRIMARY KEY (id));");
		f.updateDatabase("CREATE TABLE Groupp (id BIGINT NOT NULL,name VARCHAR(40),PRIMARY KEY (id));");
		f.updateDatabase("CREATE TABLE Alarm(" + "date DATE NOT NULL,"
				+ "text VARCHAR(40)," + "time INT NOT NULL,"
				+ "appointmentid BIGINT NOT NULL,"
				+ "username VARCHAR(30) NOT NULL,"
				+ "PRIMARY KEY (appointmentid, username),"
				+ "FOREIGN KEY (appointmentid) REFERENCES Appointment(id) "
				+ "ON UPDATE CASCADE " + "ON DELETE CASCADE,"
				+ "FOREIGN KEY (username) REFERENCES User(username) "
				+ "ON UPDATE CASCADE " + "ON DELETE CASCADE " + ");");
		f.updateDatabase("CREATE TABLE Notification("
				+ "text VARCHAR(30) NOT NULL,"
				+ "appointmentid BIGINT NOT NULL,"
				+ "username VARCHAR(30) NOT NULL,"
				+ "PRIMARY KEY (appointmentid, username),"
				+ "FOREIGN KEY (appointmentid) REFERENCES Appointment(id) "
				+ "ON UPDATE CASCADE " + "ON DELETE CASCADE,"
				+ "FOREIGN KEY (username) REFERENCES User(username) "
				+ "ON UPDATE CASCADE " + "ON DELETE CASCADE " + ");");
		f.updateDatabase("CREATE TABLE IsSummonedTo ("
				+ "appointmentid BIGINT NOT NULL,"
				+ "username VARCHAR(30) NOT NULL,"
				+ "isAccepted BOOL DEFAULT FALSE NOT NULL,"
				+ "isSeen BOOL DEFAULT FALSE NOT NULL,"
				+ "PRIMARY KEY (appointmentid, username),"
				+ "FOREIGN KEY (appointmentid) REFERENCES Appointment(id) "
				+ "ON UPDATE CASCADE " + "ON DELETE CASCADE,"
				+ "FOREIGN KEY (username) REFERENCES User (username) "
				+ "ON UPDATE CASCADE " + "ON DELETE CASCADE " + ");");
		f.updateDatabase("CREATE TABLE Reserved ("
				+ "appointmentid BIGINT NOT NULL,"
				+ "roomnumber BIGINT NOT NULL,"
				+ "PRIMARY KEY (appointmentid, roomnumber),"
				+ "FOREIGN KEY (appointmentid) REFERENCES Appointment(id) "
				+ "ON UPDATE CASCADE " + "ON DELETE CASCADE,"
				+ "FOREIGN KEY (roomnumber) REFERENCES Room(roomnumber) "
				+ "ON UPDATE CASCADE " + "ON DELETE CASCADE " + ");");
		f.updateDatabase("CREATE TABLE BelongTo ("
				+ "appointmentid BIGINT NOT NULL,"
				+ "calendarid BIGINT NOT NULL,"
				+ "PRIMARY KEY (appointmentid, calendarid),"
				+ "FOREIGN KEY (appointmentid) REFERENCES Appointment(id) "
				+ "ON UPDATE CASCADE " + "ON DELETE CASCADE,"
				+ "FOREIGN KEY (calendarid) REFERENCES Calendar(id) "
				+ "ON UPDATE CASCADE " + "ON DELETE CASCADE " + ");");
		f.updateDatabase("CREATE TABLE Follows ("
				+ "isOwner BOOL DEFAULT 0 NOT NULL,"
				+ "username VARCHAR(30) NOT NULL,"
				+ "calendarid BIGINT NOT NULL,"
				+ "PRIMARY KEY (username, calendarid), "
				+ "FOREIGN KEY (username) REFERENCES User(username) "
				+ "ON UPDATE CASCADE " + "ON DELETE CASCADE, "
				+ "FOREIGN KEY (calendarid) REFERENCES Calendar(id) "
				+ "ON UPDATE CASCADE " + "ON DELETE CASCADE " + ");");
		f.updateDatabase("CREATE TABLE MemberOf ("
				+ "username VARCHAR(30) NOT NULL," + "groupid BIGINT NOT NULL,"
				+ "PRIMARY KEY (username, groupid),"
				+ "FOREIGN KEY (username) REFERENCES User(username) "
				+ "ON UPDATE CASCADE " + "ON DELETE CASCADE,"
				+ "FOREIGN KEY (groupid) REFERENCES Groupp(id) "
				+ "ON UPDATE CASCADE " + "ON DELETE CASCADE " + ");");
	}

	@Test
	public void logOn() throws SQLException, ClassNotFoundException {
		UserModel dummy = new UserModel("chris", "hei123", "ep", "chris",
				"tonnessen", "88888888", 1);
		f.createUserModel(dummy);
		assertTrue("can't log on with right username and password",
				f.checkPassword("chris", "hei123"));
	}

	@Test
	public void logOnInvalidUsernameAndPassword() throws SQLException,
			ClassNotFoundException {
		assertFalse("can log on with wrong username/password",
				f.checkPassword("chris", "hei321"));
	}

	@Test
	public void createAppointment() throws SQLException, ClassNotFoundException {
		AppointmentModel dummy = new AppointmentModel(1L, 1, 2,
				f.getUserModel("chris"), "testtitle", "yoyo text yo", "hjemme",
				Date.valueOf("2013-02-04"), new ArrayList<UserModel>());
		f.createAppointmentModel(dummy);
		assertEquals("cound't create same appointment",
				f.getAppointmentModel(dummy).toString(), dummy.toString());
	}

	@Test
	public void updateAppointment() throws SQLException, ClassNotFoundException {
		AppointmentModel dummy = new AppointmentModel(1L, 1, 2,
				f.getUserModel("chris"), "changedtitle", "yoyo text yo",
				"hjemme", Date.valueOf("2013-02-04"),
				new ArrayList<UserModel>());
		f.updateAppointmentModel(dummy);
		assertEquals("couldn't update appointment", f
				.getAppointmentModel(dummy).toString(), dummy.toString());
	}

	@Test
	public void deleteAppointment() throws SQLException, ClassNotFoundException {
		AppointmentModel dummy = new AppointmentModel(1L, 1, 2,
				f.getUserModel("chris"), "testtitle", "yoyo text yo", "hjemme",
				Date.valueOf("2013-02-04"), new ArrayList<UserModel>());
		f.deleteAppointmentModel(dummy);
		assertNull("couldn't delete appointment", f.getAppointmentModel(dummy));
	}

	@AfterClass
	public static void out() throws SQLException, ClassNotFoundException {
		f.updateDatabase("delete from alarm;");
		f.updateDatabase("delete from appointment;");
		f.updateDatabase("delete from belongto;");
		f.updateDatabase("delete from calendar;");
		f.updateDatabase("delete from follows;");
		f.updateDatabase("delete from groupp;");
		f.updateDatabase("delete from issummonedto;");
		f.updateDatabase("delete from memberof;");
		f.updateDatabase("delete from notification;");
		f.updateDatabase("delete from reserved;");
		f.updateDatabase("delete from room;");
		f.updateDatabase("delete from user;");
	}

}
