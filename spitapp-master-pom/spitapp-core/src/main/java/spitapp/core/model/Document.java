package spitapp.core.model;

import org.hibernate.annotations.Entity;

/**
 * Hibernate Mapping class for Document on DB
 * 
 * @author green
 *
 */
@Entity
public class Document {
	
	private long documentId;
	
	private String FileName;
	
	private String FilePath;

	public long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}
	
	public String getFileName() {
		return FileName;
	}
	
	public void setFileName(String fileName) {
		this.FileName = fileName;
	}
	
	public String getFilePath() {
		return FilePath;
	}
	
	public void setFilePath(String filePath) {
		this.FilePath = filePath;
	}

}
