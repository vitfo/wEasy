package cz.vitfo.database.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cz.vitfo.database.dao.DirectoryDao;
import cz.vitfo.database.model.Directory;

public class DirectoryDaoImpl extends DaoImpl implements DirectoryDao {

	@Override
	public void saveDirectory(String name, Integer categoryId) {
		try (Connection con = dataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("insert into " + TableEnum.T_DIRECTORY + " (name, category_id) values (?, ?)");
			ps.setString(1, name);
			ps.setObject(2, categoryId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Directory> getAllDirectories() {
		List<Directory> directories = new ArrayList<>();
		try (Connection con = dataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select id, category_id, name from " + TableEnum.T_DIRECTORY + " order by id ASC");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Directory dir = new Directory();
				dir.setCategoryId((Integer) rs.getObject("category_id"));
				dir.setId(rs.getInt("id"));
				dir.setName(rs.getString("name"));
				directories.add(dir);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return directories;
	}

	@Override
	public List<Directory> getAllDirectoriesForCategory(int categoryId) {
		List<Directory> directories = new ArrayList<>();
		try (Connection con = dataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select id, category_id, name from " + TableEnum.T_DIRECTORY + " where category_id = ? order by id ASC");
			ps.setInt(1, categoryId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Directory dir = new Directory();
				dir.setCategoryId((Integer) rs.getObject("category_id"));
				dir.setId(rs.getInt("id"));
				dir.setName(rs.getString("name"));
				directories.add(dir);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return directories;
	}
}
