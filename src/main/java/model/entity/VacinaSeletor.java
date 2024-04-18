package model.entity;

import java.time.LocalDate;

public class VacinaSeletor {
	
	private String nome;
	private Pais paisOrigem;
	private Pessoa pesquisadorResponsavel;
	private int estagio;
	private LocalDate dataInicioPesquisa;
	private LocalDate dataFinalPesquisa;
	
	
	public VacinaSeletor() {
		super();
	}

	public VacinaSeletor(String nome, Pais paisOrigem, Pessoa pesquisadorResponsavel, int estagio,
			LocalDate dataInicioPesquisa, LocalDate dataFinalPesquisa) {
		super();
		this.nome = nome;
		this.paisOrigem = paisOrigem;
		this.pesquisadorResponsavel = pesquisadorResponsavel;
		this.estagio = estagio;
		this.dataInicioPesquisa = dataInicioPesquisa;
		this.dataFinalPesquisa = dataFinalPesquisa;
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

	public int getEstagio() {
		return estagio;
	}

	public void setEstagio(int estagio) {
		this.estagio = estagio;
	}

	public LocalDate getDataInicioPesquisa() {
		return dataInicioPesquisa;
	}

	public void setDataInicioPesquisa(LocalDate dataInicioPesquisa) {
		this.dataInicioPesquisa = dataInicioPesquisa;
	}

	public LocalDate getDataFinalPesquisa() {
		return dataFinalPesquisa;
	}

	public void setDataFinalPesquisa(LocalDate dataFinalPesquisa) {
		this.dataFinalPesquisa = dataFinalPesquisa;
	}

	// Verifica se este seletor tem algum campo preenchido
	// @return true caso ao menos um dos atributos tenho sido preenchido
	public boolean temFiltro() {
		return (this.nome != null && this.nome.trim().length() > 0)
				|| (this.paisOrigem != null && this.paisOrigem.getNome() != null && this.paisOrigem.getNome().trim().length() > 0)
				|| (this.pesquisadorResponsavel != null && this.pesquisadorResponsavel.getNome() != null && this.pesquisadorResponsavel.getNome().trim().length() > 0)
				|| (this.estagio > 0)
				|| (this.dataInicioPesquisa != null)
				|| (this.dataFinalPesquisa != null);
	}
}
