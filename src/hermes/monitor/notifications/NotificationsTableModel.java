package hermes.monitor.notifications;
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
	
	private Object[][] data;
	
	public NotificationsTableModel(Object[][] data) {
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
		return data[rowIndex][columnIndex];
	}

}
