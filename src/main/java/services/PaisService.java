package services;

import exception.ControleVacinasException;
import model.entity.Pais;
import model.repository.PaisRepository;

public class PaisService {

	private PaisRepository repository = new PaisRepository();
	
	public Pais salvar(Pais novoPais) throws ControleVacinasException  {
		validarSigla(novoPais);
		return repository.salvar(novoPais);
	}
	
	public void validarSigla(Pais novoPais) throws ControleVacinasException {
		if(novoPais.getSigla().length() > 2) {
			throw new ControleVacinasException("A sigla deve ser composta por apena 2 letras.");
		}
	}
}
