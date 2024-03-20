package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Vacina;

public class VacinaRepository implements BaseRepository<Vacina>{

	@Override
	public Vacina salvar(Vacina novaVacina) {
		String query = "INSERT INTO vacina (id_pesquisador, nome, pais_origem, estagio_pesquisa, data_inicio_pesquisa) VALUES (?, ?, ?, ?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			preencherParametrosVacina(pstmt, novaVacina);
			pstmt.execute();
			
			ResultSet resultado = pstmt.getGeneratedKeys();
			if(resultado.next()) {
				novaVacina.setId(resultado.getInt(1));
			}
		} catch(SQLException e) {
			System.out.println("Erro ao salvar nova vacina");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return novaVacina;
	}

	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = null;
	    boolean excluiu = false;
	    
	    String query = "DELETE FROM vacina WHERE id = ?";
	    
	    try {
	    	pstmt = conn.prepareStatement(query);
	    	pstmt.setInt(1, id);
	    	
	    	if(pstmt.executeUpdate() == 1) {
	    		excluiu = true;
	    	}
	    } catch (SQLException e) {
	        System.out.println("Erro ao excluir vacina");
	        System.out.println("Erro: " + e.getMessage());
	    } finally {
	        Banco.closePreparedStatement(pstmt);
	        Banco.closeConnection(conn);
	    }
		return excluiu;
	}

	@Override
	public boolean alterar(Vacina novaVacina) {
		String query = "UPDATE vacina SET id_pesquisador = ?, nome = ?, pais_origem = ?, "
						+ "estagio_pesquisa = ?, data_inicio_pesquisa = ? WHERE id = ?";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		boolean alterou = false;
		
		try {
			preencherParametrosVacina(pstmt, novaVacina);
			pstmt.setInt(6, novaVacina.getId());
			alterou = pstmt.executeUpdate() > 0;
			
		} catch (SQLException erro) {
			System.out.println("Erro ao atualizar vacina");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		
		return alterou;
	}

	@Override
	public Vacina consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		Vacina vacina = new Vacina();
		String query = "SELECT * FROM vacina WHERE id = " + id;
		
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				preencherObjetoVacina(vacina, resultado);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar vacina com id (" + id + ")");
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
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				Vacina vacina = new Vacina();
				preencherObjetoVacina(vacina, resultado);
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

	public void preencherParametrosVacina(PreparedStatement pstmt, Vacina novaVacina) throws SQLException {
		pstmt.setInt(1, novaVacina.getPesquisadorResponsavel().getIdpessoa());
		pstmt.setString(2, novaVacina.getNome());
		pstmt.setString(3, novaVacina.getPaisOrigem());
		pstmt.setInt(4, novaVacina.getEstagio());
		pstmt.setDate(5, Date.valueOf(novaVacina.getDataInicioPesquisa()));
	}
	
	public void preencherObjetoVacina(Vacina vacina, ResultSet resultado) throws SQLException {
		vacina.setId(Integer.parseInt(resultado.getString("id")));
		vacina.setNome(resultado.getString("nome"));
		//vacina.setPesquisadorResponsavel(resultado.getInt("id_pesquisador"));
		vacina.setPaisOrigem(resultado.getString("pais_origem"));
		vacina.setEstagio(resultado.getInt("estagio_pesquisa"));
		if(resultado.getDate("data_inicio_pesquisa") != null) {
			vacina.setDataInicioPesquisa(resultado.getDate("data_inicio_pesquisa").toLocalDate());
		}
	}
}
