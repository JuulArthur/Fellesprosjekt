package tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.model.AlarmModel;
import com.model.AppointmentModel;
import com.model.CalendarModel;
import com.model.RoomModel;
import com.model.UserModel;
import com.server.db.Factory;

/**
 * Follow description from com.server.db/DatabaseConnector.java, and make a database named
 * testdatabase for this test
 * 
 * @author cristea
 * 
 */

public class FactoryTest {

	/**
	 * Overall TODO: - move tests so that dependencies doesn't break them -
	 * write tests
	 */

	static Connection conn;
	static String rootPassword = "hei123";
	static Factory f;
	UserModel um;
	RoomModel rm;
	AppointmentModel ap;
	AlarmModel am;

	// start init
	@BeforeClass
	public static void init() throws SQLException {

		f = new Factory("testdatabase.properties");

	}

	// en init

	// start user model test
	@Test
	public void testCreateUserModelUserModel() throws SQLException,
			ClassNotFoundException {
		UserModel dummy = new UserModel("cristea", "hei123", "c@t.no", "chris",
				"tonnessen", "88888888", 1);
		um = null;
		um = f.createUserModel(dummy);
		assertNotNull("createUserModel didn't return any object", um);
		assertEquals("created object from createUserModel not equal to dummy",
				um, dummy);

	}

	@Test
	public void testGetUserModelUserModel() throws ClassNotFoundException,
			SQLException {
		UserModel dummy = new UserModel("cristea", "hei123", "c@t.no", "chris",
				"tonnessen", "88888888", 1);
		um = null;
		um = f.getUserModel(dummy);
		assertNotNull("getUsermodel didn't return any object", um);
		assertEquals("fetched object from getUserModel didn't equal dummy", um,
				dummy);
	}

	@Test
	public void testUpdateUserModelUserModel() throws SQLException,
			ClassNotFoundException {
		UserModel dummy = new UserModel("cristea", "hei123", "c@t.no",
				"cristea", "tonnessen", "88888888", 1);
		um = null;
		f.updateUserModel(dummy);
		um = f.getUserModel(dummy);
		assertEquals("updateUserModel didn't update properly", um, dummy);
	}

	@Test
	public void testDeleteUserModelUserModel() throws SQLException,
			ClassNotFoundException {
		UserModel dummy = new UserModel("cristea", "hei123", "c@t.no",
				"cristea", "tonnessen", "88888888", 1);
		um = null;
		f.deleteUserModel(dummy);
		um = f.getUserModel(dummy);
		assertNull("deleteUserModel didn't delete user", um);
	}

	// end user model test

	// start room model test
	@Test
	public void testCreateRoomModelRoomModel() throws SQLException,
			ClassNotFoundException {
		RoomModel dummy = new RoomModel(1, "testrom", 42, "hjemme");
		rm = null;
		rm = f.createRoomModel(dummy);
		assertNotNull("createRoomModel didn't reaturn any object", rm);
		assertEquals("created object from createRoomModel not equal to dummy",
				rm, dummy);
	}

	@Test
	public void testGetRoomModelRoomModel() throws ClassNotFoundException,
			SQLException {
		RoomModel dummy = new RoomModel(1, "testrom", 42, "hjemme");
		rm = null;
		rm = f.getRoomModel(dummy);
		assertNotNull("getRoomModel dudn't return any object", rm);
		assertEquals("fetched object from getRoomModel didn't equal dummy", rm,
				dummy);
	}

	@Test
	public void testUpdateRoomModel() throws SQLException,
			ClassNotFoundException {
		RoomModel dummy = new RoomModel(1, "testrom", 42, "ute");
		f.updateRoomModel(dummy);
		rm = null;
		rm = f.getRoomModel(dummy);
		assertEquals("updateRoomModel didn't update properly", rm, dummy);
	}

	@Test
	public void testDeleteRoomModelRoomModel() throws SQLException,
			ClassNotFoundException {
		RoomModel dummy = new RoomModel(1, "testrom", 42, "ute");
		f.deleteRoomModel(dummy);
		rm = new RoomModel(1, "test", 31, "lol");
		rm = f.getRoomModel(dummy);
		assertNull("deleteRoomModel didn't delete room", rm);
	}

	// end room model test

	// start appointment model test
	@Test
	public void testCreateAppointmentModel() throws ClassNotFoundException,
			SQLException {
		um = new UserModel("cristea", "hei123", "c@t.no", "chris", "tonnessen",
				"88888888", 1);
		f.createUserModel(um);

		AppointmentModel dummy = new AppointmentModel(1L, 1223, 1337, um,
				"testavtale", "dette er en test", "hjemme", new Date(0),
				new ArrayList<UserModel>(Arrays.asList(um)));
		ap = null;
		ap = f.createAppointmentModel(dummy);
		assertNotNull("createAppointmentModel didn't return any object", ap);
		assertEquals(
				"created object from createAppointmentModel not equal to dummy",
				ap, dummy);
	}

