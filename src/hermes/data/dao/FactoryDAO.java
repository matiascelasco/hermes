package hermes.data.dao;

import hermes.data.Notification;
import hermes.data.Tag;
import dao.DAO;

public class FactoryDAO {

	public static DAO<Notification> getNotificationDAO(){
		return new NotificationDAOforJDBC();
	}
	
	public static DAO<Tag> getTagDAO(){
		return new TagDAOforJDBC();
	}

}
