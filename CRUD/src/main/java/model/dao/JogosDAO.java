package model.dao;

import java.util.List;

import model.Jogos;
import model.ModelException;

public interface JogosDAO {
	boolean save(Jogos jogos) throws ModelException;

	boolean update(Jogos jogos) throws ModelException;

	boolean delete(Jogos jogos) throws ModelException;

	List<Jogos> listAll() throws ModelException;

	Jogos findById(int id) throws ModelException;
}