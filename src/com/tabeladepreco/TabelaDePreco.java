package com.tabeladepreco;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.tabeladepreco.entidades.Estado;
import com.tabeladepreco.entidades.Produto;

@SuppressWarnings("serial")
public class TabelaDePreco extends javax.swing.JFrame {

    private JPanel pnl_tabelaDePreco;
    private JPanel pnl_cadastroProduto;
    private JTabbedPane tbp_painel;
    private JLabel lbl_calcularPrecoFabrica;
    private JTextField edt_precoFinal;
    private JLabel lbl_precoFinal;
    private JLabel lbl_descricao;
    private JTextField edt_descricao;
    private JLabel lbl_quantidade;
    private JTextField edt_quantidade;
    private JTextField edt_ipi;
    private JLabel lbl_ipi;
    private JTable tbl_impostos;
    private DefaultTableModel model;
    private JScrollPane scp_impostos;
    private JLabel lbl_codigo;
    private JButton btn_excluir;
    private JButton btn_salvar;
    private JComboBox<String> cbx_codigoProduto;
    private JLabel lbl_codigoProduto;
    private JLabel edt_produto;
    private JLabel edt_precoFabrica;
    private JLabel edt_icms;
    private JLabel edt_baseRetido;
    private JLabel edt_ipi_tbp;
    private JLabel edt_retido;
    private JLabel lbl_produto;
    private JLabel lbl_precoFabrica;
    private JLabel lbl_icms;
    private JLabel lbl_ipi_tbp;
    private JLabel lbl_baseRetido;
    private JLabel lbl_retido;
    private JLabel lbl_valoresPorEstado;
    private JTextField edt_codigo;
    private JCheckBox chc_vendaDentroEstado;
    private JButton btn_calcular;
    
    private float aliquota;
    private float pauta;
    private float quantidade;
    private float ipi;
    private float precoFinal;
    private float precoFabrica;
    private List<Produto> listaDeProdutos;
    private JComboBox<String> cbx_estado;
    private JLabel lbl_estado;
    Produto pro = null;
    private JButton btn_Localizar;

    public static void main(String[] args) {
        TabelaDePreco janela = new TabelaDePreco();
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }

    public TabelaDePreco() {
    	super();
    	try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        listaDeProdutos = new Xml().LerXml("produtos.xml");
        desenhaJanela();
    }

