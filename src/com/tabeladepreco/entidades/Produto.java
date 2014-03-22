package com.tabeladepreco.entidades;

import java.util.List;


public class Produto{
    
    private int codigo;
    private String descricao;
    private float quantidade;
    private float ipi;
    private List<Estado> estados;
    
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public float getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(float quantidade) {
		this.quantidade = quantidade;
	}
	public float getIpi() {
		return ipi;
	}
	public void setIpi(float ipi) {
		this.ipi = ipi;
	}
	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	public List<Estado> getEstados(){
		return estados;
	}
	
	@Override
	public String toString() {
		String s = "Produto [codigo=" + codigo + ", descricao=" + descricao
				+ ", quantidade=" + quantidade + ", ipi=" + ipi + ",";
		for (int i = 0; i < estados.size(); i++) 
			s = s+" "+estados.get(i).toString()+" |";
		s = s.substring(0, s.length()-1)+"]";
		
		return s;
	}
	
}