package dao;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class EnumDAO<T extends Enum<T>> implements DAO<T> {

	private Set<T> values;
	
	public EnumDAO(Class<T> t){
		this.values = EnumSet.allOf(t);
	}
	
	@Override
	public T retrieve(long id) {
		for(T value: values){
			if (value.ordinal() == id){
				return value;
			}
		}
		return null;
	}

	@Override
	public List<T> findAll() {
		List<T> list = new ArrayList<T>();
		for(T value: values){
			list.add(value);
		}
		return list;
	}

	@Override
	public void persist(T obj) {}

	@Override
	public void delete(T obj) {}

}
