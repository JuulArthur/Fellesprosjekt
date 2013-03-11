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

public class TestDB {
	
	public void test() throws SQLException, ClassNotFoundException{
		DatabaseConnector db = new DatabaseConnector();
		
		String insert = "insert into Avtale (id, starttidspunkt, sluttidsunkt, moeteleder, tittel, tekst) values (4,4,4,'jarudiha','test!','tbla');";
		db.initialize();
		db.makeUpdate(insert);
		db.close();
	}
	
	public void readTest() throws SQLException, ClassNotFoundException{
		DatabaseConnector db = new DatabaseConnector();
		
		String sql = "select tittel,moeteleder from avtale";
		db.initialize();
		ResultSet rs = db.makeSingleQuery(sql);
		rs.beforeFirst();
		while(rs.next()){
			String name = rs.getString(1);
			String birthyear = rs.getString(2);
			System.out.println(String.format("%S %S\n",name,birthyear));
		}
		rs.close();
	}
	
	public void batchTest() throws SQLException, ClassNotFoundException{
		DatabaseConnector db = new DatabaseConnector();
		db.initialize();
		String insert = "insert into Gruppe (id, gruppenavn) values (?,?);";
		PreparedStatement ps = db.makeBatchUpdate(insert);
		String id =null;
		String gruppenavn =null;
//		String slutttidsunkt =null;
//		String moeteleder =null;
//		String tittel =null;
//		String tekst =null;
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		while(line.trim().length()>2){
			StringTokenizer t = new StringTokenizer(line);
			id=t.nextToken();
			gruppenavn=t.nextToken();
//			slutttidsunkt=t.nextToken();
//			moeteleder=t.nextToken();
//			tittel=t.nextToken();
//			tekst=t.nextToken();
			ps.setInt(1, Integer.parseInt(id));
			ps.setString(2, gruppenavn);
//			ps.setInt(3, Integer.parseInt(slutttidsunkt));
//			ps.setString(4, moeteleder);
//			ps.setString(5, tittel);
//			ps.setString(6, tekst);
			
			line=sc.nextLine();
		}
		System.out.println(ps.toString());
		ps.executeBatch();
		ps.close();
		db.close();
	}
	
	public static void main(String args[]){
		TestDB t = new TestDB();
		try{
			t.batchTest();
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
