package com.tabeladepreco;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

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
    private JScrollPane scp_impostos;
    private JLabel lbl_codigo;
    private JButton btn_excluir;
    private JButton btn_incluir;
    private JComboBox<String> cbx_codigoProduto;
    private JLabel lbl_codigoProduto;
    private JCheckBox chc_vendaDentroEstado;
    private JComboBox<String> cbx_estadoOrigem;
    private JComboBox<String> cbx_estadoDestino;
    private JTextField edt_produto;
    private JTextField edt_precoFabrica;
    private JTextField edt_icms;
    private JTextField edt_baseRetido;
    private JTextField edt_ipi_tbp;
    private JTextField edt_retido;
    private JButton btnCalcular;
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
                                   //			600ML			LATA			LONG NECK
    private float aliquota = 0.27f;//														
    private float pauta =  2.47f;//				2.47f 			1.78f			1.81f
    private float quantidade = 24;//			24				12				24
    private float ipi = 4.59504f;//				4.59504f		1.24026f		2.42650f
    private float precoFinal = 60;//			60				20				40
    private float precoFabrica = 0;//			53.97173f		17.79800f		35.40370f

    public static void main(String[] args) {
        TabelaDePreco janela = new TabelaDePreco();
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }

    public TabelaDePreco() {
//        super();
//        desenhaJanela();
        
        float baseDeCalculo = precoFinal-ipi-pauta;
        while(!bateuMetaCom(baseDeCalculo)){
            if(calcular(baseDeCalculo)<precoFinal)
                baseDeCalculo = baseDeCalculo+0.0001f;
            else if(calcular(baseDeCalculo)>precoFinal)
                baseDeCalculo = baseDeCalculo-0.00001f;
        }
        precoFabrica = baseDeCalculo;
        System.out.println("Preço fábrica: R$ "+precoFabrica);
        
        System.exit(0);
    }

    private void desenhaJanela() {
        try {
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
            
            btnCalcular = new JButton("Calcular");
            btnCalcular.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    
                }
            });
            btnCalcular.setBounds(186, 288, 89, 23);
            pnl_tabelaDePreco.add(btnCalcular);

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
            tbl_impostos.setModel(new DefaultTableModel(
                new Object[][] {
                    {null, null, null},
                },
                new String[] {
                    "Estado", "Aliquota", "Pauta"
                }
            ));
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
            edt_codigo.setBounds(10, 25, 65, 20);
            pnl_cadastroProduto.add(edt_codigo);
            edt_codigo.setColumns(10);
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
}