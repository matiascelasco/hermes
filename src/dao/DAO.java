package dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
	
	 public T retrieve(long i) throws SQLException;

	 public List<T> findAll() throws SQLException;

	 public void persist(T obj) throws SQLException;
	 
//	 void delete(T obj);

//	 void deleteAll();
	 
//	 long count();

}