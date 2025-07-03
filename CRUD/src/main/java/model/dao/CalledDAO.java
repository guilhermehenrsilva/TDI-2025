package model.dao;

import java.util.List;

import model.Called;
import model.ModelException;

public interface CalledDAO {
	boolean save(Called called) throws ModelException;

	boolean update(Called called) throws ModelException;

	boolean delete(Called called) throws ModelException;

	List<Called> listAll() throws ModelException;

	Called findById(int id) throws ModelException;
}
