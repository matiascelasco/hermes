package hermes.monitor.tags;

import hermes.dataloader.FactoryDAO;
import hermes.dataloader.Notification;
import hermes.dataloader.TagDAOforJDBC;
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
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
	final private JComboBox<Tag> tagForRemoveComboBox = new JComboBox<Tag>();
	final private JComboBox<Tag> tagForAssingComboBox = new JComboBox<Tag>();
	final private JComboBox<Tag> tagForRenameComboBox = new JComboBox<Tag>();
	
	final private NotificationsTable notificationsTable;
	
	private void update() throws SQLException{
		List<Tag> listOfTags = FactoryDAO.getTagDAO().findAll();
		Tag[] tags = new Tag[listOfTags.size()];
		listOfTags.toArray(tags);
		tagForAssingComboBox.removeAllItems();
		tagForRemoveComboBox.removeAllItems();
		tagForRenameComboBox.removeAllItems();
		for (Tag tag : tags) {
			tagForAssingComboBox.addItem(tag);
			tagForRemoveComboBox.addItem(tag);
			tagForRenameComboBox.addItem(tag);	
		}
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

		List<Tag> listOfTags;
		try {
			listOfTags = FactoryDAO.getTagDAO().findAll();
			Tag[] tags = new Tag[listOfTags.size()];
			listOfTags.toArray(tags);
			for (Tag tag : tags) {
				tagForAssingComboBox.addItem(tag);
				tagForRemoveComboBox.addItem(tag);
				tagForRenameComboBox.addItem(tag);				
			}

			JButton createButton = new JButton("Crear");
			JButton removeButton = new JButton("Eliminar");
			JButton assignButton = new JButton("Asignar");
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
						n.setTag(tag);
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
