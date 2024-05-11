package services;

import java.time.LocalDate;
import java.util.List;

import exception.ControleVacinasException;
import model.entity.Vacinacao;
import model.entity.VacinacaoSeletor;
import model.repository.PessoaRepository;
import model.repository.VacinaRepository;
import model.repository.VacinacaoRepository;

public class VacinacaoService {

	private VacinacaoRepository repository = new VacinacaoRepository();
	private VacinaRepository vacinaRepository = new VacinaRepository();
	private PessoaRepository pessoaRepository = new PessoaRepository();
	
	public Vacinacao salvar(Vacinacao novaVacinacao) throws ControleVacinasException {
		validarCamposObrigatorios(novaVacinacao);
		calcularMediaVacinaInserir(novaVacinacao);
		if(novaVacinacao.getVacina().getEstagio() == 1 && pessoaRepository.consultarPorId(novaVacinacao.getIdPessoa()).getTipo() != 1) {
			throw new ControleVacinasException("Erro ao salvar nova Vacinacao, apenas pesquisadores podem receber vacinas em estágio Inicial");
		} else if (novaVacinacao.getVacina().getEstagio() == 2 && pessoaRepository.consultarPorId(novaVacinacao.getIdPessoa()).getTipo() > 2) {
			throw new ControleVacinasException("Erro ao salvar nova Vacinacao, apenas pesquisadores e voluntarios podem receber vacinas em estágio de Testes");
		}

		return repository.salvar(novaVacinacao);
	}
	
	public boolean atualizar(Vacinacao novaVacinacao) throws ControleVacinasException {
		validarCamposObrigatorios(novaVacinacao);
		calcularMediaVacinaAtualizar(novaVacinacao);
		if(novaVacinacao.getVacina().getEstagio() == 1 && pessoaRepository.consultarPorId(novaVacinacao.getIdPessoa()).getTipo() != 1) {
			throw new ControleVacinasException("Erro ao salvar nova Vacinacao, apenas pesquisadores podem receber vacinas em estágio Inicial");
		} else if (novaVacinacao.getVacina().getEstagio() == 2 && pessoaRepository.consultarPorId(novaVacinacao.getIdPessoa()).getTipo() > 2) {
			throw new ControleVacinasException("Erro ao salvar nova Vacinacao, apenas pesquisadores e voluntarios podem receber vacinas em estágio de Testes");
		}
		
		return repository.alterar(novaVacinacao);
	}

	public boolean excluir(int id) {
		return repository.excluir(id);
	}

	public Vacinacao consultarPorId(int id) {
		return repository.consultarPorId(id);
	}

	public List<Vacinacao> consultarTodas() {
		return repository.consultarTodos();
	}
	
	public List<Vacinacao> consultarPorPessoa(int idpessoa) {
		return repository.consultarVacinasPorPessoa(idpessoa);
	}
	
	public List<Vacinacao> consultarPorVacina(int id) {
		return repository.consultarVacinacoesPorVacina(id);
	}
	
	public List<Vacinacao> consultarComSeletor(VacinacaoSeletor seletor){
		return repository.consultarComSeletor(seletor);
	}
	
	private void validarCamposObrigatorios(Vacinacao v) throws ControleVacinasException{
		String mensagemValidacao = "";
		if(v.getIdPessoa() < 1) {
			mensagemValidacao += " - informe o id da pessoa vacinada \n";
		}
		if(v.getVacina() == null) {
			mensagemValidacao += " - informe a vacina aplicada \n";
		}
		if(v.getDataAplicacao() != LocalDate.now()) {
			v.setDataAplicacao(LocalDate.now());;
		}
		if(v.getAvaliacao() < 1 || v.getAvaliacao() > 5) {
			v.setAvaliacao(5);;
		}
		if(!mensagemValidacao.isEmpty()) {
			throw new ControleVacinasException("Preencha o(s) seguinte(s) campo(s) \n " + mensagemValidacao);
		}
	}
	
	private void calcularMediaVacinaInserir(Vacinacao novaVacinacao) {
		double somatorio = 0;
		double media = 0;
		List<Vacinacao> vacinacoes = repository.consultarVacinacoesPorVacina(novaVacinacao.getVacina().getId());
		for(Vacinacao v : vacinacoes) {
			somatorio = somatorio + v.getAvaliacao();
		}
		somatorio = somatorio + novaVacinacao.getAvaliacao();
		media = somatorio / (vacinacoes.size() + 1);
		novaVacinacao.getVacina().setMedia(media);
		vacinaRepository.alterar(novaVacinacao.getVacina());
	}
	
	private void calcularMediaVacinaAtualizar(Vacinacao novaVacinacao) {
		double somatorio = 0;
		double media = 0;
		List<Vacinacao> vacinacoes = repository.consultarVacinacoesPorVacina(novaVacinacao.getVacina().getId());
		for(Vacinacao v : vacinacoes) {
			if(v.getId() == novaVacinacao.getId()) {
				somatorio = somatorio + 0;
			} else {
				somatorio = somatorio + v.getAvaliacao();				
			}
		}
		somatorio = somatorio + novaVacinacao.getAvaliacao();
		media = somatorio / vacinacoes.size();
		novaVacinacao.getVacina().setMedia(media);
		vacinaRepository.alterar(novaVacinacao.getVacina());
	}
}
