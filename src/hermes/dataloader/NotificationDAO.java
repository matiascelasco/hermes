package hermes.dataloader;

import hermes.enums.Category;
import hermes.enums.Content;
import hermes.enums.Context;
import hermes.enums.Kid;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationDAO {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	private static String [] fieldNames = {
		"sended",
		"received",
		"content_id",
		"context_id",
		"category_id",
		"kid_id"
	};

	public static void persist(Notification n) throws SQLException{
		Connection conn =  DBConnection.getDBConnection();

		String values = String.format("'%s', '%s', %d, %d, %d, %d",
			formatter.format(n.getDateTimeSended().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()),
			formatter.format(n.getDateTimeReceived().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()),
    		n.getContent().ordinal(),
    		n.getContext().ordinal(),
			n.getCategory().ordinal(),
			n.getKid().ordinal()
		);

		String fields = String.join(",", fieldNames);

		String sql = String.format("insert into Notifications(%s) values(%s)", fields, values);

		Statement st = conn.createStatement();
		st.executeUpdate(sql);
	}

	public static Notification retrieve(int id) throws SQLException{
		//retrieve a notification with identifier ID = id		
		Connection conn =  DBConnection.getDBConnection();
		Statement st = conn.createStatement();
		ResultSet result = st.executeQuery("SELECT * FROM Notifications WHERE (ID = "+String.valueOf(id)+");");		
		Notification n = buildNotificationFromSqlResult(result);		
		st.close();
		return n;
	}

	public static void delete(Notification n){
		//delete from database
		
	}

	public static List<Notification> findAll() throws SQLException{
		//retrieve a list of notifications stored in the database
		Connection conn =  DBConnection.getDBConnection();
		Statement st = conn.createStatement();
		ResultSet result = st.executeQuery("SELECT * FROM Notifications;");
		List<Notification> notifications = new ArrayList<Notification>();
		while (result.next()){
			notifications.add(buildNotificationFromSqlResult(result));
		}
		st.close();
		return notifications;
	}

	private static Notification buildNotificationFromSqlResult(ResultSet result) throws SQLException {
		Notification n = new Notification();

		Date sended = Date.from(LocalDate.parse(result.getString("sended"), formatter).atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date received = Date.from(LocalDate.parse(result.getString("received"), formatter).atStartOfDay(ZoneId.systemDefault()).toInstant());

		n.setDateTimeSended(sended);
		n.setDateTimeReceived(received);
		n.setCategory(Category.values()[result.getInt("category_id")]);
		n.setContext(Context.values()[result.getInt("context_id")]);
		n.setContent(Content.values()[result.getInt("content_id")]);
		n.setKid(Kid.values()[result.getInt("kid_id")]);
		return n;
	}

}
