package hermes.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import hermes.model.Kid;

public class KidDAOforJDBC extends NamedModelDAOforJDBC<Kid> {

	@Override
	protected String getTableName() {
		return "Kids";
	}

	@Override
	protected Kid buildFromSqlResult(ResultSet result) throws SQLException {
		int id = result.getInt("ID");
		Kid obj = new Kid();
		obj.setId(id);
		obj.setName(result.getString("name"));
		return obj;
	}

}
