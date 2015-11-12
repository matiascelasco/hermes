package hermes.model.loader;

import hermes.model.Notification;
import hermes.model.dao.FactoryDAO;
import hermes.model.enums.Category;
import hermes.model.enums.Content;
import hermes.model.enums.Context;
import hermes.model.enums.Kid;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import utils.Converter;

public class JSONLoader implements Loader {

	@Override
	public void load() {
		InputStream in = JSONLoader.class.getResourceAsStream("/hermes.json");
		JSONTokener tokener = new JSONTokener(in);
		saveToDatabase(new JSONArray(tokener));
	}

	protected void saveToDatabase(JSONArray notificationsJSON){
		for (int i = 0; i < notificationsJSON.length(); i++){
			
			JSONObject notificationJSON = notificationsJSON.getJSONObject(i);
			Notification notification = new Notification();
			
			notification.setDateTimeSent(Converter.stringToDate(notificationJSON.getString("sent")));
			notification.setDateTimeReceived(Converter.stringToDate(notificationJSON.getString("received")));
			notification.setCategory(Category.values()[notificationJSON.getInt("category_id") - 1]);
			notification.setContent(Content.values()[notificationJSON.getInt("content_id") - 1]);
			notification.setContext(Context.values()[notificationJSON.getInt("context_id") - 1]);
			notification.setKid(Kid.values()[notificationJSON.getInt("kid_id") - 1]);
			
			FactoryDAO.getNotificationDAO().persist(notification);
		}
	}

}
