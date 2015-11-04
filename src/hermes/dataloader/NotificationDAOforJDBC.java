package hermes.dataloader;

import hermes.enums.Category;
import hermes.enums.Content;
import hermes.enums.Context;
import hermes.enums.Kid;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.DAOforJDBC;

public class NotificationDAOforJDBC extends DAOforJDBC<Notification>{

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	@Override
	protected String prepareValues(Notification obj) {
		return String.format("'%s', '%s', %d, %d, %d, %d",
			formatter.format(obj.getDateTimeSended().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()),
			formatter.format(obj.getDateTimeReceived().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()),
			obj.getContent().ordinal(),
			obj.getContext().ordinal(),
			obj.getCategory().ordinal(),
			obj.getKid().ordinal()
		);
	}

	@Override
	protected List<String> getFieldNames() {
		List<String> names = new ArrayList<String>();
		names.add("sended");
        names.add("received");
        names.add("content_id");
        names.add("context_id");
        names.add("category_id");
        names.add("kid_id");
        return names;
	}

	@Override
	protected String getTableName() {
		return "Notifications";
	}

	@Override
	protected Notification buildFromSqlResult(ResultSet result) throws SQLException {
		Notification n = new Notification();

		Date sended = Date.from(LocalDate.parse(result.getString("sended"), formatter).atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date received = Date.from(LocalDate.parse(result.getString("received"), formatter).atStartOfDay(ZoneId.systemDefault()).toInstant());
		n.setId(result.getInt("ID"));
		n.setDateTimeSended(sended);
		n.setDateTimeReceived(received);
		n.setCategory(Category.values()[result.getInt("category_id")]);
		n.setContext(Context.values()[result.getInt("context_id")]);
		n.setContent(Content.values()[result.getInt("content_id")]);
		n.setKid(Kid.values()[result.getInt("kid_id")]);
		int tag_id = result.getInt("tag_id");
		n.setTag(FactoryDAO.getTagDAO().retrieve(tag_id));
		return n;
	}

	@Override
	protected String prepareWhere(Notification obj) {
		return String.format("ID = %d", obj.getId());
	}

	@Override
	protected String prepareForUpdate(Notification obj) {
		return String.format("sended = '%s', received = '%s', content_id = %d, context_id = %d, category_id = %d, kid_id = %d, tag_id = %s",
			formatter.format(obj.getDateTimeSended().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()),
			formatter.format(obj.getDateTimeReceived().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()),
			obj.getContent().ordinal(),
			obj.getContext().ordinal(),
			obj.getCategory().ordinal(),
			obj.getKid().ordinal(),
			obj.getTag() == null ? "null": String.valueOf(obj.getTag().getId())
		);
	}

}
