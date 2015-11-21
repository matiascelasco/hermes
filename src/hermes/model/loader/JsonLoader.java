package hermes.model.loader;

import hermes.model.Notification;
import hermes.model.dao.FactoryDAO;

import org.json.JSONArray;
import org.json.JSONObject;

import utils.Converter;

public abstract class JsonLoader implements Loader {

	public static void saveToDatabase(JSONArray notificationsJSON){
		for (int i = 0; i < notificationsJSON.length(); i++){
			
			JSONObject notificationJSON = notificationsJSON.getJSONObject(i);
			Notification notification = new Notification();
			
			notification.setDateTimeSent(Converter.stringToDate(notificationJSON.getString("sent")));
			notification.setDateTimeReceived(Converter.stringToDate(notificationJSON.getString("received")));
			notification.setCategory(FactoryDAO.getCategoryDAO().retrieve(notificationJSON.getInt("category_id")));
			notification.setContent(FactoryDAO.getContentDAO().retrieve(notificationJSON.getInt("content_id")));
			notification.setContext(FactoryDAO.getContextDAO().retrieve(notificationJSON.getInt("context_id")));
			notification.setKid(FactoryDAO.getKidDAO().retrieve(notificationJSON.getInt("kid_id")));
			
			FactoryDAO.getNotificationDAO().persist(notification);
		}
	}

}
