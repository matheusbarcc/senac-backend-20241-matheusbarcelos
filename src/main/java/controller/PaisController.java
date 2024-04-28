package controller;

import java.util.List;

import exception.ControleVacinasException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.Pais;
import services.PaisService;

@Path("/pais")
public class PaisController {
	
	private PaisService service = new PaisService();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Pais salvar(Pais novoPais) throws ControleVacinasException {
		return service.salvar(novoPais);
	}
	
	@GET
	@Path("/todos")
	public List<Pais> consultarTodos(){
		return service.consultarTodos();
	}
}
