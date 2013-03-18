package tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class FactoryTest {
	
	/**
	 * Overall TODO:
	 * - implement init() and out() methods
	 * - move tests so that dependencies doesn't break them
	 * - write tests
	 */

	static Connection conn;
	static String rootPassword = "hei123";
	
	@BeforeClass
	public static void init() throws SQLException {
		
		conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:306/?user=root&password=" + rootPassword
				);
		Statement s = conn.createStatement();
		s.executeUpdate("create databse factorytest;");
		s.executeUpdate("use databse factorytest;");
	}
	
	@AfterClass
	public static void out() throws SQLException {
		conn.createStatement().executeUpdate("drop database factorytest;");
		conn.close();
	}

	@Test
	public void testFactory() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateDatabase() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateCalendarModelCalendarModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateCalendarModelIntStringArrayListOfAppointmentModelString() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateCalendarModelCalendarModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateCalendarModelIntStringArrayListOfAppointmentModelString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCalendarModelCalendarModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCalendarModelInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteCalendarModelCalendarModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteCalendarModelInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testMakeQuery() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateUserModelStringStringStringStringStringStringInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateUserModelUserModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserModelString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserModelUserModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateUserModelStringStringStringStringStringStringInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateUserModelUserModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteUserModelString() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteUserModelUserModel() {
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
	public void testCreateAlarmModelDateStringAppointmentModelUserModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateAlarmModelAlarmModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAlarmModelStringInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAlarmModelAppointmentModelUserModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAlarmModelAlarmModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateAlarmModelDateStringAppointmentModelUserModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateAlarmModelAlarmModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteAlarmModelAppointmentModelUserModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteAlarmModelIntString() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteAlarmModelAlarmModel() {
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
	public void testGetAppointmentModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateAppointmentModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateAppointmentModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteAppointmentModel() {
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
	public void testCreateRoomModelRoomModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateRoomModelIntStringIntString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRoomModelRoomModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRoomModelInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateRoomModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteRoomModelRoomModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteRoomModelInt() {
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

}
