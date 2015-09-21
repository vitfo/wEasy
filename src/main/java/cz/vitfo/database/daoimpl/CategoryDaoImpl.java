package cz.vitfo.database.daoimpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cz.vitfo.database.dao.CategoryDao;
import cz.vitfo.database.model.Category;
import cz.vitfo.database.model.CategoryWithArticles;
import cz.vitfo.database.model.Comment;

public class CategoryDaoImpl extends DaoImpl implements CategoryDao {

	@Override
	public List<Category> getAllCategories() {
		List<Category> categories = new ArrayList<>();
		try (Connection con = dataSource.getConnection()) {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select id, name from " + TableEnum.T_CATEGORY);
			while (rs.next()) {
				categories.add(new Category(rs.getInt("id"), rs.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}
	
	@Override
	public Category getCategoryById(long id) {
		Category category = null;
		try (Connection con = dataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select id, name from " + TableEnum.T_CATEGORY + " where id = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				category = new Category();
				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return category;
	}

	

	@Override
	public void saveCategory(String name) {
		try (Connection con = dataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("insert into " + TableEnum.T_CATEGORY + " (name) values (?)");
			ps.setString(1, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<CategoryWithArticles> getAllCategoriesWithArticles() {
		List<CategoryWithArticles> categoriesWithArticles = new ArrayList<>();
		List<Category> categories = getAllCategories();
		
		for (Category category : categories) {
			CategoryWithArticles cwa = new CategoryWithArticles();
			cwa.setCategory(category);
			cwa.setArticles(new ArticleDaoImpl().getAllArticlesInCategory(category));
			categoriesWithArticles.add(cwa);
		}
		
		return categoriesWithArticles;
	}
}
