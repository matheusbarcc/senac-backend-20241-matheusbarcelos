package model.entity;

import java.time.LocalDate;

public class Vacinacao {
	private int id;
	private int idPessoa;
	private Vacina vacina;
	private LocalDate dataAplicacao;
	private double avaliacao;
	private Pessoa pessoa;
	
	public Vacinacao() {
		
	}
	
	public Vacinacao(int id, int idPessoa, Vacina vacina, LocalDate dataAplicacao, double avaliacao, Pessoa pessoa) {
		super();
		this.id = id;
		this.idPessoa = idPessoa;
		this.vacina = vacina;
		this.dataAplicacao = dataAplicacao;
		this.avaliacao = avaliacao;
		this.pessoa = pessoa;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(int idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}

	public LocalDate getDataAplicacao() {
		return dataAplicacao;
	}

	public void setDataAplicacao(LocalDate dataAplicacao) {
		this.dataAplicacao = dataAplicacao;
	}

	public double getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(double avaliacao) {
		this.avaliacao = avaliacao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
}
