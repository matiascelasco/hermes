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
			notification.setCategory(HermesDAOs.CATEGORY.retrieveByStringOrCreate("name", notificationJSON.getString("category")));
			notification.setContent(HermesDAOs.CONTENT.retrieveByStringOrCreate("name", notificationJSON.getString("content")));
			notification.setContext(HermesDAOs.CONTEXT.retrieveByStringOrCreate("name", notificationJSON.getString("context")));
			notification.setKid(HermesDAOs.KID.retrieveByStringOrCreate("name", notificationJSON.getString("kid")));

			HermesDAOs.NOTIFICATION.persist(notification);
		}
	}

}
