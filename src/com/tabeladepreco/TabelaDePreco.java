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
    private JTextField edt_produto;
    private JTextField edt_precoFabrica;
    private JTextField edt_icms;
    private JTextField edt_baseRetido;
    private JTextField edt_ipi_tbp;
    private JTextField edt_retido;
    private JLabel lbl_produto;
    private JLabel lbl_precoFabrica;
    private JLabel lbl_icms;
    private JLabel lbl_ipi_tbp;
    private JLabel lbl_baseRetido;
    private JLabel lbl_retido;
    private JLabel lbl_valoresPorEstado;
    private JTextField edt_codigo;
    private JButton btn_Localizar;
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
        
        
//										600ML			LATA			LONG NECK
//		aliquota = 0.27f;														
//		pauta =  2.47f;//				2.47f 			1.78f			1.81f
//		quantidade = 24;				24				12				24
//		ipi = 4.59504f;					4.59504f		1.24026f		2.42650f
//		precoFinal = 0;					60.00f			14.50f			34.00f
// 		precoFabrica					53.97173f		10.26375f		27.18452f
		
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
            getContentPane().add(tbp_painel);
            tbp_painel.setBounds(10, 11, 464, 350);

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
            		if( cbx_codigoProduto.getSelectedIndex() != 0 )
                		pro = produtoDaListaPorCodigo(Integer.parseInt((String) cbx_codigoProduto.getSelectedItem()));
            		else
            			pro = null;
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
            
            edt_produto = new JTextField();
            edt_produto.setBounds(126, 63, 323, 20);
            pnl_tabelaDePreco.add(edt_produto);
            edt_produto.setColumns(10);
            
            lbl_precoFabrica = new JLabel("Pre\u00E7o F\u00E1brica");
            lbl_precoFabrica.setBounds(186, 146, 80, 14);
            pnl_tabelaDePreco.add(lbl_precoFabrica);
            
            edt_precoFabrica = new JTextField();
            edt_precoFabrica.setBounds(186, 160, 86, 20);
            pnl_tabelaDePreco.add(edt_precoFabrica);
            edt_precoFabrica.setColumns(10);
            
            lbl_icms = new JLabel("ICMS");
            lbl_icms.setBounds(282, 146, 46, 14);
            pnl_tabelaDePreco.add(lbl_icms);
            
            edt_icms = new JTextField();
            edt_icms.setBounds(282, 160, 86, 20);
            pnl_tabelaDePreco.add(edt_icms);
            edt_icms.setColumns(10);
            
            lbl_baseRetido = new JLabel("Base Retido");
            lbl_baseRetido.setBounds(186, 191, 86, 14);
            pnl_tabelaDePreco.add(lbl_baseRetido);
            
            edt_baseRetido = new JTextField();
            edt_baseRetido.setBounds(186, 205, 86, 20);
            pnl_tabelaDePreco.add(edt_baseRetido);
            edt_baseRetido.setColumns(10);
            
            lbl_ipi_tbp = new JLabel("IPI");
            lbl_ipi_tbp.setBounds(90, 191, 46, 14);
            pnl_tabelaDePreco.add(lbl_ipi_tbp);
            
            edt_ipi_tbp = new JTextField();
            edt_ipi_tbp.setBounds(90, 205, 86, 20);
            pnl_tabelaDePreco.add(edt_ipi_tbp);
            edt_ipi_tbp.setColumns(10);
            
            lbl_retido = new JLabel("Retido");
            lbl_retido.setBounds(282, 191, 46, 14);
            pnl_tabelaDePreco.add(lbl_retido);
            
            edt_retido = new JTextField();
            edt_retido.setBounds(282, 205, 86, 20);
            pnl_tabelaDePreco.add(edt_retido);
            edt_retido.setColumns(10);
            
            btn_calcular = new JButton("Calcular");
            btn_calcular.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                	try{
                		precoFinal = Float.parseFloat(edt_precoFinal.getText());
                		
                		float baseDeCalculo = precoFinal-ipi-pauta;
                		while(!bateuMetaCom(baseDeCalculo)){
                			if(calcular(baseDeCalculo)<precoFinal)
                				baseDeCalculo = baseDeCalculo+0.0001f;
                			else if(calcular(baseDeCalculo)>precoFinal)
                				baseDeCalculo = baseDeCalculo-0.00001f;
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
                		JOptionPane.showMessageDialog(null, "Preencha o campo com uma referencia válida!");
                		resetFormulario();
                	}
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
            lbl_descricao.setBounds(85, 11, 58, 14);
            pnl_cadastroProduto.add(lbl_descricao);
            
            edt_descricao = new JTextField();
            edt_descricao.setBounds(85, 25, 204, 20);
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
            model = new DefaultTableModel(
                	new Object[][] {
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
                		model.addRow(new Object[]{null,null,null,null});  
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
                	new Xml().criarArquivoXml(listaDeProdutos, "arquivo.xml");
                }
            });
            btn_salvar.setBounds(10, 282, 89, 23);
            pnl_cadastroProduto.add(btn_salvar);
            
            btn_excluir = new JButton("Excluir");
            btn_excluir.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent arg0) {
            		
            		if(tbl_impostos.getSelectedRow() == -1){
            			JOptionPane.showMessageDialog(null, "Selecione um registro para excluir!");
            		}else{
            			Object[] options = { "Sim", "Não" };
            			int confirmacao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir a linha selecionada?",
            					"Excluir registro", JOptionPane.YES_NO_OPTION, 
            					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            			if(confirmacao==0){
            				model.removeRow(tbl_impostos.getSelectedRow());
            			}
            		}
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
            btn_Localizar.setToolTipText("Localizar");
            btn_Localizar.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent arg0) {
            		LocalizarProduto localizar = new LocalizarProduto();
                    JDialog dialog = new JDialog(localizar, false);
                    
                    dialog.setModal(true);// TRAVA O FOCO NA JANELA
                    dialog.setContentPane(localizar.getContentPane());// INSERE O PAINEL PRONTO
                    dialog.setBounds(localizar.getBounds());// INSERE AS CONFIGURACOES
                    dialog.setResizable(false);
                    dialog.setTitle("Localizar Produto");
                    dialog.setLocation(500, 200);
                    dialog.setVisible(true);
            	}
            });
            btn_Localizar.setBounds(60, 25, 20, 20);
            pnl_cadastroProduto.add(btn_Localizar);

            pack();
        } catch(Exception e) {
            e.printStackTrace();
        }
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

	private boolean bateuMetaCom(float baseDeCalculo){
    	float tentativa = calcular(baseDeCalculo);
        if(tentativa == precoFinal)
            return true;
        else
            return false;
    }
    
    private float calcular(float baseDeCalculo){
        return baseDeCalculo+ipi+(((pauta*quantidade)*aliquota)-(baseDeCalculo*aliquota));
    }
    
    private Produto produtoDaListaPorCodigo(int codigo) {
    	for (int i = 0; i < listaDeProdutos.size(); i++) {
			if( listaDeProdutos.get(i).getCodigo() == codigo ) 
				return listaDeProdutos.get(i);
		}
    	return null;
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
    		
    		for (int i = 0; i < estados.size(); i++) {
    			model.addRow(new Object[]{
    					estados.get(i).getUf(),
    					estados.get(i).getPauta(),
    					estados.get(i).getAliquotaEstadual(),
    					estados.get(i).getAliquotaInterestadual()
    			});
			}
    	}
    }
    
    private void limpaTabela(){
    	for (int i = 0; i < model.getRowCount(); i++) 
    		model.removeRow(i);
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
}