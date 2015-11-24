package hermes.model;

import java.util.List;

import dao.DAO;

import hermes.model.dao.HermesDAOs;

public class Model {

	public void renameTag(Tag tag, String tagNewName) {
		tag.setName(tagNewName);
		HermesDAOs.TAG.persist(tag);
	}

	public List<Tag> getAllTags() {
		return HermesDAOs.TAG.findAll();
	}

	public List<Notification> getAllNotifications() {
		List<Notification> notifications = HermesDAOs.NOTIFICATION.findAll();
		notifications.sort(Notification.byIdComparator);  //required to update table properly
		return notifications;
	}

	public void toggleTag(Tag tag, List<Notification> notifications) {
		DAO<Notification> dao = HermesDAOs.NOTIFICATION;
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
		HermesDAOs.TAG.delete(tag);
	}

	public void createNewTag(String newTagName) {
		Tag tag = new Tag();
		tag.setName(newTagName);
		HermesDAOs.TAG.persist(tag);
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

	public List<Category> getAllCategories() {
		return HermesDAOs.CATEGORY.findAll();
	}

	public List<Context> getAllContexts() {
		return HermesDAOs.CONTEXT.findAll();
	}

	public List<Content> getAllContents() {
		return HermesDAOs.CONTENT.findAll();
	}
	
	public List<Kid> getAllKids() {
		return HermesDAOs.KID.findAll();
	}

}
