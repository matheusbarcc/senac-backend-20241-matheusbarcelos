package services;

import java.time.LocalDate;
import java.util.List;

import exception.ControleVacinasException;
import model.entity.Vacinacao;
import model.repository.VacinacaoRepository;

public class VacinacaoService {

	private VacinacaoRepository repository = new VacinacaoRepository();
	
	public Vacinacao salvar(Vacinacao novaVacinacao) throws ControleVacinasException {
		validarCamposObrigatorios(novaVacinacao);

		return repository.salvar(novaVacinacao);
	}
	
	public boolean atualizar(Vacinacao novaVacinacao) throws ControleVacinasException {
		validarCamposObrigatorios(novaVacinacao);
		
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
}
