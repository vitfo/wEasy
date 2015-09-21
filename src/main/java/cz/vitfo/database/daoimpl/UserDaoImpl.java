package cz.vitfo.database.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cz.vitfo.database.dao.UserDao;
import cz.vitfo.database.model.User;

public class UserDaoImpl extends DaoImpl implements UserDao {

	@Override
	public User getUser(String email) {
		User u = new User();
		
		try (Connection con = dataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select id, username, email, password from " + TableEnum.T_USER + " where email = ?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				u.setId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setEmail(rs.getString("email"));
				u.setPassword(rs.getString("password"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}

	@Override
	public void saveUser(User user) {
		try (Connection con = dataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("insert into " + TableEnum.T_USER + " (username, email, password) values (?, ?, ?)");
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
