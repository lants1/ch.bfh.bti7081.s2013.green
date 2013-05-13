package spitapp.core.model;

import org.hibernate.annotations.Entity;

@Entity
public class Dokument {
	
	private long DocId;
	
	private String FileName;
	
	private String FilePath;

	public long getDocId() {
		return DocId;
	}

	public void setDocId(long docId) {
		this.DocId = docId;
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
