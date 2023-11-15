import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class UserView extends JPanel implements ActionListener{
	private JFrame frame;
    private JButton followUserButton;
    private JTextField followUserTextField;
    private JButton tweetMessageButton;
    private JTextField tweetMessageTextField;
    private JPanel panel;
    private JPanel panel2; 
    private JPanel panel3;
    private JPanel panel4;
    private List<SystemEntry> allUsers;
    private User user;
    private User followUser;
	private Admin adminInstance;
	private String currUser;
	
	private List<String> followers;
	private List<String> messages;
	private JPanel scrollPanel = new JPanel();
	private JPanel scrollPanel2 = new JPanel();
	
	private List<UserView> userViewList;
	
	private JButton refreshButton;
	  
	UserView(String selectedUser){
		this.userViewList = new ArrayList<UserView>();
		this.currUser = selectedUser;
		

		setCurrUser();
		

		frame = new JFrame(this.currUser);

		refreshButton = new JButton("Refresh");
		refreshButton.setBounds(400, 260, 80, 30);
		refreshButton.addActionListener(this);
				
 
		followUserTextField = new JTextField();
		followUserTextField.setBounds(10,10,150,30);
		followUserTextField.setForeground(Color.gray);
		followUserTextField.setText("User Id");
		

		followUserButton = new JButton("Follow User");
		followUserButton.setBounds(200, 10, 120, 30);
		followUserButton.addActionListener(this);
		
	
		tweetMessageButton = new JButton("Post Tweet");
		tweetMessageButton.setBounds(200, 260, 120, 30);
		tweetMessageButton.addActionListener(this);
		

		tweetMessageTextField = new JTextField();
		tweetMessageTextField.setBounds(10,260,150,30);
		tweetMessageTextField.setForeground(Color.gray);
		tweetMessageTextField.setText("Tweet Message");
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(500, 600));
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setBackground(Color.white);
		
		panel= new JPanel();
		panel.setBounds(0, 50, 500, 200);
		panel.add(new JLabel("<html>Current Following<br/></html>", SwingConstants.CENTER));
		panel.setBackground(Color.white);
		
		
		
		panel2 = new JPanel();
		panel2.setBounds(0, 340, 500, 200);
		panel2.add(new JLabel("News Feed", SwingConstants.CENTER));
		panel2.setBackground(Color.white);

        panel3 = new JPanel();
        panel3.setBounds(0, 290, 520, 20);
		panel3.add(new JLabel("Creation Time: " + getCreationTime(), SwingConstants.CENTER));
		
		

        panel4 = new JPanel();
        panel4.setBounds(0, 309, 520, 20);
        

		frame.add(refreshButton);
		frame.add(followUserTextField);
		frame.add(followUserButton);
		frame.add(tweetMessageTextField);
		frame.add(tweetMessageButton);
		frame.add(panel);
		frame.add(panel2);
		frame.add(panel3);
		frame.add(panel4);
		
	}

	String getCreationTime() {
		if(this.user == null) {
			return "Printed in console";
		}
		else {
			return this.user.getCreationTime().toString();
		}
	}

	String getLastUpdateTime() {
		if(this.user == null) {
			return "Printed in console";
		}
		else if( this.user.getLastUpdateTime() == null) {
			return "N/A";
		}
		else {
			return this.user.getLastUpdateTime().toString();
		}
	}
	
	User getCurrUser() {
		return this.user;
	}
	
	public void setUserViewList(List<UserView> userViewList) {
		this.userViewList = userViewList;
	}
	
	void setCurrUser() {
		this.adminInstance = Admin.getInstance();
		this.allUsers = this.adminInstance.getUser();

		 for(SystemEntry user : allUsers) {				 
			 if(this.currUser.equals(user.toString())) {
				 this.user = (User) user;
				 System.out.println("User is: " + user.toString());
			 }
		 }
	}
	
	void refresh() {
		scrollPanel2.removeAll(); 
		panel4.removeAll(); 
		

		this.messages = this.user.getMessages();
		
	
		getLastUpdateTime();
		

		JScrollPane scroll = new JScrollPane(new JList(this.messages.toArray()));
		scroll.setPreferredSize(new Dimension(450, 150));
		scrollPanel2.add(scroll);

		panel4.add(new JLabel("Last Update Time: " + getLastUpdateTime(), SwingConstants.CENTER));
		panel4.revalidate();
		panel4.repaint();

		frame.setVisible(true);
		panel2.add(scrollPanel2);
		panel2.revalidate();
		panel2.repaint();
	}
	
	

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == followUserButton) {
			scrollPanel.removeAll(); 
			
		
			 for(SystemEntry user : allUsers) {				
		
				 if(followUserTextField.getText().equals(user.toString()) && !this.currUser.equals(user.toString())) {
				
					 this.followUser = (User) user;	
					 this.user.follow(this.followUser); 
					 
					this.followers = this.user.getFollowers();
					 
					JScrollPane scroll = new JScrollPane(new JList(this.followers.toArray()));
					scroll.setPreferredSize(new Dimension(450, 150));
					scrollPanel.add(scroll);
					
					frame.setVisible(true);
					panel.add(scrollPanel);
					panel.revalidate();
					panel.repaint();
				 }
			 }
		}
		else if (e.getSource() == tweetMessageButton) {
			scrollPanel2.removeAll(); 
			panel4.removeAll(); 
			
			this.user.postMessage(tweetMessageTextField.getText());
			
			
			this.messages = this.user.getMessages();
			
			JScrollPane scroll = new JScrollPane(new JList(this.messages.toArray()));
			scroll.setPreferredSize(new Dimension(450, 150));
			scrollPanel2.add(scroll);
			
		
			panel4.add(new JLabel("Last Update Time: " + getLastUpdateTime(), SwingConstants.CENTER));
			panel4.revalidate();
			panel4.repaint();
			
			
			frame.setVisible(true);
			panel2.add(scrollPanel2);
			panel2.revalidate();
			panel2.repaint();
			
			for(UserView u: userViewList) {
				if(this.currUser.equals(u.getCurrUser().toString())) {
				}
				else {
					this.followers = this.getCurrUser().getFollowers();
					for(int i = 0; i < this.followers.size(); i++) {
						System.out.println("Update followers news feed: " + this.followers.get(i));
						
						if(u.getCurrUser().toString().equals(this.followers.get(i))) {
							
							u.refresh();
						}
					}
				}
			}
			
		}
		else if (e.getSource() == refreshButton) {
			refresh();
		}
	}
}