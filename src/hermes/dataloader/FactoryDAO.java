package hermes.dataloader;

import dao.DAO;
import hermes.enums.Tag;

public class FactoryDAO {

	public static DAO<Notification> getNotificationDAO(){
		return new NotificationDAOforJDBC();
	}
	
	public static DAO<Tag> getTagDAO(){
		return new TagDAOforJDBC();
	}

}
