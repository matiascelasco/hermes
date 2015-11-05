package hermes.model.dao;

import hermes.model.Notification;
import hermes.model.Tag;
import dao.DAO;

public class FactoryDAO {

	private static ConnectionWrapper connectionWrapper = new ConnectionWrapper();
	private static NotificationDAOforJDBC notificationDAO = new NotificationDAOforJDBC();
	private static TagDAOforJDBC tagDAO = new TagDAOforJDBC();
	
	static {
		notificationDAO.setConnectionWrapper(connectionWrapper);
		tagDAO.setConnectionWrapper(connectionWrapper);
		connectionWrapper.prepare();
	}
	
	public static DAO<Notification> getNotificationDAO() {
		return notificationDAO;
	}

	public static DAO<Tag> getTagDAO() {
		return tagDAO;
	}

}
