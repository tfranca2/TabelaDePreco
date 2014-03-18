package com.tabeladepreco;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class LocalizarProduto extends javax.swing.JFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JScrollPane scrollPane;
	private JTable table;
	public LocalizarProduto() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
		setBounds(500, 500, 400, 300);
		
		JLabel lblCdigo = new JLabel("C\u00F3digo");
		lblCdigo.setBounds(10, 11, 46, 14);
		getContentPane().add(lblCdigo);
		
		textField = new JTextField();
		textField.setBounds(10, 36, 46, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o");
		lblDescrio.setBounds(67, 11, 46, 14);
		getContentPane().add(lblDescrio);
		
		textField_1 = new JTextField();
		textField_1.setBounds(66, 36, 307, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
			},
			new String[] {
				"C\u00F3digo", "Descri\u00E7\u00E3o"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(44);
		table.getColumnModel().getColumn(0).setMinWidth(44);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setMinWidth(120);
		table.setBounds(1, 85, 344, 309);
		//getContentPane().add(table);
		
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 67, 363, 149);
		getContentPane().add(scrollPane);
		
		JButton btnNewButton = new JButton("Ok");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnNewButton.setBounds(314, 227, 59, 23);
		getContentPane().add(btnNewButton);
		
	}
}