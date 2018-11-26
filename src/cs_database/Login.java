package cs_database;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.*;
import javax.swing.border.LineBorder;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.PreparedStatement;

public class Login extends JFrame {
	// VARIABLES
	JLabel lbUsername;
	JLabel lbPassword;
	JTextField txtUsername = null;
	JPasswordField txtPassword = null;
	JLabel lbError;
	JLabel lbBlank;
	String username;
	String password;
	String sql;
	
	public Login(Connection connection) {

		// Set title and size
		setTitle("MillionSongDB App");
		setSize(1920, 1080);
		setBackground(new Color(135, 206, 250));//Doesn't work for some reason
		
		// Create 'Sign In' button
		JButton btnSign = new JButton("Sign In");

		btnSign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					if (Login.authenticate(getUsername(), getPassword(), connection)) {// if successful
						dispose();
						username = getUsername();
						System.out.println(username);
						//Screen theScreen = new Screen(getUsername());
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									MainWindow newWindow = new MainWindow(getUsername(), connection);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					} else {// if not successful
						lbError.setText("Username or password is incorrect.");
						txtPassword.setText("");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		//Create 'Create Account' Button
		JButton btnCreate = new JButton("Create Account");
		
		btnCreate.addActionListener(new ActionListener() { //CREATE USER IF THE USERNAME CURRENTLY DOESN'T EXIST
			public void actionPerformed(ActionEvent e) {
				//if user doesnt exist
				
				CallableStatement cStmt = null;
				
				try {
					//FIND OUT IF CURRENT USERNAME EXISTS
					cStmt = (CallableStatement) connection.prepareCall("{call getExisting(?,?)}");
					username = getUsername();
					System.out.println("username: "+username);
					cStmt.setString(1, username);
					cStmt.setString(2, "0");
					cStmt.registerOutParameter(2, Types.INTEGER);
					cStmt.execute();
					int numExisting = cStmt.getInt(2);
					
					if(numExisting >= 1) {//USER ALREADY EXISTS
						lbError.setText("Username already exists.");
					}else {
						
		                //CREATE USER********************************
						dispose();
						username = getUsername();
						password = getPassword();
						
						//FIND CURRENT NUMBER OF USERS W/IN DATABASE
						cStmt = (CallableStatement) connection.prepareCall("{call getNumUsers(?)}");
						cStmt.setString(1, "0");
						cStmt.registerOutParameter(1, Types.INTEGER);
						cStmt.execute();
						int numUsers = cStmt.getInt(1);
						
						
						//ADD USER TO DATABASE*********
						sql = "INSERT INTO user (user_ID,username,password1)"+"VALUES (?,?,?)";
						PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
						preparedStatement.setInt(1, numUsers+1);
						preparedStatement.setString(2, username);
						preparedStatement.setString(3, password);
						preparedStatement.executeUpdate();

						///****************************
						
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									MainWindow newWindow = new MainWindow(getUsername(), connection);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						//Screen newScreen = new Screen(getUsername());
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		//Create 'Cancel' Button
		JButton btnCancel = new JButton("Cancel");

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		
		// Create JPanel and Grid for Login page
		JPanel signInPanel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();
		cs.fill = GridBagConstraints.HORIZONTAL;

		// Create and position username and password labels and textfields
		lbUsername = new JLabel("Username:  ");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		signInPanel.add(lbUsername, cs);

		txtUsername = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		signInPanel.add(txtUsername, cs);
		
		lbBlank = new JLabel("\n");
		cs.gridx = 1;
		cs.gridy = 4;
		cs.gridwidth = 2;
		signInPanel.add(lbBlank, cs);

		lbError = new JLabel("");
		cs.gridx = 1;
		cs.gridy = 5;
		cs.gridwidth = 2;
		signInPanel.add(lbError, cs);

		lbPassword = new JLabel("Password:  ");
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		signInPanel.add(lbPassword, cs);

		txtPassword = new JPasswordField(20);
		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 2;
		signInPanel.add(txtPassword, cs);

		// new panel for buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(btnSign);
		signInPanel.add(btnCancel);
		getContentPane().add(signInPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.PAGE_END);

		// add to panel and JFrame
		cs.gridx = 0;
		cs.gridy = 2;
		signInPanel.add(btnCancel, cs);

		cs.gridx = 1;
		cs.gridy = 2;
		signInPanel.add(btnSign, cs);
		
		cs.gridx = 1;
		cs.gridy = 3;
		signInPanel.add(btnCreate, cs);

		this.getContentPane().add(signInPanel);

		// ..
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static boolean authenticate(String username, String password, Connection connection) throws SQLException {
		// check if user exists
		try {
			CallableStatement myCallStmt = (CallableStatement) connection.prepareCall("{call getPassword(?,?)}");
			myCallStmt.setString(1, username);
			myCallStmt.registerOutParameter(2, Types.VARCHAR);
			myCallStmt.execute();
			String passwordFromDB = myCallStmt.getString(2);
			
			if (passwordFromDB.equals(password)) {
				System.out.println("Password Correct!");
				return true;
			} else {
				System.out.println("Password Incorrect...");
				System.out.println("Username: "+username);
				System.out.println("PasswordFromDB: "+passwordFromDB);
				return false;
			}
		}catch (Exception e) {
			System.out.println("Password Incorrect...");
			System.out.println("Username: "+username);
			System.out.println("PasswordFromDB: "+"Not applicable");
			return false;
		}
		
	}

	public String getUsername() {
		return txtUsername.getText().trim();
	}

	public String getPassword() {
		String thePassword = String.valueOf(txtPassword.getPassword());
		return thePassword;
	}
}
