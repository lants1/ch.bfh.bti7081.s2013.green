package spitapp.core.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
/**
 * Service used to get and create PDF-Documents
 * 
 * @author green
 *
 */
public class PdfService{

	 
    /** Path to the resulting PDF file. */
    public static final String RESULT
        = "hello.pdf";
 
    public byte[] resource;
    
    /**
     * Creates a PDF file: hello.pdf
     * @param    args    no arguments needed
     */
    public static void main(String[] args)
    	throws DocumentException, IOException {
    	new PdfService().createPdf(RESULT);
    }
 
    /**
     * Creates a PDF document.
     * @param filename the path to the new PDF document
     * @throws    DocumentException 
     * @throws    IOException 
     */
    public byte[] createPdf(String filename)
	throws DocumentException, IOException {
        // step 1
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // step 2
        PdfWriter.getInstance(document, outputStream );
        // step 3
        document.open();
        // step 4
        document.add(new Paragraph("Hello World!"));
        document.setPageCount(1);
        // step 5
        document.close();
        
        // return the document as bytearray

		return outputStream.toByteArray();
    }

    /**
     * Convert a ByteArray to Pdf
     * @param byteArray the Data of Pdf in a ByteArray 
     * @throws IOException 
     * @throws DocumentException 
     */
    // TODO LAN this method don't work change it...
    @Deprecated
    public Document convertByteArrayToPdf (byte[] byteArray) throws IOException, DocumentException
    {
    	Document myDocument = new Document(PageSize.A4);
    	myDocument.open();
    	myDocument.add(new Paragraph(byteArray.toString()));
    	myDocument.close();
    	
    	return myDocument;
    	
    }
    
}
