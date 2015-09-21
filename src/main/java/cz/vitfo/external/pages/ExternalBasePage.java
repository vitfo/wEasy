package cz.vitfo.external.pages;

import org.apache.wicket.model.IModel;

import cz.vitfo.base.BasePage;

/**
 * Base page for external (not authentificated) pages.
 * 
 * @author vitfo
 */
public abstract class ExternalBasePage extends BasePage {
	
	private static final long serialVersionUID = -5600527754889138283L;
	
	public ExternalBasePage() {}
	
	public ExternalBasePage(IModel model) {
		super(model);
	}

}