	private void desenhaJanela() {
        try {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            getContentPane().setLayout(null);
            setSize(488,400);
            setPreferredSize(getSize()); 
            setResizable(false);
            setTitle("Montagem de Tabela de Pre\u00E7o");

            tbp_painel= new JTabbedPane();
            tbp_painel.setBounds(10, 11, 464, 350);
            getContentPane().add(tbp_painel);

            pnl_tabelaDePreco= new JPanel();
            pnl_tabelaDePreco.setBounds(48, 35, 224, 204);
            pnl_tabelaDePreco.setLayout(null);
            tbp_painel.addTab("Tabela de Pre\u00E7o", null, pnl_tabelaDePreco, null);
            
            lbl_calcularPrecoFabrica = new JLabel("Calcular Pre\u00E7o F\u00E1brica");
            lbl_calcularPrecoFabrica.setFont(new Font("Tahoma", Font.PLAIN, 16));
            lbl_calcularPrecoFabrica.setHorizontalAlignment(SwingConstants.CENTER);
            lbl_calcularPrecoFabrica.setBounds(10, 11, 439, 27);
            pnl_tabelaDePreco.add(lbl_calcularPrecoFabrica);
            
            lbl_precoFinal = new JLabel("Pre\u00E7o Final");
            lbl_precoFinal.setBounds(90, 146, 69, 14);
            pnl_tabelaDePreco.add(lbl_precoFinal);
            
            edt_precoFinal = new JTextField();
            edt_precoFinal.setBounds(90, 160, 86, 20);
            pnl_tabelaDePreco.add(edt_precoFinal);
            edt_precoFinal.setColumns(10);
            
            cbx_codigoProduto = new JComboBox<String>();
            cbx_codigoProduto.addItem("");
            for (int i = 0; i < listaDeProdutos.size(); i++)
            	cbx_codigoProduto.addItem(String.valueOf(listaDeProdutos.get(i).getCodigo()));
            cbx_codigoProduto.setBounds(10, 63, 106, 20);
            cbx_codigoProduto.addItemListener(new ItemListener() {
            	public void itemStateChanged(ItemEvent e) {
            		resetFormulario();
            		try {
            			if( cbx_codigoProduto.getSelectedIndex() != 0 )
            				pro = produtoDaListaPorCodigo(Integer.parseInt((String) cbx_codigoProduto.getSelectedItem()));
            			else
            				pro = null;
					} catch (Exception e2) { }
            		preencheCampos(pro);
            	}
            });
            pnl_tabelaDePreco.add(cbx_codigoProduto);
            
            lbl_codigoProduto = new JLabel("C\u00F3digo do produto");
            lbl_codigoProduto.setBounds(10, 49, 106, 14);
            pnl_tabelaDePreco.add(lbl_codigoProduto);
            
            chc_vendaDentroEstado = new JCheckBox("Venda para dentro do estado");
            chc_vendaDentroEstado.setSelected(true);
            chc_vendaDentroEstado.addItemListener(new ItemListener() {
            	public void itemStateChanged(ItemEvent e) {
            		aliquotaPauta(pro);
            	}
            });
            chc_vendaDentroEstado.setBounds(186, 114, 198, 23);
            pnl_tabelaDePreco.add(chc_vendaDentroEstado);
            
            lbl_produto = new JLabel("Produto");
            lbl_produto.setBounds(126, 49, 69, 14);
            pnl_tabelaDePreco.add(lbl_produto);
            
            edt_produto = new JLabel("");
            edt_produto.setBorder(UIManager.getBorder("TextField.border"));
            edt_produto.setOpaque(true);
            edt_produto.setBackground(Color.white);
            edt_produto.setBounds(126, 63, 323, 20);
            pnl_tabelaDePreco.add(edt_produto);
            
            lbl_precoFabrica = new JLabel("Pre\u00E7o F\u00E1brica");
            lbl_precoFabrica.setBounds(186, 146, 80, 14);
            pnl_tabelaDePreco.add(lbl_precoFabrica);
            
            edt_precoFabrica = new JLabel("");
            edt_precoFabrica.setBorder(UIManager.getBorder("TextField.border"));
            edt_precoFabrica.setOpaque(true);
            edt_precoFabrica.setBackground(Color.white);
            edt_precoFabrica.setBounds(186, 160, 86, 20);
            pnl_tabelaDePreco.add(edt_precoFabrica);
            
            lbl_icms = new JLabel("ICMS");
            lbl_icms.setBounds(282, 146, 46, 14);
            pnl_tabelaDePreco.add(lbl_icms);
            
            edt_icms = new JLabel("");
            edt_icms.setBorder(UIManager.getBorder("TextField.border"));
            edt_icms.setOpaque(true);
            edt_icms.setBackground(Color.white);
            edt_icms.setBounds(282, 160, 86, 20);
            pnl_tabelaDePreco.add(edt_icms);
            
            lbl_baseRetido = new JLabel("Base Retido");
            lbl_baseRetido.setBounds(186, 191, 86, 14);
            pnl_tabelaDePreco.add(lbl_baseRetido);
            
            edt_baseRetido = new JLabel("");
            edt_baseRetido.setBorder(UIManager.getBorder("TextField.border"));
            edt_baseRetido.setOpaque(true);
            edt_baseRetido.setBackground(Color.white);
            edt_baseRetido.setBounds(186, 205, 86, 20);
            pnl_tabelaDePreco.add(edt_baseRetido);
            
            lbl_ipi_tbp = new JLabel("IPI");
            lbl_ipi_tbp.setBounds(90, 191, 46, 14);
            pnl_tabelaDePreco.add(lbl_ipi_tbp);
            
            edt_ipi_tbp = new JLabel("");
            edt_ipi_tbp.setBorder(UIManager.getBorder("TextField.border"));
            edt_ipi_tbp.setOpaque(true);
            edt_ipi_tbp.setBackground(Color.white);
            edt_ipi_tbp.setBounds(90, 205, 86, 20);
            pnl_tabelaDePreco.add(edt_ipi_tbp);
            
            lbl_retido = new JLabel("Retido");
            lbl_retido.setBounds(282, 191, 46, 14);
            pnl_tabelaDePreco.add(lbl_retido);
            
            edt_retido = new JLabel("");
            edt_retido.setBorder(UIManager.getBorder("TextField.border"));
            edt_retido.setOpaque(true);
            edt_retido.setBackground(Color.white);
            edt_retido.setBounds(282, 205, 86, 20);
            pnl_tabelaDePreco.add(edt_retido);
            
            btn_calcular = new JButton("Calcular");
            btn_calcular.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                	
                	try{
                		precoFinal = Float.parseFloat(edt_precoFinal.getText());
                		
                		double baseDeCalculo = precoFinal-ipi-pauta;
                		
                		while(!bateuMetaCom(baseDeCalculo)){
                			if(calcular(baseDeCalculo)<precoFinal)
                				baseDeCalculo = baseDeCalculo+0.00001;
                			else if(calcular(baseDeCalculo)>precoFinal)
                				baseDeCalculo = baseDeCalculo-0.000001;
                		}
                		
                		edt_precoFabrica.setText(String.valueOf( baseDeCalculo ));
                		edt_precoFabrica.setForeground(Color.RED);
                		lbl_precoFabrica.setForeground(Color.RED);
                		
                		precoFabrica = Float.parseFloat(edt_precoFabrica.getText());
                		float icms = precoFabrica * aliquota;
                		edt_icms.setText(String.valueOf( icms ));
                		float baseRetido = pauta*quantidade;
                		edt_baseRetido.setText(String.valueOf( baseRetido ));
                		float retido = (baseRetido*aliquota)-icms;
                		edt_retido.setText(String.valueOf( retido ));
                	} catch(Exception e){
                		JOptionPane.showMessageDialog(null, "Preencha o campo com uma referencia v�lida!");
                		resetFormulario();
                	}
                	
                	String preco = edt_precoFabrica.getText(); 
                	String inteiros = preco.substring( 0, preco.indexOf(".")+1 );
            	    String decimais = preco.substring( preco.indexOf(".")+1, preco.length() ); 
            	    decimais = decimais.substring(0,5);
            	    preco = inteiros+decimais;
            	    
            	    edt_precoFabrica.setText(preco);
                }
            });
            btn_calcular.setBounds(185, 288, 89, 23);
            pnl_tabelaDePreco.add(btn_calcular);
            
            cbx_estado = new JComboBox<String>();
            cbx_estado.addItemListener(new ItemListener() {
            	public void itemStateChanged(ItemEvent e) {
            		aliquotaPauta(pro);
            	}
            });
            cbx_estado.setBounds(130, 115, 46, 20);
            pnl_tabelaDePreco.add(cbx_estado);
            
            lbl_estado = new JLabel("Estado");
            lbl_estado.setBounds(90, 118, 46, 14);
            pnl_tabelaDePreco.add(lbl_estado);

            pnl_cadastroProduto= new JPanel();
            tbp_painel.addTab("Cadastro de Produto", null, pnl_cadastroProduto, null);
            pnl_cadastroProduto.setLayout(null);
            
            lbl_descricao = new JLabel("Descri\u00E7\u00E3o");
            lbl_descricao.setBounds(95, 11, 58, 14);
            pnl_cadastroProduto.add(lbl_descricao);
            
            edt_descricao = new JTextField();
            edt_descricao.setBounds(95, 25, 194, 20);
            pnl_cadastroProduto.add(edt_descricao);
            edt_descricao.setColumns(10);
            
            lbl_quantidade = new JLabel("Quantidade");
            lbl_quantidade.setBounds(299, 11, 70, 14);
            pnl_cadastroProduto.add(lbl_quantidade);
            
            edt_quantidade = new JTextField();
            edt_quantidade.setBounds(299, 25, 70, 20);
            pnl_cadastroProduto.add(edt_quantidade);
            edt_quantidade.setColumns(10);
            
            lbl_ipi = new JLabel("IPI");
            lbl_ipi.setBounds(379, 11, 46, 14);
            pnl_cadastroProduto.add(lbl_ipi);
            
            edt_ipi = new JTextField();
            edt_ipi.setColumns(10);
            edt_ipi.setBounds(379, 25, 70, 20);
            pnl_cadastroProduto.add(edt_ipi);
            
            scp_impostos = new JScrollPane();
            scp_impostos.setBounds(10, 71, 439, 200);
            pnl_cadastroProduto.add(scp_impostos);
            
            tbl_impostos = new JTable();
            tbl_impostos.setToolTipText("Aperte <<enter>> para inserir uma nova linha.");
            tbl_impostos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tbl_impostos.getTableHeader().setReorderingAllowed(false);
            model = new DefaultTableModel(
                	new String[][] {
                    		{null, null, null, null},
                    	},
                    	new String[] {
                    		"Estado", "Pauta", "Aliquota Estadual", "Aliquota Interestadual"
                    	});
            tbl_impostos.setModel( model );
            tbl_impostos.addKeyListener(new KeyAdapter(){  
                @Override  
                public void keyPressed(KeyEvent e) {                  
                	if(e.getKeyCode() == KeyEvent.VK_ENTER) { 
                		for (int i = 0; i < model.getRowCount(); i++) {
							if(tbl_impostos.getValueAt(i, 0).equals(""))
								break;
							else if(i == model.getRowCount()-1)
								model.addRow(new String[]{null,null,null,null});
						}
                    }  
                	if(e.getKeyCode() == KeyEvent.VK_DELETE) { 
                		excluirComponente();
                	}  
                }
            });
            scp_impostos.setViewportView(tbl_impostos);
            
            lbl_codigo = new JLabel("C\u00F3digo");
            lbl_codigo.setBounds(10, 11, 46, 14);
            pnl_cadastroProduto.add(lbl_codigo);
            
            btn_salvar = new JButton("Salvar");
            btn_salvar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                	
                	Produto p = produtoDaListaPorCodigo(Integer.parseInt(edt_codigo.getText()));
            		p.setDescricao(edt_descricao.getText());
            		p.setIpi(Float.parseFloat(edt_ipi.getText()));
            		p.setQuantidade(Float.parseFloat(edt_quantidade.getText()));
                	
                	for (int i = 0; i < listaDeProdutos.size(); i++) {
                		try {
                			if(listaDeProdutos.get(i).getCodigo() == Integer.parseInt(edt_codigo.getText())){
                				listaDeProdutos.get(i).setEstados(estadosTabela());
                				break;
                			}
						} catch (Exception e) {	}
					}
                	new Xml().criarArquivoXml(listaDeProdutos, "produtos.xml");
                	listaDeProdutos = new Xml().LerXml("produtos.xml");
                	
                	cbx_codigoProduto.removeAllItems();
                	cbx_codigoProduto.addItem("");
                    for (int i = 0; i < listaDeProdutos.size(); i++)
                    	cbx_codigoProduto.addItem(String.valueOf(listaDeProdutos.get(i).getCodigo()));
                }
            });
            btn_salvar.setBounds(10, 282, 89, 23);
            pnl_cadastroProduto.add(btn_salvar);
            
            btn_excluir = new JButton("Excluir");
            btn_excluir.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent arg0) {
            		excluirComponente();
            	}
            });
            btn_excluir.setBounds(360, 282, 89, 23);
            pnl_cadastroProduto.add(btn_excluir);
            
            lbl_valoresPorEstado = new JLabel("Valores por estado");
            lbl_valoresPorEstado.setHorizontalAlignment(SwingConstants.CENTER);
            lbl_valoresPorEstado.setBounds(10, 56, 439, 14);
            pnl_cadastroProduto.add(lbl_valoresPorEstado);
            
            edt_codigo = new JTextField();
            edt_codigo.addFocusListener(new FocusAdapter() {
            	@Override
            	public void focusLost(FocusEvent arg0) {
            		limpaTabela();
            		preencheTabela(produtoDaListaPorCodigo(Integer.parseInt(edt_codigo.getText())));
            	}
            });
            edt_codigo.setBounds(10, 25, 45, 20);
            pnl_cadastroProduto.add(edt_codigo);
            edt_codigo.setColumns(10);
            
            btn_Localizar = new JButton("...");
            btn_Localizar.setFocusable(false);
            btn_Localizar.setFocusTraversalKeysEnabled(false);
            btn_Localizar.setFocusPainted(false);
            btn_Localizar.setToolTipText("Localizar");
            btn_Localizar.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent arg0) {
            		LocalizarProduto localizar = new LocalizarProduto();
                    JDialog dialog = new JDialog(localizar, false);
                    
                    dialog.setModal(true);
                    dialog.setContentPane(localizar.getContentPane());
                    dialog.setBounds(localizar.getBounds());
                    dialog.setResizable(false);
                    dialog.setTitle("Localizar Produto");
                    dialog.setLocation(500, 200);
                    dialog.setVisible(true);
                    
                    try {
                    	Produto p = produtoDaListaPorCodigo(Integer.parseInt((String) localizar.tbl_localizarProduto.getValueAt(localizar.tbl_localizarProduto.getSelectedRow(), 0)));
                    	edt_codigo.setText(String.valueOf(p.getCodigo()));
                    	limpaTabela();
                    	preencheTabela(produtoDaListaPorCodigo(Integer.parseInt(edt_codigo.getText())));						
					} catch (Exception e) {
						limpaTabela();
						edt_codigo.setText("");
						edt_descricao.setText("");
						edt_quantidade.setText("");
						edt_ipi.setText("");
						model.addRow(new String[]{ null, null, null, null });
					}
            	}
            });
            btn_Localizar.setBounds(65, 25, 20, 20);
            pnl_cadastroProduto.add(btn_Localizar);

            pack();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

	private boolean bateuMetaCom(double baseDeCalculo){
    	double tentativa = calcular(baseDeCalculo);
        if(tentativa == precoFinal)
            return true;
        else
            return false;
    }
    
    private float calcular(double baseDeCalculo){
        return (float) (baseDeCalculo+ipi+(((pauta*quantidade)*aliquota)-(baseDeCalculo*aliquota)));
    }
    
    private Produto produtoDaListaPorCodigo(int codigo) {
    	for (int i = 0; i < listaDeProdutos.size(); i++) {
			if( listaDeProdutos.get(i).getCodigo() == codigo ) 
				return listaDeProdutos.get(i);
		}
    	return new Produto();
    }
    
    protected void resetFormulario() {
    	edt_baseRetido.setText("");
    	edt_ipi_tbp.setText("");
    	edt_icms.setText("");
    	edt_baseRetido.setText("");
    	edt_retido.setText("");
    	edt_produto.setText("");
    	edt_precoFabrica.setText("");
    	edt_precoFabrica.setForeground(Color.BLACK);
    	lbl_precoFabrica.setForeground(Color.BLACK);
    	edt_precoFinal.setText("");;
    	edt_precoFinal.requestFocus();
    	cbx_estado.removeAllItems();
    }
    
    private Estado estadoDalinha(int linha){
		String uf = (String) tbl_impostos.getValueAt(linha, 0);
		String pauta = (String) tbl_impostos.getValueAt(linha, 1);
		String aliquotaEstadual = (String) tbl_impostos.getValueAt(linha, 2);
		String aliquotaInterestadual = (String) tbl_impostos.getValueAt(linha, 3);
    	
    	Estado estado = new Estado();
    	estado.setUf(uf);
    	estado.setPauta(Float.parseFloat(pauta));
    	estado.setAliquotaEstadual(Float.parseFloat(aliquotaEstadual));
    	estado.setAliquotaInterestadual(Float.parseFloat(aliquotaInterestadual));
    	
    	return estado;
    }
    
    private void preencheCampos(Produto p){
    	if(p==null){
    		resetFormulario();
    	}else{
    		edt_produto.setText(p.getDescricao());
	    	edt_ipi_tbp.setText(String.valueOf(p.getIpi()));
	    	quantidade = p.getQuantidade();
	    	ipi = p.getIpi();
	    	cbx_estado.removeAllItems();
	    	for (int i = 0; i < p.getEstados().size(); i++)
	    		cbx_estado.addItem(p.getEstados().get(i).getUf());
	    	aliquotaPauta(p);
    	}
    }
    
    private void preencheTabela(Produto p){
    	if(p==null){
    		resetFormulario();
    	}else{
    		edt_descricao.setText(p.getDescricao());
    		edt_quantidade.setText(String.valueOf(p.getQuantidade()));
    		edt_ipi.setText(String.valueOf(p.getIpi()));
			
    		List<Estado> estados = p.getEstados();
    		
    		try {
    			for (int i = 0; i < estados.size(); i++) {
    				model.addRow(new String[]{
						estados.get(i).getUf(),
						String.valueOf(estados.get(i).getPauta()),
						String.valueOf(estados.get(i).getAliquotaEstadual()),
						String.valueOf(estados.get(i).getAliquotaInterestadual())
    				});
    			}
			} catch (Exception e) {
				p.setCodigo(Integer.parseInt(edt_codigo.getText()));
				listaDeProdutos.add(p);
				model.addRow(new String[]{null, null, null, null});
			}
    	}
    }
    
    private void limpaTabela(){
    	model.setNumRows(0);
    }
    
    private void aliquotaPauta(Produto p){
    	for (int i = 0; i < p.getEstados().size(); i++) {
			if( p.getEstados().get(i).getUf() == cbx_estado.getSelectedItem() ) {
				pauta = p.getEstados().get(i).getPauta();
				if(chc_vendaDentroEstado.isSelected())
					aliquota = p.getEstados().get(i).getAliquotaEstadual();
				else
					aliquota = p.getEstados().get(i).getAliquotaInterestadual();
			}
		}
    }
    
    private void removeEstadoLista(Produto p, Estado e){
    	for (int i = 0; i < listaDeProdutos.size(); i++)
			if(listaDeProdutos.get(i).getCodigo() == p.getCodigo()){
				for (int j = 0; j < p.getEstados().size(); j++)
					if(p.getEstados().get(j).getUf() == e.getUf()){
						p.getEstados().remove(j);
						break;
					}
			}
    }
    
    private void removeProdutoLista(Produto p){
    	for (int i = 0; i < listaDeProdutos.size(); i++)
			if(listaDeProdutos.get(i).getCodigo() == p.getCodigo()){
				listaDeProdutos.remove(i);
				break;
			}
    }
    
    private List<Estado> estadosTabela(){
    	List<Estado> e = new ArrayList<Estado>();
    	for (int i = 0; i < tbl_impostos.getRowCount(); i++) {
    		try {
    			e.add(estadoDalinha(i));
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
    	return e;
    }
    
    private void excluirComponente(){
    	if(tbl_impostos.getSelectedRow() == -1){
    		if(tbl_impostos.getRowCount()>0){
    			JOptionPane.showMessageDialog(null, "Selecione um registro para excluir!");
    		} else {
    			Object[] options = { "Sim", "N�o" };
    			int confirmacao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir o produto selecionado?",
    					"Excluir registro", JOptionPane.YES_NO_OPTION, 
    					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    			if(confirmacao==0){
    				Produto p = produtoDaListaPorCodigo(Integer.parseInt(edt_codigo.getText()));
    				removeProdutoLista(p);
    				edt_codigo.setText("");
    				edt_descricao.setText("");
    				edt_quantidade.setText("");
    				edt_ipi.setText("");
    				
    				new Xml().criarArquivoXml(listaDeProdutos, "produtos.xml");
                	listaDeProdutos = new Xml().LerXml("produtos.xml");
                	
                	cbx_codigoProduto.removeAllItems();
                	cbx_codigoProduto.addItem("");
                    for (int i = 0; i < listaDeProdutos.size(); i++)
                    	cbx_codigoProduto.addItem(String.valueOf(listaDeProdutos.get(i).getCodigo()));
    				
    			}
    		}
		} else {
			Object[] options = { "Sim", "N�o" };
			int confirmacao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir a linha selecionada?",
					"Excluir registro", JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if(confirmacao==0){
				Estado e = estadoDalinha(tbl_impostos.getSelectedRow());
				Produto p = produtoDaListaPorCodigo(Integer.parseInt(edt_codigo.getText()));
				removeEstadoLista(p, e);
				model.removeRow(tbl_impostos.getSelectedRow());
			}
		}
    }
}