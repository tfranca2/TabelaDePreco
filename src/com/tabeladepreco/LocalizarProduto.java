package com.tabeladepreco;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class LocalizarProduto extends javax.swing.JFrame {
	private JTextField edt_codigoLocalizar;
	private JTextField edt_descricaoLocalizar;
	private JScrollPane scrollPane;
	private JTable tbl_localizarProduto;
	private JLabel lbl_descricaoLocaizar;
	private JLabel lbl_codigoLocalizar;
	private JButton btn_ok;
	DefaultTableModel model;
	
	public LocalizarProduto() {
		desenharJanela();	
	}
	
	private void desenharJanela(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
		setBounds(500, 500, 400, 300);
		
		lbl_codigoLocalizar = new JLabel("C\u00F3digo");
		lbl_codigoLocalizar.setBounds(10, 11, 46, 14);
		getContentPane().add(lbl_codigoLocalizar);
		
		edt_codigoLocalizar = new JTextField();
		edt_codigoLocalizar.setBounds(10, 36, 46, 20);
		getContentPane().add(edt_codigoLocalizar);
		edt_codigoLocalizar.setColumns(10);
		
		lbl_descricaoLocaizar = new JLabel("Descri\u00E7\u00E3o");
		lbl_descricaoLocaizar.setBounds(67, 11, 46, 14);
		getContentPane().add(lbl_descricaoLocaizar);
		
		edt_descricaoLocalizar = new JTextField();
		edt_descricaoLocalizar.setBounds(66, 36, 307, 20);
		getContentPane().add(edt_descricaoLocalizar);
		edt_descricaoLocalizar.setColumns(10);
		
		
		tbl_localizarProduto = new JTable();
		model = new DefaultTableModel(
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
				};
		tbl_localizarProduto.setModel(model);
		tbl_localizarProduto.getColumnModel().getColumn(0).setResizable(false);
		tbl_localizarProduto.getColumnModel().getColumn(0).setPreferredWidth(44);
		tbl_localizarProduto.getColumnModel().getColumn(0).setMinWidth(44);
		tbl_localizarProduto.getColumnModel().getColumn(1).setResizable(false);
		tbl_localizarProduto.getColumnModel().getColumn(1).setPreferredWidth(120);
		tbl_localizarProduto.getColumnModel().getColumn(1).setMinWidth(120);
		tbl_localizarProduto.setBounds(1, 85, 344, 309);		
		
		scrollPane = new JScrollPane(tbl_localizarProduto);
		scrollPane.setBounds(10, 67, 363, 149);
		getContentPane().add(scrollPane);
		
		btn_ok = new JButton("Ok");
		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btn_ok.setBounds(314, 227, 59, 23);
		getContentPane().add(btn_ok);
	}
	

}