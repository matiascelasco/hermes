package hermes.model.dao;

//import hermes.model.loader.JsonLoader;
//import hermes.model.loader.JsonFileLoader;
//import hermes.model.loader.Loader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.IOUtils;

public class ConnectionWrapper {
	private Connection connection;
	private static String dbName = "hermes.db";

	public void prepare(){
		//user directory
		File homeDir = new File(System.getProperty("user.home"));
		//HermesDB directory create
		File dir = new File(homeDir, "HermesDB");

		boolean alreadyExists = dir.exists();
		if (!dir.exists() && !dir.mkdirs()) {
		    throw new RuntimeException("Unable to create " + dir.getAbsolutePath());
		}
		else{
			//File file = new File (dir+"/"+dbName);
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
//				Loader loader = new JsonFileLoader();
//				loader.load();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void createSchema(){
		try {
			InputStream in = ConnectionWrapper.class.getResourceAsStream("/schema.sql");
			String sql = IOUtils.toString(in, StandardCharsets.UTF_8.name());
			executeUpdate(sql);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
