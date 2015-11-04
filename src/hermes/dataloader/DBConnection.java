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
			   System.out.println("Opened database successfully");
			   Statement statement = connection.createStatement();
			   statement.setQueryTimeout(30);  // set timeout to 30 sec.
			   statement.executeUpdate("drop table if exists notifications");
			   System.out.println("Drop notifications table if exists");
			   statement.executeUpdate("create table notifications (id integer, name string)");
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
