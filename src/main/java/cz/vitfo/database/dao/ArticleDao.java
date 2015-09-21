package cz.vitfo.database.dao;

import java.util.List;

import cz.vitfo.database.model.Article;
import cz.vitfo.database.model.Category;

public interface ArticleDao {

	public void saveTextAsArticle(String text, Category category);
	public void updateArticle(Article article);
	public List<Article> getAllArticles();
	public List<Article> getAllArticlesInCategory(Category category);
	public Article getArticle(int id);
}
