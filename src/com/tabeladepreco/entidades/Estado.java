package com.tabeladepreco.entidades;

public class Estado{
    private String nome;
    private Produto produto;
    private float aliquotaInterna;
    private float aliquotaExterna;
    
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome.toUpperCase();
    }
    public float getAliquotaInterna() {
        return aliquotaInterna;
    }
    public void setAliquotaInterna(float aliquotaInterna) {
        this.aliquotaInterna = aliquotaInterna;
    }
    public float getAliquotaExterna() {
        return aliquotaExterna;
    }
    public void setAliquotaExterna(float aliquotaExterna) {
        this.aliquotaExterna = aliquotaExterna;
    }
    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    @Override
    public String toString() {
        return "Estado [nome=" + nome + ", produto="
                + produto + ", aliquotaInterna=" + aliquotaInterna
                + ", aliquotaExterna=" + aliquotaExterna + "]";
    }
}