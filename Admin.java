import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

/**
 * Implementation of Visitor pattern 
 */
public class Admin extends TreeView implements SystemEntry, UserTypeVisitor{
	private List<SystemEntry> user;
	private List<SystemEntry> userGroup;
	private List<String> userList;
	private List<String> userGroupList;
	private User currentUser;
	private UserGroup currentUserGroup;
	protected static Admin adminInstance;
	private static float totalPositiveFound = 0;
	
	private float positivePercentage;
	
    public Admin() {
    	user = new ArrayList<SystemEntry>();
    	userGroup = new ArrayList<SystemEntry>();
    	userList = new ArrayList<String>();
    	userGroupList =  new ArrayList<String>();
    }
    
    /*
     * Singleton instance created
     */
    public static Admin getInstance() {
		if (adminInstance == null) {
			adminInstance = new Admin();
		}
		return adminInstance;
	}
    
 
    public void addUser(String user) {
    	this.currentUser = new User(user);
    	this.user.add(this.currentUser);
    	this.userList.add(user);

		//creation time
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		this.currentUser.setCreationTime(timestamp);
    	

  
    	
		System.out.println("Total users: " + getTotalUsers());
	 	System.out.println("Added " + user + " user");
	}
    

    public void addUserGroup(String userGroup) {
    	this.currentUserGroup = new UserGroup(userGroup);
    	this.userGroup.add(this.currentUserGroup);
    	this.userGroupList.add(userGroup);

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    	


    
    	System.out.println("Total user groups: " + getTotalUserGroups());
    	System.out.println("Added " + userGroup + " user group");
    }

    public List<SystemEntry> getUser() {
    	return this.user;
    }
    

    public int getMessageTotal() {
    	if(this.currentUser == null) {
    		return 0;
    	}
    	else {
    		return this.currentUser.getTotalMessages();
    	}
    }

    public float getPositivePercentage() {
    	if(this.currentUser == null) {
    		return positivePercentage;
    	}
    	else {
    		visit(this.currentUser);
    		return positivePercentage;
    	}
    }
    
    /*
	 * Composite method
	 */
	@Override
	public int getTotalUsers() {
		return user.size();
	}
	
	/*
	 * Composite method
	 */ 
	@Override
	public int getTotalUserGroups(){
		return userGroup.size();
		
	}

	/*
	 * From Visitor class
	 */
	@Override
	public void visit(User user) {
		List<String> checkMessages = user.getMessages();
		float totalMessages = user.getTotalMessages();	
		String[] positiveWords = {"happy", "good", "wonderful", "love", "lovely",
				"winner", "exceptional", "great", "awesome"};
		String[] words = new String[] {};
		
		 if(totalMessages == 0) {
		 }
		 else {
				for(int i = 0; i < checkMessages.size(); i++) {
					words = checkMessages.get(i).split("\\W+");	
				}
				
				 for (String word: words) {
					 for (String positiveWord: positiveWords) {
				           if(word.contains(positiveWord)) { 
				        	   totalPositiveFound++;
				        	 
				           }
				     }
			     }
				positivePercentage = (float)((totalPositiveFound / totalMessages) * 100);
		 }
	}

	@Override
	public void visit(UserGroup userGroup) {

		
	}

	@Override
	public boolean validate(String id) {

		if(id.contains(" ")) {
			return false;
		}
		if(userList.contains(id) || userGroupList.contains(id)) {
			return false;
		}
		else {

			return true; 
		}
	}

	public String getLastUpdatedUser() {
		if(currentUser.getLastUpdateUser() == null) {
			return "N/A";
		} else {
			return currentUser.getLastUpdateUser();
		}
	}
	


}