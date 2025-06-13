package model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Called;
import model.ModelException;
import model.User;

public class MySQLCalledDAO implements CalledDAO {

	@Override
	public boolean save(Called called) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlInsert = "INSERT INTO calleds VALUES (DEFAULT, ?, ?, ?, ?, ?);";

		db.prepareStatement(sqlInsert);

		db.setString(1, called.getTitulo());
		db.setString(2, called.getDescricao());
		db.setDate(3, called.getDataAbertura() == null ? new Date() : called.getDataAbertura());
		db.setString(4, called.getStatus());
		db.setInt(5, called.getUser().getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Called called) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlUpdate = "UPDATE calleds " + " SET titulo = ?, " + " descricao = ?, " + " data_abertura = ?, "
				+ " status = ?, " + " user_id = ? " + " WHERE id = ?; ";

		db.prepareStatement(sqlUpdate);

		db.setString(1, called.getTitulo());
		db.setString(2, called.getDescricao());
		db.setDate(3, called.getDataAbertura() == null ? new Date() : called.getDataAbertura());
		db.setString(4, called.getStatus());
		db.setInt(5, called.getUser().getId());
		db.setInt(6, called.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Called called) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlDelete = " DELETE FROM calleds " + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);
		db.setInt(1, called.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public List<Called> listAll() throws ModelException {
		DBHandler db = new DBHandler();

		List<Called> calleds = new ArrayList<Called>();

		// Declara uma instrução SQL
		String sqlQuery = " SELECT c.id as 'called_id', c.*, u.* \n" + " FROM calleds c \n" + " INNER JOIN users u \n"
				+ " ON c.user_id = u.id;";

		db.createStatement();

		db.executeQuery(sqlQuery);

		while (db.next()) {
			User user = new User(db.getInt("user_id"));
			user.setName(db.getString("nome"));
			user.setGender(db.getString("sexo"));
			user.setEmail(db.getString("email"));

			Called called = new Called(db.getInt("called_id"));
			called.setTitulo(db.getString("titulo"));
			called.setDescricao(db.getString("descricao"));
			called.setDataAbertura(db.getDate("data_abertura"));
			called.setStatus(db.getString("status"));
			called.setUser(user);

			calleds.add(called);
		}

		return calleds;
	}

	@Override
	public Called findById(int id) throws ModelException {
		DBHandler db = new DBHandler();

		String sql = "SELECT * FROM calleds WHERE id = ?;";

		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();

		Called c = null;
		while (db.next()) {
			c = new Called(id);
			c.setTitulo(db.getString("titulo"));
			c.setDescricao(db.getString("descricao"));
			c.setDataAbertura(db.getDate("data_abertura"));
			c.setStatus(db.getString("status"));

			UserDAO userDAO = DAOFactory.createDAO(UserDAO.class);
			User user = userDAO.findById(db.getInt("user_id"));
			c.setUser(user);

			break;
		}

		return c;
	}
}
