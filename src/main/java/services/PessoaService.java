package services;

import java.util.List;

import exception.ControleVacinasException;
import model.entity.Pessoa;
import model.repository.PessoaRepository;

public class PessoaService {
	
	private PessoaRepository repository = new PessoaRepository();

	public Pessoa salvar(Pessoa novaPessoa) throws ControleVacinasException{
		validarPessoa(novaPessoa);
		return repository.salvar(novaPessoa);
	}
	
	public boolean atualizar(Pessoa pessoaEditada) throws ControleVacinasException{
		validarPessoa(pessoaEditada);
		return repository.alterar(pessoaEditada);
	}
	
	public boolean excluir(int id){
		return repository.excluir(id);
	}
	
	public Pessoa consultarPorId(int id){
		return repository.consultarPorId(id);
	}
	
	public List<Pessoa> consultarTodas(){
		return repository.consultarTodos();
	}
	
	private void validarPessoa(Pessoa pessoa) throws ControleVacinasException{
		if(pessoa.getNome() == null || pessoa.getNome() == "") {
			throw new ControleVacinasException("O campo 'Nome' deve ser preenchido.");
		}
		if(pessoa.getDtnascimento() == null) {
			throw new ControleVacinasException("O campo 'Data de nascimento' deve ser preenchido.");
		}
		if(pessoa.getSexo() == null || pessoa.getSexo() == "") {
			throw new ControleVacinasException("O campo 'Sexo' deve ser preenchido.");
		}
		if(pessoa.getCpf() == null || pessoa.getCpf() == "") {
			throw new ControleVacinasException("O campo 'CPF' deve ser preenchido.");
		} 
		if(pessoa.getTipo() == null || pessoa.getTipo() == "") {
			throw new ControleVacinasException("O campo 'Tipo' deve ser preenchido.");
		}
	}	
}
