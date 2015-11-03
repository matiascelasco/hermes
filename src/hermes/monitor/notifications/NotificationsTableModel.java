package hermes.monitor.notifications;
import hermes.dataloader.Notification;

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
	
	private Notification[] data;
	
	public NotificationsTableModel(Notification[] data) {
		this.data = data;
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	public int getRowCount() {
		return data.length;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
			case 0:
				return (Object) data[rowIndex].getFechaHoraEnvio();
			case 1:
				return (Object) data[rowIndex].getContext();
			case 2:
				return (Object) data[rowIndex].getContent();
			case 3:
				return (Object) data[rowIndex].getCategory();
			case 4:
				return (Object) data[rowIndex].getKid();
			case 5:
				return (Object) data[rowIndex].getTag();
			default:
				throw new IllegalArgumentException("columnIndex must be between 0 and 5");
		}
	}

}
