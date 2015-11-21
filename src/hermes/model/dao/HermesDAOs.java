package hermes.model.dao;

import hermes.model.Category;
import hermes.model.Content;
import hermes.model.Context;
import hermes.model.Kid;
import hermes.model.Notification;
import hermes.model.Tag;
import dao.DAO;
import dao.DAOforJDBC;

public class HermesDAOs {
	
	private static ConnectionWrapper connectionWrapper;
	public static final DAO<Notification> NOTIFICATION;
	public static final DAO<Tag> TAG;
	public static final DAO<Kid> KID;
	public static final DAO<Context> CONTEXT;
	public static final DAO<Content> CONTENT;
	public static final DAO<Category> CATEGORY;
	
	static {
		connectionWrapper = new ConnectionWrapper();
		
		DAOforJDBC<Notification> notificationsDao = new NotificationDAOforJDBC();
		notificationsDao.setConnectionWrapper(connectionWrapper);
		NOTIFICATION  = notificationsDao;
		
		DAOforJDBC<Tag> tagsDao = new TagDAOforJDBC();
		tagsDao.setConnectionWrapper(connectionWrapper);
		TAG = tagsDao;
		
		DAOforJDBC<Kid> kidsDao = new KidDAOforJDBC();
		kidsDao.setConnectionWrapper(connectionWrapper);
		KID = kidsDao;
		
		DAOforJDBC<Content> contentsDao = new ContentDAOforJDBC();
		contentsDao.setConnectionWrapper(connectionWrapper);
		CONTENT = contentsDao;
		
		DAOforJDBC<Context> contextsDao = new ContextDAOforJDBC();
		contextsDao.setConnectionWrapper(connectionWrapper);
		CONTEXT = contextsDao;
				
		DAOforJDBC<Category> categoriesDao = new CategoryDAOforJDBC();
		categoriesDao.setConnectionWrapper(connectionWrapper);
		CATEGORY = categoriesDao;
		
		connectionWrapper.prepare();
	}
}
