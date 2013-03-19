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
	public static void init() {
		f = new Factory("testdatabase.properties");
	}

	@Test
	public void logOn() throws SQLException, ClassNotFoundException {
		UserModel dummy = new UserModel("chris", "hei123", "ep", "chris", "tonnessen", "88888888", 1);
		f.createUserModel(dummy);
		assertTrue("can't log on with right username and password", f.checkPassword("chris", "hei123"));
	}
	
	@Test
	public void logOnInvalidUsernameAndPassword() throws SQLException, ClassNotFoundException {
		assertFalse("can log on with wrong username/password", f.checkPassword("chris", "hei321"));
	}
	
	@Test
	public void createAppointment() throws SQLException, ClassNotFoundException {
		AppointmentModel dummy = new AppointmentModel(1L, 1, 2, f.getUserModel("chris"), "testtitle", "yoyo text yo", "hjemme", Date.valueOf("2013-02-04"), new ArrayList<UserModel>());
		f.createAppointmentModel(dummy);
		assertEquals("cound't create same appointment", f.getAppointmentModel(dummy).toString(), dummy.toString());
	}
	
	@Test
	public void updateAppointment() throws SQLException, ClassNotFoundException {
		AppointmentModel dummy = new AppointmentModel(1L, 1, 2, f.getUserModel("chris"), "changedtitle", "yoyo text yo", "hjemme", Date.valueOf("2013-02-04"), new ArrayList<UserModel>());
		f.updateAppointmentModel(dummy);
		assertEquals("couldn't update appointment", f.getAppointmentModel(dummy).toString(), dummy.toString());
	}
	
	@Test
	public void deleteAppointment() throws SQLException, ClassNotFoundException {
		AppointmentModel dummy = new AppointmentModel(1L, 1, 2, f.getUserModel("chris"), "testtitle", "yoyo text yo", "hjemme", Date.valueOf("2013-02-04"), new ArrayList<UserModel>());
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
