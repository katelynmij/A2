import java.sql.Timestamp;

//Observer pattern


public interface Observer {
	public void update(String sender, String obs, String message, java.sql.Timestamp lastUpdateTime);

}