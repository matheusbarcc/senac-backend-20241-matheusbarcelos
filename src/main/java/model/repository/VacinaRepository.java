package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import model.entity.Pais;
import model.entity.Pessoa;
import model.entity.Vacina;
import model.entity.VacinaSeletor;

public class VacinaRepository implements BaseRepository<Vacina> {

	LocalDate dataAtual = LocalDate.now();
	
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
				vacina.setMedia(resultado.getDouble("MEDIA"));
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
	
	public ArrayList<Vacina> consultarComSeletor(VacinaSeletor seletor){
		ArrayList<Vacina> vacinas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String sql = " SELECT v.* FROM vacina v "
					+ " INNER JOIN pais p on v.id_pais_origem = p.id "
					+ " INNER JOIN pessoa pe on v.id_pesquisador = pe.id ";
			
		if(seletor.temFiltro()) {
			sql = preencherFiltros(seletor, sql);
		}
		
		if(seletor.temPaginacao()) {
			sql += " LIMIT " + seletor.getLimite()
					+ " OFFSET " + seletor.getOffset();
		}
		
		try {
			resultado = stmt.executeQuery(sql);
			while(resultado.next()) {
				Vacina vacina = construirDoResultSet(resultado);
				vacinas.add(vacina);
			}
		} catch(SQLException e) {
			System.out.println("Erro ao consultar vacinas com seletor");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return vacinas;
	}
	
	public int count(VacinaSeletor seletor) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		int total = 0;
		ResultSet resultado = null;
		String sql = " SELECT count(*) FROM vacina v "
				+ " INNER JOIN pais p on v.id_pais_origem = p.id "
				+ " INNER JOIN pessoa pe on v.id_pesquisador = pe.id ";
		
		if(seletor.temFiltro()) {
			sql = preencherFiltros(seletor, sql);
		}
		
		try {
			resultado = stmt.executeQuery(sql);
			if(resultado.next()){
				total = resultado.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao contar registros de vacinas");
		 	System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return total;
	}
	
	public int contarPaginas(VacinaSeletor seletor) {
		int totalPaginas = 0;
		int totalRegistros = this.count(seletor);
		
		totalPaginas = totalRegistros / seletor.getLimite();
		int resto = totalRegistros % seletor.getLimite();
		
		if(resto > 0) {
			totalPaginas++;
		}
		
		return totalPaginas;
	}
	
	public String preencherFiltros(VacinaSeletor seletor, String sql) {
		sql += " WHERE ";
		boolean primeiro = true;
		
		if(seletor.getNome() != null) {
			if(!primeiro) {
				sql += " AND ";
			}
			sql += "upper(v.nome) LIKE UPPER('" + seletor.getNome() + "%')";
			primeiro = false;
		}
		
		if(seletor.getNomePais() != null) {
			if(!primeiro) {
				sql += " AND ";
			}
			sql += "upper(p.nome) LIKE UPPER('" + seletor.getNomePais() + "%')";
			primeiro = false;
		}
		if(seletor.getNomePesquisador() != null &&  seletor.getNomePesquisador() != null) {
			if(!primeiro) {
				sql += " AND ";
			}
			sql += "upper(pe.nome) LIKE UPPER('" + seletor.getNomePesquisador() + "%')";
			primeiro = false;
		}
		if(seletor.getEstagio() > 0) {
			if(!primeiro) {
				sql += " AND ";
			}
			
			sql += " v.estagio_pesquisa =" + seletor.getEstagio();
			primeiro = false;
		}
		if((seletor.getDataInicioPesquisa() != null) && (seletor.getDataFinalPesquisa() != null)) {
			if(!primeiro) {
				sql += " AND ";
			}
			sql += " v.data_inicio_pesquisa BETWEEN '" + seletor.getDataInicioPesquisa() + "' AND '" + seletor.getDataFinalPesquisa() + "'";
			primeiro = false;
		}
		if(seletor.getDataInicioPesquisa() != null) {
			if(!primeiro) {
				sql += " AND ";
			}
			
			sql += " v.data_inicio_pesquisa BETWEEN '" + seletor.getDataInicioPesquisa() + "' AND '" + dataAtual + "'";
			primeiro = false;
		}
		if(seletor.getDataFinalPesquisa() != null) {
			if(!primeiro) {
				sql += " AND ";
			}
			
			sql += " v.data_inicio_pesquisa BETWEEN '0000-00-00' AND '" + seletor.getDataFinalPesquisa() + "'";
			primeiro = false;
		}
		
		return sql;
	}
	
	private Vacina construirDoResultSet(ResultSet resultado) throws SQLException{
		Vacina v = new Vacina();
		PessoaRepository pessoaRepository = new PessoaRepository();
		PaisRepository paisRepository = new PaisRepository();
		
		v.setId(resultado.getInt("ID"));
		v.setNome(resultado.getString("NOME"));
		Pais pais = paisRepository.consultarPorId(resultado.getInt("ID_PAIS_ORIGEM"));
		v.setPaisOrigem(pais);
		Pessoa pesquisador = pessoaRepository.consultarPorId(resultado.getInt("ID_PESQUISADOR"));
		v.setPesquisadorResponsavel(pesquisador);
		v.setEstagio(resultado.getInt("ESTAGIO_PESQUISA"));
		v.setDataInicioPesquisa(resultado.getDate("DATA_INICIO_PESQUISA").toLocalDate());
		
		return v;
	}
}
