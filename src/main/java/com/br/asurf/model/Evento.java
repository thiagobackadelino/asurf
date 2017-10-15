package com.br.asurf.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "eventos")
public class Evento   implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private String start;
	private String end;
	private String url;
	private String horario;
	
	@ManyToOne
	private Praia praia;
	
	@ManyToOne
	private Modalidade modalidade;
	
	@ManyToMany(mappedBy="eventos", targetEntity=Usuario.class, fetch=FetchType.LAZY)
	 private List<Usuario> usuarios;
	
	
	public Evento(){
		
	}	
	
	public Evento(String title, String start, String end, String url) {
		super();
		this.title = title;
		this.start = start;
		this.end = end;
		this.url = url;
	}

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public Praia getPraia() {
		return praia;
	}

	public void setPraia(Praia praia) {
		this.praia = praia;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}
	
	
	
	
}