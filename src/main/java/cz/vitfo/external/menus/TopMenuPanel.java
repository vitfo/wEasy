package cz.vitfo.external.menus;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import cz.vitfo.external.pages.homepage.HomePage;
import cz.vitfo.external.pages.loginpage.LoginPage;
import cz.vitfo.internal.pages.editwithmodal.EditPage;

public class TopMenuPanel extends Panel {

	public TopMenuPanel(String id) {
		super(id);

		add(new BookmarkablePageLink("homepage", HomePage.class));
		
		Link loginLink = new Link("login") {
			@Override
			public void onClick() {
				setResponsePage(LoginPage.class);
			}

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisible(!AuthenticatedWebSession.get().isSignedIn());
			}
		};
		add(loginLink);
		
		// Administration link.
		Link administrationLink = new Link("administration") {
			@Override
			public void onClick() {
				setResponsePage(EditPage.class);
			}

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisible(AuthenticatedWebSession.get().getRoles().hasRole(Roles.ADMIN));
			}
		};
		add(administrationLink);

		// Log out link.
		Link logoutLink = new Link("logout") {
			@Override
			public void onClick() {
				AuthenticatedWebSession.get().invalidate();
				setResponsePage(HomePage.class);
			}

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisible(AuthenticatedWebSession.get().isSignedIn());
			}
		};
		add(logoutLink);
	}

}
