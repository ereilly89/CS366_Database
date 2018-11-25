package cs_database;
import java.awt.EventQueue;
import javax.swing.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.ResultSetMetaData;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class main extends JFrame{

	static final String databasePrefix = "cs366-2187_reillyem11"; // need to change
	static final String netID = "reillyem11"; // Please enter your netId
	static final String hostName = "washington.uww.edu"; // washington.uww.edu
	static final String databaseURL = "jdbc:mysql://" + hostName + "/" + databasePrefix+ "?autoReconnect=true&useSSL=false&useInformationSchema=true"; //noAccessToProcedureBodies=true";
	static final String password = "er1829"; // please enter your own password

	private static Connection connection = null;
	private static Statement statement = null;
	private ResultSet resultSet = null;
	
	public static void main(String args[]) throws SQLException {
		
			//try {
				//Connect user to database
				main newMain = new main();
				newMain.Connection();
				//Prompt user to login using login JFrame object
				Login newLogin = new Login(connection);
				
				//JFrame
				JFrame login = new JFrame();
				
				//Sign-In button
				JButton signIn = new JButton("Sign In");
				signIn.setSize(100,200);
				
				//Initialize
				login.add(signIn);
				
				
				/*
				//	2. 	Ask for student ID and return info about a student
				
				Scanner input = new Scanner(System.in);
				
				System.out.print("ENTER STUDENT ID: ");
				int studentIDinput = input.nextInt();
				
				CallableStatement cStmt = (CallableStatement) connection.prepareCall("{call getStudentInfo("+studentIDinput+")}");
				cStmt.execute();
				ResultSet rs = cStmt.getResultSet();
				
				while(rs.next()) {
					System.out.println("Name: "+rs.getString("sname"));
					System.out.println("Major: "+rs.getString("major"));
					System.out.println("Year: "+rs.getString("level"));
					System.out.println("Age: "+rs.getInt("age"));
				}
				
				//	3. Print out information on all classes
			
				main question3 = new main();
				question3.Connection();
				String spname = "getClassInfo";
				question3.getClassInfo(spname);
				
				//3.b
				
				main question3b = new main();
				question3b.Connection();
				String sqlQuery = "SELECT cname, meets_at, room\r\n" + 
						"FROM class c;";
				question3b.simpleQuery(sqlQuery);
				
				*/
				//connection.close();
				
			//} catch (SQLException e) {
				//e.printStackTrace();
			//}
	}
	
	public void Connection(){

		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("databaseURL"+ databaseURL);
			connection = DriverManager.getConnection(databaseURL, netID, password);
			System.out.println("Successfully connected to the database");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	} // end of Connection
	
	public void getNumFaculty(String spName) {

		try {
			statement = connection.createStatement();
			int total =0;
			CallableStatement myCallStmt = (CallableStatement) connection.prepareCall("{call "+spName+"(?)}");
	        myCallStmt.registerOutParameter(1,Types.BIGINT);
	        myCallStmt.execute();
	        total = myCallStmt.getInt(1);
	        System.out.println();
	        System.out.println("The total faculty = "+ total);

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void getClassInfo(String spName) {
		try {
			statement = connection.createStatement();
			String listName;
			CallableStatement myCallStmt = (CallableStatement) connection.prepareCall("{call "+spName+"(?)}");
		    myCallStmt.setString(1,"");
		    myCallStmt.registerOutParameter(1,Types.VARCHAR);
	        myCallStmt.execute();
	        listName = myCallStmt.getString(1);
	        System.out.println("The Class Info... \n"+listName);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void simpleQuery(String sqlQuery) {
	    
    	try {
    		statement = connection.createStatement();
    		resultSet = statement.executeQuery(sqlQuery);

    		ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
    		int columns = metaData.getColumnCount();

    		for (int i=1; i<= columns; i++) {
    			System.out.print(metaData.getColumnName(i)+"\t");
    		}

    		System.out.println();

    		while (resultSet.next()) {
       
    			for (int i=1; i<= columns; i++) {
    				System.out.print(resultSet.getObject(i)+"\t\t");
    			}
    			System.out.println();
    		}
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    } // end of simpleQuery method
}
