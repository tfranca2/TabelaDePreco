package com.tabeladepreco.entidades;

public class Produto{
    
    private int codigo;
    private String descricao;
    private float quantidade;
    private float ipi;
    private float pauta;
    
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
        this.descricao = descricao.toUpperCase();
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
    public float getPauta() {
        return pauta;
    }
    public void setPauta(float pauta) {
        this.pauta = pauta;
    }
    @Override
    public String toString() {
        return "Produto [codigo=" + codigo + ", descricao=" + descricao
                + ", quantidade=" + quantidade + ", ipi=" + ipi + ", pauta="
                + pauta + "]";
    }
}