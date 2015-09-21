package cz.vitfo.database.model;

import java.io.Serializable;
import java.util.List;

public class CategoryWithArticles implements Serializable {

	private static final long serialVersionUID = 3060015771689540302L;
	
	private Category category;
	private List<Article> articles;
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public List<Article> getArticles() {
		return articles;
	}
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
}
