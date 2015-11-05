package hermes.monitor.helpers;

import java.util.List;
import hermes.data.Tag;
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
