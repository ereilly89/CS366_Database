package cs_database;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JRadioButton;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JMenuBar;
import javax.swing.JProgressBar;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.DropMode;
import javax.swing.ButtonGroup;

public class MainWindow {

	private JFrame frmDatabase;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnUser;
	private JRadioButton rdbtnSong;
	private JRadioButton rdbtnArtist;
	private JRadioButton rdbtnAlbum;
	private JTextArea searchText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmDatabase.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDatabase = new JFrame();
		frmDatabase.getContentPane().setBackground(new Color(135, 206, 250));
		frmDatabase.setTitle("Database");
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
		
		JList playlistList = new JList();
		playlistPanel.add(playlistList, "1, 4, 2, 1, fill, fill");
		
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
		
		JList songsList = new JList();
		songsPanel.add(songsList, "1, 4, 2, 1, fill, fill");
		
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
		
		JList followedList = new JList();
		followedPanel.add(followedList, "1, 4, 2, 1, fill, fill");
		
		JMenuBar menuBar = new JMenuBar();
		frmDatabase.setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		JMenuItem mntmLogin = new JMenuItem("Login/Logout");
		menuFile.add(mntmLogin);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		menuFile.add(mntmQuit);
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
