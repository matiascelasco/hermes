package hermes.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import hermes.model.Category;

public class CategoryDAOforJDBC extends NamedModelDAOforJDBC<Category> {

	@Override
	protected String getTableName() {
		return "Categories";
	}

	@Override
	protected Category buildFromSqlResult(ResultSet result) throws SQLException {
		int id = result.getInt("ID");
		Category obj = new Category();
		obj.setId(id);
		obj.setName(result.getString("name"));
		return obj;
	}


}
