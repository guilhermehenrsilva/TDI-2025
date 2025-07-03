package model.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.User;

public class MySQLUserDAO implements UserDAO {

	@Override
	public boolean save(User user) throws ModelException {

		DBHandler db = new DBHandler();

		String sqlInsert = "INSERT INTO users VALUES " + " (DEFAULT, ?, ?, ?);";

		db.prepareStatement(sqlInsert);
		db.setString(1, user.getName());
		db.setString(2, user.getGender());
		db.setString(3, user.getEmail());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(User user) throws ModelException {

		DBHandler db = new DBHandler();

		// ATENÇÃO: Você precisará adicionar a 'senha' aqui no UPDATE se ela for
		// atualizável
		String sqlUpdate = "UPDATE users " + "SET nome = ?, " + "sexo = ?, " + "email = ? " + "WHERE id = ?";

		db.prepareStatement(sqlUpdate);

		db.setString(1, user.getName());
		db.setString(2, user.getGender());
		db.setString(3, user.getEmail());
		// Se for incluir senha no update, adicione db.setString(4, user.getSenha());
		db.setInt(4, user.getId()); // Se adicionar senha, este deve ser db.setInt(5, user.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(User user) throws ModelException {

		DBHandler db = new DBHandler();

		String sqlDelete = " DELETE FROM users " + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);
		db.setInt(1, user.getId());

		try {

			return db.executeUpdate() > 0;

		} catch (ModelException e) {
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				return false;
			}

			throw e;
		}
	}

	@Override
	public List<User> listAll() throws ModelException {

		DBHandler db = new DBHandler();

		List<User> users = new ArrayList<User>();

		String sqlQuery = "SELECT * FROM users";

		db.createStatement();

		db.executeQuery(sqlQuery);

		while (db.next()) {
			User u = createUser(db);
			users.add(u);
		}

		return users;
	}

	@Override
	public User findById(int id) throws ModelException {

		DBHandler db = new DBHandler();

		String sql = "SELECT * FROM users WHERE id = ?";

		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();

		User u = null;
		while (db.next()) {
			u = createUser(db);
			break;
		}

		return u;
	}

	// Certifique-se que o createUser leia a senha também
	private User createUser(DBHandler db) throws ModelException {
		User u = new User(db.getInt("id"));
		u.setName(db.getString("nome"));
		u.setGender(db.getString("sexo"));
		u.setEmail(db.getString("email"));
		u.setSenha(db.getString("senha")); // Lendo a senha do banco de dados
		return u;
	}

	@Override
	public User findByEmail(String email) throws ModelException { // Este método é essencial para o login
		DBHandler db = new DBHandler();

		String sql = "SELECT * FROM users WHERE email = ?";

		db.prepareStatement(sql);
		db.setString(1, email);
		db.executeQuery();

		User u = null;
		while (db.next()) {
			u = createUser(db);
			break;
		}

		return u;
	}
}