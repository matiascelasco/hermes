package hermes.model.dao;

import hermes.model.Notification;
import hermes.model.Tag;
import hermes.model.enums.Category;
import hermes.model.enums.Content;
import hermes.model.enums.Context;
import hermes.model.enums.Kid;
import dao.DAO;
import dao.EnumDAO;

public class FactoryDAO {

	
	private static ConnectionWrapper connectionWrapper = new ConnectionWrapper();
	private static NotificationDAOforJDBC notificationDAO = new NotificationDAOforJDBC();
	private static TagDAOforJDBC tagDAO = new TagDAOforJDBC();
	private static EnumDAO<Kid> kidDAO = new EnumDAO<Kid>(Kid.class);
	private static EnumDAO<Context> contextDAO = new EnumDAO<Context>(Context.class);
    private static EnumDAO<Content> contentDAO = new EnumDAO<Content>(Content.class);
    private static EnumDAO<Category> categoryDAO = new EnumDAO<Category>(Category.class);
	
	
	static {
		notificationDAO.setConnectionWrapper(connectionWrapper);
		tagDAO.setConnectionWrapper(connectionWrapper);
//		kidDAO.setConnectionWrapper(connectionWrapper);
//		contextDAO.setConnectionWrapper(connectionWrapper);
//		contentDAO.setConnectionWrapper(connectionWrapper);
//		categoryDAO.setConnectionWrapper(connectionWrapper);
		connectionWrapper.prepare();
	}
	
	public static DAO<Notification> getNotificationDAO() {
		return notificationDAO;
	}

	public static DAO<Tag> getTagDAO() {
		return tagDAO;
	}
	
	public static DAO<Kid> getKidDAO() {
		return kidDAO;
	}
	
	public static DAO<Content> getContentDAO() {
		return contentDAO;
	}
	
	public static DAO<Context> getContextDAO() {
		return contextDAO;
	}
	
	public static DAO<Category> getCategoryDAO() {
		return categoryDAO;
	}
	
	

}
