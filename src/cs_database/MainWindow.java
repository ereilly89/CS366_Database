package cs_database;
import java.awt.EventQueue;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.PreparedStatement;

import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JMenuBar;
import javax.swing.JProgressBar;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.DropMode;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;

public class MainWindow {

	private JFrame frmDatabase;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnUser;
	private JRadioButton rdbtnSong;
	private JRadioButton rdbtnArtist;
	private JRadioButton rdbtnAlbum;
	private JTextArea searchText;
	private String username;//stores the current user of the application
	private int userID;
	private Connection connection;//stores the connection to mysql
	private String displayLabel;//label for displaying search results
	private DefaultListModel searchModel; //model for using JList object for search
	private DefaultListModel playlistModel; //model for using JList object for playlist
	private DefaultListModel followModel; //model for using JList object for user following
	CallableStatement myCallStmt;  //for query execution
	String sql; //for insertion of tuples
	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	
	public MainWindow(String theUser, Connection connection) throws SQLException {
		username = theUser;
		this.connection = connection;
		userID = getUserID(username);
		initialize();
		this.frmDatabase.setVisible(true);
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize() throws SQLException {
		
		frmDatabase = new JFrame();
		frmDatabase.getContentPane().setBackground(new Color(135, 206, 250));
		frmDatabase.setTitle("Million Song Database Application");
		frmDatabase.setForeground(Color.BLACK);
		frmDatabase.setBackground(Color.DARK_GRAY);
		frmDatabase.setBounds(100, 100, 700, 600);
		frmDatabase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDatabase.getContentPane().setLayout(new MigLayout("", "[183.00px][][427.00px]", "[54.00px][137.00px][37.00][256.00px][][]"));
		
		JPanel playlistPanel = new JPanel();
		playlistPanel.setBackground(new Color(100, 149, 237));
		frmDatabase.getContentPane().add(playlistPanel, "cell 0 0 1 2,grow");
		playlistPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JLabel lblPlaylists = new JLabel("Playlists");
		lblPlaylists.setFont(new Font("Tahoma", Font.BOLD, 20));
		playlistPanel.add(lblPlaylists, "2, 2");
		
		playlistModel = new DefaultListModel();
		JList playlistList = new JList(playlistModel);
		playlistPanel.add(playlistList, "1, 4, 2, 1, fill, fill");
		JScrollPane playlistScrollPane;
		playlistScrollPane = new JScrollPane(playlistList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); //
		playlistPanel.add(playlistScrollPane, "1, 4, 2, 1, fill, fill"); //
		playlistModel.add(0,"(Create Playlist)");
		
		JPanel searchPanel = new JPanel();
		searchPanel.setBackground(new Color(135, 206, 250));
		frmDatabase.getContentPane().add(searchPanel, "cell 2 0,grow");
		searchPanel.setLayout(new MigLayout("", "[100px][5px][49px][5px][51px][5px][55px][5px][140px]", "[23px][5px][23px]"));
		
		searchText = new JTextArea();
		searchPanel.add(searchText, "cell 0 0 7 1,growx,aligny top");
		searchText.setWrapStyleWord(true);
		searchText.setRows(1);
		searchText.setLineWrap(true);
		searchText.setBackground(new Color(255, 255, 255));
		
		JButton btnSearch = new JButton("Search");
		searchPanel.add(btnSearch, "cell 8 0,alignx left,aligny top");
		btnSearch.setBackground(new Color(135, 206, 250));
		
		rdbtnUser = new JRadioButton("User");
		buttonGroup.add(rdbtnUser);
		searchPanel.add(rdbtnUser, "cell 0 2,alignx right,aligny top");
		rdbtnUser.setContentAreaFilled(false);
		rdbtnUser.setBackground(new Color(135, 206, 250));
		
		rdbtnSong = new JRadioButton("Song");
		buttonGroup.add(rdbtnSong);
		searchPanel.add(rdbtnSong, "cell 2 2,alignx left,aligny top");
		rdbtnSong.setContentAreaFilled(false);
		rdbtnSong.setBackground(new Color(135, 206, 250));
		
		rdbtnArtist = new JRadioButton("Artist");
		buttonGroup.add(rdbtnArtist);
		searchPanel.add(rdbtnArtist, "cell 4 2,alignx left,aligny top");
		rdbtnArtist.setContentAreaFilled(false);
		rdbtnArtist.setBackground(new Color(135, 206, 250));
		
		rdbtnAlbum = new JRadioButton("Album");
		buttonGroup.add(rdbtnAlbum);
		searchPanel.add(rdbtnAlbum, "cell 6 2,alignx left,aligny top");
		rdbtnAlbum.setContentAreaFilled(false);
		rdbtnAlbum.setBackground(new Color(135, 206, 250));
		
		JPanel songsPanel = new JPanel();
		songsPanel.setBackground(new Color(100, 149, 237));
		frmDatabase.getContentPane().add(songsPanel, "cell 2 1 1 3,grow");
		songsPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JLabel lblSongs = new JLabel("Songs");
		lblSongs.setFont(new Font("Tahoma", Font.BOLD, 20));
		songsPanel.add(lblSongs, "2, 2");
		
		searchModel = new DefaultListModel();
		JList songsList = new JList(searchModel);
		songsPanel.add(songsList, "1, 4, 2, 1, fill, fill"); 
		JScrollPane songScrollPane; //
		songScrollPane = new JScrollPane(songsList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); //
		songsPanel.add(songScrollPane, "1, 4, 2, 1, fill, fill"); //
		
		JPanel followedPanel = new JPanel();
		followedPanel.setBackground(new Color(100, 149, 237));
		frmDatabase.getContentPane().add(followedPanel, "cell 0 3,grow");
		followedPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JLabel lblFollowed = new JLabel("Followed");
		lblFollowed.setFont(new Font("Tahoma", Font.BOLD, 20));
		followedPanel.add(lblFollowed, "2, 2");
		
		followModel = new DefaultListModel();
		JList followedList = new JList(followModel);
		followedPanel.add(followedList, "1, 4, 2, 1, fill, fill");
		JScrollPane followScrollPane; //
		followScrollPane = new JScrollPane(followedList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); //
		followedPanel.add(followScrollPane, "1, 4, 2, 1, fill, fill"); //
		JMenuBar menuBar = new JMenuBar();
		frmDatabase.setJMenuBar(menuBar);
		
		//********Fetch and display preexisting playlists, and users the current user follows
		
		ResultSet rs;
		
		//Initialize Follows List
		updateFollowed();
		
		//Initialize Playlist List
		updatePlaylists();
		
		//*********************************FILE MENU**************************************//
		
		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		//Create Login/Logout option
		JMenuItem mntmLogin = new JMenuItem("Login/Logout "+username);
		menuFile.add(mntmLogin);
		
		mntmLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmDatabase.dispose();
				Login newLogin = new Login(connection);
			}
		});
		
		//Create Quit option
		JMenuItem mntmQuit = new JMenuItem("Quit");
		menuFile.add(mntmQuit);
		
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		//********************************************************************
		
		//When USER radio button clicked
		rdbtnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayLabel = "Users";
			}
		});
		
		//When Song radio button clicked
		rdbtnSong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayLabel = "Songs";
			}
		});
		
		//When ARTIST radio button clicked
		rdbtnArtist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayLabel = "Artists";
			}
		});
		
		//When ALBUM radio button clicked
		rdbtnAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayLabel = "Albums";
			}
		});
		
		
		//********************************************************************
		
		//When SEARCH is clicked
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Display the changed display label
				lblSongs.setText(displayLabel);
				
				//Display query***********************
				
				//USERS
				
				if(displayLabel.equals("Users")) {
					
					try {
						myCallStmt = (CallableStatement) connection.prepareCall("{call searchUsers(?)}");
						myCallStmt.setString(1, "%"+searchText.getText().trim()+"%");
						myCallStmt.execute();
						ResultSet rs = myCallStmt.getResultSet();
						
						searchModel.clear();
						while(rs.next()) {
							searchModel.addElement(rs.getString("username"));
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				//SONGS
				
				if(displayLabel.equals("Songs")) {
					CallableStatement myCallStmt;
					try {
						myCallStmt = (CallableStatement) connection.prepareCall("{call searchSongs(?)}");
						myCallStmt.setString(1, "%"+searchText.getText().trim()+"%");
						myCallStmt.execute();
						ResultSet rs = myCallStmt.getResultSet();
						
						searchModel.clear();
						while(rs.next()) {
							searchModel.addElement(rs.getString("title")+"\t"+rs.getString("artistName"));
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				//ARTISTS
				
				if(displayLabel.equals("Artists")) {
					CallableStatement myCallStmt;
					try {
						myCallStmt = (CallableStatement) connection.prepareCall("{call searchArtists(?)}");
						myCallStmt.setString(1, "%"+searchText.getText().trim()+"%");
						myCallStmt.execute();
						ResultSet rs = myCallStmt.getResultSet();
						
						searchModel.clear();
						while(rs.next()) {
							searchModel.addElement(rs.getString("artistName"));
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				//ALBUMS
				
				if(displayLabel.equals("Albums")) {
					CallableStatement myCallStmt;
					try {
						myCallStmt = (CallableStatement) connection.prepareCall("{call searchAlbums(?)}");
						myCallStmt.setString(1, "%"+searchText.getText().trim()+"%");
						myCallStmt.execute();
						ResultSet rs = myCallStmt.getResultSet();
						
						searchModel.clear();
						while(rs.next()) {
							searchModel.addElement(rs.getString("albumName")+"\t"+rs.getString("artistName"));
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				searchText.setText("");
				
				//************************************
			}
		});
		
		//**Follow User when user clicked****************************************************//
		
		MouseListener mouseListener = new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		    	try {
		    		if (e.getClickCount() == 2 && displayLabel.equals("Users")) {
				           String selectedItem = (String)  songsList.getSelectedValue();
				           
				           if(!selectedItem.equals(username)) {
				        	   System.out.println("selectedItem: "+selectedItem+", username: "+username);
				        	// add user to followed if they aren't already followed and add to the database
					           boolean isFollowing = false;
					           
								try {
									ResultSet rs;
								    
									int followedUserID = getUserID(selectedItem);
									
									//Find out if the current user is already following the selected user
									myCallStmt = (CallableStatement) connection.prepareCall("{call isFollowing(?,?)}");
									myCallStmt.setInt(1, userID);
									myCallStmt.setInt(2, followedUserID);
									myCallStmt.execute();
									rs = myCallStmt.getResultSet();
									
									System.out.println("test");
									
									while(!rs.next()) { //User hasn't already been followed so let them follow
										DefaultListModel model = (DefaultListModel) followedList.getModel();
								           if(model == null)
								           {
								                 model = new DefaultListModel();
								                 followedList.setModel(model);
								           }
								           model.addElement(selectedItem);
								          
								           sql = "INSERT INTO follow (user_ID,userID)"+"VALUES (?,?)";
										   PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
										   preparedStatement.setInt(1, userID);
										   preparedStatement.setInt(2, followedUserID);
										   preparedStatement.executeUpdate();
										   System.out.println("User added");
										   break;
									}
								
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
				           }
				         }
		    	}catch (Exception ex) {
		    		System.out.println("no selection made.");
		    	}
		        
		    }
		};
		songsList.addMouseListener(mouseListener);
		
		//***************Create Playlist Button*****************************************************
		
		MouseListener playlistListener = new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		    	try {
		    		if (e.getClickCount() == 2 && playlistList.getSelectedValue().equals("(Create Playlist)")) {
		    			System.out.println("Created playlist...");
				    }
		    	}catch (Exception ex) {
		    		System.out.println("no selection made.");
		    	}
		        
		    }
		};
		playlistList.addMouseListener(playlistListener);
		
		//*************Right click followed user option (to delete)*****************************************************************************
		
		final JPopupMenu deleteMenu = new JPopupMenu();
		JMenuItem item = new JMenuItem("Delete");
		
		MouseListener deleteClick = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {//Delete the selected user from current user's friend's list
				System.out.println("Delete click event fired.");
				try {
					
					//Delete the user from the database
					int followedUser = getUserID((String) followedList.getSelectedValue());
					sql = "DELETE FROM `cs366-2187_reillyem11`.`follow` WHERE (`user_ID` = '"+userID+"') and (`userID` = '"+followedUser+"');";
					PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
					preparedStatement.executeUpdate();
					  
					//Delete the user from the GUI
					updateFollowed();
					System.out.println("Deleted user's ID: "+followedUser);
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
		
		MouseListener followedListener = new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		    	try {
		    		if (SwingUtilities.isRightMouseButton(e)) {//if right clicked
		    			
		    			//get the pointer and select the list item 
		    			int row = followedList.locationToIndex(e.getPoint());
		    			followedList.setSelectedIndex(row);
		    			
		    			//Show the delete menu
		    			deleteMenu.show(e.getComponent(), e.getX(), e.getY());
				    }
		    	}catch (Exception ex) {
		    		System.out.println("no selection made.");
		    	}
		        
		    }
		};
		
		item.addMouseListener(deleteClick);
		deleteMenu.add(item);
		
		followedList.addMouseListener(followedListener);
		
		//******************************************************************************************
	}
	
	public void updateFollowed() throws SQLException {
		
		//Clear Preexisting contents
		followModel.clear();
	
		//Update Follows List
		myCallStmt = (CallableStatement) connection.prepareCall("{call displayFollows(?)}");
		myCallStmt.setInt(1, userID);
		myCallStmt.execute();
		ResultSet rs = myCallStmt.getResultSet();
		
		while(rs.next()) {
			System.out.println("user added?");
			followModel.addElement(getUsername(rs.getInt(2)));
		}
	}
	
	public void updatePlaylists() throws SQLException {
		
		//Clear Preexisting contents
		playlistModel.clear();
		
		//Update Playlist List
		myCallStmt = (CallableStatement) connection.prepareCall("{call displayPlaylists(?)}");
		myCallStmt.setInt(1, userID);
		myCallStmt.execute();
		ResultSet rs = myCallStmt.getResultSet();
		
		while(rs.next()) {
			System.out.println("playlist added?");
			playlistModel.addElement(rs.getString(1));
		}
	}
	
	public int getUserID(String user) throws SQLException {
		CallableStatement myCallStmt = (CallableStatement) connection.prepareCall("{call getUserID(?)}");
		myCallStmt.setString(1, user);
		myCallStmt.execute();
		ResultSet rs = myCallStmt.getResultSet();
		int thisUserID = -1;
		if(rs.next()) {
			thisUserID = rs.getInt(1);
		}
		return thisUserID;
	}
	
	public String getUsername(int userID) throws SQLException {
		CallableStatement myCallStmt = (CallableStatement) connection.prepareCall("{call getUsername(?)}");
		myCallStmt.setInt(1, userID);
		myCallStmt.execute();
		ResultSet rs = myCallStmt.getResultSet();
		String thisUsername = "";
		if(rs.next()) {
			thisUsername = rs.getString(1);
		}
		return thisUsername;
	}
	
	public JRadioButton getRdbtnUser() {
		return rdbtnUser;
	}
	public JRadioButton getRdbtnSong() {
		return rdbtnSong;
	}
	public JRadioButton getRdbtnArtist() {
		return rdbtnArtist;
	}
	public JRadioButton getRdbtnAlbum() {
		return rdbtnAlbum;
	}
	public JTextArea getSearchText() {
		return searchText;
	}
}
