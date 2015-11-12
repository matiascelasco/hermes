package hermes.monitor.view;
import hermes.model.Model;
import hermes.model.Notification;
import hermes.model.Tag;
import hermes.model.dao.FactoryDAO;
import hermes.model.enums.Category;
import hermes.model.enums.Content;
import hermes.model.enums.Context;
import hermes.model.enums.Kid;
import hermes.monitor.view.utils.GridBagConstraintsBuilder;
import hermes.monitor.view.utils.NotificationRowFilterBuilder;
import hermes.monitor.view.utils.NotificationsTableModel;

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
	
	/* Methods that retrieve data from the view */
	public RowFilter<NotificationsTableModel, Object> getFilterToBeApplied() {
		Date fromDate = (Date) filtersPanel.fromDateTimeSpinner.getValue();
		Date toDate = (Date) filtersPanel.toDateTimeSpinner.getValue();
		Context context = (Context) filtersPanel.contextComboBox.getSelectedItem();
		Content content = (Content) filtersPanel.contentComboBox.getSelectedItem();
		Category category = (Category) filtersPanel.categoryComboBox.getSelectedItem();
		Kid kid = (Kid) filtersPanel.kidComboBox.getSelectedItem();
		Tag tag = (Tag) filtersPanel.tagsComboBox.getSelectedItem();
		
		NotificationRowFilterBuilder filterBuilder = 
				new NotificationRowFilterBuilder()
					.fromDate(fromDate)
					.toDate(toDate)
					.context(context)
					.content(content)
					.category(category)
					.kid(kid)
					.tag(tag);
		
		return filterBuilder.build();
	}
	
	public String getNameForNewTag() {
		return tagsPanel.newTagNameField.getText();
	}
	
	public Tag getTagToBeDeleted() {
		return (Tag) tagsPanel.tagForRemoveComboBox.getSelectedItem();
	}
	
	public Tag getTagToBeToggled() {
		return (Tag) tagsPanel.tagForAssingComboBox.getSelectedItem();
	}
	
	public String getNewNameToRenameTag() {
		return tagsPanel.renameTagNameField.getText();
	}
	
	public Tag getTagToBeRenamed() {
		return (Tag) tagsPanel.tagForRenameComboBox.getSelectedItem();
	}

	public List<Notification> getSelectedNotifications() {
		return notificationsPanel.getSelectedNotifications();
	}

	/* Methods that update the view */
	public void updateTagComboBoxes(List<Tag> tags) {
		tagsPanel.tagForAssingComboBox.updateContent(tags);
		tagsPanel.tagForRemoveComboBox.updateContent(tags);
		tagsPanel.tagForRenameComboBox.updateContent(tags);
		filtersPanel.updateTagComboBox(tags);
	}
	
	public void filterTable(RowFilter<NotificationsTableModel, Object> filterToBeApplied) {
		notificationsPanel.filterTable(filterToBeApplied);
	}
	
	public void clearFiltersForm() {
		filtersPanel.clearFiltersForm();
	}

	public void updateTable(List<Notification> allNotifications) {
		notificationsPanel.updateTable(allNotifications);
	}
	
	/* Methods that register listeners */
	public void addFilterButtonPressedListener(ActionListener listener) {
		filtersPanel.filterButton.addActionListener(listener);
	}
	
	public void addClearButtonPressedListener(ActionListener listener) {
		filtersPanel.clearButton.addActionListener(listener);
	}
	
	public void addTagCreatedListener(ActionListener listener) {
		tagsPanel.createButton.addActionListener(listener);
	}

	public void addTagDeletedListener(ActionListener listener) {
		tagsPanel.deleteButton.addActionListener(listener);
	}

	public void addTagAssignedListener(ActionListener listener) {
		tagsPanel.assignButton.addActionListener(listener);
	}

	public void addTagRenamedListener(ActionListener listener) {
		tagsPanel.renameButton.addActionListener(listener);
	}

}
