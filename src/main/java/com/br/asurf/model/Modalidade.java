package com.br.asurf.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "modalidades")
public class Modalidade implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@NotBlank(message = "Descricao é obrigatório")
	private String descricao;
	
	private boolean ativo;

	@ManyToMany(mappedBy="modalidades", targetEntity=Praia.class, fetch=FetchType.LAZY)
	private List<Praia> praias;
	
	@OneToMany(mappedBy="modalidade", targetEntity=Evento.class, fetch=FetchType.LAZY)
	private List<Evento> eventos;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Praia> getPraias() {
		return praias;
	}

	public void setPraias(List<Praia> praias) {
		this.praias = praias;
	}

	
	
	
}
