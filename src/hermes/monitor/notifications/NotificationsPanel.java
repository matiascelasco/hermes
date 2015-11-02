package hermes.monitor.notifications;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NotificationsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public NotificationsPanel() {
		setBorder(BorderFactory.createTitledBorder("Notificaciones"));
		add(new JLabel("lorem ipsum dolor sit amet"));
	}
	
}
