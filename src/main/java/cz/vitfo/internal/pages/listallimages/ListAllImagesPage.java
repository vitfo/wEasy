package cz.vitfo.internal.pages.listallimages;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.ByteArrayResource;

import cz.vitfo.database.dao.ImageDao;
import cz.vitfo.database.daoimpl.ImageDaoImpl;
import cz.vitfo.database.model.Image;
import cz.vitfo.internal.pages.InternalBasePage;

public class ListAllImagesPage extends InternalBasePage  {

	private static final long serialVersionUID = -6842730542504808757L;

	public ListAllImagesPage() {
		IModel<List<Image>> imagesModel = new AbstractReadOnlyModel<List<Image>>() {
			@Override
			public List<Image> getObject() {
				ImageDaoImpl dao = new ImageDaoImpl();
				return dao.getAllImages();
			}
		}; 
		this.setDefaultModel(imagesModel);
		
		final WebMarkupContainer container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		add(container);
		
		ListView images = new ListView<Image>("images", imagesModel) {
			@Override
			protected void populateItem(ListItem<Image> item) {
				final Image img = item.getModelObject();
				item.add(new org.apache.wicket.markup.html.image.Image("image", new ByteArrayResource(null, img.getBytes())));
				item.add(new Label("name", img.getFileName()));
				item.add(new AjaxLink("delete") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						ImageDao dao = new ImageDaoImpl();
						dao.deleteImage(img.getId());
						
						target.add(container);
					}
				});
			}
		};
		container.add(images);
	}
	
	@Override
	protected IModel<String> getSubTitle() {
		return new ResourceModel("submenu.listAllImagesPage");
	}
}
