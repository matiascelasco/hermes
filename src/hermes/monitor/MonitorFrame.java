package hermes.monitor;
import hermes.monitor.filters.FiltersPanel;
import hermes.dataloader.Notification;
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
import java.util.Random;

import javax.swing.JFrame;


public class MonitorFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MonitorFrame() {
		super("Hermes Monitor");
		
		Container content = this.getContentPane();

		Notification[] data = new Notification[50];
		
		Random random = new Random();
		
		for (int i = 0; i < 50; i++){
			data[i] = new Notification();
			data[i].setDateTimeSended(new GregorianCalendar(2013 + random.nextInt(3),
											   random.nextInt(12),
											   random.nextInt(28),
											   random.nextInt(24),
											   random.nextInt(60),
											   random.nextInt(60)).getTime());
			data[i].setContent(Content.values()[random.nextInt(Content.values().length)]);
			data[i].setContext(Context.values()[random.nextInt(Context.values().length)]);
			data[i].setCategory(Category.values()[random.nextInt(Category.values().length)]);
			data[i].setKid(Kid.values()[random.nextInt(Kid.values().length)]);
			data[i].setTag(Tag.values()[random.nextInt(Tag.values().length)]);
		}
		
		NotificationsPanel notificationsPanel = new NotificationsPanel(data);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content.setLayout(new GridBagLayout());

        Date minDate;
        Date maxDate;
        
        /*
        TODO: el ordenamiento de la columna de fecha no anda
        TODO: mencionar como comentario en la entrega la version de java (8) y el compilation level (1.5 en vez de 1.4)
        TODO: no se como hacer que la tabla sea responsive. el problema es que en ese panel use un tipo distinto de layout
        */
        
        if (data.length > 0){
        	minDate = data[0].getDateTimeSended();
        	maxDate = data[0].getDateTimeSended();
        	
        	for (Notification notification: data){
        		if (notification.getDateTimeSended().before(minDate)){
        			minDate = notification.getDateTimeSended();
        		}
        		if (notification.getDateTimeSended().after(maxDate)){
        			maxDate = notification.getDateTimeSended();
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
        				.weightX(1).weightY(1)
        				.fill(GridBagConstraints.BOTH)
        				.build());
        
        content.add(new TagsPanel(),
        			new GridBagConstraintsBuilder()
        				.at(1, 0).size(1)
        				.weightX(1).weightY(1)
        				.fill(GridBagConstraints.BOTH)
        				.build());
        
        content.add(notificationsPanel,
        			new GridBagConstraintsBuilder()
        				.at(0, 1)
        				.size(2, 1)
        				.weightX(1).weightY(1)
        				.fill(GridBagConstraints.BOTH)
        				.build());
	}
	
}
