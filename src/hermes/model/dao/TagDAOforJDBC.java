package hermes.model.dao;


import hermes.model.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DAOforJDBC;

class TagDAOforJDBC extends DAOforJDBC<Tag>{

	@Override
	protected String prepareValues(Tag obj) {
		return String.format("'%s'", obj.getName());
	}

	@Override
	protected List<String> getFieldNames() {
		List<String> names = new ArrayList<String>();
		names.add("name");
		return names;
	}

	@Override
	protected String getTableName() {
		return "Tags";
	}

	@Override
	protected Tag buildFromSqlResult(ResultSet result) throws SQLException {
		int id = result.getInt("ID");
		Tag tag = new Tag();
		tag.setId(id);
		tag.setName(result.getString("name"));
		return tag;
	}

	@Override
	protected String prepareWhere(Tag obj) {
		return String.format("ID = %d", obj.getId());
	}

	@Override
	protected String prepareForUpdate(Tag obj) {
		return String.format("name = '%s'", obj.getName());
	}
	
	@Override
	public boolean exists(Tag tag){
		if (tag.getId() == 0){
			return false;
		}
		return super.exists(tag);
	}
	
}
