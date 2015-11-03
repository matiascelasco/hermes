package hermes.monitor.notifications;

import hermes.dataloader.Notification;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

public class NotificationsTable extends JTable {

	private static final long serialVersionUID = 1L;

	static private class DateTimeCellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		private static final DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		@Override
		public void setValue(Object value) {
			Date date = (Date) value;
			setText(dateFormatter.format(date.getTime()));
		}		
	}
	
	private static TableCellRenderer dateTimeRenderer = new DateTimeCellRenderer();
	
	private TableRowSorter<NotificationsTableModel> sorter;
	
	public NotificationsTable(Notification[] data) {
		super(new NotificationsTableModel(data));
		sorter = new TableRowSorter<NotificationsTableModel>((NotificationsTableModel) getModel());
		setRowSorter(sorter);
		setFillsViewportHeight(true);
	}
	
	public void setFilter(RowFilter<NotificationsTableModel, Object> filter) {
		sorter.setRowFilter(filter);
	}
	
	public Object getValueAt(int row, int column) {
		Object value = super.getValueAt(row, column);
		if (column == 0){
			return (Date) value;
		}
		return value;
    }

	
    public TableCellRenderer getCellRenderer(int row, int column) {
    	if ((column == 0)) {
            return dateTimeRenderer;
        }
        return super.getCellRenderer(row, column);
    }
    
    public Class<?> getColumnClass(int column) {
    	if (column == 0){
    		return Date.class;
    	}
    	return super.getColumnClass(column);
    }
    
}
