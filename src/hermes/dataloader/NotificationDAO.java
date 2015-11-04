package hermes.dataloader;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class NotificationDAO {
	
	public static void persist(Notification n){
		Connection conn =  DBConnection.getDBConnection();
		//prepare "values" part of script 
		String values = String.valueOf(n.getId()) +","+ //id notification
				        String.valueOf(n.getKid().getId())+","+ //kid_id
				        "\'"+n.getKid().getName()+"\'"+","+ //kid_name
						"\'"+String.valueOf(n.getDateTimeSended())+"\'"+","+
						"\'"+String.valueOf(n.getDateTimeReceived())+"\'"+","+
						String.valueOf(n.getContent().getId())+","+
						"\'"+n.getContent().getDescription()+"\'"+","+
						String.valueOf(n.getCategory().getId())+","+
						"\'"+n.getCategory().getDescription()+"\'"+","+
						String.valueOf(n.getContext().getId())+","+
						"\'"+n.getContext().getDescription()+"\'";		
				        
		if(conn != null){
			try{							
				Statement st = conn.createStatement();
				String sql = "insert into Notifications(ID, kid_id, kid, sended, received,"+
				             "content_id, content, category_id, category, context_id, context)"+
						     " values("+values+");";
				System.out.println(sql);
				st.executeUpdate(sql);
			} catch ( Exception e ) {
				System.err.println( "something went wrong: "+e.getClass().getName() + ": " + e.getMessage() );
				DBConnection.closeDBConnection();
			}		
		}
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
