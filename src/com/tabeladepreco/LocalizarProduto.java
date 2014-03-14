package com.tabeladepreco;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class LocalizarProduto extends javax.swing.JFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;
	public LocalizarProduto() {
		getContentPane().setLayout(null);
		
		JLabel lblCdigo = new JLabel("C\u00F3digo");
		lblCdigo.setBounds(10, 11, 46, 14);
		getContentPane().add(lblCdigo);
		
		textField = new JTextField();
		textField.setBounds(10, 36, 86, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o");
		lblDescrio.setBounds(107, 11, 46, 14);
		getContentPane().add(lblDescrio);
		
		textField_1 = new JTextField();
		textField_1.setBounds(106, 36, 267, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 226, 363, -157);
		getContentPane().add(scrollPane);
		
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
		table.getColumnModel().getColumn(0).setMinWidth(10);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setMinWidth(95);
		table.setBounds(10, 67, 363, 183);
		getContentPane().add(table);
	}
}