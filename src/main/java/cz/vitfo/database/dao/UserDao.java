package cz.vitfo.database.dao;

import cz.vitfo.database.model.User;

public interface UserDao {

	public void saveUser(User user);
	public User getUser(String email);
}
