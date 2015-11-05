package hermes.model.dao;

import hermes.model.Notification;
import hermes.model.Tag;
import dao.DAO;

public class FactoryDAO {

	public static DAO<Notification> getNotificationDAO() {
		return new NotificationDAOforJDBC();
	}

	public static DAO<Tag> getTagDAO() {
		return new TagDAOforJDBC();
	}

}
