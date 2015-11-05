package hermes.monitor.views.filters;

import hermes.data.Tag;
import hermes.data.enums.Category;
import hermes.data.enums.Content;
import hermes.data.enums.Context;
import hermes.data.enums.Kid;
import hermes.monitor.helpers.GridBagConstraintsBuilder;
import hermes.monitor.helpers.TagsComboBox;
import hermes.monitor.views.notifications.NotificationsTable;
import hermes.monitor.views.notifications.NotificationsTableModel;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.RowFilter;
import javax.swing.SpinnerDateModel;


public class FiltersPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private void addBoxWithLabel(String labelString, Component component, int x, int y){
		
		add(new JLabel(labelString),
			new GridBagConstraintsBuilder()
				.at(x,  y)
				.size(1)
				.weightX(1).weightY(1)
				.anchor(GridBagConstraints.EAST)
				.insets(new Insets(0, 5, 0, 0))
				.build());
		
		add(component, 
			new GridBagConstraintsBuilder()
				.at(x + 1,  y)
				.size(1)
				.weightX(1).weightY(1)
				.fill(GridBagConstraints.HORIZONTAL)
				.insets(new Insets(5, 0, 5, 5))
				.build());
	}
	
	private JSpinner fromDateTimeSpinner = new JSpinner(new SpinnerDateModel());
	private JSpinner toDateTimeSpinner = new JSpinner(new SpinnerDateModel());
	private JComboBox<Context> contextComboBox = new JComboBox<Context>(Context.values());
	private JComboBox<Content> contentComboBox = new JComboBox<Content>(Content.values());
	private JComboBox<Category> categoryComboBox = new JComboBox<Category>(Category.values());
	private JComboBox<Kid> kidComboBox = new JComboBox<Kid>(Kid.values());
	private TagsComboBox tagsComboBox;
	private Date minDate;
	private Date maxDate;

	
	private JButton filterButton = new JButton("Filtrar");
	private JButton clearButton = new JButton("Limpiar");
	
	public FiltersPanel(final NotificationsTable notificationsTable, Date minDate, Date maxDate, List<Tag> tags) {
		this.minDate = minDate;
		this.maxDate = maxDate;
		setBorder(BorderFactory.createTitledBorder("Filtros"));
		setLayout(new GridBagLayout());

		tagsComboBox = new TagsComboBox(tags);
		clearFiltersForm();
		
		addBoxWithLabel("Contenido: ", contentComboBox, 0, 0);
		addBoxWithLabel("Context: ", contextComboBox, 2, 0);
		addBoxWithLabel("Categoría: ", categoryComboBox, 0, 1);
		addBoxWithLabel("Niño: ", kidComboBox, 2, 1);
		addBoxWithLabel("Desde: ", fromDateTimeSpinner, 0, 2);
		addBoxWithLabel("Hasta: ", toDateTimeSpinner, 2, 2);
		addBoxWithLabel("Etiqueta: ", tagsComboBox, 0, 3);
		add(filterButton,
			new GridBagConstraintsBuilder()
				.at(1,  4)
				.width(3)
				.height(1)
				.fill(GridBagConstraints.HORIZONTAL)
				.insets(new Insets(5, 0, 5, 70))
				.build());
		add(clearButton,
			new GridBagConstraintsBuilder()
				.at(1,  5)
				.width(3)
				.height(1)
				.fill(GridBagConstraints.HORIZONTAL)
				.insets(new Insets(5, 0, 5, 70))
				.build());
	}

	public void clearFiltersForm(){
		fromDateTimeSpinner.setValue(minDate);
		toDateTimeSpinner.setValue(maxDate);
		contextComboBox.setSelectedIndex(-1);
		contentComboBox.setSelectedIndex(-1);
		categoryComboBox.setSelectedIndex(-1);
		kidComboBox.setSelectedIndex(-1);
		tagsComboBox.setSelectedIndex(-1);
	}
	
	public void updateTagComboBox(List<Tag> tags) {
		tagsComboBox.updateContent(tags);
	}

	public  RowFilter<NotificationsTableModel, Object> getFilterToBeApplied() {
		
		Date fromDate = (Date) fromDateTimeSpinner.getValue();
		Date toDate = (Date) toDateTimeSpinner.getValue();
		Context context = (Context) contextComboBox.getSelectedItem();
		Content content = (Content) contentComboBox.getSelectedItem();
		Category category = (Category) categoryComboBox.getSelectedItem();
		Kid kid = (Kid) kidComboBox.getSelectedItem();
		Tag tag = (Tag) tagsComboBox.getSelectedItem();
		
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

	public void addFilterButtonPressedListener(ActionListener listener) {
		filterButton.addActionListener(listener);
	}
	
	public void addClearButtonPressedListener(ActionListener listener) {
		clearButton.addActionListener(listener);
	}
	
}
