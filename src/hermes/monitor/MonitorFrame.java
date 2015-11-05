package hermes.monitor;
import hermes.monitor.filters.FiltersPanel;
import hermes.data.Notification;
import hermes.data.dao.FactoryDAO;
import hermes.helpers.GridBagConstraintsBuilder;
import hermes.monitor.notifications.NotificationsPanel;
import hermes.monitor.tags.TagsPanel;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JFrame;


public class MonitorFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MonitorFrame() throws SQLException {
		super("Hermes Monitor");

		Container content = this.getContentPane();

		List<Notification> data = FactoryDAO.getNotificationDAO().findAll();

		NotificationsPanel notificationsPanel = new NotificationsPanel(data);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content.setLayout(new GridBagLayout());

        Date minDate;
        Date maxDate;

        /*
        TODO: si achico el frame la tabla se descuajeringa toda
        TODO: el ordenamiento de la columna de fecha no anda
        TODO: que los fields de fecha se actualizen automáticamente de manera tal que Desde nunca sea mayor que Hasta
        TODO: mencionar como comentario en la entrega la version de java (8) y el compilation level (1.5 en vez de 1.4)
        */

        if (data.isEmpty()){
        	minDate = new GregorianCalendar().getTime();
        	maxDate = new GregorianCalendar().getTime();
        }
        else {
        	minDate = data.get(0).getDateTimeSended();
        	maxDate = data.get(0).getDateTimeSended();

        	for (Notification notification: data){
        		if (notification.getDateTimeSended().before(minDate)){
        			minDate = notification.getDateTimeSended();
        		}
        		if (notification.getDateTimeSended().after(maxDate)){
        			maxDate = notification.getDateTimeSended();
        		}
        	}
        }

        content.add(new FiltersPanel(notificationsPanel.getTable(), minDate, maxDate),
        			new GridBagConstraintsBuilder()
        				.at(0, 0).size(1)
        				.weightX(1).weightY(1)
        				.fill(GridBagConstraints.BOTH)
        				.build());

        content.add(new TagsPanel(notificationsPanel.getTable()),
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
