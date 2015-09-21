package cz.vitfo.database.daoimpl;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cz.vitfo.database.dao.ImageDao;
import cz.vitfo.database.model.Category;
import cz.vitfo.database.model.Directory;
import cz.vitfo.database.model.Image;

public class ImageDaoImpl extends DaoImpl implements ImageDao {

	@Override
	public List<Image> getAllImages() {
		List<Image> images = new ArrayList<>();
		try (Connection con = dataSource.getConnection()) {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select id, directory_id, name, data from " + TableEnum.T_IMAGE);
			while (rs.next()) {
				Image img = new Image();
				img.setId(rs.getInt("id"));
				img.setDirectoryId((Integer) rs.getObject("directory_id"));
				img.setFileName(rs.getString("name"));
				img.setBytes(rs.getBytes("data"));
				images.add(img);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return images;
	}
	
	@Override
	public List<Image> getAllImagesInCategory(Category category) {
		List<Image> images = new ArrayList<>();
		try (Connection con = dataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select id, directory_id, name, data from " + TableEnum.T_IMAGE + " where category_id = ? order by saved DESC");
			ps.setObject(1, (category != null) ? category.getId() : null);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Image img = new Image();
				img.setId(rs.getInt("id"));
				img.setDirectoryId((Integer) rs.getObject("directory_id"));
				img.setFileName(rs.getString("name"));
				img.setBytes(rs.getBytes("data"));
				images.add(img);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return images;
	}

	@Override
	public Image getImageById(long id) {
		Image img = null;
		try (Connection con = dataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select id, directory_id, name, data from " + TableEnum.T_IMAGE + " where id = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				img = new Image();
				img.setId(rs.getInt("id"));
				img.setFileName(rs.getString("name"));
				img.setBytes(rs.getBytes("data"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	@Override
	public List<Image> getAllImagesInDirectory(Directory directory) {
		List<Image> images = new ArrayList<>();
		try (Connection con = dataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select id, directory_id, name, data from " + TableEnum.T_IMAGE + " where directory_id = ? order by saved DESC");
			ps.setObject(1, directory.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Image img = new Image();
				img.setId(rs.getInt("id"));
				img.setDirectoryId((Integer) rs.getObject("directory_id")); 
				img.setFileName(rs.getString("name"));
				img.setBytes(rs.getBytes("data"));
				images.add(img);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return images;
	}
	
	@Override
	public void saveImageFile(Image uploadedImageFile) {
		try (Connection con = dataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("insert into " + TableEnum.T_IMAGE.name() + " (directory_id, saved, name, data) values (?, current_timestamp, ?, ?)");
			ps.setObject(1, uploadedImageFile.getDirectoryId());
			ps.setString(2, uploadedImageFile.getFileName());
			ps.setBinaryStream(3, new ByteArrayInputStream(uploadedImageFile.getBytes()), uploadedImageFile.getBytes().length);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
