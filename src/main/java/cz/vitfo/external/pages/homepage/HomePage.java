package cz.vitfo.external.pages.homepage;

import java.util.List;

import org.apache.wicket.markup.repeater.RepeatingView;

import cz.vitfo.database.daoimpl.CategoryDaoImpl;
import cz.vitfo.database.daoimpl.DaoImpl;
import cz.vitfo.database.model.CategoryWithArticles;
import cz.vitfo.external.pages.ExternalBasePage;
import cz.vitfo.external.panels.BasePanel;
import cz.vitfo.external.panels.BasePanelModel;

public class HomePage extends ExternalBasePage {
	
	private static CategoryDaoImpl dao = new CategoryDaoImpl();
	
	public HomePage() {
		// get all categories with articles
		List<CategoryWithArticles> categoriesWithArticles = dao.getAllCategoriesWithArticles();
		
		// creates repeating view
		RepeatingView rv = new RepeatingView("basePanelRepeater");
		
		// for each category there will be one panel, each panel has its own model that contains list of articles
		for (final CategoryWithArticles cwa : categoriesWithArticles) {
			rv.add(new BasePanel(rv.newChildId(), new BasePanelModel(cwa.getArticles())) {
				
				@Override
				protected String getPanelTitle() {
					return cwa.getCategory().getName();
				}
			});
		}
		add(rv);
	}
	
	@Override
	protected String getTitle() {
		return getString("homepage.title");
	}


}
