package cz.vitfo.external.pages.registerpage;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import cz.vitfo.database.daoimpl.DaoImpl;
import cz.vitfo.database.daoimpl.UserDaoImpl;
import cz.vitfo.database.model.User;
import cz.vitfo.external.pages.ExternalBasePage;
import cz.vitfo.external.pages.loginpage.LoginPage;

public class RegisterPage extends ExternalBasePage {
	
	private String username;
	private String email;
	private String password;
	private String passwordCheck;
	
	private AjaxSubmitLink submit;
	
	public RegisterPage() {
		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		add(feedback);
		
		final StatelessForm form = new StatelessForm("registerForm") {
			@Override
			protected void onSubmit() {
				User user = new User();
				user.setUsername(username);
				user.setEmail(email);
				user.setPassword(password);
				
				UserDaoImpl dao = new UserDaoImpl();
				dao.saveUser(user);
				
				setResponsePage(LoginPage.class);
			}
		};
		form.setDefaultModel(new CompoundPropertyModel(this));
		add(form);
		
		form.add(new TextField<String>("username").setRequired(true));
		
		TextField<String> emailTF = new TextField<String>("email");
		emailTF.add(EmailAddressValidator.getInstance());
		emailTF.setRequired(true);
		form.add(emailTF);
		
		form.add(new PasswordTextField("password"));
		form.add(new PasswordTextField("passwordCheck"));
		
		submit = new AjaxSubmitLink("submit", form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (
						password != null &&
						passwordCheck != null &&
						password.equals(passwordCheck)) {
					
					super.onSubmit(target, form);
				} else {
					error(getString("form.notEquals"));
					target.add(feedback);
				}
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				target.add(feedback);
			}
		};
		submit.setOutputMarkupId(true);
		form.add(submit);
	}

	@Override
	protected String getTitle() {
		return getString("registerpage.title");
	}
}
