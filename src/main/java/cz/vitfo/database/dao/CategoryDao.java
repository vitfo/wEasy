package cz.vitfo.database.dao;

import java.util.List;

import cz.vitfo.database.model.Category;
import cz.vitfo.database.model.CategoryWithArticles;

public interface CategoryDao {

	public void saveCategory(String name);
	public List<Category> getAllCategories();
	public Category getCategoryById(long id);
	
	public List<CategoryWithArticles> getAllCategoriesWithArticles();
}
