package hermes.monitor.view.utils;

import java.util.List;
import javax.swing.JComboBox;

public class ListComboBox<T> extends JComboBox<T> {

	private static final long serialVersionUID = 1L;

	public void updateContent(List<T> list){
		removeAllItems();
		for (T item : list) {
			addItem(item);
		}
	}
	
	
}
