package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Pessoa;

public class PessoaRepository implements BaseRepository<Pessoa>{

	@Override
	public Pessoa salvar(Pessoa novaPessoa) {
		String query = "INSERT INTO pessoa (nome, dtnascimento, sexo, cpf, tipo) VALUES (?, ?, ?, ?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			preencherParametrosPessoa(pstmt, novaPessoa);
			
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			
			if(resultado.next()) {
				novaPessoa.setIdpessoa(resultado.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar nova pessoa!");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return novaPessoa;
	}

	@Override
	public boolean excluir(int id) {
	    Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = null;
	    boolean excluiu = false;

	    String query = "DELETE FROM pessoa WHERE id = ?";

	    try {
	        pstmt = conn.prepareStatement(query);
	        pstmt.setInt(1, id);

	        if (pstmt.executeUpdate() == 1) {
	            excluiu = true;
	        }
	    } catch (SQLException e) {
	        System.out.println("Erro ao excluir pessoa.");
	        System.out.println("Erro: " + e.getMessage());
	    } finally {
	        Banco.closePreparedStatement(pstmt);
	        Banco.closeConnection(conn);
	    }

	    return excluiu;
	}


	@Override
	public boolean alterar(Pessoa entidade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Pessoa consultarPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Pessoa> consultarTodos() {
		ArrayList<Pessoa> pessoas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		String query = "SELECT * FROM pessoa";
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				Pessoa pessoa = new Pessoa();
				
				pessoa.setIdpessoa(Integer.parseInt(resultado.getString("idpessoa")));
				pessoa.setNome(resultado.getString("nome"));
				pessoa.setDtnascimento(resultado.getDate("dtnascimento").toLocalDate());
				pessoa.setSexo(resultado.getString("sexo"));
				pessoa.setCpf(resultado.getString("cpf"));
				pessoa.setTipo(resultado.getString("tipo"));
				pessoas.add(pessoa);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao executar consultar todas as jogadors");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return pessoas;
	}
	
	private void preencherParametrosPessoa(PreparedStatement pstmt
			, Pessoa novaPessoa) throws SQLException {
		pstmt.setString(1, novaPessoa.getNome());
		pstmt.setDate(2, Date.valueOf(novaPessoa.getDtnascimento()));
		pstmt.setString(3, novaPessoa.getSexo());
		pstmt.setString(4, novaPessoa.getCpf());
		pstmt.setString(5, novaPessoa.getTipo());
	}
}
