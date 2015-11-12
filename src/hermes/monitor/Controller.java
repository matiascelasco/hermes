package hermes.monitor;

import hermes.model.Model;
import hermes.model.Notification;
import hermes.model.Tag;
import hermes.monitor.view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class Controller {

	private View view;
	private Model model;

	public Controller(Model model, View view){
		this.view = view;
		this.model = model;
	}

	public void prepare(){
		view.addTagCreatedListener(new TagCreatedListener());
		view.addTagDeletedListener(new TagDeletedListener());
		view.addTagAssignedListener(new TagToggledOnNotificationListener());
		view.addTagRenamedListener(new TagRenamedListener());
		view.addFilterButtonPressedListener(new FilterButtonPressedListener());
		view.addClearButtonPressedListener(new ClearButtonPressedListener());
	}

	private class TagCreatedListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String newTagName = view.getNameForNewTag();
			if (model.validateTagName(newTagName) && !model.tagNameAlreadyExists(newTagName)){
				model.createNewTag(newTagName);
				view.updateTagComboBoxes(model.getAllTags());
			}
		}

	}

	private class TagDeletedListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Tag tag = view.getTagToBeDeleted();
			model.deleteTag(tag);
			view.updateTagComboBoxes(model.getAllTags());
			view.updateTable(model.getAllNotifications());
		}

	}

	private class TagToggledOnNotificationListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Tag tag = view.getTagToBeToggled();
			List<Notification> notifications = view.getSelectedNotifications();
			model.toggleTag(tag, notifications);
			view.updateTable(model.getAllNotifications());
		}

	}

	private class TagRenamedListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Tag tag = view.getTagToBeRenamed();
			String tagNewName = view.getNewNameToRenameTag();
			if (model.validateTagName(tagNewName) && !model.tagNameAlreadyExists(tagNewName)){
				model.renameTag(tag, tagNewName);
				view.updateTagComboBoxes(model.getAllTags());
				view.updateTable(model.getAllNotifications());
			}
		}

	}

	private class FilterButtonPressedListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			view.filterTable(view.getFilterToBeApplied());
		}

	}

	private class ClearButtonPressedListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			view.clearFiltersForm();
			view.filterTable(view.getFilterToBeApplied());
		}

	}
}
