package hermes.model.loader;

import hermes.model.Notification;
import hermes.model.dao.HermesDAOs;

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
			notification.setCategory(HermesDAOs.CATEGORY.retrieve(notificationJSON.getInt("category_id")));
			notification.setContent(HermesDAOs.CONTENT.retrieve(notificationJSON.getInt("content_id")));
			notification.setContext(HermesDAOs.CONTEXT.retrieve(notificationJSON.getInt("context_id")));
			notification.setKid(HermesDAOs.KID.retrieve(notificationJSON.getInt("kid_id")));

			HermesDAOs.NOTIFICATION.persist(notification);
		}
	}

}
