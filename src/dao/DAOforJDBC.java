package dao;

import hermes.data.loader.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class DAOforJDBC<T> implements DAO<T> {

	public T retrieve(long i){
		try {
			Connection conn;
			conn = DBConnection.getDBConnection();
			Statement st = conn.createStatement();
			String sql = String.format("SELECT * FROM %s WHERE ID = %d;", getTableName(), i);
			ResultSet result = st.executeQuery(sql);
			if (!result.next()){
			    return null;
			}
			T n = buildFromSqlResult(result);
			st.close();
			return n;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<T> findAll(){
		try {
			Connection conn =  DBConnection.getDBConnection();
			Statement st = conn.createStatement();
			ResultSet result = st.executeQuery(String.format("SELECT * FROM %s;", getTableName()));
			List<T> objects = new ArrayList<T>();
			while (result.next()){
				objects.add(buildFromSqlResult(result));
			}
			st.close();
			return objects;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void persist(T obj){
		try{
			Connection conn =  DBConnection.getDBConnection();
			String sql;
			if (exists(obj)){
				sql = String.format("UPDATE %s SET %s WHERE %s", getTableName(), prepareForUpdate(obj), prepareWhere(obj));
			} else {
				String fields = String.join(",", getFieldNames());
				sql = String.format("INSERT INTO %s(%s) VALUES(%s)", getTableName(), fields, prepareValues(obj));
			}
			Statement st = conn.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean exists(T obj){
		try{
			Connection conn =  DBConnection.getDBConnection();
			String sql;
			sql = String.format("SELECT * FROM %s WHERE %s;", getTableName(), prepareWhere(obj));
			Statement st = conn.createStatement();
			return st.executeQuery(sql).next();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void delete(T obj){
		try{
			Connection conn =  DBConnection.getDBConnection();
			String sql;
			sql = String.format("DELETE FROM %s WHERE %s;", getTableName(), prepareWhere(obj));
			Statement st = conn.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract String prepareWhere(T obj);
	protected abstract String prepareForUpdate(T obj);
	protected abstract String prepareValues(T obj);
	protected abstract List<String> getFieldNames();
	protected abstract String getTableName();
	protected abstract T buildFromSqlResult(ResultSet result) throws SQLException;

}
