package hermes.dataloader;

import hermes.enums.Tag;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DAOforJDBC;

public class TagDAOforJDBC extends DAOforJDBC<Tag>{

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
		Tag tag = new Tag();
		tag.setId(result.getInt("ID"));
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

	
}
