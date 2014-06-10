package com.mborrajo.util;

import java.sql.*;

public class SQLiteHelper {
	
	String DBName = "";
	Connection c = null;
	
	SQLiteHelper(String DBName){
		this.DBName = DBName;
	}
	
	boolean testConnection(){
		
		try{
			connect();
		}
		catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			return false;
		}
		return true;
	}
	
	private void connect() throws Exception{
		if (DBName == "" | DBName == null){
			throw new Exception("DB Name cannot be empty.");
		}
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:" + DBName);
	}
	
	ResultSet execute(String sql){
		ResultSet rs = null;
		Statement stmt = null;
		try{
			c.setAutoCommit(false);
			stmt = c.createStatement();
			rs = stmt.executeQuery( sql );
		}
		catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return rs;
	}
	
	public ResultSet select(String from,String what){
		ResultSet rs = execute("Select " + what + " from " + from + ";");
		return rs;
	}
	
}
