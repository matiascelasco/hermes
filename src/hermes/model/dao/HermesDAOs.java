package hermes.model.dao;

import hermes.model.Notification;
import hermes.model.Tag;
import hermes.model.enums.Category;
import hermes.model.enums.Content;
import hermes.model.enums.Context;
import hermes.model.enums.Kid;
import dao.DAO;
import dao.DAOforJDBC;
import dao.EnumDAO;

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
		
		KID = new EnumDAO<Kid>(Kid.class);
//		DAOforJDBC<Kid> kidsDao = new KidDAOforJDBC();
//		KID.setConnectionWrapper(connectionWrapper);
//		KID = kidsDao;
		
		CONTENT = new EnumDAO<Content>(Content.class);
//		DAOforJDBC<Content> contentsDao = new ContentDAOforJDBC();
//		contentsDao.setConnectionWrapper(connectionWrapper);
//		CONTENT = contentsDao;
		
		CONTEXT = new EnumDAO<Context>(Context.class);
//		DAOforJDBC<Context> contextsDao = new ContextDAOforJDBC();
//		contextsDao.setConnectionWrapper(connectionWrapper);
//		CONTEXT = contextsDao;
				
		CATEGORY = new EnumDAO<Category>(Category.class);
//		DAOforJDBC<Category> categoriesDao = new CategoryDAOforJDBC();
//		categoriesDao.setConnectionWrapper(connectionWrapper);
//		CATEGORY = categoriesDao;
		
		connectionWrapper.prepare();
	}
}
