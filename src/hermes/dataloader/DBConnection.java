package hermes.dataloader;

import hermes.enums.Category;
import hermes.enums.Content;
import hermes.enums.Context;
import hermes.enums.Kid;
import hermes.enums.Tag;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
import java.util.Random;

public class DBConnection {
	private static Connection connection;
	private static String dbName = "hermes.db";
	
	private static void loadData() throws SQLException{
		
		Random random = new Random();
		
		for (int i = 0; i < 50; i++){
			Notification n = new Notification();
			n.setDateTimeSended(new GregorianCalendar(2013 + random.nextInt(3),
											   random.nextInt(12),
											   random.nextInt(28),
											   random.nextInt(24),
											   random.nextInt(60),
											   random.nextInt(60)).getTime());
			n.setDateTimeReceived(new GregorianCalendar(2013 + random.nextInt(3),
											   random.nextInt(12),
											   random.nextInt(28),
											   random.nextInt(24),
											   random.nextInt(60),
											   random.nextInt(60)).getTime());
			n.setContent(Content.values()[random.nextInt(Content.values().length)]);
			n.setContext(Context.values()[random.nextInt(Context.values().length)]);
			n.setCategory(Category.values()[random.nextInt(Category.values().length)]);
			n.setKid(Kid.values()[random.nextInt(Kid.values().length)]);
			new NotificationDAOforJDBC().persist(n);
		}
		
	}
	
	public static Connection getDBConnection() throws SQLException {
		File file = new File (dbName);
		boolean alreadyExists = file.exists();
		
		if(connection == null){		   
//			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
			connection.setAutoCommit(true);
		   
			System.out.println("Opened database successfully");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			if (!alreadyExists){
				String sql = "CREATE TABLE Notifications" +
						"(ID 			 INTEGER 	PRIMARY KEY NOT NULL," +
						" kid_id         INT    			NOT NULL, " + 
						" sended         VARCHAR(20)     	NOT NULL, " +
						" received       VARCHAR(20)     	, " +
						" content_id     INT     			NOT NULL, " +
						" category_id    INT, " +
						" context_id     INT);"
				+ "CREATE TABLE Tags" +
						"(ID 			 INTEGER 	PRIMARY KEY NOT NULL," +
						" name         VARCHAR(20)     	NOT NULL);";
				statement.executeUpdate(sql);
				loadData();				
			}
			statement.close();		   
   
			System.out.println("Creation of notifications table");			   
	   }
	   return connection;	   	  	  
	}
	
	public static void closeDBConnection() throws SQLException{
		connection.close();
		System.out.println("Database connection succesfully closed!");
	}
		
}
