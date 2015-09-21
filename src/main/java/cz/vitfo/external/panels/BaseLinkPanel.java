package cz.vitfo.external.panels;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import cz.vitfo.database.model.Article;
import cz.vitfo.external.pages.articlepage.ArticlePage;

/**
 * Panel that contains link.
 * @author User
 *
 */
public abstract class BaseLinkPanel extends Panel {
	
	public BaseLinkPanel(String id, final IModel<Article> model) {
		super(id, model);
		
		Link link = new Link("link") {
			@Override
			public void onClick() {
				// create page parameter so the correct article will be loaded when clicked on link
				PageParameters pp = new PageParameters();
				pp.set(0, model.getObject().getId());
				setResponsePage(ArticlePage.class, pp);
			}
		};
		add(link.add(new Label("linkLabel", new Model(model.getObject().getHeader()))));
	}

}
