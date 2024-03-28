package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Pais;
import model.entity.Pessoa;
import model.entity.Vacina;

public class VacinaRepository implements BaseRepository<Vacina> {

	@Override
	public Vacina salvar(Vacina novaVacina) {
		String sql = " INSERT INTO vacina(id_pesquisador, nome, id_pais_origem, estagio_pesquisa, data_inicio_pesquisa, media) "
				   + " VALUES(?, ?, ?, ?, ?, ?) ";
		Connection conexao = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conexao, sql);
		
		try {
			pstmt.setInt(1, novaVacina.getPesquisadorResponsavel().getId());
			pstmt.setString(2, novaVacina.getNome());
			pstmt.setInt(3, novaVacina.getPaisOrigem().getId());
			pstmt.setInt(4, novaVacina.getEstagio());
			pstmt.setDate(5, Date.valueOf(novaVacina.getDataInicioPesquisa()));
			pstmt.setDouble(6, novaVacina.getMedia());
			
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			if(resultado.next()) {
				novaVacina.setId(resultado.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar nova vacina");
			System.out.println("Erro: " + e.getMessage());
		}
		
		return novaVacina;
	}

	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM vacina WHERE id = " + id;
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir vacina");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public boolean alterar(Vacina vacinaEditada) {
		boolean alterou = false;
		String query = " UPDATE vacina "
				     + " SET id_pesquisador=?, nome=?, id_pais_origem=?, estagio_pesquisa=?, data_inicio_pesquisa=?, "
				     + " media=? WHERE id=? ";
		Connection conn = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			stmt.setInt(1, vacinaEditada.getPesquisadorResponsavel().getId());
			stmt.setString(2, vacinaEditada.getNome());
			stmt.setInt(3, vacinaEditada.getPaisOrigem().getId());
			stmt.setInt(4, vacinaEditada.getEstagio());
			stmt.setDate(5, Date.valueOf(vacinaEditada.getDataInicioPesquisa()));
			stmt.setDouble(6, vacinaEditada.getMedia());
			
			stmt.setInt(7, vacinaEditada.getId());
			alterou = stmt.executeUpdate() > 0;
		} catch (SQLException erro) {
			System.out.println("Erro ao atualizar vacina");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return alterou;
	}

	@Override
	public Vacina consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		Vacina vacina = null;
		ResultSet resultado = null;
		String query = "SELECT * FROM vacina WHERE id = " + id;
		
		try{
			resultado = stmt.executeQuery(query);
			PessoaRepository pessoaRepository = new PessoaRepository();
			PaisRepository paisRepository = new PaisRepository();
			if(resultado.next()){
				vacina = new Vacina();
				vacina.setId(Integer.parseInt(resultado.getString("ID")));
				vacina.setNome(resultado.getString("NOME"));
				Pais pais = paisRepository.consultarPorId(resultado.getInt("ID_PAIS_ORIGEM"));
				vacina.setPaisOrigem(pais);
				vacina.setEstagio(resultado.getInt("ESTAGIO_PESQUISA"));
				vacina.setDataInicioPesquisa(resultado.getDate("DATA_INICIO_PESQUISA").toLocalDate()); 
				Pessoa pesquisador = pessoaRepository.consultarPorId(resultado.getInt("ID_PESQUISADOR"));
				vacina.setPesquisadorResponsavel(pesquisador);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar vacina com o id: " + id);
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return vacina;
	}

	@Override
	public ArrayList<Vacina> consultarTodos() {
		ArrayList<Vacina> vacinas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM vacina";
		
		try{
			resultado = stmt.executeQuery(query);
			PessoaRepository pessoaRepository = new PessoaRepository();
			PaisRepository paisRepository = new PaisRepository();
			while(resultado.next()){
				Vacina vacina = new Vacina();
				vacina.setId(Integer.parseInt(resultado.getString("ID")));
				vacina.setNome(resultado.getString("NOME"));
				Pais pais = paisRepository.consultarPorId(resultado.getInt("ID_PAIS_ORIGEM"));
				vacina.setPaisOrigem(pais);
				vacina.setEstagio(resultado.getInt("ESTAGIO_PESQUISA"));
				vacina.setDataInicioPesquisa(resultado.getDate("DATA_INICIO_PESQUISA").toLocalDate()); 
				Pessoa pesquisador = pessoaRepository.consultarPorId(resultado.getInt("ID_PESQUISADOR"));
				vacina.setPesquisadorResponsavel(pesquisador);
				vacinas.add(vacina);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar todas as vacinas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return vacinas;
	}
	
	public ArrayList<Vacina> consultarPorResponsavel(int id){
		ArrayList<Vacina> vacinas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		String query = " SELECT * FROM vacina WHERE id_pesquisador = " + id;
		
		ResultSet resultado = null;
		try{
			resultado = stmt.executeQuery(query);
			PessoaRepository pessoaRepository = new PessoaRepository();
			PaisRepository paisRepository = new PaisRepository();
			while(resultado.next()){
				Vacina vacina = new Vacina();
				vacina.setId(Integer.parseInt(resultado.getString("ID")));
				vacina.setNome(resultado.getString("NOME"));
				Pais pais = paisRepository.consultarPorId(resultado.getInt("ID_PAIS_ORIGEM"));
				vacina.setPaisOrigem(pais);
				vacina.setEstagio(resultado.getInt("ESTAGIO_PESQUISA"));
				vacina.setDataInicioPesquisa(resultado.getDate("DATA_INICIO_PESQUISA").toLocalDate()); 
				Pessoa pesquisador = pessoaRepository.consultarPorId(resultado.getInt("ID_PESQUISADOR"));
				vacina.setPesquisadorResponsavel(pesquisador);
				vacinas.add(vacina);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar todas as vacinas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return vacinas;
	}
}
