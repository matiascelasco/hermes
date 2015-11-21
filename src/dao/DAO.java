package dao;

import java.util.List;

public interface DAO<T> {
	
	 public T retrieve(long i);

	 public List<T> findAll();

	 public void persist(T obj);
	 
	 void delete(T obj);

	 public T retrieveByStringOrCreate(String fieldName, String value);

	 public T retrieveByString(String fieldName, String value);

	 public T retrieveByInt(String fieldName, long value);

//	 void deleteAll();
	 
//	 long count();

}