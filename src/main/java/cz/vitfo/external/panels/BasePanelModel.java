package cz.vitfo.external.panels;

import java.util.List;

import org.apache.wicket.model.IModel;

import cz.vitfo.database.model.Article;

public class BasePanelModel implements IModel<List<Article>> {
	
	private static final long serialVersionUID = 7667463351386476200L;
	
	private List<Article> articles;
	
	public BasePanelModel(List<Article> articles) {
		this.articles = articles;
	}

	@Override
	public void detach() {}

	@Override
	public List<Article> getObject() {
		return articles;
	}

	@Override
	public void setObject(List<Article> object) {}
}
