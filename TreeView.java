import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
 

/*
 * Implements singleton pattern
 */
public class TreeView extends JPanel implements ActionListener {
    private DynamicTree treePanel;
    
    private JButton addUserButton;
    private JTextField addUserTextField;
    
    private JButton addGroupButton;
    private JTextField addGroupTextField;
    
    private JButton userViewButton;
    private JButton userTotalButton;
    private JButton groupTotalButton;
    private JButton messageTotalButton;
    private JButton percentageButton;
    

    private JButton verification;
    private JButton lastUpdatedUser;
    
    private Admin adminInstance;
    
    private List<UserView> userViewList;
    
    

    private ArrayList<Visitable> items;
    
    private boolean isValid;
    
    public TreeView() {
        super(new BorderLayout());
        
        this.userViewList = new ArrayList<UserView>();
        
        this.items = new ArrayList<Visitable>();
        
        

        treePanel = new DynamicTree();        
         

        addUserButton = new JButton("Add User");
        addUserButton.setPreferredSize(new Dimension(100, 30));
        addUserButton.addActionListener(this);
        

        addUserTextField = new JTextField();
        addUserTextField.setPreferredSize(new Dimension(150, 30));
        addUserTextField.setForeground(Color.gray);
        addUserTextField.setText("User Id");
        

        addGroupButton = new JButton("Add Group");
        addGroupButton.setPreferredSize(new Dimension(100, 30));
        addGroupButton.addActionListener(this);
        

        addGroupTextField = new JTextField();
        addGroupTextField.setPreferredSize(new Dimension(150, 30));
        addGroupTextField.setForeground(Color.gray);
        addGroupTextField.setText("Group Id");

        userViewButton = new JButton("Open User View");
        userViewButton.setPreferredSize(new Dimension(250, 30));
        userViewButton.addActionListener(this);

        userTotalButton = new JButton("Show User Total");
        userTotalButton.setPreferredSize(new Dimension(150, 30));
        userTotalButton.addActionListener(this);
        

        groupTotalButton = new JButton("Show Group Total");
        groupTotalButton.setPreferredSize(new Dimension(150, 30));
        groupTotalButton.addActionListener(this);
        

        messageTotalButton = new JButton("Show Message Total");
        messageTotalButton.addActionListener(this);
        

        percentageButton = new JButton("Show Positive Percentage");
        percentageButton.addActionListener(this);

        verification = new JButton("Verification");
        verification.addActionListener(this);


        lastUpdatedUser = new JButton("Last Update");
        lastUpdatedUser.addActionListener(this);
        

        treePanel.setPreferredSize(new Dimension(300, 150));
        add(treePanel, BorderLayout.CENTER);
 
        JPanel panel = new JPanel(new GridLayout(0,3));
        JPanel panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(350,300));
        
        panel2.add(addUserButton);
        panel2.add(addUserTextField);
        panel2.add(addGroupButton);
        panel2.add(addGroupTextField);
        panel2.add(userViewButton);
        panel2.add(userTotalButton);
        panel2.add(groupTotalButton);
        panel2.add(messageTotalButton);
        panel2.add(percentageButton);
        panel2.add(verification);
        panel2.add(lastUpdatedUser);
        add(panel, BorderLayout.SOUTH);  
        add(panel2, BorderLayout.EAST);
    }
    
    

   public String getUserGroup() {
	   return addGroupTextField.getText();
   }
    
    public void actionPerformed(ActionEvent e) {    
        if(e.getSource() == addUserButton) {
        	treePanel.addObject(addUserTextField.getText());
        	
        	 //Get singleton instance from TreeView
        	this.adminInstance = Admin.getInstance();

        	isValid = this.adminInstance.validate(addUserTextField.getText());

        	this.adminInstance.addUser(addUserTextField.getText());
        	
        	System.out.println("User button");	
        }
        else if(e.getSource() == addGroupButton) {
        	DefaultMutableTreeNode parentNode = null;

            TreePath parentPath = treePanel.getCurrentTreeNode().getSelectionPath();
        	 parentNode = (DefaultMutableTreeNode)
                     (parentPath.getLastPathComponent());
        	 

        	treePanel.addObject(parentNode, addGroupTextField.getText());
        	
       	 	//Get singleton instance from Admin
        	this.adminInstance = Admin.getInstance();

        	isValid = this.adminInstance.validate(addGroupTextField.getText());

        	this.adminInstance.addUserGroup(addGroupTextField.getText());
        	
        	System.out.println("User group button");
        }
        else if(e.getSource() == userViewButton) {
        	 TreePath parentPath = treePanel.getCurrentTreeNode().getSelectionPath();
   
        	 if(parentPath != null) {
              	UserView newUserView = new UserView(parentPath.getLastPathComponent().toString());
              	
              	userViewList.add(newUserView);
              	newUserView.setUserViewList(userViewList);	
              	
        	 }
        	 else {
        		 System.out.println("No user selected");
        	 } 
        	System.out.println("User view button");
        }
        else if(e.getSource() == userTotalButton) {
        	//Get singleton instance from Admin
        	this.adminInstance = Admin.getInstance();
        	

        	JOptionPane.showMessageDialog(null, this.adminInstance.getTotalUsers(),"User Total", JOptionPane.PLAIN_MESSAGE);
        	System.out.println("User total button");
        }
        else if(e.getSource() == groupTotalButton) {
        	//Get singleton instance from Admin
        	this.adminInstance = Admin.getInstance();

        	JOptionPane.showMessageDialog(null, this.adminInstance.getTotalUserGroups(),"User Group Total", JOptionPane.PLAIN_MESSAGE);
        	System.out.println("Group total button");
        }
        else if(e.getSource() == messageTotalButton) {
        	//Get singleton instance from Admin
        	this.adminInstance = Admin.getInstance();

        	JOptionPane.showMessageDialog(null, this.adminInstance.getMessageTotal(),"Total Message(s)", JOptionPane.PLAIN_MESSAGE);
        	System.out.println("Message total button");
        }
        else if(e.getSource() == percentageButton) {
        	//Get singleton instance from Admin
        	this.adminInstance = Admin.getInstance();
        	

            for(Visitable item: items) {
              item.accept(this.adminInstance);
              System.out.println("ITEM: " + item);
            }
            
            
            float positivePercentage = this.adminInstance.getPositivePercentage();
           

        	JOptionPane.showMessageDialog(null, positivePercentage,"Positive Percentage(%)", JOptionPane.PLAIN_MESSAGE);
        	System.out.println("Positive percentage button");
       } 
       else if(e.getSource() == verification) {
    	   this.adminInstance = Admin.getInstance();

    	   if(isValid == true) {
    		   System.out.println("User id is valid");
    	   }
    	   else {

    		  JOptionPane.showMessageDialog(null, "The id either contains a space or is already taken", "Not Valid", JOptionPane.ERROR_MESSAGE);
          	  System.out.println("User id is not valid");   
    		   	
    	   }
    	   System.out.println("Verification button");
       }
       else if(e.getSource() == lastUpdatedUser) {    	   

       	   JOptionPane.showMessageDialog(null, "On the console", "Last Updated User", JOptionPane.PLAIN_MESSAGE);
    	  
       	   System.out.println("Last updated user button");
       }
    }
 

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Admin Control Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        TreeView newContentPane = new TreeView();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
         
        frame.pack();
        frame.setVisible(true);
    }
}