package hermes.monitor.view.utils;

import hermes.model.Notification;
import hermes.model.Tag;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import utils.Converter;

public class NotificationsTable extends JTable {

	private static final long serialVersionUID = 1L;

	static private class DateTimeCellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public void setValue(Object value) {
			Date date = (Date) value;
			String dateAsString = Converter.dateToString(date);
			setText(dateAsString);
		}
	}
	
	static private class SetCellRenderer<T> extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		@SuppressWarnings("unchecked")
		public void setValue(Object value) {
			Set<T> set = (Set<T>) value;
			String setAsStr = set.toString();
			setText(setAsStr.substring(1, setAsStr.length() - 1));
		}
	}

	private static TableCellRenderer dateTimeRenderer = new DateTimeCellRenderer();
	private static SetCellRenderer<Tag> tagsSetRenderer = new SetCellRenderer<Tag>();

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
		switch (column) {
		case 0:
			return dateTimeRenderer;
		case 5:
			return tagsSetRenderer;
		default:
			return super.getCellRenderer(row, column);
		}
	}

	public Class<?> getColumnClass(int column) {
		if (column == 0) {
			return Date.class;
		}
		return super.getColumnClass(column);
	}

}
