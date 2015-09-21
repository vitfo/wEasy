package cz.vitfo.database.dao;

import java.util.List;

import cz.vitfo.database.model.Category;
import cz.vitfo.database.model.Directory;
import cz.vitfo.database.model.Image;

public interface ImageDao {

	public List<Image> getAllImages();
	public List<Image> getAllImagesInCategory(Category category);
	public List<Image> getAllImagesInDirectory(Directory directory);
	
	public void saveImageFile(Image uploadedImageFile);
	
	public Image getImageById(long id);
}
