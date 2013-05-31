package spitapp.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.vaadin.server.StreamResource.StreamSource;

public class PdfStream implements StreamSource {
	
	private byte[] resource;
    @Override
    public InputStream getStream() {
        // Here we return the pdf contents
        return new ByteArrayInputStream(resource);
    }
	public void setResource(byte[] file) {
		this.resource = file;
	}
}
