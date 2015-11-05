package hermes.monitor;

import java.sql.SQLException;
import java.util.List;

import hermes.data.Tag;
import hermes.data.dao.FactoryDAO;

import javax.swing.JComboBox;

public class TagsComboBox extends JComboBox<Tag> {

	private static final long serialVersionUID = 1L;

	private void populate(){
		List<Tag> tags;
		try {
			tags = FactoryDAO.getTagDAO().findAll();
			removeAllItems();
			for (Tag tag : tags) {
				addItem(tag);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public TagsComboBox(){
		populate();
	}
	
	public void updateContent(){
		populate();
	}
}
