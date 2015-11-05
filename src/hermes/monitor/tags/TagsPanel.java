package hermes.monitor.tags;

import hermes.data.Notification;
import hermes.data.Tag;
import hermes.data.dao.FactoryDAO;
import hermes.helpers.GridBagConstraintsBuilder;
import hermes.monitor.TagsComboBox;
import hermes.monitor.notifications.NotificationsTable;
import hermes.monitor.notifications.NotificationsTableModel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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

	final private JTextField newTagNameField = new JTextField();;
	final private JTextField renameTagNameField = new JTextField();;
	final private TagsComboBox tagForRemoveComboBox = new TagsComboBox();
	final private TagsComboBox tagForAssingComboBox = new TagsComboBox();
	final private TagsComboBox tagForRenameComboBox = new TagsComboBox();
	
	final private NotificationsTable notificationsTable;
	
	private void update() throws SQLException{
		tagForAssingComboBox.updateContent();
		tagForRemoveComboBox.updateContent();
		tagForRenameComboBox.updateContent();
		((NotificationsTableModel) notificationsTable.getModel()).updateData();
		notificationsTable.repaint();
	}
	
	public TagsPanel(final NotificationsTable notificationsTable) {
		this.notificationsTable = notificationsTable;
		GridBagConstraintsBuilder separatorConstrainsBuilder =
			new GridBagConstraintsBuilder()
				.x(0)
				.width(5)
				.height(1)
				.weightY(1)
				.fill(GridBagConstraints.HORIZONTAL);

		setBorder(BorderFactory.createTitledBorder("Etiquetas"));

		setLayout(new GridBagLayout());

		JButton createButton = new JButton("Crear");
		JButton removeButton = new JButton("Eliminar");
		JButton assignButton = new JButton("Asignar/Desasignar");
		JButton renameButton = new JButton("Renombrar");

		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tag tag = new Tag();
				tag.setName(newTagNameField.getText());
				try {
					FactoryDAO.getTagDAO().persist(tag);
					update();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		renameButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Tag tag = (Tag) tagForRenameComboBox.getSelectedItem();
				tag.setName(renameTagNameField.getText());
				try {
					FactoryDAO.getTagDAO().persist(tag);
					update();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		removeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Tag tag = (Tag) tagForRemoveComboBox.getSelectedItem();
				tag.setName(renameTagNameField.getText());
				try {
					FactoryDAO.getTagDAO().delete(tag);
					update();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		assignButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Tag tag = (Tag) tagForAssingComboBox.getSelectedItem();
				NotificationsTableModel tableModel = (NotificationsTableModel) notificationsTable.getModel();
				for (int x :notificationsTable.getSelectedRows()){
					Notification n = tableModel.getNotification(notificationsTable.convertRowIndexToModel(x));
					if (n.getTag() != null && n.getTag().equals(tag)){
						n.setTag(null);
					} else {
						n.setTag(tag);
					}
					try {
						FactoryDAO.getNotificationDAO().persist(n);
						update();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});

		addLabelComponentAndButton("Crear etiqueta:", newTagNameField, createButton, 0);
		add(new JSeparator(), separatorConstrainsBuilder.y(1).build());
		addLabelComponentAndButton("Eliminar etiqueta:", tagForRemoveComboBox, removeButton, 2);
		add(new JSeparator(), separatorConstrainsBuilder.y(3).build());
		addLabelComponentAndButton("Asignar/desasignar:", tagForAssingComboBox, assignButton, 4);
		add(new JSeparator(), separatorConstrainsBuilder.y(5).build());
		addLabelComponentAndButton("Renombrar etiqueta:", tagForRenameComboBox, null, 6);
		addLabelComponentAndButton("Nuevo nombre:", renameTagNameField, renameButton, 7);

	}

}
