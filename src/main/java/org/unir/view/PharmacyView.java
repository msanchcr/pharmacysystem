package org.unir.view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import org.unir.core.DistributorEnum;
import org.unir.core.MedicineEnum;
import org.unir.core.Order;
import org.unir.core.OrderFactory;
import org.unir.util.Util;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;

public class PharmacyView extends JFrame {

    private final String JFRAME_TITLE = "Pharmacy view";
    private final String JBUTTON_SUBMIT = "Submit";
    private final String JBUTTON_CLEAR = "Clear";
    private final String JBUTTON_BACK = "Back";
    private final String JLABEL_FIELD_NAME = "Name: ";
    private final String JLABEL_FIELD_TYPE = "Type: ";
    private final String JLABEL_FIELD_AMOUNT = "Amount: ";
    private final String JLABEL_FIELD_DISTRIBUTOR = "Distributor: ";
    private final String JLABEL_FIELD_DIRECTIONS = "Directions: ";
    private final String JLABEL_DIR_1 = "Calle de la Rosa 28";
    private final String JLABEL_DIR_2 = "Calle Alcazabilla 3";
	
	private static PharmacyView instance;
	private JPanel contentPane;
	private JTextField textFieldName;
	private JTextField textFieldAmount;

	/**
	 * Create the frame.
	 */
	public PharmacyView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelMain = new JPanel();
		contentPane.add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.PAGE_AXIS));
		
		JPanel panelForm = new JPanel();
		panelMain.add(panelForm);
		panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.PAGE_AXIS));
		
		JPanel panelFieldName = new JPanel();
		panelForm.add(panelFieldName);
		
		JLabel labelFieldName = new JLabel(JLABEL_FIELD_NAME);
		panelFieldName.add(labelFieldName);
		
		textFieldName = new JTextField();
		textFieldName.setText("");
		panelFieldName.add(textFieldName);
		textFieldName.setColumns(10);
		
		JPanel panelFieldType = new JPanel();
		panelForm.add(panelFieldType);
		
		JLabel labelFieldType = new JLabel(JLABEL_FIELD_TYPE);
		panelFieldType.add(labelFieldType);
		
		JComboBox fieldType = new JComboBox();
		panelFieldType.add(fieldType);
		for (MedicineEnum medicine: MedicineEnum.values()) {
            fieldType.addItem(medicine.getMedicineName());
        }
		
		JPanel panelFieldAmount = new JPanel();
		panelForm.add(panelFieldAmount);
		
		JLabel labelFieldAmount = new JLabel(JLABEL_FIELD_AMOUNT);
		panelFieldAmount.add(labelFieldAmount);
		
		textFieldAmount = new JTextField();
		panelFieldAmount.add(textFieldAmount);
		textFieldAmount.setColumns(10);
		
		JPanel panelFieldDistributor = new JPanel();
		panelForm.add(panelFieldDistributor);
		
		JLabel labelFieldDistributor = new JLabel(JLABEL_FIELD_DISTRIBUTOR);
		panelFieldDistributor.add(labelFieldDistributor);
        ButtonGroup fieldDistributor = new ButtonGroup();
        for (DistributorEnum distributor: DistributorEnum.values()) {
            JRadioButton radioButton = new JRadioButton(distributor.getDistributorName());
            radioButton.setActionCommand(distributor.getDistributorName());
            fieldDistributor.add(radioButton);
            panelFieldDistributor.add(radioButton);
        }
		
		JPanel panelFieldDirections = new JPanel();
		panelForm.add(panelFieldDirections);
		
		JLabel labelFieldDirections = new JLabel(JLABEL_FIELD_DIRECTIONS);
		panelFieldDirections.add(labelFieldDirections);
        JCheckBox direction1 = new JCheckBox(JLABEL_DIR_1);
        JCheckBox direction2 = new JCheckBox(JLABEL_DIR_2);
        panelFieldDirections.add(direction1);
        panelFieldDirections.add(direction2);
		
		JPanel panelActions = new JPanel();
		panelMain.add(panelActions);
		
		JButton cancel = new JButton(JBUTTON_CLEAR);
		
		JButton submit = new JButton(JBUTTON_SUBMIT);
		submit.addActionListener( e -> {
	            List<String> directions = new ArrayList<String> ();
	            if (direction1.isSelected()) {
	                directions.add(direction1.getText());
	            }
	            if (direction2.isSelected()) {
	                directions.add(direction2.getText());
	            }
	            OrderFactory factory = OrderFactory.getInstance();
	            try {
	                Order order = factory.create(
	                    (textFieldName.getText() == null) ? null : textFieldName.getText(),
	                    (fieldType.getSelectedItem() == null)? null : fieldType.getSelectedItem().toString(),
	                    Util.parseInt(textFieldAmount.getText()),
	                    (fieldDistributor.getSelection() == null)? null : fieldDistributor.getSelection().getActionCommand(),
	                    directions
	                );
	                System.out.println("New order has been created.");
	                System.out.println(order);
	                cancel.doClick();
	            } catch (IllegalArgumentException ex) {
	            	System.out.println("An error occurred while creating the order.");
	                System.out.println(ex.getMessage());
	            }
		});
		panelActions.add(submit);
		
		cancel.addActionListener(e -> {
			System.out.println("Clear button has been clicked.");
			textFieldName.setText(null);
	        fieldType.setSelectedIndex(0);
	        textFieldAmount.setText(null);
	        fieldDistributor.clearSelection();
	        direction1.setSelected(false);
	        direction2.setSelected(false);
		});
		panelActions.add(cancel);
		
		JPanel panelNav = new JPanel();
		contentPane.add(panelNav, BorderLayout.SOUTH);
		
		JButton back = new JButton(JBUTTON_BACK);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Back button has been clicked.");
				setVisible(false);
				MainView view = MainView.getInstance();
				view.setVisible(true);
			}
		});
		panelNav.add(back);
	}
	
	public static PharmacyView getInstance () {
		if (instance == null) {
			instance = new PharmacyView ();
		}
		return instance;
	}

}
