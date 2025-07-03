package model.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import model.Jogos;
import model.ModelException;
import model.User;

public class MySQLJogosDAO implements JogosDAO {

	@Override
	public boolean save(Jogos jogos) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlInsert = "INSERT INTO jogos (nome, genero, plataforma, ano, preco, desenvolvedora, usuario) VALUES (?, ?, ?, ?, ?, ?, ?);";

		try {
			db.prepareStatement(sqlInsert);
			db.setString(1, jogos.getNome());
			db.setString(2, jogos.getGenero());
			db.setString(3, jogos.getPlataforma());
			db.setInt(4, jogos.getAno());
			db.setDouble(5, jogos.getPreco());
			db.setString(6, jogos.getDesenvolvedora());
			db.setInt(7, jogos.getUsuario().getId());

			return db.executeUpdate() > 0;
		} catch (ModelException e) {
			e.printStackTrace();
			throw new ModelException("Erro ao salvar o jogo no banco de dados.", e);
		}
	}

	@Override
	public boolean update(Jogos jogos) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlUpdate = "UPDATE jogos SET nome = ?, genero = ?, plataforma = ?, ano = ?, preco = ?, desenvolvedora = ?, usuario = ? WHERE id = ?;";

		try {
			db.prepareStatement(sqlUpdate);
			db.setString(1, jogos.getNome());
			db.setString(2, jogos.getGenero());
			db.setString(3, jogos.getPlataforma());
			db.setInt(4, jogos.getAno());
			db.setDouble(5, jogos.getPreco());
			db.setString(6, jogos.getDesenvolvedora());
			db.setInt(7, jogos.getUsuario().getId());
			db.setInt(8, jogos.getId());

			return db.executeUpdate() > 0;
		} catch (ModelException e) {
			e.printStackTrace();
			throw new ModelException("Erro ao atualizar o jogo no banco de dados.", e);
		}
	}

	@Override
	public boolean delete(Jogos jogos) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlDelete = "DELETE FROM jogos WHERE id = ?;";

		try {
			db.prepareStatement(sqlDelete);
			db.setInt(1, jogos.getId());

			return db.executeUpdate() > 0;
		} catch (ModelException e) {
			e.printStackTrace();
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ModelException("Não foi possível excluir o jogo devido a dados relacionados.", e);
			}
			throw new ModelException("Erro ao excluir o jogo do banco de dados.", e);
		}
	}

	@Override
	public List<Jogos> listAll() throws ModelException {
		DBHandler db = new DBHandler();
		List<Jogos> listaDeJogos = new ArrayList<>();

		// CORREÇÃO AQUI: Removido 'u.nome AS usuario_nome, u.email AS usuario_email,
		// u.genero AS usuario_genero'
		String sqlQuery = "SELECT j.* FROM jogos j INNER JOIN users u ON j.usuario = u.id ORDER BY j.nome;";

		try {
			db.createStatement();
			db.executeQuery(sqlQuery);

			while (db.next()) {
				listaDeJogos.add(createJogos(db));
			}
		} catch (ModelException e) {
			e.printStackTrace();
			throw new ModelException("Erro ao listar jogos do banco de dados.", e);
		}
		return listaDeJogos;
	}

	@Override
	public Jogos findById(int id) throws ModelException {
		DBHandler db = new DBHandler();
		Jogos jogos = null;

		// CORREÇÃO AQUI: Removido 'u.nome AS usuario_nome, u.email AS usuario_email,
		// u.genero AS usuario_genero'
		String sqlQuery = "SELECT j.* FROM jogos j INNER JOIN users u ON j.usuario = u.id WHERE j.id = ?;";

		try {
			db.prepareStatement(sqlQuery);
			db.setInt(1, id);
			db.executeQuery();

			if (db.next()) {
				jogos = createJogos(db);
			}
		} catch (ModelException e) {
			e.printStackTrace();
			throw new ModelException("Erro ao buscar jogo por ID no banco de dados.", e);
		}
		return jogos;
	}

	private Jogos createJogos(DBHandler db) throws ModelException {
		Jogos jogos = new Jogos(db.getInt("id"));
		jogos.setNome(db.getString("nome"));
		jogos.setGenero(db.getString("genero"));
		jogos.setPlataforma(db.getString("plataforma"));
		jogos.setAno(db.getInt("ano"));
		jogos.setPreco(db.getDouble("preco"));
		jogos.setDesenvolvedora(db.getString("desenvolvedora"));

		UserDAO userDAO = DAOFactory.createDAO(UserDAO.class);
		// Esta linha é a responsável por carregar o objeto User completo
		User usuario = userDAO.findById(db.getInt("usuario"));
		jogos.setUsuario(usuario);

		return jogos;
	}
}