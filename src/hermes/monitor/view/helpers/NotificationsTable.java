package hermes.monitor.view.helpers;

import hermes.model.Model;
import hermes.model.Notification;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

public class NotificationsTable extends JTable {

	private static final long serialVersionUID = 1L;

	static private class DateTimeCellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public void setValue(Object value) {
			Date date = (Date) value;
			String dateAsString = Model.dateTimeFormatter.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			setText(dateAsString);
		}
	}

	private static TableCellRenderer dateTimeRenderer = new DateTimeCellRenderer();

	private TableRowSorter<NotificationsTableModel> sorter;

	public NotificationsTable(List<Notification> data) {
		super(new NotificationsTableModel(data));
		sorter = new TableRowSorter<NotificationsTableModel>(
				(NotificationsTableModel) getModel());
		setRowSorter(sorter);
		setFillsViewportHeight(true);
	}

	public void setFilter(RowFilter<NotificationsTableModel, Object> filter) {
		sorter.setRowFilter(filter);
	}

	public Object getValueAt(int row, int column) {
		Object value = super.getValueAt(row, column);
		if (column == 0) {
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
		if (column == 0) {
			return Date.class;
		}
		return super.getColumnClass(column);
	}

}