package tests;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.server.db.Factory;

public class SystemTests {
	
	static Factory f;
	
	@BeforeClass
	public static void init() {
		f = new Factory("testdatabase.properties");
	}

	@Test
	public void logOn() {
		fail("Not yet implemented");
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
