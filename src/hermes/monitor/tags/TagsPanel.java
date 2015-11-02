package hermes.monitor.tags;

import hermes.enums.Tag;
import hermes.helpers.GridBagConstraintsBuilder;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class TagsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private void addLabelComponentAndButton(String labelString, Component component, String buttonString, int row){
		
		add(new JLabel(labelString),
				new GridBagConstraintsBuilder()
					.at(0, row)
					.size(1)
					.build());
		
		add(component,
			new GridBagConstraintsBuilder()
				.at(1, row)
				.size(1)
				.fill(GridBagConstraints.HORIZONTAL)
				.build());
	
		if (buttonString != null){
			add(new JButton(buttonString),
					new GridBagConstraintsBuilder()
			.at(3, row)
			.size(1)
			.fill(GridBagConstraints.HORIZONTAL)
			.build());			
		}
	}
	
	public TagsPanel() {
		setBorder(BorderFactory.createTitledBorder("Etiquetas"));
		
		setLayout(new GridBagLayout());
		
		addLabelComponentAndButton("Crear etiqueta:", new JTextField(), "Crear", 0);
		addLabelComponentAndButton("Eliminar etiqueta:", new JComboBox<Tag>(Tag.values()), "Eliminar", 1);
		add(new JSeparator(), new GridBagConstraintsBuilder().x(0).y(1).width(3).height(1).build());
		addLabelComponentAndButton("Asignar/desasignar:", new JComboBox<Tag>(Tag.values()), "Asignar", 2);
		addLabelComponentAndButton("Renombrar etiqueta:", new JComboBox<Tag>(Tag.values()), null, 3);
		addLabelComponentAndButton("Nuevo nombre:", new JTextField(), "Renombrar", 4);
		
	
	}
	
}
