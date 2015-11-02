package hermes.monitor.filters;

import hermes.enums.Category;
import hermes.enums.Content;
import hermes.enums.Context;
import hermes.enums.Kid;
import hermes.enums.Tag;
import hermes.helpers.GridBagConstraintsBuilder;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;


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
	
	public FiltersPanel() {
		setBorder(BorderFactory.createTitledBorder("Filtros"));
		setLayout(new GridBagLayout());
		
		addBoxWithLabel("Contenido: ", new JComboBox<Content>(Content.values()), 0, 0);
		addBoxWithLabel("Context: ", new JComboBox<Context>(Context.values()), 2, 0);
		addBoxWithLabel("Categoría: ", new JComboBox<Category>(Category.values()), 0, 1);
		addBoxWithLabel("Niño: ", new JComboBox<Kid>(Kid.values()), 2, 1);
		addBoxWithLabel("Desde: ", new JSpinner(new SpinnerDateModel()), 0, 2);
		addBoxWithLabel("Hasta: ", new JSpinner(new SpinnerDateModel()), 2, 2);
		addBoxWithLabel("Etiqueta: ", new JComboBox<Tag>(Tag.values()), 0, 3);
		add(new JButton("Filtrar"),
			new GridBagConstraintsBuilder()
				.at(1,  4)
				.width(3)
				.height(1)
				.fill(GridBagConstraints.HORIZONTAL)
				.insets(new Insets(5, 0, 5, 70))
				.build());
	}
}
