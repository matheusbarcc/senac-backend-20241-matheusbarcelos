package model.entity;

import java.time.LocalDate;

public class Vacina {
	private int id;
	private String nome; 
	private Pais paisOrigem;
	private Pessoa pesquisadorResponsavel;
	private LocalDate dataInicioPesquisa;
	private int estagio;
	private double media;
	
	public Vacina() {
		
	}
	
	public Vacina(int id, String nome, Pais paisOrigem, Pessoa pesquisadorResponsavel, LocalDate dataInicioPesquisa,
			int estagio, double media) {
		super();
		this.id = id;
		this.nome = nome;
		this.paisOrigem = paisOrigem;
		this.pesquisadorResponsavel = pesquisadorResponsavel;
		this.dataInicioPesquisa = dataInicioPesquisa;
		this.estagio = estagio;
		this.media = media;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Pais getPaisOrigem() {
		return paisOrigem;
	}

	public void setPaisOrigem(Pais paisOrigem) {
		this.paisOrigem = paisOrigem;
	}

	public Pessoa getPesquisadorResponsavel() {
		return pesquisadorResponsavel;
	}

	public void setPesquisadorResponsavel(Pessoa pesquisadorResponsavel) {
		this.pesquisadorResponsavel = pesquisadorResponsavel;
	}

	public LocalDate getDataInicioPesquisa() {
		return dataInicioPesquisa;
	}

	public void setDataInicioPesquisa(LocalDate dataInicioPesquisa) {
		this.dataInicioPesquisa = dataInicioPesquisa;
	}

	public int getEstagio() {
		return estagio;
	}

	public void setEstagio(int estagio) {
		this.estagio = estagio;
	}

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}
}
