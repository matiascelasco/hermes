package hermes.monitor.view.helpers;
import java.util.List;

import hermes.model.Notification;

import javax.swing.table.AbstractTableModel;

public class NotificationsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static String[] columnNames = {
		"Fecha y hora de envío",
		"Contenido",
		"Contexto",
		"Categoría",
		"Niñ@",
		"Etiquetas"
	};
	
	private List<Notification> data;
	
	public NotificationsTableModel(List<Notification> data) {
		this.data = data;
		this.data.sort(Notification.byIdComparator);
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	public int getRowCount() {
		return data.size();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public Notification getNotification(int rowIndex){
		return data.get(rowIndex);
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
			case 0:
				return (Object) data.get(rowIndex).getDateTimeSended();
			case 1:
				return (Object) data.get(rowIndex).getContent();
			case 2:
				return (Object) data.get(rowIndex).getContext();
			case 3:
				return (Object) data.get(rowIndex).getCategory();
			case 4:
				return (Object) data.get(rowIndex).getKid();
			case 5:
				return (Object) data.get(rowIndex).tags;
			default:
				throw new IllegalArgumentException("columnIndex must be between 0 and 5");
		}
	}
	
	public void updateData(List<Notification> updatedOnes){
		
		if (updatedOnes.size() != data.size()){
			throw new RuntimeException(
				"Something is wrong: when trying to update the JTable with the views, " +
				"the sizes of both updated and old list of notifications differs. " +
				"That don't makes sense since we are not performing any notifications CRUD " +
				"(except for updates)."
			);
		}
		
		for (int i = 0; i < updatedOnes.size(); i++){
			Notification oldOne = data.get(i);
			Notification updatedOne = updatedOnes.get(i);
			
			if (oldOne.getId() != updatedOne.getId()){
				throw new RuntimeException("List of updated notifications given is supposed to be sorted by id");
			}
			
			oldOne.tags = updatedOne.tags;
			oldOne.setContext(updatedOne.getContext());
			oldOne.setContent(updatedOne.getContent());
			oldOne.setCategory(updatedOne.getCategory());
			oldOne.setKid(updatedOne.getKid());
			oldOne.setDateTimeReceived(updatedOne.getDateTimeReceived());
			oldOne.setDateTimeSended(updatedOne.getDateTimeSended());
			
		}
	}

}
