package hermes.monitor.view.utils;

import java.util.List;

import hermes.model.Tag;

import javax.swing.JComboBox;

public class TagsComboBox extends JComboBox<Tag> {

	private static final long serialVersionUID = 1L;

	public TagsComboBox(List<Tag> tags){
		updateContent(tags);
	}
	
	public void updateContent(List<Tag> tags){
		removeAllItems();
		for (Tag tag : tags) {
			addItem(tag);	
		}
	}
	
	
}
