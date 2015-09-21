package cz.vitfo.external.panels;

import org.apache.wicket.model.IModel;

import cz.vitfo.database.model.Article;

public class BaseLinkPanelModel implements IModel<Article> {

	private static final long serialVersionUID = 7667463351383476220L;

	private Article article;
	
	public BaseLinkPanelModel(Article article) {
		this.article = article;
	}

	@Override
	public void detach() {}

	@Override
	public Article getObject() {
		return article;
	}

	@Override
	public void setObject(Article object) {}

	
}
