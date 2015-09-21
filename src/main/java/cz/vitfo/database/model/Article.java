package cz.vitfo.database.model;

import java.io.Serializable;
import java.sql.Timestamp;

import cz.vitfo.database.daoimpl.CategoryDaoImpl;
import cz.vitfo.database.daoimpl.DaoImpl;

public class Article implements Serializable {

	private static final long serialVersionUID = 613669306117338055L;
	
	private int id;
	private Integer categoryId;
	private Timestamp saved;
	private Category category;
	private String header;
	private String text;
	
	public Article() {}
	public Article(int id, Timestamp saved, Category category, String header, String text) {
		this.id = id;
		this.saved = saved;
		this.category = category;
		this.header = header;
		this.text = text;
	}
	
	public Timestamp getSaved() {
		return saved;
	}
	public void setSaved(Timestamp saved) {
		this.saved = saved;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Category getCategory() {
		if (this.category != null) {
			return category;
		}
		else {
			//TODO
			CategoryDaoImpl dao = new CategoryDaoImpl();
			this.category = dao.getCategoryById(categoryId);
			return this.category;
		}
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
}
