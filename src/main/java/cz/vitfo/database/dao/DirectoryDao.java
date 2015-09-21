package cz.vitfo.database.dao;

import java.util.List;

import cz.vitfo.database.model.Directory;

public interface DirectoryDao {

	public void saveDirectory(String name, Integer categoryId);
	public List<Directory> getAllDirectories();
	public List<Directory> getAllDirectoriesForCategory(int categoryId);
}
