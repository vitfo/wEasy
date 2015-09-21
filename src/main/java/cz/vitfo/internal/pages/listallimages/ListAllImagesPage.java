package cz.vitfo.internal.pages.listallimages;

import java.util.List;

import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.ByteArrayResource;

import cz.vitfo.database.daoimpl.ImageDaoImpl;
import cz.vitfo.database.model.Image;
import cz.vitfo.internal.pages.InternalBasePage;

public class ListAllImagesPage extends InternalBasePage  {

	private static final long serialVersionUID = -6842730542504808757L;

	public ListAllImagesPage() {
		IModel<List<Image>> pageModel = new AbstractReadOnlyModel<List<Image>>() {
			@Override
			public List<Image> getObject() {
				ImageDaoImpl dao = new ImageDaoImpl();
				return dao.getAllImages();
			}
		}; 
		this.setDefaultModel(pageModel);
		
		RepeatingView rv = new RepeatingView("images");
		for (Image img : (List<Image>) this.getDefaultModel().getObject()) {
			rv.add(new org.apache.wicket.markup.html.image.Image(rv.newChildId(), new ByteArrayResource(null, img.getBytes())));
		}
		add(rv);
	}
	
	@Override
	protected IModel<String> getSubTitle() {
		return new ResourceModel("submenu.listAllImagesPage");
	}
}
