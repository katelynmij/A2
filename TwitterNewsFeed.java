import java.util.List;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;


public class TwitterNewsFeed implements Subject{
	
	private List<Observer> observers;
	
	public TwitterNewsFeed() {
		this.observers = new ArrayList<>();
	}
	
	
	
	public void displayNewsFeed(HashMap<String, List<String>> messages) {
		System.out.println("= News Feed =");
		messages.entrySet().forEach(entry -> {			
		    System.out.println(entry.getKey() + ": " + entry.getValue());
		});
		
	}
		


	public void attach(Observer obs) {
		observers.add(obs);
	}
	
	

	public void detach(Observer obs) {
		observers.remove(obs);
	}
	

	public void notifyObservers(String sender, String message, Timestamp lastUpdateTime ) {
		System.out.println("Notified followers");
		
		if(!observers.isEmpty() && observers != null) {
			for (Observer obs : observers) {
				obs.update(sender, obs.toString(), message);
			}
			
		}
		else {
			System.out.println("no post");
		}
	}

	
	
}