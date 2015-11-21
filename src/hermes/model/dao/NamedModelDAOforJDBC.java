package hermes.model.dao;

import hermes.model.NamedObject;

import java.util.ArrayList;
import java.util.List;

import dao.DAOforJDBC;

public abstract class NamedModelDAOforJDBC<T extends NamedObject> extends DAOforJDBC<T>{

	@Override
	protected String prepareWhere(T obj) {
		return String.format("ID = %d", obj.getId());
	}

	@Override
	protected String prepareForUpdate(T obj) {
		return String.format("name = '%s'", obj.getName());
	}

	@Override
	protected String prepareValues(T obj) {
		return String.format("'%s'", obj.getName());
	}

	@Override
	protected List<String> getFieldNames() {
		List<String> fields = new ArrayList<String>();
		fields.add("name");
		return fields;
	}

}
