package controller;

import java.util.ArrayList;
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
import model.entity.Vacinacao;
import services.VacinacaoService;

@Path("/vacinacao")
public class VacinacaoController {
	private VacinacaoService service = new VacinacaoService();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Vacinacao salvar(Vacinacao novaVacinacao) throws ControleVacinasException {
		return service.salvar(novaVacinacao);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean atualizar(Vacinacao vacinacaoEditada) throws ControleVacinasException {
		return service.atualizar(vacinacaoEditada);
	}
	
	@DELETE
	@Path("/{id}")
	public boolean excluir(@PathParam("id") int id) {
		return service.excluir(id);
	}
	
	@GET
	@Path("/{id}")
	public Vacinacao consultarPorId(@PathParam("id") int id) {
		return service.consultarPorId(id);
	}
	
	@GET
	@Path("/todas")
	public List<Vacinacao> consultarTodas(){
		return service.consultarTodas();
	}
	
	@GET
	@Path("/pessoa/{idpessoa}")
	public List<Vacinacao> consultarPorPessoa(@PathParam("idpessoa") int idpessoa) {
		return service.consultarPorPessoa(idpessoa);
	}
	
	@GET
	@Path("/vacina/{id}")
	public List<Vacinacao> consultarPorVacina(@PathParam("id") int id) {
		return service.consultarPorVacina(id);
	}
}
