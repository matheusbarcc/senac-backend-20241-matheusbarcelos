package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Pais;

public class PaisRepository {

	public Pais salvar(Pais novoPais) {
		String sql = " INSERT INTO pais (nome, sigla) VALUES(?, ?)";
		Connection conexao = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conexao, sql);
		
		try {
			pstmt.setString(1, novoPais.getNome());
			pstmt.setString(2, novoPais.getSigla());
			
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			if(resultado.next()) {
				novoPais.setId(resultado.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar novo país");
			System.out.println("Erro: " + e.getMessage());
		}
		
		return novoPais;
	}
	
	public ArrayList<Pais> consultarTodos(){
		ArrayList<Pais> paises = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM pais";
		
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				Pais pais = new Pais();
				pais.setId(resultado.getInt("ID"));
				pais.setNome(resultado.getString("NOME"));
				pais.setSigla(resultado.getString("SIGLA"));
				paises.add(pais);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar todos os paises");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return paises;
	}
	
	public Pais consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		Pais pais = null;
		ResultSet resultado = null;
		String query = " SELECT * FROM pais WHERE id = " + id;
		
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				pais = new Pais();
				pais.setId(resultado.getInt("ID"));
				pais.setNome(resultado.getString("NOME"));
				pais.setSigla(resultado.getString("SIGLA"));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar pais com o id: " + id);
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return pais;
	}
}
