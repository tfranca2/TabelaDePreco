package com.tabeladepreco.entidades;

public class Estado{
    private String uf;
    private float pauta;
    private float aliquotaEstadual;
    private float aliquotaInterestadual;
    
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public float getPauta() {
		return pauta;
	}
	public void setPauta(float pauta) {
		this.pauta = pauta;
	}
	public float getAliquotaEstadual() {
		return aliquotaEstadual;
	}
	public void setAliquotaEstadual(float aliquotaEstadual) {
		this.aliquotaEstadual = aliquotaEstadual;
	}
	public float getAliquotaInterestadual() {
		return aliquotaInterestadual;
	}
	public void setAliquotaInterestadual(float aliquotaInterestadual) {
		this.aliquotaInterestadual = aliquotaInterestadual;
	}
	
	@Override
	public String toString() {
		return "Estado [uf=" + uf + ", pauta=" + pauta
				+ ", aliquotaEstadual=" + aliquotaEstadual
				+ ", aliquotaInterestadual=" + aliquotaInterestadual + "]";
	}
}
