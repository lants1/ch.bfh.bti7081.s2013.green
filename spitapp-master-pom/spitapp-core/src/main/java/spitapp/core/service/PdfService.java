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
 
    public byte[] resource;
    
    /**
     * Creates a PDF
     * @param    args    no arguments needed
     */
    public static void main(String[] args)
    	throws DocumentException{
    	new PdfService().createPdf("Hello World!");
    }
 
    /**
     * Creates a PDF document.
     * @throws    DocumentException
     */
    public byte[] createPdf(String content) throws DocumentException{
        // step 1
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
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


    
}
