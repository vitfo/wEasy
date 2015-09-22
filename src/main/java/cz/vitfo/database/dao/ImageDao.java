package cz.vitfo.database.dao;

import java.util.List;

import cz.vitfo.database.model.Category;
import cz.vitfo.database.model.Directory;
import cz.vitfo.database.model.Image;

public interface ImageDao {

	public List<Image> getAllImages();
	public List<Image> getAllImagesInCategory(Category category);
	public List<Image> getAllImagesInDirectory(Directory directory);
	
	/**
	 * Saves {@link Image}
	 * 
	 * @param uploadedImageFile - image to be saved
	 */
	public void saveImageFile(Image uploadedImageFile);
	
	/**
	 * Gets {@link Image}.
	 * 
	 * @param id - id of the image
	 * @return image
	 */
	public Image getImageById(int id);
	
	/**
	 * Deletes {@link Image}.
	 * 
	 * @param id - id of the image
	 */
	public void deleteImage(int id);
}
