package cz.vitfo.external.pages.articlepage;

import org.apache.wicket.model.IModel;

import cz.vitfo.database.daoimpl.ArticleDaoImpl;

/**
 * Model containing article.
 * The model needs id of the article to fetch it from datasource.
 * @author User
 *
 */
public class ArticleModel implements IModel<String> {

	private static final long serialVersionUID = -1514779344427109259L;
	private static ArticleDaoImpl dao = new ArticleDaoImpl();
	
	private int articleId;
	
	public ArticleModel(int articleId) {
		this.articleId = articleId;
	}

	@Override
	public void detach() {}

	@Override
	public String getObject() {
		return dao.getArticle(articleId).getText();
	}

	@Override
	public void setObject(String object) {}

}
