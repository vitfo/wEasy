package cz.vitfo.database.model;

import java.io.Serializable;

import org.apache.wicket.markup.html.form.upload.FileUpload;

public class Image implements Serializable {

	private int id;
	private Integer directoryId;
	private String fileName;
	private byte[] bytes;
	
	public Image(Integer directoryId, FileUpload uploadedImageFile) {
		this.directoryId = directoryId;
		this.fileName = uploadedImageFile.getClientFileName();
		this.bytes = uploadedImageFile.getBytes();
	}
	
	public Image() {}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getDirectoryId() {
		return directoryId;
	}

	public void setDirectoryId(Integer directoryId) {
		this.directoryId = directoryId;
	}
	
	
}
