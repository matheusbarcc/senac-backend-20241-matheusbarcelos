package model.entity;

import java.time.LocalDate;

public class VacinaSeletor {
	
	private String nome;
	private String nomePais;
	private String nomePesquisador;
	private int estagio;
	private LocalDate dataInicioPesquisa;
	private LocalDate dataFinalPesquisa;
	
	
	public VacinaSeletor() {
		super();
	}
	
	public VacinaSeletor(String nome, String nomePais, String nomePesquisador, int estagio,
			LocalDate dataInicioPesquisa, LocalDate dataFinalPesquisa) {
		super();
		this.nome = nome;
		this.nomePais = nomePais;
		this.nomePesquisador = nomePesquisador;
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

	public String getNomePais() {
		return nomePais;
	}

	public void setNomePais(String nomePais) {
		this.nomePais = nomePais;
	}

	public String getNomePesquisador() {
		return nomePesquisador;
	}

	public void setNomePesquisador(String nomePesquisador) {
		this.nomePesquisador = nomePesquisador;
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
				|| (this.nomePais != null && this.nomePais.trim().length() > 0)
				|| (this.nomePesquisador != null && this.nomePesquisador.trim().length() > 0)
				|| (this.estagio > 0)
				|| (this.dataInicioPesquisa != null)
				|| (this.dataFinalPesquisa != null);
	}
}
