package cz.vitfo.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.resource.SharedResourceReference;

import cz.vitfo.database.daoimpl.TrackingDaoImpl;
import cz.vitfo.database.model.TrackInfo;
import cz.vitfo.external.menus.TopMenuPanel;

/**
 * Base class for all pages.
 * 
 * @author vitfo
 */
public abstract class BasePage extends WebPage {

	private static final long serialVersionUID = 4809590366914866734L;

	public BasePage() {
		init();
	}

	public BasePage(IModel model) {
		super(model);
		init();
	}

	/**
	 * Method for initializing BasePage. 
	 * This method does not override org.apache.wicket.Page#init().
	 */
	private void init() {
		// Sets the page title
		add(new Label("title", getTitle()));
		
		// Calls method to save data about user.
		// If you do not want to track the activity, comment the line.
		trackUser();
		
		// add top menu panel
		add(new TopMenuPanel("topMenu"));
	}

	/**
	 * Gets the title of the page.
	 * 
	 * @return - title
	 */
	protected abstract String getTitle();

	/**
	 * Tracks users activity. The activity should be saved to the database.
	 */
	protected void trackUser() {
		String ip, url, session;

		WebRequest req = (WebRequest) RequestCycle.get().getRequest();
		HttpServletRequest httpReq = (HttpServletRequest) req.getContainerRequest();
		ip = httpReq.getRemoteHost();
		url = httpReq.getRequestURL().toString();
		session = httpReq.getRequestedSessionId();

		TrackInfo info = new TrackInfo(ip, url, session);

		TrackingDaoImpl trackingDao = new TrackingDaoImpl();
		trackingDao.save(info);
	}
}
