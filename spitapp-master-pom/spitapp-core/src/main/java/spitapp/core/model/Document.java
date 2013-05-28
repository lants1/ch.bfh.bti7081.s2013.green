package spitapp.core.model;

/**
 * Hibernate Mapping class for Document on DB
 * 
 * @author green
 *
 */
public class Document implements SpitappSaveable{
	
	private long documentId;
	
	private String fileName;
	
	private byte[] file;

	public long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the file
	 */
	public byte[] getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(byte[] file) {
		this.file = file;
	}
	
	


}
