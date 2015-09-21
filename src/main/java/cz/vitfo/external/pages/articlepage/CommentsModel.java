package cz.vitfo.external.pages.articlepage;

import java.util.List;

import org.apache.wicket.model.IModel;

import cz.vitfo.database.daoimpl.CommentDaoImpl;
import cz.vitfo.database.model.Comment;

/**
 * Model containing comments for the article.
 * The model needs id of the article.
 * 
 * @author vitfo
 */
public class CommentsModel implements IModel<List<Comment>> {

	private static final long serialVersionUID = -1514779344427172259L;
	private static CommentDaoImpl dao = new CommentDaoImpl();
	
	private int articleId;
	
	public CommentsModel(int articleId) {
		this.articleId = articleId;
	}

	@Override
	public void detach() {}

	@Override
	public List<Comment> getObject() {
		return dao.getAllCommentsForArticle(articleId);
	}

	@Override
	public void setObject(List<Comment> object) {}

}
