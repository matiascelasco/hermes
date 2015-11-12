package hermes.monitor.view;


import hermes.model.Notification;
import hermes.monitor.view.utils.NotificationsTable;
import hermes.monitor.view.utils.NotificationsTableModel;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;

public class NotificationsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private NotificationsTable table;
	
	public NotificationsPanel(List<Notification> data) {
		
		setBorder(BorderFactory.createTitledBorder("Notificaciones"));
		setLayout(new BorderLayout());
		table = new NotificationsTable(data);
		
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	public NotificationsTable getTable() {
		return table;
	}

	public List<Notification> getSelectedNotifications() {
		List<Notification> notifications = new ArrayList<Notification>();
		for (int rowIndex :table.getSelectedRows()){
			NotificationsTableModel tableModel = (NotificationsTableModel) table.getModel();
			notifications.add(tableModel.getNotification(table.convertRowIndexToModel(rowIndex)));
		}
		return notifications;
	}

	public void filterTable(RowFilter<NotificationsTableModel, Object> filterToBeApplied) {
		table.setFilter(filterToBeApplied);
	}

	public void updateTable(List<Notification> allNotifications) {
		((NotificationsTableModel) table.getModel()).updateData(allNotifications);
		table.repaint();
	}

}
