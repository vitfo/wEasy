package cz.vitfo.base;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import cz.vitfo.database.daoimpl.UserDaoImpl;
import cz.vitfo.database.model.User;

/**
 * Authentication session for the application.
 * 
 * @author vitfo
 */
public class BasicAutorizationAndAuthenticationSession extends AuthenticatedWebSession {

	private static final long serialVersionUID = -2815836672309139503L;
	
	private String email;
	private Integer userId;
	private String username;

	public BasicAutorizationAndAuthenticationSession(Request request) {
		super(request);
	}

	// TODO vitfo, inserted at 10. 9. 2015 10:34:26 - very simple authentication, should be improved
	@Override
	public boolean authenticate(String email, String password) {
		UserDaoImpl dao = new UserDaoImpl();
		User user = dao.getUser(email);
		if (
				user != null &&
				user.getEmail() != null &&
				user.getPassword() != null &&
				user.getEmail().equals(email) && 
				user.getPassword().equals(password)) {
			
			this.userId = user.getId();
			this.username = user.getUsername();
			this.email = user.getEmail();
			return true;
		}
		return false;
	}

	@Override
	public Roles getRoles() {
		Roles roles = new Roles();
		
		if (isSignedIn()) {
			roles.add(Roles.USER);
			
			if (email.equals("admin@admin.admin")) {
				roles.add(Roles.ADMIN);
			}
		}
		
		return roles;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	public String getUsername() {
		return this.username;
	}
}
