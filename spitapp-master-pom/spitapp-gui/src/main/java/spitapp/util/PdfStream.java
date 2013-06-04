package spitapp.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.vaadin.server.StreamResource.StreamSource;

/**
 * PdfStream class to handle pdf documents
 * @author vonop1
 */
public class PdfStream implements StreamSource {
	
	private static final long serialVersionUID = 6367988474586312060L;
	private byte[] resource;
	
    /**
     * returns the pdf contents
     */
    @Override
    public InputStream getStream() {
        return new ByteArrayInputStream(resource);
    }
    /**
     * sets the pdf resource
     * @param file the pdf resource to set
     */
	public void setResource(byte[] file) {
		this.resource = file;
	}
}
