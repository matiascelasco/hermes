package hermes.dataloader;

import hermes.enums.Category;
import hermes.enums.Content;
import hermes.enums.Context;
import hermes.enums.Kid;
import hermes.enums.Tag;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class DBConnection {
	private static Connection connection;
	private static String dbName = "hermes.db";
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	
	private static void loadData(String path) throws SQLException, NumberFormatException, IOException{
		
		//load from CSV file								
		CSVReader reader = new CSVReader(new FileReader(path + "/" + "hermes.csv"));
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
		
	}

	private static void generateRandomCSV(String path) throws IOException{
		CSVWriter writer = new CSVWriter(new FileWriter(path+"/"+"hermes.csv"));				
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
			//save the notification in data file
			String[] entries = (String.valueOf(n.getId())+"#"+ 
														   formatter.format(n.getDateTimeSended().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())+"#"+
														   formatter.format(n.getDateTimeReceived().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())+"#"+
														   String.valueOf(n.getContent().getId())+"#"+
														   String.valueOf(n.getContext().getId())+"#"+
														   String.valueOf(n.getCategory().getId())+"#"+
														   String.valueOf(n.getKid().getId())+"#"
												           ).split("#"); 
		    writer.writeNext(entries);					
		}
		writer.close();
	}
	     
	public static Connection getDBConnection() throws SQLException {
		//user directory
		File homeDir = new File(System.getProperty("user.home"));		
		//HermesDB directory create
		File dir = new File(homeDir, "HermesDB");		
		
		boolean alreadyExists = dir.exists();
		if (!dir.exists() && !dir.mkdirs()) {			
		    System.out.println("Unable to create " + dir.getAbsolutePath());
		}
		else{
			File file = new File (dir+"/"+dbName);	
			//alreadyExists = file.exists();
		}			
				
		if(connection == null){
//			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + dir+"/"+dbName);
			connection.setAutoCommit(true);

			System.out.println("Opened database successfully");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			String sql = "PRAGMA foreign_keys = ON;";
			if (!alreadyExists){
				sql += 
						
					"CREATE TABLE Tags (" +
						"ID           INTEGER 	     PRIMARY KEY NOT NULL," +
						"name         VARCHAR(20)    NOT NULL" +
					");" +
							
					"CREATE TABLE Notifications (" +
						"ID             INTEGER      PRIMARY KEY NOT NULL," +
						"kid_id         INT          NOT NULL," +
						"sended         VARCHAR(20)  NOT NULL," +
						"received       VARCHAR(20), " +
						"content_id     INT          NOT NULL," +
						"category_id    INT," +
						"tag_id         INT," +       
						"context_id     INT," +
						"FOREIGN KEY(tag_id) REFERENCES Tags(ID) ON DELETE SET NULL" +
					");";
				statement.executeUpdate(sql);
				try {
					generateRandomCSV(dir.getAbsolutePath());
					loadData(dir.getAbsolutePath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				statement.execute(sql);
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
