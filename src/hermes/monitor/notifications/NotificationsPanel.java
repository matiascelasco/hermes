package hermes.monitor.notifications;

import hermes.helpers.GridBagConstraintsBuilder;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.sun.jmx.snmp.Timestamp;

public class NotificationsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public NotificationsPanel() {
		setBorder(BorderFactory.createTitledBorder("Notificaciones"));
		
		
		
		setLayout(new BorderLayout());
		
		JTable table = new JTable(new NotificationsModel()){
		    public TableCellRenderer getCellRenderer(int row, int column) {
		        if ((column == 0)) {
		            return new DefaultTableCellRenderer(){
		            	@Override
		            	public void setValue(Object value) {
		                    setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(((GregorianCalendar) value).getTimeInMillis()));
		                }
		            };
		        }
		        return super.getCellRenderer(row, column);
		    }
		};
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		add(scrollPane, BorderLayout.CENTER);
	}
	
}
