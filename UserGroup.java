import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

//Uses composite pattern
public class UserGroup implements SystemEntry, Visitable {
	private String id;
	private static int numOfUserGroups = 0;
	private List<User> userGroup;
	private Timestamp creationTime;

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
		System.out.println("creation time for user group: " + this.creationTime);
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}


	
	public UserGroup(String id){
		super();
		this.id = id;
		this.userGroup = new ArrayList<User>();
		numOfUserGroups ++;
	}
	

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	public void add(User user) {
		userGroup.add(user);
	}

	public int getTotalUsers() {
		// Do nothing 
		return 0;
	}

	public int getTotalUserGroups() {
		return numOfUserGroups;
	}


	public void accept(UserTypeVisitor visitor) {
		visitor.visit(this);
	}
	
	
}