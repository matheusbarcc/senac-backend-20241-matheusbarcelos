package services;

import java.util.List;

import exception.ControleVacinasException;
import model.entity.Pessoa;
import model.repository.PessoaRepository;
import model.repository.VacinacaoRepository;

public class PessoaService {

	private PessoaRepository repository = new PessoaRepository();
	private VacinacaoRepository vacinacaoRepository = new VacinacaoRepository();
	
	public Pessoa salvar(Pessoa novaPessoa) throws ControleVacinasException {
		validarCamposObrigatorios(novaPessoa);
		
		validarCpf(novaPessoa);
		
		return repository.salvar(novaPessoa);
	}
	
	public boolean atualizar(Pessoa pessoaEditada) throws ControleVacinasException {
		validarCamposObrigatorios(pessoaEditada);
		
		return repository.alterar(pessoaEditada);
	}

	public boolean excluir(int id) throws ControleVacinasException{
		if(vacinacaoRepository.consultarVacinasPorPessoa(id).isEmpty()) {
			return repository.excluir(id);			
		} else {
			throw new ControleVacinasException("A pessoa de id " + id + " não pode ser excluída pois possui " + vacinacaoRepository.consultarVacinasPorPessoa(id).size()
					+ " vacinações cadastradas.");
		}
		
	}

	public Pessoa consultarPorId(int id) {
		return repository.consultarPorId(id);
	}

	public List<Pessoa> consultarTodas() {
		return repository.consultarTodos();
	}
	
	public List<Pessoa> consultarPesquisadores(){
		return repository.consultarPesquisadores();
	}
	
	private void validarCpf(Pessoa novaPessoa) throws ControleVacinasException {
		if(repository.cpfJaCadastrado(novaPessoa.getCpf())) {
			throw new ControleVacinasException("CPF " + novaPessoa.getCpf() + " já cadastrado "); 
		}
	}

	private void validarCamposObrigatorios(Pessoa p) throws ControleVacinasException{
		String mensagemValidacao = "";
		if(p.getNome() == null || p.getNome().isEmpty()) {
			mensagemValidacao += " - informe o nome \n";
		}
		if(p.getDataNascimento() == null) {
			mensagemValidacao += " - informe a data de nascimento \n";
		}
		if(p.getCpf() == null || p.getCpf().isEmpty() || p.getCpf().length() != 11) {
			mensagemValidacao += " - informe o CPF";
		}
		if(p.getSexo() == ' ') {
			mensagemValidacao += " - informe o sexo";
		}
		if(p.getTipo() < 1 || p.getTipo() > 3) {
			mensagemValidacao += " - informe o tipo (entre 1 e 3)";
		}
		
		if(!mensagemValidacao.isEmpty()) {
			throw new ControleVacinasException("Preencha o(s) seguinte(s) campo(s) \n " + mensagemValidacao);
		}
	}
}
