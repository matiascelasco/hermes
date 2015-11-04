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
			   connection.setAutoCommit(false);
			   
			   System.out.println("Opened database successfully");
			   Statement statement = connection.createStatement();
			   statement.setQueryTimeout(30);  // set timeout to 30 sec.
			   statement.executeUpdate("drop table if exists notifications");
			   System.out.println("Drop notifications table if exists");
			   
			   String sql = "CREATE TABLE Notifications " +
					   		"(ID 			 INT 	PRIMARY KEY NOT NULL," +
					   		" kid_id         INT    			NOT NULL, " + 
					   		" kid       	 VARCHAR(50)     	NOT NULL, " + 
					   		" sended         VARCHAR(20)     	NOT NULL, " +
					   		" received       VARCHAR(20)     	, " +
					   		" content_id     INT     			NOT NULL, " +
					   		" content        VARCHAR(50), " +
					   		" category_id    INT, " +
					   		" category       VARCHAR(50), " +
					   		" context_id     INT, " +
					   		" context        VARCHAR(50)) ";					   						   	
			   statement.executeUpdate(sql);
			   System.out.println("Creation of notification table");			   
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
