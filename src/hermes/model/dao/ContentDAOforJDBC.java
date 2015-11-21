package hermes.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import hermes.model.Content;

public class ContentDAOforJDBC extends NamedModelDAOforJDBC<Content> {

	@Override
	protected String getTableName() {
		return "Contents";
	}

	@Override
	protected Content buildFromSqlResult(ResultSet result) throws SQLException {
		int id = result.getInt("ID");
		Content obj = new Content();
		obj.setId(id);
		obj.setName(result.getString("name"));
		return obj;
	}
	
}
