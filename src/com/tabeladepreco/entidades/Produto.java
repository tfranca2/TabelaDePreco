package com.tabeladepreco.entidades;

public class Produto{
    
    private int codigo;
    private String descricao;
    private float quantidade;
    private float ipi;
    private Estado estado;
    
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
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		return "Produto [codigo=" + codigo + ", descricao=" + descricao
				+ ", quantidade=" + quantidade + ", ipi=" + ipi + ", estado="
				+ estado + "]";
	}
}