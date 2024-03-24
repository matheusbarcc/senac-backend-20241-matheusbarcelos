package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Vacinacao;
	
public class VacinacaoRepository implements BaseRepository<Vacinacao> {

	@Override
	public Vacinacao salvar(Vacinacao novaVacinacao) {
		String sql = " INSERT INTO aplicacao_vacina (id_pessoa, id_vacina, data_aplicacao, avaliacao) "
				   + " VALUES(?, ?, ?, ?)";
		Connection conexao = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);
		
		try {
			stmt.setInt(1, novaVacinacao.getIdPessoa());
			stmt.setInt(2, novaVacinacao.getVacina().getId());
			stmt.setDate(3, Date.valueOf(novaVacinacao.getDataAplicacao()));
			stmt.setInt(4, novaVacinacao.getAvaliacao());
			
			stmt.execute();
			ResultSet resultado = stmt.getGeneratedKeys();
			if(resultado.next()) {
				novaVacinacao.setId(resultado.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar nova aplicação");
			System.out.println("Erro: " + e.getMessage());
		}
		
		return novaVacinacao;
	}

	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM aplicacao_vacina WHERE id = " + id;
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir aplicação");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public boolean alterar(Vacinacao vacinacaoEditada) {
		boolean alterou = false;
		String query = " UPDATE aplicacao_vacina "
					 + " SET id_pessoa=?, id_vacina=?, data_aplicacao=?, avaliacao=? "
				     + " WHERE id=? ";
		Connection conn = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			stmt.setInt(1, vacinacaoEditada.getIdPessoa());
			stmt.setInt(2, vacinacaoEditada.getVacina().getId());
			stmt.setDate(3, Date.valueOf(vacinacaoEditada.getDataAplicacao()));
			stmt.setInt(4, vacinacaoEditada.getAvaliacao());
			
			stmt.setInt(5, vacinacaoEditada.getId());
			alterou = stmt.executeUpdate() > 0;
		} catch (SQLException erro) {
			System.out.println("Erro ao atualizar aplicação de vacina");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return alterou;
	}

	@Override
	public Vacinacao consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		Vacinacao vacinacao = null;
		ResultSet resultado = null;
		String query = " SELECT * FROM aplicacao_vacina WHERE id = " + id;
		
		try{
			resultado = stmt.executeQuery(query);
			VacinaRepository vacinaRepository = new VacinaRepository();
			if(resultado.next()){
				vacinacao = new Vacinacao();
				vacinacao.setId(resultado.getInt("ID"));
				vacinacao.setIdPessoa(resultado.getInt("ID_PESSOA"));
				vacinacao.setDataAplicacao(resultado.getDate("DATA_APLICACAO").toLocalDate());
				vacinacao.setAvaliacao(resultado.getInt("AVALIACAO"));
				vacinacao.setVacina(vacinaRepository.consultarPorId(resultado.getInt("ID_VACINA")));
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar a aplicação de vacina com o id: " + id);
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return vacinacao;
	}

	@Override
	public ArrayList<Vacinacao> consultarTodos() {
		ArrayList<Vacinacao> aplicacoes = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM aplicacao_vacina";
		
		try{
			resultado = stmt.executeQuery(query);
			VacinaRepository vacinaRepository = new VacinaRepository();
			while(resultado.next()){
				Vacinacao vacinacao = new Vacinacao();
				vacinacao.setId(resultado.getInt("ID"));
				vacinacao.setIdPessoa(resultado.getInt("ID_PESSOA"));
				vacinacao.setDataAplicacao(resultado.getDate("DATA_APLICACAO").toLocalDate());
				vacinacao.setAvaliacao(resultado.getInt("AVALIACAO"));
				vacinacao.setVacina(vacinaRepository.consultarPorId(resultado.getInt("ID_VACINA")));
				
				aplicacoes.add(vacinacao);
			}
		} catch (SQLException erro){
			System.out.println("Erro consultar todas as aplicações de vacinas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return aplicacoes;
	}
	
	public ArrayList<Vacinacao> consultarVacinasPorPessoa(int idpessoa){
		ArrayList<Vacinacao> aplicacoesPessoa = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM aplicacao_vacina WHERE id_pessoa = "+idpessoa;
		try {
			resultado = stmt.executeQuery(query);
			VacinaRepository vacinaRepository = new VacinaRepository();
			
			while(resultado.next()){
				Vacinacao vacinacao = new Vacinacao();
				vacinacao.setId(resultado.getInt("ID"));
				vacinacao.setIdPessoa(resultado.getInt("ID_PESSOA"));
				vacinacao.setDataAplicacao(resultado.getDate("DATA_APLICACAO").toLocalDate());
				vacinacao.setAvaliacao(resultado.getInt("AVALIACAO"));
				vacinacao.setVacina(vacinaRepository.consultarPorId(resultado.getInt("ID_VACINA")));
				
				aplicacoesPessoa.add(vacinacao);
			}
		} catch(SQLException e){
			System.out.println("Erro consultar as aplicações de vacinas da pessoa de id = " + idpessoa);
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return aplicacoesPessoa;
	}
}
