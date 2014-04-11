package com.tabeladepreco;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.tabeladepreco.entidades.Produto;

@SuppressWarnings("serial")
public class LocalizarProduto extends javax.swing.JFrame {
	private JTextField edt_descricaoLocalizar;
	private JScrollPane scrollPane;
	public JTable tbl_localizarProduto;
	private JLabel lbl_descricaoLocaizar;
	private JButton btn_ok;
	DefaultTableModel model;
	TableRowSorter<TableModel> sorter;
	
	List<Produto> listaDeProdutos;
	
	public LocalizarProduto() {
		desenharJanela();
		listaDeProdutos = new Xml().LerXml("produtos.xml");
		preencheTabela();
	}
	
	private void desenharJanela(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
		setBounds(500, 500, 387, 300);
		
		lbl_descricaoLocaizar = new JLabel("Localizar: ");
		lbl_descricaoLocaizar.setBounds(10, 11, 69, 14);
		getContentPane().add(lbl_descricaoLocaizar);
		
		edt_descricaoLocalizar = new JTextField();
		edt_descricaoLocalizar.setBounds(10, 25, 363, 20);
		getContentPane().add(edt_descricaoLocalizar);
		edt_descricaoLocalizar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = edt_descricaoLocalizar.getText();
	               if (text.length() == 0) {
	                 sorter.setRowFilter(null);
	               } else {
	                 try {
	                   sorter.setRowFilter(
	                       RowFilter.regexFilter("(?i)"+text));
	                 } catch (PatternSyntaxException pse) {
	                   System.err.println("Erro");
	                 }
	               }
			}
		});
		edt_descricaoLocalizar.setColumns(10);
		
		
		tbl_localizarProduto = new JTable();
		tbl_localizarProduto.setFocusTraversalKeysEnabled(false);
		tbl_localizarProduto.setFocusable(false);
		tbl_localizarProduto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		model = new DefaultTableModel(
				new Object[][] {
						{null, null},
					},
					new String[] {
						"C\u00F3digo", "Descri\u00E7\u00E3o"
					}
				) {
					@SuppressWarnings({ "rawtypes", "unused" })
					Class[] columnTypes = new Class[] {
						String.class, String.class
					};
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int column) {
			               Class returnValue;
			               if ((column >= 0) && (column < getColumnCount())) {
			                 returnValue = getValueAt(0, column).getClass();
			               } else {
			                 returnValue = Object.class;
			               }
			               return returnValue;
		             }
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
		tbl_localizarProduto.getColumnModel().getColumn(0).setMaxWidth(44);
		tbl_localizarProduto.getColumnModel().getColumn(1).setResizable(false);
		tbl_localizarProduto.getColumnModel().getColumn(1).setPreferredWidth(120);
		tbl_localizarProduto.getColumnModel().getColumn(1).setMinWidth(120);
		tbl_localizarProduto.getTableHeader().setReorderingAllowed(false);
		tbl_localizarProduto.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount()==2) {
					dispose();
				}
			}
		});
		sorter = new TableRowSorter<TableModel>(model);
		tbl_localizarProduto.setRowSorter(sorter);
		tbl_localizarProduto.setBounds(1, 85, 344, 309);		
		scrollPane = new JScrollPane(tbl_localizarProduto);
		scrollPane.setBounds(10, 56, 363, 160);
		getContentPane().add(scrollPane);
		
		btn_ok = new JButton("Ok");
		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( tbl_localizarProduto.getSelectedRow()!=-1 )
					dispose();
				else
					JOptionPane.showMessageDialog(null, "Selecione algum objeto!");
			}
		});
		btn_ok.setBounds(314, 227, 59, 23);
		getContentPane().add(btn_ok);
	}
	
	 private void preencheTabela(){
		 model.setNumRows(0);
		for (int i = 0; i < listaDeProdutos.size(); i++) {
			model.addRow(new String[]{
				String.valueOf(listaDeProdutos.get(i).getCodigo()),
				String.valueOf(listaDeProdutos.get(i).getDescricao())
			});
		}
    }
}