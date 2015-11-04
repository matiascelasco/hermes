package hermes.dataloader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {
	private static Connection connection;
	
	public static Connection getDBConnection(){
	   if(connection == null){		   
		   try {
			   Class.forName("org.sqlite.JDBC");
			   connection = DriverManager.getConnection("jdbc:sqlite:hermes.db");
			   connection.setAutoCommit(true);
			   
			   System.out.println("Opened database successfully");
			   Statement statement = connection.createStatement();
			   
			   statement.executeUpdate("drop table if exists Notifications");
			   System.out.println("Drop notifications table if exists");
			   
			   String sql = "CREATE TABLE Notifications" +
					   		"(ID 			 INTEGER 	PRIMARY KEY NOT NULL," +
					   		" kid_id         INTEGER    			NOT NULL, " + 
					   		" kid       	 TEXT     	NOT NULL, " + 
					   		" sended         TEXT     	NOT NULL, " +
					   		" received       TEXT     	, " +
					   		" content_id     INTEGER     			NOT NULL, " +
					   		" content        TEXT, " +
					   		" category_id    INTEGER, " +
					   		" category       TEXT, " +
					   		" context_id     INTEGER, " +
					   		" context        TEXT); ";	
			   System.out.println(sql);
			   statement.executeUpdate(sql);
			   statement.close();		   
			   
			   System.out.println("Creation of notifications table");			   
		   } catch ( Exception e ) {
			   System.err.println( "something went wrong: "+e.getClass().getName() + ": " + e.getMessage() );	    
		   }		   
	   }
	   return connection;	   	  	  
	}
	
	public static void closeDBConnection(){
		if(connection != null){
			try{
				connection.close();
				System.out.println("Database connection succesfully close!");
			}
			catch ( Exception e ) {
				System.err.println( "something went wrong: "+e.getClass().getName() + ": " + e.getMessage() );	
			}
		}
		
	}
		
}
