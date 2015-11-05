package hermes.monitor.views.tags;

import hermes.data.Tag;
import hermes.monitor.helpers.GridBagConstraintsBuilder;
import hermes.monitor.helpers.TagsComboBox;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;

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

	private JTextField newTagNameField = new JTextField();;
	private JTextField renameTagNameField = new JTextField();

	private TagsComboBox tagForRemoveComboBox;
	private TagsComboBox tagForAssingComboBox;
	private TagsComboBox tagForRenameComboBox;

	private JButton createButton;
	private JButton deleteButton;
	private JButton assignButton;
	private JButton renameButton;


	public TagsPanel(List<Tag> tags) {
		tagForRemoveComboBox = new TagsComboBox(tags);
		tagForAssingComboBox = new TagsComboBox(tags);
		tagForRenameComboBox = new TagsComboBox(tags);
		GridBagConstraintsBuilder separatorConstrainsBuilder =
			new GridBagConstraintsBuilder()
				.x(0)
				.width(5)
				.height(1)
				.weightY(1)
				.fill(GridBagConstraints.HORIZONTAL);

		setBorder(BorderFactory.createTitledBorder("Etiquetas"));

		setLayout(new GridBagLayout());

		createButton = new JButton("Crear");
		deleteButton = new JButton("Eliminar");
		assignButton = new JButton("Asignar/Desasignar");
		renameButton = new JButton("Renombrar");

		addLabelComponentAndButton("Crear etiqueta:", newTagNameField, createButton, 0);
		add(new JSeparator(), separatorConstrainsBuilder.y(1).build());
		addLabelComponentAndButton("Eliminar etiqueta:", tagForRemoveComboBox, deleteButton, 2);
		add(new JSeparator(), separatorConstrainsBuilder.y(3).build());
		addLabelComponentAndButton("Asignar/desasignar:", tagForAssingComboBox, assignButton, 4);
		add(new JSeparator(), separatorConstrainsBuilder.y(5).build());
		addLabelComponentAndButton("Renombrar etiqueta:", tagForRenameComboBox, null, 6);
		addLabelComponentAndButton("Nuevo nombre:", renameTagNameField, renameButton, 7);

	}

	public Tag getTagToBeRenamed() {
		return (Tag) tagForRenameComboBox.getSelectedItem();
	}

	public String getNewNameToRenameTag() {
		return renameTagNameField.getText();
	}

	public void updateTagComboBoxes(List<Tag> tags) {
		tagForAssingComboBox.updateContent(tags);
		tagForRemoveComboBox.updateContent(tags);
		tagForRenameComboBox.updateContent(tags);
	}

	public Tag getTagToBeAssigned() {
		return (Tag) tagForAssingComboBox.getSelectedItem();
	}

	public Tag getTagToBeDeleted() {
		return (Tag) tagForRemoveComboBox.getSelectedItem();
	}

	public String getNameForNewTag() {
		return newTagNameField.getText();
	}

	public void addTagCreatedListener(ActionListener listener) {
		createButton.addActionListener(listener);
	}

	public void addTagDeletedListener(ActionListener listener) {
		deleteButton.addActionListener(listener);
	}

	public void addTagRenamedListener(ActionListener listener) {
		renameButton.addActionListener(listener);
	}

	public void addTagAssignedListener(ActionListener listener) {
		assignButton.addActionListener(listener);
	}

}
