package hermes.monitor.view;

import hermes.model.Tag;
import hermes.monitor.view.utils.GridBagConstraintsBuilder;
import hermes.monitor.view.utils.ListComboBox;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class TagsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private void addLabelComponentAndButton(String labelString, Component component, JButton button, int row){
		add(new JLabel(labelString),
				new GridBagConstraintsBuilder()
					.at(0, row)
					.anchor(GridBagConstraints.EAST)
					.size(1)
					.weightX(1).weightY(1)
					.build());

		component.setPreferredSize(new Dimension(150, 20));

		add(component,
			new GridBagConstraintsBuilder()
				.at(1, row)
				.size(1)
				.weightX(1).weightY(1)
				.fill(GridBagConstraints.HORIZONTAL)
				.insets(new Insets(10, 5, 10, 5))
				.build());

		if (button != null){
			add(button,
					new GridBagConstraintsBuilder()
			.at(3, row)
			.size(1)
			.weightX(1).weightY(1)
			.fill(GridBagConstraints.HORIZONTAL)
			.build());
		}
	}

	JTextField newTagNameField = new JTextField();;
	JTextField renameTagNameField = new JTextField();

	ListComboBox<Tag> tagForRemoveComboBox = new ListComboBox<Tag>();
	ListComboBox<Tag> tagForAssingComboBox = new ListComboBox<Tag>();
	ListComboBox<Tag> tagForRenameComboBox = new ListComboBox<Tag>();

	JButton createButton = new JButton("Crear");
	JButton deleteButton = new JButton("Eliminar");
	JButton assignButton = new JButton("Asignar/Desasignar");
	JButton renameButton = new JButton("Renombrar");

	public TagsPanel() {
		GridBagConstraintsBuilder separatorConstrainsBuilder =
			new GridBagConstraintsBuilder()
				.x(0)
				.width(5)
				.height(1)
				.weightY(1)
				.fill(GridBagConstraints.HORIZONTAL);

		setBorder(BorderFactory.createTitledBorder("Etiquetas"));

		setLayout(new GridBagLayout());

		addLabelComponentAndButton("Crear etiqueta:", newTagNameField, createButton, 0);
		add(new JSeparator(), separatorConstrainsBuilder.y(1).build());
		addLabelComponentAndButton("Eliminar etiqueta:", tagForRemoveComboBox, deleteButton, 2);
		add(new JSeparator(), separatorConstrainsBuilder.y(3).build());
		addLabelComponentAndButton("Asignar/desasignar:", tagForAssingComboBox, assignButton, 4);
		add(new JSeparator(), separatorConstrainsBuilder.y(5).build());
		addLabelComponentAndButton("Renombrar etiqueta:", tagForRenameComboBox, null, 6);
		addLabelComponentAndButton("Nuevo nombre:", renameTagNameField, renameButton, 7);

	}

}