	@Test
	public void testGetAppointmentModel() throws SQLException,
			ClassNotFoundException {
		AppointmentModel dummy = new AppointmentModel(1L, 1223, 1337, um,
				"testavtale", "dette er en test", "hjemme", new Date(0),
				new ArrayList<UserModel>(Arrays.asList(um)));
		ap = null;
		ap = f.getAppointmentModel(dummy);
		assertNotNull("getAppointmentModel didn't return any object", ap);
		assertEquals(
				"fetched object from getAppointmentModel not equal to dummy",
				ap, dummy);
	}

	@Test
	public void testUpdateAppointmentModel() throws ClassNotFoundException,
			SQLException {
		AppointmentModel dummy = new AppointmentModel(1L, 1223, 1337, um,
				"trolololo", "dette er en test", "hjemme", new Date(0),
				new ArrayList<UserModel>(Arrays.asList(um)));
		f.updateAppointmentModel(dummy);
		ap = null;
		ap = f.getAppointmentModel(dummy);
		assertEquals("updateAppointmentModel didn't work as intended", ap,
				dummy);
	}

	@Test
	public void testDeleteAppointmentModel() throws SQLException,
			ClassNotFoundException {
		AppointmentModel dummy = new AppointmentModel(1L, 1223, 1337, um,
				"trolololo", "dette er en test", "hjemme", new Date(0),
				new ArrayList<UserModel>(Arrays.asList(um)));
		f.deleteAppointmentModel(dummy);
		ap = new AppointmentModel(2L, 2313, 1231, um, "hei", "paa", "deg",
				new Date(1L), new ArrayList<UserModel>(Arrays.asList(um)));
		ap = f.getAppointmentModel(dummy);
		assertNull("deleteAppointmentModel didn't delete the object", ap);
	}

	// end appointment model test

	// start alarm model test
	@Test
	public void testCreateAlarmModelAlarmModel() throws ClassNotFoundException,
			SQLException {
		// set up shit, um should already be in the database
		ap = new AppointmentModel(2L, 1223, 1337, um, "trolololo",
				"dette er en test", "hjemme", new Date(0),
				new ArrayList<UserModel>(Arrays.asList(um)));
		um = new UserModel("cristea2", "hei123", "c@t.no", "chris",
				"tonnessen", "88888888", 1);
		f.createAppointmentModel(ap);

		AlarmModel dummy = new AlarmModel(Date.valueOf("2013-02-28"), "ALARM!",
				0, ap, um);
		am = null;
		am = f.createAlarmModel(dummy);
		assertNotNull("crateAlarmModel didn't return any object", am);
		assertEquals("createAlarmModel doesn't equal dummy", am, dummy);
	}

	@Test
	public void testGetAlarmModelAlarmModel() throws ClassNotFoundException,
			SQLException {
		AlarmModel dummy = new AlarmModel(Date.valueOf("2013-02-28"), "ALARM!",
				0, ap, um);
		am = null;
		am = f.getAlarmModel(dummy);
		assertNotNull("getAlarmModel didn't return any object", am);
		assertEquals("getAlarmModel didn't equal dummy", am, dummy);
	}

	@Test
	public void testUpdateAlarmModelAlarmModel() throws SQLException,
			ClassNotFoundException {
		AlarmModel dummy = new AlarmModel(Date.valueOf("2015-03-12"),
				"ALARM!...NOT", 0, ap, um);
		f.updateAlarmModel(dummy);
		am = null;
		am = f.getAlarmModel(dummy);
		assertEquals("updateAlarmModel didn't udpate object", am, dummy);
	}

	@Test
	public void testDeleteAlarmModelAlarmModel() throws SQLException,
			ClassNotFoundException {
		AlarmModel dummy = new AlarmModel(Date.valueOf("2015-03-12"),
				"ALARM!...NOT", 0, ap, um);
		f.deleteAlarmModel(dummy);
		um = new UserModel("hei", "hei", "op", "lo", "l", "992", 0);
		am = new AlarmModel(Date.valueOf("2013-03-24"), "ALAAARM", 0, ap, um);
		am = f.getAlarmModel(dummy);
		assertNull("deleteAlarmModel didn't delete object", am);
	}

	// end alarm model test

	// start calendar model test
	@Test
	public void testCreateCalendarModelCalendarModel() {
		CalendarModel dummy = new CalendarModel(3L, "testkalender",
				new ArrayList<AppointmentModel>(Arrays.asList(ap)), "chris");
		
	}

	@Test
	public void testGetCalendarModelCalendarModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateCalendarModelCalendarModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteCalendarModelCalendarModel() {
		fail("Not yet implemented");
	}

	// end calendar model test

	@Test
	public void testMakeQuery() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckPasswordStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckPasswordUserModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetIsSummonedTo() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateIsSummonedTo() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteIsSummonedTo() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateNotificationModelStringIntString() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateNotificationModelNotificationModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNotificationModelNotificationModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNotificationModelStringInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateNotificationModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteNotificationModelNotificationModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteNotificationModelStringInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateGroupModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteGroupModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateGroupModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddMemberOf() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetGroupModel() {
		fail("Not yet implemented");
	}

	//start close
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
	//end close

}
