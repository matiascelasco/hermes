package hermes.monitor;
import hermes.monitor.filters.FiltersPanel;
import hermes.enums.Category;
import hermes.enums.Content;
import hermes.enums.Context;
import hermes.enums.Kid;
import hermes.enums.Tag;
import hermes.helpers.GridBagConstraintsBuilder;
import hermes.monitor.notifications.NotificationsPanel;
import hermes.monitor.tags.TagsPanel;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFrame;


public class MonitorFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MonitorFrame() {
		super("Hermes Monitor");
		
		Container content = this.getContentPane();

		Object[][] data = {
			{new GregorianCalendar(2015, 1 - 1, 1).getTime(), Content.ENTUSIASMADO, Context.HOGAR, Category.PREDETERMINADA, Kid.ROBIN, Tag.CHARLAS_CON_PADRE},
			{new GregorianCalendar(2015, 12 - 1, 31, 23, 59, 58).getTime(), Content.ENTUSIASMADO, Context.PISTA, Category.EMOCIONES, Kid.BATMAN, Tag.IMPORTANTE},
			{new GregorianCalendar(2014, 11 - 1, 1, 13, 5, 58).getTime(), Content.ALEGRE, Context.ESTABLO_TERAPIA, Category.PREDETERMINADA, Kid.JIMMY, Tag.CHARLAS_CON_PADRE},
		};
		
		NotificationsPanel notificationsPanel = new NotificationsPanel(data);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content.setLayout(new GridBagLayout());

        Date minDate;
        Date maxDate;
        
        if (data.length > 0){
        	minDate = (Date) data[0][0];
        	maxDate = (Date) data[0][0];
        	
        	for (Object[] row: data){
        		if (((Date) row[0]).before(minDate)){
        			minDate = (Date) row[0];
        		}
        		if (((Date) row[0]).after(maxDate)){
        			maxDate = (Date) row[0];
        		}
        	}        	
        }
        else {
        	minDate = new GregorianCalendar().getTime();
        	maxDate = new GregorianCalendar().getTime();
        }
        
        content.add(new FiltersPanel(notificationsPanel.getTable(), minDate, maxDate),
        			new GridBagConstraintsBuilder()
        				.at(0, 0).size(1)
        				.fill(GridBagConstraints.VERTICAL)
        				.build());
        
        content.add(new TagsPanel(),
        			new GridBagConstraintsBuilder()
        				.at(1, 0).size(1)
        				.fill(GridBagConstraints.VERTICAL)
        				.build());
        
        content.add(notificationsPanel,
        			new GridBagConstraintsBuilder()
        				.at(0, 1)
        				.size(2, 1)
        				.fill(GridBagConstraints.BOTH)
        				.build());
	}
	
}
