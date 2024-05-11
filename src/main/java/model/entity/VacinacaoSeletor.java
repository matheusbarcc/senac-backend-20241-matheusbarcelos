package model.entity;

import java.time.LocalDate;

public class VacinacaoSeletor extends BaseSeletor{

	private String nomePessoa;
	private String nomeVacina;
	private LocalDate dataAplicacaoInicio;
	private LocalDate dataAplicacaoFinal;
	private int avaliacao;
	
	public VacinacaoSeletor() {
		super();
	}

	public VacinacaoSeletor(String nomePessoa, String nomeVacina, LocalDate dataAplicacaoInicio,
			LocalDate dataAplicacaoFinal, int avaliacao) {
		super();
		this.nomePessoa = nomePessoa;
		this.nomeVacina = nomeVacina;
		this.dataAplicacaoInicio = dataAplicacaoInicio;
		this.dataAplicacaoFinal = dataAplicacaoFinal;
		this.avaliacao = avaliacao;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public String getNomeVacina() {
		return nomeVacina;
	}

	public void setNomeVacina(String nomeVacina) {
		this.nomeVacina = nomeVacina;
	}

	public LocalDate getDataAplicacaoInicio() {
		return dataAplicacaoInicio;
	}

	public void setDataAplicacaoInicio(LocalDate dataAplicacaoInicio) {
		this.dataAplicacaoInicio = dataAplicacaoInicio;
	}

	public LocalDate getDataAplicacaoFinal() {
		return dataAplicacaoFinal;
	}

	public void setDataAplicacaoFinal(LocalDate dataAplicacaoFinal) {
		this.dataAplicacaoFinal = dataAplicacaoFinal;
	}

	public int getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(int avaliacao) {
		this.avaliacao = avaliacao;
	}
	
	public boolean temFiltro() {
		return (this.nomePessoa != null && this.nomePessoa.trim().length() > 0)
				|| (this.nomeVacina != null && this.nomeVacina.trim().length() > 0)
				|| (this.dataAplicacaoInicio != null)
				|| (this.dataAplicacaoFinal != null)
				|| (this.avaliacao > 0);
	}
}
