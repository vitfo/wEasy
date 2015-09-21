package cz.vitfo.database.daoimpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cz.vitfo.database.dao.CommentDao;
import cz.vitfo.database.model.Comment;

public class CommentDaoImpl extends DaoImpl implements CommentDao {

	@Override
	public List<Comment> getAllCommentsForArticle(int articleId) {
		List<Comment> comments = new ArrayList<>();
		try (Connection con = dataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select id, text, created, article_id, user_id from " + TableEnum.T_COMMENT + " where article_id = ? order by created DESC");
			ps.setInt(1, articleId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Comment c = new Comment();
				c.setId(rs.getInt("id"));
				c.setText(rs.getString("text"));
				c.setCreated(rs.getDate("created"));
				c.setArticleId(rs.getInt("article_id"));
				c.setUserId(rs.getInt("user_id"));
				comments.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comments;
	}
	@Override
	public void saveComment(Comment comment) {
		try (Connection con = dataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement("insert into " + TableEnum.T_COMMENT + " (text, created, article_id, user_id) values (?, ?, ?, ?)");
			ps.setString(1, comment.getText());
			ps.setDate(2, new Date(System.currentTimeMillis()));
			ps.setInt(3, comment.getArticleId());
			ps.setInt(4, comment.getUserId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
