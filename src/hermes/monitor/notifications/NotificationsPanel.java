package hermes.monitor.notifications;


import hermes.data.Notification;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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

}
