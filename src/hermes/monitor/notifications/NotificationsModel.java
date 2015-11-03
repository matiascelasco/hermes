package hermes.monitor.notifications;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.table.AbstractTableModel;

public class NotificationsModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	private static String[] columnNames = {
		"Fecha y hora de envío",
		"Contenido",
		"Contexto",
		"Categoría",
		"Niñ@",
		"Etiquetas"
	};
	private static Object[][] data = {
		{new GregorianCalendar(2015, 12 - 1, 31, 23, 59), "Entusiasmado", "Pista", "Emociones", "Luis", "Importante"},
	};
	
	public int getRowCount() {
		return data.length;
	}

	public int getColumnCount() {
		return 6;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}

}
