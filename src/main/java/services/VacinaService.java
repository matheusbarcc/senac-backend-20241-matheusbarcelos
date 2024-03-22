package services;

import java.util.List;

import model.entity.Vacina;
import model.repository.VacinaRepository;

public class VacinaService {

	private VacinaRepository repository = new VacinaRepository();
	
	public Vacina salvar(Vacina novaVacina) {
		return repository.salvar(novaVacina);
	}
	
	public boolean atualizar(Vacina vacinaAlterada) {
		return repository.alterar(vacinaAlterada);
	}
	
	public boolean excluir(int id) {
		return repository.excluir(id);
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
