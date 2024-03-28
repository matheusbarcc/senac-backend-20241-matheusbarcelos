package services;

import java.util.List;

import exception.ControleVacinasException;
import model.entity.Vacina;
import model.repository.VacinaRepository;
import model.repository.VacinacaoRepository;

public class VacinaService {

	private VacinaRepository repository = new VacinaRepository();
	private VacinacaoRepository vacinacaoRepository = new VacinacaoRepository();
	
	public Vacina salvar(Vacina novaVacina) {
		return repository.salvar(novaVacina);
	}
	
	public boolean atualizar(Vacina vacinaAlterada) {
		return repository.alterar(vacinaAlterada);
	}
	
	public boolean excluir(int id) throws ControleVacinasException{
		if(vacinacaoRepository.consultarVacinasPorPessoa(id).isEmpty()) {
			return repository.excluir(id);			
		} else {
			throw new ControleVacinasException("A vacina de id " + id + " não pode ser excluída pois possui " + vacinacaoRepository.consultarVacinasPorPessoa(id).size()
					+ " aplicações cadastradas.");
		}
	}
	
	public Vacina consultarPorId(int id) {
		return repository.consultarPorId(id);
	}
	
	public List<Vacina> consultarTodas(){
		return repository.consultarTodos();
	}
	
	public List<Vacina> consultarPorResponsavel(int id){
		return repository.consultarPorResponsavel(id);
	}
}
