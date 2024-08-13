package com.ninza.hrm.api.genericutility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import com.mysql.jdbc.Driver;

public class DataBaseUtility {
	
	public static ResultSet result=null;
	public static Connection con;
	public static Driver driver;
	FileUtility fLib = new FileUtility();
	public void getDbconnection(String url , String username, String password) throws SQLException {
		try {
		Driver driver = new Driver();
		DriverManager.registerDriver(driver);
		
		 con= DriverManager.getConnection(url, username, password);
		}catch (Exception e) {
		}		
	}
	
	public void getDbconnection() throws Throwable {
		try {
		String DBURL = fLib.getDataFromPropertiesFile("DBUrl");
		String DBUSERNAME = fLib.getDataFromPropertiesFile("DB_Username");
		String DBPASSWORD = fLib.getDataFromPropertiesFile("DB_Password");

		driver = new Driver();
		DriverManager.registerDriver(driver);
		
		 con= DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
		}catch (Exception e) {
		}		
	}
	
	public void closeDbconnection() throws SQLException {
		try {
			con.close();
		} catch (Exception e) {
		}
	
	}
	
	public ResultSet executeSelectQuery(String query) throws Throwable {
		ResultSet result = null;
		try {
	       Statement stat =	con.createStatement();
	       result= stat.executeQuery(query);
		}catch (Exception e) {
		}
	   return result;
	}
	
	public int executeNonselectQuery(String query) {
		int result = 0;
		try {
		       Statement stat =	con.createStatement();
		      result =  stat.executeUpdate(query);
			}catch (Exception e) {
			}
		return result;  
	}
	
	public String  verifyDbData(String dbURl , String userName , String password, String query , String coulumnName , String expectedData) throws SQLException {
		Connection con = null;
		boolean flag = false;
		String actData= null;
		try {
		Driver driver = new Driver();
		DriverManager.registerDriver(driver);
		    
		     con= DriverManager.getConnection(dbURl ,userName, password);
	         Statement stat =	con.createStatement();
		     ResultSet result =  stat.executeQuery(query);
		     while (result.next()) {
				 actData = result.getString(coulumnName);
				if(actData.equalsIgnoreCase(expectedData)) {
					System.out.println(expectedData + " is avilable in DB");
					flag = true;
					break;
				}
			}
		}catch (Exception e) {
			 if(!flag) {
					System.out.println(expectedData + " is avilable is not avilable in  DB");
		     }			
			 }finally {
				 con.close();
			 }
		return actData;	     
	}
	
	public boolean executeQueryVerifyAndGetData(String query,int columnIndex,String expectedData) throws SQLException
	{
		boolean flag = false;
		result = con.createStatement().executeQuery(query);
		
		while(result.next())
			if(result.getString(columnIndex).equals(expectedData))
			{
				flag = true;
				break;
			}
		if(flag)
		{
			System.out.println(expectedData + "======> data verified in database table");
			return true;
		}
		else
		{
			System.out.println(expectedData + "======> data not verified in database table");
			return false;
		}
	}
	
	

}












