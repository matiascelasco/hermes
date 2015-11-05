package dao;

import java.util.List;

public interface DAO<T> {
	
	 public T retrieve(long i);

	 public List<T> findAll();

	 public void persist(T obj);
	 
	 void delete(T obj);

//	 void deleteAll();
	 
//	 long count();

}