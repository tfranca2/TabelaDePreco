package com.tabeladepreco;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tabeladepreco.entidades.Estado;
import com.tabeladepreco.entidades.Produto;

public class Xml {
	
	public List<Produto> LerXml(String xml){
		try{
			List<Produto> produtos = realizaLeituraXML(xml);
			return produtos;
		} catch (ParserConfigurationException e) {
			System.out.println("O parser não foi configurado corretamente.");
			e.printStackTrace();
		} catch (SAXException e) {
			System.out.println("Problema ao fazer o parse do arquivo.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("O arquivo não pode ser lido.");
			e.printStackTrace();
			
			Produto p = new Produto();
			p.setCodigo(0);
			p.setDescricao("   ");
			p.setIpi(0);
			p.setQuantidade(0);
			
			List<Estado> es = new ArrayList<>();
			Estado e1 = new Estado();
			e1.setUf("  ");
			e1.setPauta(0);
			e1.setAliquotaEstadual(0);
			e1.setAliquotaInterestadual(0);
			es.add(e1);
			
			p.setEstados(es);
			
			List<Produto> ps = new ArrayList<Produto>();
			ps.add(p);
			
			criarArquivoXml(ps, "produtos.xml");
		}
		return null;
	}
	
	private List<Produto> realizaLeituraXML(String arquivoXML) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(arquivoXML);
		
		Element raiz = doc.getDocumentElement();
		NodeList listaProdutos = raiz.getElementsByTagName("produto");

		List<Produto> lista = new ArrayList<Produto>(listaProdutos.getLength());
		for (int i=0; i<listaProdutos.getLength(); i++){
			Element elementoProduto = (Element) listaProdutos.item(i);
			Produto produto = criaProduto(elementoProduto);
			
			NodeList listaEstados= elementoProduto.getElementsByTagName("estado");
			List<Estado> listaEs = new ArrayList<Estado>(listaEstados.getLength());
			for (int j=0; j<listaEstados.getLength(); j++){
				Element elementoEstado = (Element) listaEstados.item(j);
				Estado estado = criaEstado(elementoEstado);
				
				listaEs.add(estado);
			}

			produto.setEstados(listaEs);
			lista.add(produto);
		}

		return lista;
	}

	private String obterValorElemento(Element elemento, String nomeElemento){
		NodeList listaElemento = elemento.getElementsByTagName(nomeElemento);
		if (listaElemento == null) return null;
		Element noElemento = (Element) listaElemento.item(0);
		if (noElemento == null) return null;
		Node no = noElemento.getFirstChild();
		return no.getNodeValue();
	}

	private Produto criaProduto(Element elemento){
		Produto produto = new Produto();
		produto.setCodigo(Integer.parseInt(obterValorElemento(elemento,"codigo")));
		produto.setDescricao(obterValorElemento(elemento,"descricao"));
		produto.setQuantidade(Float.parseFloat(obterValorElemento(elemento,"quantidade")));
		produto.setIpi(Float.parseFloat(obterValorElemento(elemento,"ipi")));

		return produto;
	}
	
	private Estado criaEstado(Element elemento){
		Estado  estado = new Estado();
		estado.setUf(obterValorElemento(elemento,"uf"));
		estado.setPauta(Float.parseFloat(obterValorElemento(elemento,"pauta")));
		estado.setAliquotaEstadual(Float.parseFloat(obterValorElemento(elemento,"aliquotaEstadual")));
		estado.setAliquotaInterestadual(Float.parseFloat(obterValorElemento(elemento,"aliquotaInterestadual")));
		
		return estado;
	}
	
    public void criarArquivoXml(List<Produto> listaDeProdutos, String arquivo){
    	try {
	    	
	    	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

	    	Document doc = docBuilder.newDocument();
	    	Element rootElement = doc.createElement("produtos");
	    	doc.appendChild(rootElement);

	    	for (int i = 0; i < listaDeProdutos.size(); i++) {
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

	    		codigo.appendChild(doc.createTextNode(String.valueOf(listaDeProdutos.get(i).getCodigo())));
	    		descricao.appendChild(doc.createTextNode(listaDeProdutos.get(i).getDescricao()));
	    		quantidade.appendChild(doc.createTextNode(String.valueOf(listaDeProdutos.get(i).getQuantidade())));
	    		ipi.appendChild(doc.createTextNode(String.valueOf(listaDeProdutos.get(i).getIpi())));
	    		
	    		for (int j = 0; j < listaDeProdutos.get(i).getEstados().size(); j++) {
	    			Element estado = doc.createElement("estado");
	    			
	    			Element uf = doc.createElement("uf");
	    			Element pauta = doc.createElement("pauta");
	    			Element aliquotaEstadual = doc.createElement("aliquotaEstadual");
	    			Element aliquotaInterestadual = doc.createElement("aliquotaInterestadual");
	    			
	    			estado.appendChild(uf);
	    			estado.appendChild(pauta);
	    			estado.appendChild(aliquotaEstadual);
	    			estado.appendChild(aliquotaInterestadual);

	    			uf.appendChild(doc.createTextNode(listaDeProdutos.get(i).getEstados().get(j).getUf()));
	    			pauta.appendChild(doc.createTextNode(String.valueOf(listaDeProdutos.get(i).getEstados().get(j).getPauta())));
	    			aliquotaEstadual.appendChild(doc.createTextNode(String.valueOf(listaDeProdutos.get(i).getEstados().get(j).getAliquotaEstadual())));
	    			aliquotaInterestadual.appendChild(doc.createTextNode(String.valueOf(listaDeProdutos.get(i).getEstados().get(j).getAliquotaInterestadual())));
	    			
	    			produto.appendChild(estado);
				}
			}
	    	
	    	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    	Transformer transformer = transformerFactory.newTransformer();
	    	DOMSource source = new DOMSource(doc);
	    	
	    	StringWriter stringWriter = new StringWriter();
	    	StreamResult result = new StreamResult(stringWriter);

	    	transformer.transform(source, result);

	    	FileOutputStream saida;  
	    	PrintStream fileSaida;
	    	
	    	try {  
	    		saida = new FileOutputStream(arquivo);  
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
