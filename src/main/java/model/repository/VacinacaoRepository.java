package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import model.entity.Pessoa;
import model.entity.Vacina;
import model.entity.Vacinacao;
import model.entity.VacinacaoSeletor;
	
public class VacinacaoRepository implements BaseRepository<Vacinacao> {

	LocalDate dataAtual = LocalDate.now();
	
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
			stmt.setDouble(4, novaVacinacao.getAvaliacao());
			
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
			stmt.setDouble(4, vacinacaoEditada.getAvaliacao());
			
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
				vacinacao.setAvaliacao(resultado.getDouble("AVALIACAO"));
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
				vacinacao.setAvaliacao(resultado.getDouble("AVALIACAO"));
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
				vacinacao.setAvaliacao(resultado.getDouble("AVALIACAO"));
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
	
	public ArrayList<Vacinacao> consultarVacinacoesPorVacina(int id){
		ArrayList<Vacinacao> aplicacoesVacina = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM aplicacao_vacina WHERE id_vacina = "+id;
		try {
			resultado = stmt.executeQuery(query);
			VacinaRepository vacinaRepository = new VacinaRepository();
			
			while(resultado.next()){
				Vacinacao vacinacao = new Vacinacao();
				vacinacao.setId(resultado.getInt("ID"));
				vacinacao.setIdPessoa(resultado.getInt("ID_PESSOA"));
				vacinacao.setDataAplicacao(resultado.getDate("DATA_APLICACAO").toLocalDate());
				vacinacao.setAvaliacao(resultado.getDouble("AVALIACAO"));
				vacinacao.setVacina(vacinaRepository.consultarPorId(resultado.getInt("ID_VACINA")));
				
				aplicacoesVacina.add(vacinacao);
			}
		} catch(SQLException e){
			System.out.println("Erro consultar as aplicações de vacinas da vacina de id = " + id);
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return aplicacoesVacina;
	}
	
	public ArrayList<Vacinacao> consultarComSeletor(VacinacaoSeletor seletor){
		ArrayList<Vacinacao> vacinacoes = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		
		String sql = " SELECT av.* FROM aplicacao_vacina av "
					+ " INNER JOIN pessoa p on av.id_pessoa = p.id "
					+ " INNER JOIN vacina v on av.id_vacina = v.id ";
		
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
				Vacinacao vacinacao = construirDoResultSet(resultado);
				vacinacoes.add(vacinacao);
			}
		} catch(SQLException e) {
			System.out.println("Erro ao consultar vacinacoes com seletor");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		
		return vacinacoes;
	}
	
	public String preencherFiltros(VacinacaoSeletor seletor, String sql) {
		sql += " WHERE ";
		boolean primeiro = true;
		
		if(seletor.getNomePessoa() != null) {
			if(!primeiro) {
				sql += " AND ";
			}
			sql += "upper(p.nome) LIKE UPPER('" + seletor.getNomePessoa() + "%')";
			primeiro = false;
		}
		
		if(seletor.getNomeVacina() != null) {
			if(!primeiro) {
				sql += " AND ";
			}
			sql += "upper(v.nome) LIKE UPPER('" + seletor.getNomeVacina() + "%')";
			primeiro = false;
		}
		if((seletor.getDataAplicacaoInicio() != null) && (seletor.getDataAplicacaoFinal() != null)) {
			if(!primeiro) {
				sql += " AND ";
			}
			sql += " av.data_aplicacao BETWEEN '" + seletor.getDataAplicacaoInicio() + "' AND '" + seletor.getDataAplicacaoFinal() + "'";
			primeiro = false;
		}
		if(seletor.getDataAplicacaoInicio() != null) {
			if(!primeiro) {
				sql += " AND ";
			}
			
			sql += " av.data_aplicacao BETWEEN '" + seletor.getDataAplicacaoInicio() + "' AND '" + dataAtual + "'";
			primeiro = false;
		}
		if(seletor.getDataAplicacaoFinal() != null) {
			if(!primeiro) {
				sql += " AND ";
			}
			
			sql += " av.data_aplicacao BETWEEN '0000-00-00' AND '" + seletor.getDataAplicacaoFinal() + "'";
			primeiro = false;
		}
		if(seletor.getAvaliacao() > 0) {
			if(!primeiro) {
				sql += " AND ";
			}
			
			sql += " av.avaliacao =" + seletor.getAvaliacao();
			primeiro = false;
		}
		return sql;
	}
	
	private Vacinacao construirDoResultSet(ResultSet resultado) throws SQLException{
		Vacinacao v = new Vacinacao();
		VacinaRepository vacinaRepository = new VacinaRepository();
		PessoaRepository pessoaRepository = new PessoaRepository();
		
		v.setId(resultado.getInt("ID"));
		v.setIdPessoa(resultado.getInt("ID_PESSOA"));
		Vacina vacina = vacinaRepository.consultarPorId(resultado.getInt("ID_VACINA"));
		v.setVacina(vacina);
		v.setDataAplicacao(resultado.getDate("DATA_APLICACAO").toLocalDate());
		v.setAvaliacao(resultado.getInt("AVALIACAO"));
		Pessoa pessoa = pessoaRepository.consultarPorId(resultado.getInt("ID_PESSOA"));
		v.setPessoa(pessoa);
		
		return v;
	}
}
