package com.tabeladepreco.entidades;


public class Produto{
    
    private int codigo;
    private String descricao;
    private float quantidade;
    private float ipi;
    private Estado estados;
    
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
	public Estado getEstados() {
		return estados;
	}
	public void setEstados(Estado estados) {
		this.estados = estados;
	}
}