package com.br.asurf.model;

public enum Dificuldade {

	INICIANTE("Iniciante"),
	INTERMEDIARIO("Intermediario"),
	AVANCADO("Avancado");
	
	private String descricao;
	
	Dificuldade(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
}