package cz.vitfo.internal.pages;

import org.apache.wicket.Application;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import cz.vitfo.base.BasePage;
import cz.vitfo.internal.menus.AdministrationMenu;

/**
 * Base page for internal (authentificated) pages.
 * 
 * @author vitfo
 */
public abstract class InternalBasePage extends BasePage {
	
	private static final long serialVersionUID = -3658285920511775186L;

	public InternalBasePage() {
		add(new AdministrationMenu("editMenu"));
		
		add(new Label("subtitle", getSubTitle()));
	}

	@Override
	protected void onConfigure() {
		super.onConfigure();
		
		AuthenticatedWebApplication application = (AuthenticatedWebApplication) Application.get();
        AuthenticatedWebSession session = AuthenticatedWebSession.get();
        if (!session.isSignedIn()) {
                application.restartResponseAtSignInPage();
        }
	}
	
	@Override
	protected String getTitle() {
		return getString("administration.title");
	}
	
	/**
	 * Gets the subtitle for the page.
	 * 
	 * @return subtitle
	 */
	protected abstract IModel<String> getSubTitle();
}
