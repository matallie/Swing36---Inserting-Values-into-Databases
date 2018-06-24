package gui;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

public class FormPanel extends JPanel {
	
	private JLabel nameLabel;
	private JLabel occupationLabel;
	private JTextField nameField;
	private JTextField occupationField;
	private JButton okbtn;
	private FormListener formListener;
	private JList ageList;
	private JComboBox empCombo;
	private JCheckBox citizenCheck;
	private JTextField taxField;
	private JLabel taxLabel;
	
	private JRadioButton maleRadio;
	private JRadioButton femaleRadio;
	private ButtonGroup genderGroup;
	
	public FormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 250;
		setPreferredSize(dim);
		
		nameLabel = new JLabel("Name: ");
		occupationLabel = new JLabel("Occupation: ");
		nameField = new JTextField(10);
		occupationField = new JTextField(10);
		ageList = new JList();
		empCombo = new JComboBox<>();
		citizenCheck = new JCheckBox();
		taxField = new JTextField(10);
		taxLabel = new JLabel("Tax ID: ");
		okbtn = new JButton("OK");
		
		// Set up Mnemonics
		okbtn.setMnemonic(KeyEvent.VK_O);
		
		nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		nameLabel.setLabelFor(nameField);
		
		occupationLabel.setDisplayedMnemonic(KeyEvent.VK_C);
		occupationLabel.setLabelFor(occupationField);
		
		// Set up Radio Buttons
		maleRadio = new JRadioButton("male");
		femaleRadio = new JRadioButton("female");
		
		maleRadio.setActionCommand("male");
		femaleRadio.setActionCommand("female");
		
		genderGroup = new ButtonGroup();
		
		maleRadio.setSelected(true);
		
		// Set up gender radios
		genderGroup.add(maleRadio);
		genderGroup.add(femaleRadio);
		
		// Set up tax ID
		taxLabel.setEnabled(false);
		taxField.setEnabled(false);
		
		citizenCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isTicked = citizenCheck.isSelected();
				taxLabel.setEnabled(isTicked);
				taxField.setEnabled(isTicked);
			}
		});
		
		// Set up list box
		DefaultListModel ageModel = new DefaultListModel();
		ageModel.addElement(new AgeCategory(0, "Under 18"));
		ageModel.addElement(new AgeCategory(1, "18 to 65"));
		ageModel.addElement(new AgeCategory(2, "65 or over"));
		ageList.setModel(ageModel);
		
		ageList.setPreferredSize(new Dimension(110, 68));
		ageList.setBorder(BorderFactory.createEtchedBorder());
		ageList.setSelectedIndex(1);
		
		// Set up combo box
		DefaultComboBoxModel empModel = new DefaultComboBoxModel();
		empModel.addElement("employed");
		empModel.addElement("self-employed");
		empModel.addElement("unemployed");
		empCombo.setModel(empModel);
		empCombo.setSelectedIndex(0);
		empCombo.setEditable(true);
		
		okbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String occupation = occupationField.getText();
				AgeCategory ageCat = (AgeCategory)ageList.getSelectedValue();
				String empCat = (String)empCombo.getSelectedItem();
				String taxId = taxField.getText();
				boolean usCitizen = citizenCheck.isSelected();
				
				String gender = genderGroup.getSelection().getActionCommand();
				
				FormEvent ev = new FormEvent(this, name, occupation, ageCat.getid(), empCat,
						taxId, usCitizen, gender);
				
				if (formListener != null) {
					formListener.formEventOccurred(ev);
				}
			}
		});
		
		Border innerBorder = BorderFactory.createTitledBorder("Add Person");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		layoutComponents();
		
	}
	
	public void layoutComponents() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.NONE;
		
		/////////////////// First Row ///////////////////
		
		gc.gridy = 0; // ROW

		
		// Set the height and width of the row
		gc.weightx = 1;
		gc.weighty = 0.1;
		
		// Set Label
		gc.gridx = 0; // COLUMN
		// Position label to the right
		gc.anchor = GridBagConstraints.LINE_END;
		// Insets Parameters (top, left, bottom, right)
		gc.insets = new Insets(0, 0, 0, 5);
		add(nameLabel, gc);
		
		// Set text field
		gc.gridx = 1; // COLUMN
		// Position field to the left
		gc.anchor = GridBagConstraints.LINE_START;
		// Insets = null
		gc.insets = new Insets(0, 0, 0, 0);;
		add(nameField, gc);
		
		/////////////////// Next Row ///////////////////
		
		gc.gridy++; // ROW

		// Set the height and width of the row
		gc.weightx = 1;
		gc.weighty = 0.1;
		
		// Set Label
		gc.gridx = 0; // COLUMN
		// Position label to the right
		gc.anchor = GridBagConstraints.LINE_END;
		// Insets Parameters (top, left, bottom, right)
		gc.insets = new Insets(0, 0, 0, 5);
		add(occupationLabel, gc);
		
		// Set text field
		gc.gridx = 1; // COLUMN
		// Position field to the left
		gc.anchor = GridBagConstraints.LINE_START;
		// Insets = null
		gc.insets = new Insets(0, 0, 0, 0);
		add(occupationField, gc);
		
		/////////////////// Next Row ///////////////////
		
		gc.gridy++; // ROW
		
		// Set the height and width of the row
		gc.weightx = 1;
		gc.weighty = 0.2;
		
		// Set Label
		gc.gridx = 0; // COLUMN
		// Position label to the right
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		// Insets Parameters (top, left, bottom, right)
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Age: "), gc);
		
		// Set list box
		gc.gridx = 1; // COLUMN
		// Move button to the top of the row
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		// Insets = null
		gc.insets = new Insets(0, 0, 0, 0);;
		add(ageList, gc);
		
		/////////////////// Next Row ///////////////////
				
		gc.gridy++; // ROW
		
		// Set the height and width of the row
		gc.weightx = 1;
		gc.weighty = 0.2;
		
		// Set Label
		gc.gridx = 0; // COLUMN
		// Position label to the right
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		// Insets Parameters (top, left, bottom, right)
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Employment: "), gc);
		
		// Set combo box
		gc.gridx = 1; // COLUMN
		// Move button to the top of the row
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		// Insets = null
		gc.insets = new Insets(0, 0, 0, 0);;
		add(empCombo, gc);

		/////////////////// Next Row ///////////////////
				
		gc.gridy++; // ROW
		
		// Set the height and width of the row
		gc.weightx = 1;
		gc.weighty = 0.2;
		
		// Set Label
		gc.gridx = 0; // COLUMN
		// Position label to the right
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		// Insets Parameters (top, left, bottom, right)
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("US Citizen: "), gc);
		
		// Set check box
		gc.gridx = 1; // COLUMN
		// Move button to the top of the row
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		// Insets = null
		gc.insets = new Insets(0, 0, 0, 0);;
		add(citizenCheck, gc);
		
		/////////////////// Next Row ///////////////////
				
		gc.gridy++; // ROW
		
		// Set the height and width of the row
		gc.weightx = 1;
		gc.weighty = 0.2;
		
		// Set Label
		gc.gridx = 0; // COLUMN
		// Position label to the right
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		// Insets Parameters (top, left, bottom, right)
		gc.insets = new Insets(0, 0, 0, 5);
		add(taxLabel, gc);
		
		// Set field
		gc.gridx = 1; // COLUMN
		// Move button to the top of the row
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		// Insets = null
		gc.insets = new Insets(0, 0, 0, 0);;
		add(taxField, gc);
		
		/////////////////// Next Row ///////////////////
				
		gc.gridy++; // ROW
		
		// Set the height and width of the row
		gc.weightx = 1;
		gc.weighty = 0.05;
		
		// Set Label
		gc.gridx = 0; // COLUMN
		// Position label to the right
		gc.anchor = GridBagConstraints.LINE_END;
		// Insets Parameters (top, left, bottom, right)
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Gender: "), gc);
		
		// Set Radio - MALE
		gc.gridx = 1; // COLUMN
		// Move button to the top of the row
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		// Insets = null
		gc.insets = new Insets(0, 0, 0, 0);;
		add(maleRadio, gc);
		
		/////////////////// Next Row ///////////////////
		
		gc.gridy++; // ROW
		
		// Set the height and width of the row
		gc.weightx = 1;
		gc.weighty = 0.2;
		
		// Set Radio - Female
		gc.gridx = 1; // COLUMN
		// Move button to the top of the row
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		// Insets = null
		gc.insets = new Insets(0, 0, 0, 0);;
		add(femaleRadio, gc);

		/////////////////// Next Row ///////////////////
		
		gc.gridy++; // ROW
		
		// Set the height and width of the row
		gc.weightx = 1;
		gc.weighty = 2.0;
		
		gc.gridx = 1; // COLUMN
		// Move button to the top of the row
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		// Insets = null
		gc.insets = new Insets(0, 0, 0, 0);;
		add(okbtn, gc);
	}

	public void setFormListener(FormListener listener) {
		this.formListener = listener;
	}
}


class AgeCategory {
	private int id;
	private String text;
	
	public AgeCategory(int id, String text) {
		this.id = id;
		this.text = text;
	}
	
	public String toString() {
		return text;
	}
	
	public int getid() {
		return id;
	}
}