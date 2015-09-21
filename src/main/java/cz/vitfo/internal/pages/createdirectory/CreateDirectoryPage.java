package cz.vitfo.internal.pages.createdirectory;

import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import cz.vitfo.database.daoimpl.CategoryDaoImpl;
import cz.vitfo.database.daoimpl.DirectoryDaoImpl;
import cz.vitfo.database.model.Category;
import cz.vitfo.internal.pages.InternalBasePage;
import cz.vitfo.internal.pages.editwithmodal.EditPage;

public class CreateDirectoryPage extends InternalBasePage {
	
	private static final long serialVersionUID = 1088502493140163118L;
	
	private String directory;
	private DropDownChoice<Category> categoryDDCH;
	private Category selectedCategory = null;
	
	public CreateDirectoryPage() {
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		add(feedback);
		
		Form form = new Form("form") {
			@Override
			protected void onSubmit() {
				DirectoryDaoImpl dao = new DirectoryDaoImpl();
				dao.saveDirectory(directory, (selectedCategory != null) ? selectedCategory.getId() : null);
				setResponsePage(EditPage.class);
			}
		};
		add(form);
		
		IModel<List<Category>> categoryModel = new AbstractReadOnlyModel<List<Category>>() {
			@Override
			public List<Category> getObject() {
				CategoryDaoImpl dao = new CategoryDaoImpl();
				return dao.getAllCategories();
			}
		};
		
		// shows Category "name" property
		ChoiceRenderer choiceRenderer = new ChoiceRenderer("name");
		categoryDDCH = new DropDownChoice<Category>("select", new PropertyModel(this, "selectedCategory"), categoryModel, choiceRenderer);
		form.add(categoryDDCH);
		
		TextField<String> directoryTF = new TextField<String>("directory", new PropertyModel<String>(this, "directory"));
		directoryTF.setRequired(true);
		form.add(directoryTF);
		
		Button submitBT = new Button("submit");
		form.add(submitBT);
	}
	
	@Override
	protected IModel<String> getSubTitle() {
		return new ResourceModel("submenu.createDirectoryPage");
	}
}
