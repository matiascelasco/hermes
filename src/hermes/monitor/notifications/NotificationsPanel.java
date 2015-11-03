package hermes.monitor.notifications;


import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class NotificationsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private NotificationsTable table;
	
	public NotificationsPanel(Object [][] data) {
		
		setBorder(BorderFactory.createTitledBorder("Notificaciones"));
		setLayout(new BorderLayout());
		table = new NotificationsTable(data);
		
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	public NotificationsTable getTable() {
		return table;
	}

}
