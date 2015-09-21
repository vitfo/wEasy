package cz.vitfo.internal.pages.editwithmodal;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import cz.vitfo.database.daoimpl.ArticleDaoImpl;
import cz.vitfo.database.daoimpl.DaoImpl;
import cz.vitfo.database.model.Article;
import cz.vitfo.internal.pages.InternalBasePage;

public class EditPage extends InternalBasePage {
	
	private static final long serialVersionUID = -5368136852515244170L;

	/**
	 * Creates {@link EditPage} with no values.
	 * It is used to create new {@link Article}.
	 */
	public EditPage() {
		add(new EditPanel("editPanel"));
	}
	
	/**
	 * Tries to get and parse page parameter and create {@link EditPanel} with article default values.
	 * It is used to edit already created {@link Article}.
	 * 
	 * @param params page parameter with {@link Article} id.
	 */
	public EditPage(PageParameters params) {
		StringValue id= params.get(0);
		try {
			ArticleDaoImpl dao = new ArticleDaoImpl();
			Article article = dao.getArticle(Integer.parseInt(id.toString()));
			add(new EditPanel("editPanel", article));
		} catch (Exception e) {
			add(new EditPanel("editPanel"));
		}
	}

	@Override
	protected IModel<String> getSubTitle() {
		return new ResourceModel("submenu.editPage");
	}
}

