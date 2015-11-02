package hermes.monitor;
import hermes.monitor.filters.FiltersPanel;
import hermes.helpers.GridBagConstraintsBuilder;
import hermes.monitor.notifications.NotificationsPanel;
import hermes.monitor.tags.TagsPanel;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;


public class MonitorFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MonitorFrame() {
		super("Hermes Monitor");
		
		Container content = this.getContentPane();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content.setLayout(new GridBagLayout());
        
        content.add(new FiltersPanel(),
        			new GridBagConstraintsBuilder().at(0, 0).size(1).fill(GridBagConstraints.VERTICAL).build());
        content.add(new TagsPanel(),
        			new GridBagConstraintsBuilder().at(1, 0).size(1).fill(GridBagConstraints.VERTICAL).build());
        content.add(new NotificationsPanel(),
        			new GridBagConstraintsBuilder().at(0, 1).size(2, 1).build());
	}
	
}
