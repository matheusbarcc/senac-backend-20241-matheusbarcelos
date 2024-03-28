package controller;

import java.util.List;

import exception.ControleVacinasException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import services.PessoaService;
import model.entity.Pessoa;

@Path("/pessoa")
public class PessoaController {

	private PessoaService service = new PessoaService();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Pessoa salvar(Pessoa novaPessoa) throws ControleVacinasException {
		return service.salvar(novaPessoa);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean atualizar(Pessoa pessoaEditada) throws ControleVacinasException {
		return service.atualizar(pessoaEditada);
	}
	
	@DELETE
	@Path("/{id}")
	public boolean excluir(@PathParam("id") int id) throws ControleVacinasException {
		return service.excluir(id);
	}
	
	@GET
	@Path("/{id}")
	public Pessoa consultarPorId(@PathParam("id") int id) {
		return service.consultarPorId(id);
	}
	
	@GET
	@Path("/todas")
	public List<Pessoa> consultarTodas(){
		return service.consultarTodas();
	}
}
