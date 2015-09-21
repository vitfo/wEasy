package cz.vitfo.external.panels;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;

import cz.vitfo.database.model.Article;

/**
 * Panel that contains all links for articles from one category.
 * @author User
 *
 */
public abstract class BasePanel extends Panel {

	private static final long serialVersionUID = 3513797842860694427L;

	public BasePanel(String id, IModel<List<Article>> model) {
		super(id, model);
		add(new Label("panelTitle", getPanelTitle()));
		
		// create panel for each link to article in category
		RepeatingView rv = new RepeatingView("baseLinkPanelRepeater");
		for (final Article ar : model.getObject()) {
			rv.add(new BaseLinkPanel(rv.newChildId(), new BaseLinkPanelModel(ar)) {});
		};
		add(rv);
	}
	
	/**
	 * Gets the title of the panel.
	 * @return title of the panel
	 */
	protected abstract String getPanelTitle();
}
