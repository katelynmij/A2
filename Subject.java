import java.sql.Timestamp;
//Observer pattern
public interface Subject {
	public void attach(Observer observer);
	public void detach(Observer observer);
	public void notifyObservers(String sender, String message, Timestamp lastUpdateTime);
}