package hermes.dataloader;

import hermes.enums.Category;
import hermes.enums.Content;
import hermes.enums.Context;
import hermes.enums.Kid;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.opencsv.CSVReader;

public class DBConnection {
	private static Connection connection;
	private static String dbName = "hermes.db";

	private static void loadData() throws SQLException{

		//load from CSV file
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				
				try{
				CSVReader reader = new CSVReader(new FileReader("hermes.csv"));
			     String [] nextLine;
			     while ((nextLine = reader.readNext()) != null) {
			        //creates notification with readed data	   
			    	Date sended;
			    	Date received;	    	
			    	int content_id, context_id, category_id, kid_id;
			    	//id sended received content_id context_id, category_id, kid_id
			    	
			    	content_id = Integer.valueOf(nextLine[3]);
			    	context_id = Integer.valueOf(nextLine[4]);
			    	category_id = Integer.valueOf(nextLine[5]);
			    	kid_id = Integer.valueOf(nextLine[6]);	    	
			    	sended = Date.from(LocalDate.parse(nextLine[1], formatter).atStartOfDay(ZoneId.systemDefault()).toInstant());
			    	received = Date.from(LocalDate.parse(nextLine[2], formatter).atStartOfDay(ZoneId.systemDefault()).toInstant());            
			    	
			    	
			    	Notification n = new Notification();
			    	n.setDateTimeSended(sended);
			    	n.setDateTimeReceived(received);
			    	n.setKid(Kid.values()[kid_id-1]);
			    	n.setContent(Content.values()[content_id-1]);
			    	n.setCategory(Category.values()[category_id-1]);
			    	n.setContext(Context.values()[context_id-1]);	    	    
			    	
					FactoryDAO.getNotificationDAO().persist(n);
			     }
			     reader.close();
				//persist				
				}
				catch(IOException e){
					System.out.println("error trying reading data from CSV file");
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
				String sql = 
					"PRAGMA foreign_keys = ON;CREATE TABLE Tags" +
							"(ID 			 INTEGER 	PRIMARY KEY NOT NULL," +
							" name         VARCHAR(20)     	NOT NULL);" +
					"CREATE TABLE Notifications (" +
						"ID             INTEGER      PRIMARY KEY NOT NULL," +
						"kid_id         INT          NOT NULL," +
						"sended         VARCHAR(20)  NOT NULL," +
						"received       VARCHAR(20), " +
						"content_id     INT          NOT NULL," +
						"category_id    INT," +
						"tag_id         INT," +       
						"context_id     INT," +
						"FOREIGN KEY(tag_id) REFERENCES Tags(ID) ON DELETE SET NULL);";
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
