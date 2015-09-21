package cz.vitfo.database.daoimpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cz.vitfo.database.dao.ArticleDao;
import cz.vitfo.database.model.Article;
import cz.vitfo.database.model.Category;
import cz.vitfo.database.model.CategoryWithArticles;

public class ArticleDaoImpl extends DaoImpl implements ArticleDao {

	@Override
	public void saveTextAsArticle(String text, Category category) {
		if (text == null) {
			throw new IllegalArgumentException("Argument [text] can not be null.");
		}
		if (category == null) {
			throw new IllegalArgumentException("Argument [category] can not be null.");
		}
		try (Connection con = dataSource.getConnection()) {
			String header = "";
			// in text there are <>
			if (text.contains("<")) {
				header = text.substring(0, text.indexOf("</"));
				header = header.substring(header.indexOf(">") + 1);
			} else if (text.contains("&lt;")) {
				header = text.substring(0, text.indexOf("&lt;/"));
				header = header.substring(header.indexOf("&gt;") + 1);
			}
			
			if (header.length() > 100) {
				header = header.substring(0, 100);
			}
			
			PreparedStatement ps = con.prepareStatement("insert into " + TableEnum.T_ARTICLE + " (saved, category_id, header, text) values (?, ?, ?, ?)");
			ps.setDate(1, new Date(System.currentTimeMillis()));
			ps.setLong(2, category.getId());
			ps.setString(3, header);
			ps.setString(4, text);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Article> getAllArticles() {
		List<Article> articles = new ArrayList<>();
		
		try (Connection con = dataSource.getConnection()) {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select id, category_id, saved, header, text from " + TableEnum.T_ARTICLE);
			while (rs.next()) {
				Article ar = new Article();
				ar.setId(rs.getInt("id"));
				ar.setCategoryId((Integer) rs.getObject("category_id"));
				ar.setSaved(rs.getTimestamp("saved"));
				ar.setHeader(rs.getString("header"));
				ar.setText(rs.getString("text"));
				articles.add(ar);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articles;
	}

	@Override
	public Article getArticle(int id) {
		Article ar = new Article();
		
		try (Connection con = dataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select id, category_id, saved, header, text from " + TableEnum.T_ARTICLE + " where id = ?");
			ps.setInt(1, id);
			ps.setMaxRows(1);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ar.setId(rs.getInt("id"));
				ar.setCategoryId((Integer) rs.getObject("category_id"));
				ar.setSaved(rs.getTimestamp("saved"));
				ar.setHeader(rs.getString("header"));
				ar.setText(rs.getString("text"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ar;
	}
	
	@Override
	public List<Article> getAllArticlesInCategory(Category category) {
		List<Article> articles = new ArrayList<>();
		try (Connection con = dataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select id, saved, category_id, header, text from " + TableEnum.T_ARTICLE + " where category_id = ?");
			ps.setInt(1, category.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Article ar = new Article();
				ar.setId(rs.getInt("id"));
				ar.setSaved(rs.getTimestamp("saved"));
				ar.setCategory(new CategoryDaoImpl().getCategoryById(rs.getInt("category_id")));
				ar.setHeader(rs.getString("header"));
				ar.setText(rs.getString("text"));
				articles.add(ar);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articles;
	}
	
	
	
	@Override
	public void updateArticle(Article article) {
		try (Connection con = dataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("update " + TableEnum.T_ARTICLE + " set category_id = ?, text = ? where id = ?");
			ps.setInt(1, article.getCategoryId());
			ps.setString(2, article.getText());
			ps.setInt(3, article.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
