package model.entity;

import java.time.LocalDate;

public class Pessoa {
	
	private int idpessoa;
	private String nome;
	private LocalDate dtnascimento;
	private String sexo;
	private String cpf;
	private String tipo;
	
	public Pessoa() {
		super();
	}

	public Pessoa(int idpessoa, String nome, LocalDate dtnascimento, String sexo, String cpf, String tipo) {
		super();
		this.idpessoa = idpessoa;
		this.nome = nome;
		this.dtnascimento = dtnascimento;
		this.sexo = sexo;
		this.cpf = cpf;
		this.tipo = tipo;
	}

	public int getIdpessoa() {
		return idpessoa;
	}


	public void setIdpessoa(int idpessoa) {
		this.idpessoa = idpessoa;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public LocalDate getDtnascimento() {
		return dtnascimento;
	}


	public void setDtnascimento(LocalDate dtnascimento) {
		this.dtnascimento = dtnascimento;
	}


	public String getSexo() {
		return sexo;
	}


	public void setSexo(String sexo) {
		this.sexo = sexo;
	}


	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
