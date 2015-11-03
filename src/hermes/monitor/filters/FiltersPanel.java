package hermes.monitor.filters;

import hermes.enums.Category;
import hermes.enums.Content;
import hermes.enums.Context;
import hermes.enums.Kid;
import hermes.enums.Tag;
import hermes.helpers.GridBagConstraintsBuilder;
import hermes.monitor.notifications.NotificationsTable;
import hermes.monitor.notifications.NotificationsTableModel;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.swing.RowFilter.ComparisonType;


public class FiltersPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private <E extends Enum<E>> void addBoxWithLabel(String labelString, Component component, int x, int y){
		
		add(new JLabel(labelString),
			new GridBagConstraintsBuilder()
				.at(x,  y)
				.size(1)
				.anchor(GridBagConstraints.EAST)
				.insets(new Insets(0, 5, 0, 0))
				.build());
		
		add(component, 
			new GridBagConstraintsBuilder()
				.at(x + 1,  y)
				.size(1)
				.fill(GridBagConstraints.HORIZONTAL)
				.insets(new Insets(5, 0, 5, 5))
				.build());
	}
	
	private static RowFilter<NotificationsTableModel, Object> orDateTimeFilter(Date dateTime, ComparisonType... types){
		List<RowFilter<NotificationsTableModel, Object>> filters = new ArrayList<RowFilter<NotificationsTableModel, Object>>();
		for (ComparisonType t : types){
			RowFilter<NotificationsTableModel, Object> filter = RowFilter.dateFilter(t, dateTime);
			filters.add(filter);
		}
		return RowFilter.orFilter(filters);
	}
	
	public FiltersPanel(final NotificationsTable notificationsTable, Date minDate, Date maxDate) {
		setBorder(BorderFactory.createTitledBorder("Filtros"));
		setLayout(new GridBagLayout());

		final JSpinner fromDateTimeSpinner = new JSpinner(new SpinnerDateModel(minDate, null, null, Calendar.SECOND));
		final JSpinner toDateTimeSpinner = new JSpinner(new SpinnerDateModel(maxDate, null, null, Calendar.SECOND));
		final JComboBox<Context> contextComboBox = new JComboBox<Context>(Context.values());
		final JComboBox<Content> contentComboBox = new JComboBox<Content>(Content.values());
		final JComboBox<Category> categoryComboBox = new JComboBox<Category>(Category.values());
		final JComboBox<Kid> kidComboBox = new JComboBox<Kid>(Kid.values());
		final JComboBox<Tag> tagComboBox = new JComboBox<Tag>(Tag.values());


		contextComboBox.setSelectedIndex(-1);
		contentComboBox.setSelectedIndex(-1);
		categoryComboBox.setSelectedIndex(-1);
		kidComboBox.setSelectedIndex(-1);
		tagComboBox.setSelectedIndex(-1);
		
		JButton filterButton = new JButton("Filtrar");
		filterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date fromDate = (Date) fromDateTimeSpinner.getValue();
				Date toDate = (Date) toDateTimeSpinner.getValue();
				Context context = (Context) contextComboBox.getSelectedItem();
				Content content = (Content) contentComboBox.getSelectedItem();
				Category category = (Category) categoryComboBox.getSelectedItem();
				Kid kid = (Kid) kidComboBox.getSelectedItem();
				Tag tag = (Tag) tagComboBox.getSelectedItem();
				
				List<RowFilter<NotificationsTableModel, Object>> filters = new ArrayList<RowFilter<NotificationsTableModel, Object>>();
				
				RowFilter<NotificationsTableModel, Object> dateFromGE = orDateTimeFilter(fromDate, ComparisonType.AFTER, ComparisonType.EQUAL);
				RowFilter<NotificationsTableModel, Object> dateToLE = orDateTimeFilter(toDate, ComparisonType.BEFORE, ComparisonType.EQUAL);
				if (context != null){
					RowFilter<NotificationsTableModel, Object> contextFilter = RowFilter.regexFilter(context.toString());
					filters.add(contextFilter);					
				}
				
				if (content != null){
					RowFilter<NotificationsTableModel, Object> contentFilter = RowFilter.regexFilter(content.toString());
					filters.add(contentFilter);
				}
				
				if (category != null){
					RowFilter<NotificationsTableModel, Object> categoryFilter = RowFilter.regexFilter(category.toString());
					filters.add(categoryFilter);
				}
				
				if (kid != null){
					RowFilter<NotificationsTableModel, Object> kidFilter = RowFilter.regexFilter(kid.toString());
					filters.add(kidFilter);
				}
				
				if (tag != null){
					RowFilter<NotificationsTableModel, Object> tagFilter = RowFilter.regexFilter(tag.toString());
					filters.add(tagFilter);
				}
				
				filters.add(dateFromGE);
				filters.add(dateToLE);
				
				notificationsTable.setFilter(RowFilter.andFilter(filters));
			}
		});
		
		
		addBoxWithLabel("Contenido: ", contentComboBox, 0, 0);
		addBoxWithLabel("Context: ", contextComboBox, 2, 0);
		addBoxWithLabel("Categoría: ", categoryComboBox, 0, 1);
		addBoxWithLabel("Niño: ", kidComboBox, 2, 1);
		addBoxWithLabel("Desde: ", fromDateTimeSpinner, 0, 2);
		addBoxWithLabel("Hasta: ", toDateTimeSpinner, 2, 2);
		addBoxWithLabel("Etiqueta: ", tagComboBox, 0, 3);
		add(filterButton,
			new GridBagConstraintsBuilder()
				.at(1,  4)
				.width(3)
				.height(1)
				.fill(GridBagConstraints.HORIZONTAL)
				.insets(new Insets(5, 0, 5, 70))
				.build());
	}
	
}
