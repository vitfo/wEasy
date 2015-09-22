package cz.vitfo.internal.pages.editwithmodal;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.StringValue;

import cz.vitfo.database.daoimpl.ImageDaoImpl;
import cz.vitfo.database.model.Image;

/**
 * Class that represents my ResourceReference -> my image reference
 * 
 * @author vitfo
 *
 */
public class ImageResourceReference extends ResourceReference {

	private static final long serialVersionUID = 6402188485466805555L;

	public ImageResourceReference() {
		super(ImageResourceReference.class, "imageResourceReference");
	}

	@Override
	public IResource getResource() {
		// Return my class ImageResource representing image resource.
		return new ImageResource();
	}

	/**
	 * Class that represents my image resource - DynamicImageResource. The
	 * method getImageData returns byte[] array. The method can receive
	 * attributes as parameter.
	 * 
	 * @author User
	 *
	 */
	private static class ImageResource extends DynamicImageResource {

		@Override
		protected byte[] getImageData(Attributes attributes) {
			// get PageParameters from attributes
			PageParameters parameters = attributes.getParameters();
			StringValue imageId = parameters.get("imageId");

			byte[] imageBytes = null;
			try {
				int id = Integer.parseInt(imageId.toString());
				ImageDaoImpl dao = new ImageDaoImpl();
				Image img = dao.getImageById(id);
				imageBytes = img.getBytes();
			} catch (Exception e) {
			}

			return imageBytes;
		}
	}
}
