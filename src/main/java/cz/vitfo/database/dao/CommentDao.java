package cz.vitfo.database.dao;

import java.util.List;

import cz.vitfo.database.model.Comment;

public interface CommentDao {

	public void saveComment(Comment comment);
	public List<Comment> getAllCommentsForArticle(int articleId);
}
