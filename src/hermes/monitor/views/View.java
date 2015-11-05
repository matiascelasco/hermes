package hermes.monitor.views;
import hermes.monitor.Model;
import hermes.monitor.helpers.GridBagConstraintsBuilder;
import hermes.data.Notification;
import hermes.data.Tag;
import hermes.data.dao.FactoryDAO;
import hermes.monitor.views.filters.FiltersPanel;
import hermes.monitor.views.notifications.NotificationsPanel;
import hermes.monitor.views.notifications.NotificationsTableModel;
import hermes.monitor.views.tags.TagsPanel;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.RowFilter;


public class View extends JFrame {

	private static final long serialVersionUID = 1L;

	private FiltersPanel filtersPanel;
	private TagsPanel tagsPanel;
	private NotificationsPanel notificationsPanel;

	public View(Model model){
		super("Hermes Monitor");

		Container content = this.getContentPane();

		List<Notification> data = FactoryDAO.getNotificationDAO().findAll();

		notificationsPanel = new NotificationsPanel(data);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content.setLayout(new GridBagLayout());

        Date minDate;
        Date maxDate;

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

        List<Tag> tags = model.getAllTags();
        filtersPanel = new FiltersPanel(notificationsPanel.getTable(), minDate, maxDate, tags);
        tagsPanel = new TagsPanel(tags);

        content.add(filtersPanel,
        			new GridBagConstraintsBuilder()
        				.at(0, 0).size(1)
        				.weightX(1).weightY(1)
        				.fill(GridBagConstraints.BOTH)
        				.build());

        content.add(tagsPanel,
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

	public void clearFiltersForm() {
		filtersPanel.clearFiltersForm();
	}

	public Tag getTagToBeRenamed() {
		return tagsPanel.getTagToBeRenamed();
	}

	public String getNewNameToRenameTag() {
		return tagsPanel.getNewNameToRenameTag();
	}

	public void updateTagComboBoxes(List<Tag> tags) {
		tagsPanel.updateTagComboBoxes(tags);
		filtersPanel.updateTagComboBox(tags);
	}

	public Tag getTagToBeToggled() {
		return tagsPanel.getTagToBeAssigned();
	}

	public List<Notification> getSelectedNotifications() {
		return notificationsPanel.getSelectedNotifications();
	}

	public Tag getTagToBeDeleted() {
		return tagsPanel.getTagToBeDeleted();
	}

	public String getNameForNewTag() {
		return tagsPanel.getNameForNewTag();
	}

	public RowFilter<NotificationsTableModel, Object> getFilterToBeApplied() {
		return filtersPanel.getFilterToBeApplied();
	}

	public void filterTable(RowFilter<NotificationsTableModel, Object> filterToBeApplied) {
		notificationsPanel.filterTable(filterToBeApplied);
	}

	public void addTagCreatedListener(ActionListener listener) {
		tagsPanel.addTagCreatedListener(listener);
	}

	public void addTagDeletedListener(ActionListener listener) {
		tagsPanel.addTagDeletedListener(listener);
	}

	public void addTagAssignedListener(ActionListener listener) {
		tagsPanel.addTagAssignedListener(listener);
	}

	public void addTagRenamedListener(ActionListener listener) {
		tagsPanel.addTagRenamedListener(listener);
	}

	public void addFilterButtonPressedListener(ActionListener listener) {
		filtersPanel.addFilterButtonPressedListener(listener);
	}

	public void addClearButtonPressedListener(ActionListener listener) {
		filtersPanel.addClearButtonPressedListener(listener);
	}

	public void updateTable(List<Notification> allNotifications) {
		notificationsPanel.updateTable(allNotifications);
	}

}
