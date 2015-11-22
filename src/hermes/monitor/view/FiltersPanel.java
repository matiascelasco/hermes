package hermes.monitor.view;

import hermes.model.Category;
import hermes.model.Content;
import hermes.model.Context;
import hermes.model.Kid;
import hermes.model.Tag;
import hermes.monitor.view.utils.DateTimePicker;
import hermes.monitor.view.utils.GridBagConstraintsBuilder;
import hermes.monitor.view.utils.NotificationsTable;
import hermes.monitor.view.utils.TagsComboBox;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


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
	
	DateTimePicker fromDateTimeSpinner = new DateTimePicker();
	DateTimePicker toDateTimeSpinner = new DateTimePicker();
	JComboBox<Context> contextComboBox = new JComboBox<Context>();
	JComboBox<Content> contentComboBox = new JComboBox<Content>();
	JComboBox<Category> categoryComboBox = new JComboBox<Category>();
	JComboBox<Kid> kidComboBox = new JComboBox<Kid>();
	TagsComboBox tagsComboBox;
	private Date minDate;
	private Date maxDate;

	
	JButton filterButton = new JButton("Filtrar");
	JButton clearButton = new JButton("Limpiar");
	
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
		fromDateTimeSpinner.setDate(minDate);
		toDateTimeSpinner.setDate(maxDate);
		contextComboBox.setSelectedIndex(-1);
		contentComboBox.setSelectedIndex(-1);
		categoryComboBox.setSelectedIndex(-1);
		kidComboBox.setSelectedIndex(-1);
		tagsComboBox.setSelectedIndex(-1);
	}
	
	public void updateTagComboBox(List<Tag> tags) {
		tagsComboBox.updateContent(tags);
	}

}
