package dao;


import hermes.model.dao.ConnectionWrapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class DAOforJDBC<T> implements DAO<T> {

	private ConnectionWrapper connection;
	
	public void setConnectionWrapper(ConnectionWrapper connection){
		this.connection = connection;
	}
	
	public T retrieve(long id){
		try {
			String sql = String.format("SELECT * FROM %s WHERE ID = %d;", getTableName(), id);
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			if (!result.next()){
			    return null;
			}
			T n = buildFromSqlResult(result);
			statement.close();
			return n;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<T> findAll(){
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(String.format("SELECT * FROM %s;", getTableName()));
			List<T> objects = new ArrayList<T>();
			while (result.next()){
				objects.add(buildFromSqlResult(result));
			}
			statement.close();
			return objects;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void persist(T obj){
		String sql;
		if (exists(obj)){
			sql = String.format("UPDATE %s SET %s WHERE %s", getTableName(), prepareForUpdate(obj), prepareWhere(obj));
		} else {
			String fields = String.join(",", getFieldNames());
			sql = String.format("INSERT INTO %s(%s) VALUES(%s)", getTableName(), fields, prepareValues(obj));
		}
		connection.executeUpdate(sql);
	}

	public boolean exists(T obj){
		try{
			String sql = String.format("SELECT * FROM %s WHERE %s;", getTableName(), prepareWhere(obj));
			Statement statement = connection.createStatement();
			boolean answer = statement.executeQuery(sql).next();
			statement.close();
			return answer;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void delete(T obj){
		String sql;
		sql = String.format("DELETE FROM %s WHERE %s;", getTableName(), prepareWhere(obj));
		connection.executeUpdate(sql);
	}

	protected abstract String prepareWhere(T obj);
	protected abstract String prepareForUpdate(T obj);
	protected abstract String prepareValues(T obj);
	protected abstract List<String> getFieldNames();
	protected abstract String getTableName();
	protected abstract T buildFromSqlResult(ResultSet result) throws SQLException;

}
