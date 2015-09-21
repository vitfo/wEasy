package cz.vitfo.internal.pages.createcategory;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import cz.vitfo.database.daoimpl.CategoryDaoImpl;
import cz.vitfo.internal.pages.InternalBasePage;
import cz.vitfo.internal.pages.editwithmodal.EditPage;

public class CreateCategoryPage extends InternalBasePage {
	
	private String category;
	
	public CreateCategoryPage() {
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		add(feedback);
		
		Form form = new Form("form") {
			@Override
			protected void onSubmit() {
				CategoryDaoImpl dao = new CategoryDaoImpl();
				dao.saveCategory(category);
				setResponsePage(EditPage.class);
			}
		};
		add(form);
		
		TextField categoryTF = new TextField("category", new PropertyModel(this, "category"));
		categoryTF.setRequired(true);
		form.add(categoryTF);
		
		Button submitBT = new Button("submit");
		form.add(submitBT);
	}
	
	@Override
	protected IModel<String> getSubTitle() {
		return new ResourceModel("submenu.createCategoryPage");
	}
}
