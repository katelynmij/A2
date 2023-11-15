import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//Leaf class of composite pattern
public class User implements SystemEntry, Observer, Visitable{
	private String id;
	private static int numOfUsers = 0;
	private static int numOfMessages = 0;
	private HashMap<String, List<String>> followers;
	private HashMap<String, List<String>> messages;
	private List<String> follower;
	private List<String> message;
	private List<String> listOfFollowers;
	private List<String> listOfMessages;
	private TwitterNewsFeed twitterNewsFeed;
	
	private Timestamp creationTime;
	private Timestamp lastUpdateTime;
	private String lastUpdateUser;

	public User(String id){
		super();
		this.id = id;
		this.followers = new HashMap<String, List<String>>();
		this.messages = new HashMap<String, List<String>>();
		this.follower = new ArrayList<String>();
		this.message = new ArrayList<String>();
		this.twitterNewsFeed = new TwitterNewsFeed();
		
		numOfUsers++;
	}
		
	
	public String getId() {
		return this.id;
	}

	public String toString() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	
	
	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}
	
	
	public Timestamp getCreationTime() {
		return creationTime;
	}
	


	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}
	

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	
	

	public void follow(Observer user) {
		follower.add(user.toString());
		 Arrays.asList(follower);
		 
		//Add followers to current user
		followers.put(getId(), follower);
		
		twitterNewsFeed.attach(user);
		System.out.println(getId() + " now following " + user.toString());
	}
	

	public List<String> getFollowers() {
		this.listOfFollowers = new ArrayList<String>();
		
		for (String key : followers.keySet()) {
		    for (String r : followers.get(key)) {
		    	listOfFollowers.add(r.toString());    
		    }
		}
		 return listOfFollowers;	
	}
	
	
	public List<String> getMessages(){
		this.listOfMessages = new ArrayList<String>();
		
		for (String key : messages.keySet()) {
		    for (String r : messages.get(key)) {
		    	listOfMessages.add(r.toString());    
		    }
		}
		return listOfMessages;
	}

	
	public void postMessage(String message) {
		String post;
		numOfMessages++;	
		
	
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		setLastUpdateTime(timestamp);
		System.out.println("UPDATE TIME: " + getLastUpdateTime());	
		

		lastUpdateUser = getId();
		
		post = getId() + ": " + message;
		this.message.add(post);
		Arrays.asList(this.message);
		
		messages.put(getId(), this.message);
		  
		System.out.println("LAST UPDATED USER: " + getId());			
	    twitterNewsFeed.notifyObservers(getId(), message, getLastUpdateTime());  
	    twitterNewsFeed.displayNewsFeed(messages);
	}

	@Override
	public void update(String sender, String obs, String message, Timestamp lastUpdateTime) {
		String post;

		if(message == null) {
			System.out.println("No message");
		}
		else {
			numOfMessages++; 
			
			post = sender + ": " + message;
			this.message.add(post);
			Arrays.asList(this.message);
			

			messages.put(obs, this.message);
			
	
			setLastUpdateTime(lastUpdateTime);
			
			System.out.println("Message to " + obs + " from " + sender + ": " + message);
			System.out.println("FOLLWER LAST UPDATE TIME: " + getLastUpdateTime());
		
		}		
	}
	


	public int getTotalMessages() {
		return numOfMessages;
	}
		

	@Override
	public int getTotalUsers() {
		return numOfUsers;
	}

	@Override
	public int getTotalUserGroups() {
		return 0;
	}


	@Override
	public void accept(UserTypeVisitor visitor) {
		visitor.visit(this);
	}
}