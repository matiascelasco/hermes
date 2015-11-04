package dao;

import hermes.dataloader.DBConnection;
import hermes.enums.Tag;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class DAOforJDBC<T> implements DAO<T> {

	public T retrieve(long i) throws SQLException {
		Connection conn =  DBConnection.getDBConnection();
		Statement st = conn.createStatement();
		ResultSet result = st.executeQuery(String.format("SELECT * FROM %s WHERE ID = %d;", getTableName(), i));		
		T n = buildFromSqlResult(result);
		st.close();
		return n;
	}

	public List<T> findAll() throws SQLException {
		Connection conn =  DBConnection.getDBConnection();
		Statement st = conn.createStatement();
		ResultSet result = st.executeQuery(String.format("SELECT * FROM %s;", getTableName()));
		List<T> objects = new ArrayList<T>();
		while (result.next()){
			objects.add(buildFromSqlResult(result));
		}
		st.close();
		return objects;
	}

	public void persist(T obj) throws SQLException {
		Connection conn =  DBConnection.getDBConnection();

		String fields = String.join(",", getFieldNames());

		String sql = String.format("INSERT INTO %s(%s) VALUES(%s)", getTableName(), fields, prepareValues(obj));

		Statement st = conn.createStatement();
		st.executeUpdate(sql);
	}
	
	protected abstract String prepareValues(T obj);
	protected abstract List<String> getFieldNames();
	protected abstract String getTableName();
	protected abstract T buildFromSqlResult(ResultSet result) throws SQLException;

}
