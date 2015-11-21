package hermes.model.dao;

import hermes.model.Notification;
import hermes.model.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utils.Converter;

import dao.DAOforJDBC;

class NotificationDAOforJDBC extends DAOforJDBC<Notification>{


	@Override
	protected String prepareValues(Notification obj) {
		return String.format("'%s', '%s', %d, %d, %d, %d",
			Converter.dateToString(obj.getDateTimeSent()),
			Converter.dateToString(obj.getDateTimeReceived()),
			obj.getContent().getId(),
			obj.getContext().getId(),
			obj.getCategory().getId(),
			obj.getKid().getId()
		);
	}

	@Override
	protected List<String> getFieldNames() {
		List<String> names = new ArrayList<String>();
		names.add("sent");
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
		Date sent = Converter.stringToDate(result.getString("sent"));
		Date received = Converter.stringToDate(result.getString("received"));
		n.setId(result.getInt("ID"));
		n.setDateTimeSent(sent);
		n.setDateTimeReceived(received);
		n.setCategory(HermesDAOs.CATEGORY.retrieve(result.getInt("category_id")));
		n.setContext(HermesDAOs.CONTEXT.retrieve(result.getInt("context_id")));
		n.setContent(HermesDAOs.CONTENT.retrieve(result.getInt("content_id")));
		n.setKid(HermesDAOs.KID.retrieve(result.getInt("kid_id")));
		return n;
	}

	protected void loadManyToManyRelatedModels(Notification notification) throws SQLException {
		notification.tags.clear();
		Statement statement = connection.createStatement();
		String sql = String.format("SELECT notification_id, tag_id FROM Notifications_Tags WHERE notification_id = %d;", notification.getId());
		ResultSet result = statement.executeQuery(sql);
		while (result.next()){
			notification.tags.add(HermesDAOs.TAG.retrieve(result.getInt("tag_id")));
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
		return String.format("sent = '%s', received = '%s', content_id = %d, context_id = %d, category_id = %d, kid_id = %d",
			Converter.dateToString(obj.getDateTimeSent()),
			Converter.dateToString(obj.getDateTimeReceived()),
			obj.getContent().getId(),
			obj.getContext().getId(),
			obj.getCategory().getId(),
			obj.getKid().getId()
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
