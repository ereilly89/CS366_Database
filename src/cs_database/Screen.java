package cs_database;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Screen extends JFrame{
	public Screen(String theUser) {

		//save the username of who is using the application
		String username = theUser;

		// Set title and size
		setTitle("MillionSongDB App");
		setSize(1920, 1080);

		// Create JPanel and Grid for Login page
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();
		cs.fill = GridBagConstraints.HORIZONTAL;
		
		
		getContentPane().add(mainPanel, BorderLayout.CENTER);

		// add to panel and JFrame

		this.getContentPane().add(mainPanel);

		// ..
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
