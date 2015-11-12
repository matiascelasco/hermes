package hermes.model.dao;

import hermes.model.Model;
import hermes.model.Notification;
import hermes.model.Tag;
import hermes.model.enums.Category;
import hermes.model.enums.Content;
import hermes.model.enums.Context;
import hermes.model.enums.Kid;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.DAOforJDBC;

class NotificationDAOforJDBC extends DAOforJDBC<Notification>{


	@Override
	protected String prepareValues(Notification obj) {
		return String.format("'%s', '%s', %d, %d, %d, %d",
			Model.dateTimeFormatter.format(obj.getDateTimeSended().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()),
			Model.dateTimeFormatter.format(obj.getDateTimeReceived().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()),
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

		Date sended = Date.from(LocalDate.parse(result.getString("sended"), Model.dateTimeFormatter).atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date received = Date.from(LocalDate.parse(result.getString("received"), Model.dateTimeFormatter).atStartOfDay(ZoneId.systemDefault()).toInstant());
		n.setId(result.getInt("ID"));
		n.setDateTimeSended(sended);
		n.setDateTimeReceived(received);
		n.setCategory(Category.values()[result.getInt("category_id")]);
		n.setContext(Context.values()[result.getInt("context_id")]);
		n.setContent(Content.values()[result.getInt("content_id")]);
		n.setKid(Kid.values()[result.getInt("kid_id")]);
		return n;
	}

	protected void loadManyToManyRelatedModels(Notification notification) throws SQLException {
		notification.tags.clear();
		Statement statement = connection.createStatement();
		String sql = String.format("SELECT notification_id, tag_id FROM Notifications_Tags WHERE notification_id = %d;", notification.getId());
		ResultSet result = statement.executeQuery(sql);
		while (result.next()){
			notification.tags.add(FactoryDAO.getTagDAO().retrieve(result.getInt("tag_id")));
		}
		statement.close();
	}

	@Override
	protected String prepareWhere(Notification obj) {
		return String.format("ID = %d", obj.getId());
	}

	@Override
	public void persist(Notification notification){
		super.persist(notification);
		String sql = String.format("DELETE FROM Notifications_Tags WHERE notification_id = %d;", notification.getId());
		if (!notification.tags.isEmpty()){
			for (Tag tag : notification.tags) {
				sql += String.format("INSERT INTO Notifications_Tags(notification_id, tag_id) VALUES(%d, %d);", notification.getId(), tag.getId());
			}
		}
		connection.executeUpdate(sql);
	}

	@Override
	protected String prepareForUpdate(Notification obj) {
		return String.format("sended = '%s', received = '%s', content_id = %d, context_id = %d, category_id = %d, kid_id = %d",
			Model.dateTimeFormatter.format(obj.getDateTimeSended().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()),
			Model.dateTimeFormatter.format(obj.getDateTimeReceived().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()),
			obj.getContent().ordinal(),
			obj.getContext().ordinal(),
			obj.getCategory().ordinal(),
			obj.getKid().ordinal()
		);
	}

	@Override
	public boolean exists(Notification notification){
		if (notification.getId() == 0){
			return false;
		}
		return super.exists(notification);
	}

}
