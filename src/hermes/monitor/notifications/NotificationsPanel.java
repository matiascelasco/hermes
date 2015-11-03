package hermes.monitor.notifications;


import hermes.enums.Category;
import hermes.enums.Content;
import hermes.enums.Context;
import hermes.enums.Kid;
import hermes.enums.Tag;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;

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
