package hermes.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import hermes.model.Context;

public class ContextDAOforJDBC extends NamedModelDAOforJDBC<Context> {

	@Override
	protected String getTableName() {
		return "Contexts";
	}

	@Override
	protected Context buildFromSqlResult(ResultSet result) throws SQLException {
		int id = result.getInt("ID");
		Context obj = new Context();
		obj.setId(id);
		obj.setName(result.getString("name"));
		return obj;
	}

}