package hermes.model.loader;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionWrapper {
	private Connection connection;
	private static String dbName = "hermes.db";

	public ConnectionWrapper(){
		//user directory
		File homeDir = new File(System.getProperty("user.home"));
		//HermesDB directory create
		File dir = new File(homeDir, "HermesDB");

		boolean alreadyExists = dir.exists();
		if (!dir.exists() && !dir.mkdirs()) {
		    throw new RuntimeException("Unable to create " + dir.getAbsolutePath());
		}
		else{
			new File (dir+"/"+dbName);
			//alreadyExists = file.exists();
		}

		try {
			//Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + dir+"/"+dbName);
			connection.setAutoCommit(true);

			Statement statement = connection.createStatement();
			statement.executeUpdate("PRAGMA foreign_keys = ON;");
			statement.close();
			if (!alreadyExists){
				createSchema();
				Loader loader = new CSVLoader(dir.getAbsolutePath());
				loader.load();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void createSchema(){
		String sql =

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
			");"
		;
		executeUpdate(sql);
	}

	public void executeUpdate(String sql){
		try {
			Statement statement;
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			statement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Statement createStatement() throws SQLException{
		return connection.createStatement();
	}

//	public ResultSet executeQuery(String sql){
//		try {
//			Statement statement = connection.createStatement();
//			ResultSet result = statement.executeQuery(sql);
//			statement.close();
//			return result;
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//
//	}

	public void close() throws SQLException{
		//TODO: bad smell detected: this code is never used :S
		connection.close();
	}

}
