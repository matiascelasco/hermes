package hermes.model;

import java.time.format.DateTimeFormatter;
import java.util.List;

import dao.DAO;

import hermes.model.dao.FactoryDAO;

public class Model {

	public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	
	public void renameTag(Tag tag, String tagNewName) {
		tag.setName(tagNewName);
		FactoryDAO.getTagDAO().persist(tag);
	}

	public List<Tag> getAllTags() {
		return FactoryDAO.getTagDAO().findAll();
	}

	public List<Notification> getAllNotifications() {
		List<Notification> notifications = FactoryDAO.getNotificationDAO().findAll();
		notifications.sort(Notification.byIdComparator);  //required to update table properly
		return notifications;
	}

	public void toggleTag(Tag tag, List<Notification> notifications) {
		DAO<Notification> dao = FactoryDAO.getNotificationDAO();
		for (Notification notification: notifications){
			if (notification.tags.contains(tag)){
				notification.tags.remove(tag);
			} else {
				notification.tags.add(tag);
			}
			dao.persist(notification);
		}
	}

	public void deleteTag(Tag tag) {
		FactoryDAO.getTagDAO().delete(tag);
	}

	public void createNewTag(String newTagName) {
		Tag tag = new Tag();
		tag.setName(newTagName);
		FactoryDAO.getTagDAO().persist(tag);
	}

	public boolean validateTagName(String newTagName) {
		for (int i = 0; i < newTagName.length(); i++){
			if (newTagName.charAt(i) != ' '){
				return true;
			}
		}
		return false;
	}

	public boolean tagNameAlreadyExists(String newTagName) {
		for (Tag tag: getAllTags()){
			if (tag.getName().equals(newTagName)){
				return true;
			}
		}
		return false;
	}

}
