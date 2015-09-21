package cz.vitfo.external.pages.loginpage;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.string.Strings;

import cz.vitfo.external.pages.ExternalBasePage;
import cz.vitfo.external.pages.homepage.HomePage;
import cz.vitfo.external.pages.registerpage.RegisterPage;
import cz.vitfo.internal.pages.editwithmodal.EditPage;

public class LoginPage extends ExternalBasePage {
	
    private String email;
	private String password;
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(new Link("register") {
			@Override
			public void onClick() {
				setResponsePage(RegisterPage.class);
			}
		});
		
		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		add(feedback);
		
		StatelessForm form = new StatelessForm("loginForm") {
			@Override
			protected void onSubmit() {
				if (Strings.isEmpty(email)) {
					return;
				}
				boolean authResult = AuthenticatedWebSession.get().signIn(email, password);
				if (authResult) {
					setResponsePage(HomePage.class);
				} else {
					error(getString("form.login.failed"));
				}
			}
		};
		form.setDefaultModel(new CompoundPropertyModel(this));
		add(form);
		
		form.add(new TextField<String>("email").setRequired(true));
		form.add(new PasswordTextField("password"));
		
		SubmitLink submit = new SubmitLink("submit", form);
		form.add(submit);
	}

	@Override
	protected String getTitle() {
		return getString("loginpage.title");
	}

}
