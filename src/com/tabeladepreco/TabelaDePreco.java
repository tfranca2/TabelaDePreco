package com.tabeladepreco;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.Color;

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
    private JButton btn_incluir;
    private JComboBox<String> cbx_codigoProduto;
    private JLabel lbl_codigoProduto;
    private JComboBox<String> cbx_estadoOrigem;
    private JComboBox<String> cbx_estadoDestino;
    private JTextField edt_produto;
    private JTextField edt_precoFabrica;
    private JTextField edt_icms;
    private JTextField edt_baseRetido;
    private JTextField edt_ipi_tbp;
    private JTextField edt_retido;
    private JLabel lbl_origem;
    private JLabel lbl_destino;
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

    public static void main(String[] args) {
        TabelaDePreco janela = new TabelaDePreco();
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }

    public TabelaDePreco() {
        super();
        desenhaJanela();
        
        					//			600ML			LATA			LONG NECK
		aliquota = 0.27f;//														
		pauta =  2.47f;//				2.47f 			1.78f			1.81f
		quantidade = 24;//				24				12				24
		ipi = 4.59504f;//				4.59504f		1.24026f		2.42650f
		precoFinal = 0;//			60.00f			14.50f			34.00f
	//  precoFabrica					53.97173f		10.26375f		27.18452f
		        
		
		
        criarArquivoXml();
	     
        //System.exit(0);
    }

	private void desenhaJanela() {
        try {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            getContentPane().setLayout(null);
            setSize(500,450);
            setPreferredSize(getSize()); 
            setTitle("Montagem de Tabela de Pre\u00E7o");

            tbp_painel= new JTabbedPane();
            getContentPane().add(tbp_painel);
            tbp_painel.setBounds(10, 11, 464, 390);

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
            lbl_precoFinal.setBounds(10, 45, 69, 14);
            pnl_tabelaDePreco.add(lbl_precoFinal);
            
            edt_precoFinal = new JTextField();
            edt_precoFinal.setBounds(10, 59, 106, 20);
            pnl_tabelaDePreco.add(edt_precoFinal);
            edt_precoFinal.setColumns(10);
            
            cbx_codigoProduto = new JComboBox<String>();
            cbx_codigoProduto.setBounds(139, 59, 106, 20);
            pnl_tabelaDePreco.add(cbx_codigoProduto);
            
            lbl_codigoProduto = new JLabel("C\u00F3digo do produto");
            lbl_codigoProduto.setBounds(139, 45, 106, 14);
            pnl_tabelaDePreco.add(lbl_codigoProduto);
            
            chc_vendaDentroEstado = new JCheckBox("Venda para dentro do estado");
            chc_vendaDentroEstado.setSelected(true);
            chc_vendaDentroEstado.addItemListener(new ItemListener() {
            	public void itemStateChanged(ItemEvent arg0) {
            		if(chc_vendaDentroEstado.isSelected()){
            			cbx_estadoDestino.setEnabled(false);
            		} else {
            			cbx_estadoDestino.setEnabled(true);
            		} 
            	}
            });
            chc_vendaDentroEstado.setBounds(269, 58, 180, 23);
            pnl_tabelaDePreco.add(chc_vendaDentroEstado);
            
            lbl_origem = new JLabel("Origem");
            lbl_origem.setBounds(173, 90, 46, 14);
            pnl_tabelaDePreco.add(lbl_origem);
            
            lbl_destino = new JLabel("Destino");
            lbl_destino.setBounds(238, 90, 46, 14);
            pnl_tabelaDePreco.add(lbl_destino);
            
            cbx_estadoOrigem = new JComboBox<String>();
            cbx_estadoOrigem.setBounds(173, 104, 55, 20);
            pnl_tabelaDePreco.add(cbx_estadoOrigem);
            
            cbx_estadoDestino = new JComboBox<String>();
            cbx_estadoDestino.setEnabled(false);
            cbx_estadoDestino.setBounds(238, 104, 55, 20);
            pnl_tabelaDePreco.add(cbx_estadoDestino);
            
            lbl_produto = new JLabel("Produto");
            lbl_produto.setBounds(90, 146, 69, 14);
            pnl_tabelaDePreco.add(lbl_produto);
            
            edt_produto = new JTextField();
            edt_produto.setBounds(90, 160, 86, 20);
            pnl_tabelaDePreco.add(edt_produto);
            edt_produto.setColumns(10);
            
            lbl_precoFabrica = new JLabel("Pre\u00E7o F\u00E1brica");
            lbl_precoFabrica.setBounds(186, 146, 80, 14);
            pnl_tabelaDePreco.add(lbl_precoFabrica);
            
            edt_precoFabrica = new JTextField();
            edt_precoFabrica.setForeground(Color.RED);
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
                	
                	precoFinal = Float.parseFloat(edt_precoFinal.getText());
                	
                	float baseDeCalculo = precoFinal-ipi-pauta;
                    while(!bateuMetaCom(baseDeCalculo)){
                        if(calcular(baseDeCalculo)<precoFinal)
                            baseDeCalculo = baseDeCalculo+0.0001f;
                        else if(calcular(baseDeCalculo)>precoFinal)
                            baseDeCalculo = baseDeCalculo-0.00001f;
                    }
                    edt_precoFabrica.setText(String.valueOf( baseDeCalculo ));
                    lbl_precoFabrica.setForeground(Color.RED);
                    
                    edt_ipi_tbp.setText(String.valueOf(ipi));
            		precoFabrica = Float.parseFloat(edt_precoFabrica.getText());
            		float icms = precoFabrica * aliquota;
            		edt_icms.setText(String.valueOf( icms ));
            		float baseRetido = pauta*quantidade;
            		edt_baseRetido.setText(String.valueOf( baseRetido ));
            		float retido = (baseRetido*aliquota)-icms;
            		edt_retido.setText(String.valueOf( retido ));
                }
            });
            btn_calcular.setBounds(186, 288, 89, 23);
            pnl_tabelaDePreco.add(btn_calcular);

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
            
            lbl_quantidade = new JLabel("Quant.");
            lbl_quantidade.setBounds(299, 11, 46, 14);
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
            scp_impostos.setBounds(10, 71, 439, 230);
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
            scp_impostos.setViewportView(tbl_impostos);
            
            lbl_codigo = new JLabel("C\u00F3digo");
            lbl_codigo.setBounds(10, 11, 46, 14);
            pnl_cadastroProduto.add(lbl_codigo);
            
            btn_incluir = new JButton("Incluir");
            btn_incluir.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    
                }
            });
            btn_incluir.setBounds(10, 328, 89, 23);
            pnl_cadastroProduto.add(btn_incluir);
            
            btn_excluir = new JButton("Excluir");
            btn_excluir.setBounds(360, 328, 89, 23);
            pnl_cadastroProduto.add(btn_excluir);
            
            lbl_valoresPorEstado = new JLabel("Valores por estado");
            lbl_valoresPorEstado.setHorizontalAlignment(SwingConstants.CENTER);
            lbl_valoresPorEstado.setBounds(10, 56, 439, 14);
            pnl_cadastroProduto.add(lbl_valoresPorEstado);
            
            edt_codigo = new JTextField();
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
            btn_excluir.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    
                }
            });

            pack();
        } catch(Exception e) {
            e.printStackTrace();
        }
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
    
    private void criarArquivoXml(){
    	try {
	    	
	    	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	    	
	    	//root elements
	    	Document doc = docBuilder.newDocument();
	    	Element rootElement = doc.createElement("produtos");
	    	doc.appendChild(rootElement);
	    	
	    	//tag principal
	    	Element produto = doc.createElement("produto");
	    	rootElement.appendChild(produto);
	    	
	    	Element codigo = doc.createElement("codigo");
	    	Element descricao = doc.createElement("descricao");
	    	Element quantidade = doc.createElement("quantidade");
	    	Element ipi = doc.createElement("ipi");
	    	
	    	produto.appendChild(codigo);
	    	produto.appendChild(descricao);
	    	produto.appendChild(quantidade);
	    	produto.appendChild(ipi);
	    	
	    	codigo.appendChild(doc.createTextNode("100"));
	    	descricao.appendChild(doc.createTextNode("CERVEJA PROIBIDA 600ML"));
	    	quantidade.appendChild(doc.createTextNode("24"));
	    	ipi.appendChild(doc.createTextNode("4.59504"));
	    	
	    	Element estado = doc.createElement("estado");
	    	
	    	Element uf = doc.createElement("uf");
	    	Element pauta = doc.createElement("pauta");
	    	Element aliquotaEstadual = doc.createElement("aliquotaEstadual");
	    	Element aliquotaInterestadual = doc.createElement("aliquotaInterestadual");
	    	
	    	estado.appendChild(uf);
	    	estado.appendChild(pauta);
	    	estado.appendChild(aliquotaEstadual);
	    	estado.appendChild(aliquotaInterestadual);
	    	
	    	uf.appendChild(doc.createTextNode("CE"));
	    	pauta.appendChild(doc.createTextNode("2.47"));
	    	aliquotaEstadual.appendChild(doc.createTextNode("0.27"));
	    	aliquotaInterestadual.appendChild(doc.createTextNode("0.27"));
	    	
	    	produto.appendChild(estado);
	    	
	    	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    	Transformer transformer = transformerFactory.newTransformer();
	    	DOMSource source = new DOMSource(doc);
	    	
	    	StringWriter stringWriter = new StringWriter();
	    	StreamResult result = new StreamResult(stringWriter);

	    	transformer.transform(source, result);
	    	
	    	//escrevendo dados no arquivo xml
	    	FileOutputStream saida;  
	    	PrintStream fileSaida;
	    	
	    	try {  
	    		saida = new FileOutputStream(".\\arquivo.xml");  
	    		fileSaida = new PrintStream(saida);  
	    		fileSaida.print(stringWriter.toString());  
	    	}catch (Exception e) {  
	    		System.err.println(e);  
	    	}  
	    	
	    } catch (ParserConfigurationException pce) {
	    	pce.printStackTrace();
	    } catch (TransformerConfigurationException ex) {
	    	Logger.getLogger(TabelaDePreco.class.getName()).log(Level.SEVERE, null, ex);
	    } catch (TransformerException ex) {
	    	Logger.getLogger(TabelaDePreco.class.getName()).log(Level.SEVERE, null, ex);
	    } 
    }
    
}