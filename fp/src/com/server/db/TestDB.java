package com.server.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;
import java.util.StringTokenizer;
import com.model.UserModel;

public class TestDB {
	
	public void test() throws SQLException, ClassNotFoundException{
		DatabaseConnector db = new DatabaseConnector();
		
		String insert = "insert into Groupp (id, groupName) values (2,'test');";
		db.initialize();
		db.makeUpdate(insert);
		db.close();
	}
	
	public void readTest() throws SQLException, ClassNotFoundException{
		DatabaseConnector db = new DatabaseConnector();
		
		String sql = "select username,password from User WHERE username='perok'";
		db.initialize();
		ResultSet rs = db.makeSingleQuery(sql);
		rs.beforeFirst();
		while(rs.next()){
			String name = rs.getString(1);
			String password = rs.getString(2);
			System.out.println(String.format("%S %S\n",name,password));
		}
		rs.close();
	}
	
	public void batchTest() throws SQLException, ClassNotFoundException{
		DatabaseConnector db = new DatabaseConnector();
		db.initialize();
		String insert = "insert into Groupp (id, groupName) values (?,?);";
		PreparedStatement ps = db.makeBatchUpdate(insert);
		String id =null;
		String groupName =null;
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		while(line.trim().length()>2){
			StringTokenizer t = new StringTokenizer(line);
			id=t.nextToken();
			groupName=t.nextToken();
			ps.setInt(1, Integer.parseInt(id));
			ps.setString(2, groupName);
			ps.addBatch();
			line=sc.nextLine();
		}
		System.out.println(ps.toString());
		ps.executeBatch();
		ps.close();
		db.close();
	}
	
	public UserModel testCreateUser(String userName, String password, String email, 
			String name, String surname, String phoneNumber, int isAdmin ) throws SQLException, ClassNotFoundException{
		Factory f = new Factory();
		UserModel um = f.createUserModel(userName, password, email, 
		 name, surname, phoneNumber, isAdmin );
				return um;
		
	}
	
	public static void main(String args[]){
		TestDB t = new TestDB();
		Factory f = new Factory();
//		t.testCreateUser("christea", "enkelt", "jada", "chrisboy", "t�nnemann", "32234890", 0);
		try{
			System.out.println(f.createUserModel("heidi", "skjer", "neitakk", 
					"Heidi", "Englestad", "81549300", 1 ));
			System.out.println(f.getUserModel("heidi"));
			f.updateUserModel("heidi", "sdfdsf", "neitakk", 
					"Heidi", "Englestad", "12345687", 0 );
			System.out.println(f.getUserModel("heidi"));
//		}
//		catch(FileNotFoundException e){
//			e.getStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
