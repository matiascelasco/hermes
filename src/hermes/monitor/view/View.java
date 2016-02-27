package hermes.monitor.view;
import hermes.model.Category;
import hermes.model.Content;
import hermes.model.Context;
import hermes.model.Kid;
import hermes.model.Model;
import hermes.model.Notification;
import hermes.model.Tag;
import hermes.model.dao.HermesDAOs;
import hermes.monitor.view.utils.GridBagConstraintsBuilder;
import hermes.monitor.view.utils.NotificationRowFilterBuilder;
import hermes.monitor.view.utils.NotificationsTableModel;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;


public class View extends JFrame {

	private static final long serialVersionUID = 1L;

	private FiltersPanel filtersPanel;
	private TagsPanel tagsPanel;
	private NotificationsPanel notificationsPanel;

	Model model;
	
	public View(Model model){
		super("Hermes Monitor");

		this.model = model;
		
		Container content = this.getContentPane();

		List<Notification> data = HermesDAOs.NOTIFICATION.findAll();

		notificationsPanel = new NotificationsPanel(data);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content.setLayout(new GridBagLayout());

		
        filtersPanel = new FiltersPanel(notificationsPanel.getTable());
        tagsPanel = new TagsPanel();
        

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
        
        updateComboBoxes();
	}

	/* Methods that retrieve data from the view */
	public RowFilter<NotificationsTableModel, Object> getFilterToBeApplied() {
		Date fromDate = (Date) filtersPanel.fromDateTimePicker.getDate();
		Date toDate = (Date) filtersPanel.toDateTimePicker.getDate();
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
	public void updateComboBoxes() {
		filtersPanel.tagsComboBox.updateContent(model.getAllTags());
		filtersPanel.contextComboBox.updateContent(model.getAllContexts());
		filtersPanel.contentComboBox.updateContent(model.getAllContents());
		filtersPanel.categoryComboBox.updateContent(model.getAllCategories());
		filtersPanel.kidComboBox.updateContent(model.getAllKids());

		tagsPanel.tagForRemoveComboBox.updateContent(model.getAllTags());
		tagsPanel.tagForAssingComboBox.updateContent(model.getAllTags());
		tagsPanel.tagForRenameComboBox.updateContent(model.getAllTags());
	}
	
	public void filterTable(RowFilter<NotificationsTableModel, Object> filterToBeApplied) {
		notificationsPanel.filterTable(filterToBeApplied);
	}

	public void showAll() {
		filtersPanel.clearFiltersForm();
		filterTable(getFilterToBeApplied());
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

	public void showPopupMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}

}
