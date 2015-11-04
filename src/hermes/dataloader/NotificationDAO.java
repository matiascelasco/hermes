package hermes.dataloader;

import java.util.List;

public class NotificationDAO {
	
	public static void persist(Notification n){
		//insert a notification object
	}
	
	public static Notification retrieve(int id){
		return null;
	}
	
	public static void delete(Notification n){
		//delete from database
	}
	
	public static List<Notification> findAll(){
		//retrieve a list of notifications stored in the database
		return null;
	}
	
	public static void finish(){
		DBConnection.closeDBConnection();	
	}
}
