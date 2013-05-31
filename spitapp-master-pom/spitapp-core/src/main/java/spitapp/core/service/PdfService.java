package spitapp.core.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Jpeg;
import com.lowagie.text.pdf.PdfWriter;
/**
 * Service used to get and create PDF-Documents
 * 
 * @author green
 *
 */
class PdfService{
    
    /**
     * Creates a PDF
     * @param    args    no arguments needed
     * @throws IOException 
     * @throws MalformedURLException 
     */
    static void main(String[] args)
    	throws DocumentException, MalformedURLException, IOException{
    	new PdfService().createPdf("Hello World!");
    }
 
    /**
     * Creates a PDF document.
     * @throws    DocumentException
     * @throws IOException 
     * @throws MalformedURLException 
     */
    byte[] createPdf(String content) throws DocumentException, MalformedURLException, IOException{
        // step 1
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // step 2
        PdfWriter.getInstance(document, outputStream );
        // step 3
        document.open();
        // step 4
        Paragraph header = new Paragraph();
        
        Jpeg logo = new Jpeg(getClass().getResource("/spitapp/core/images/TheOneAndOnlyLogo.jpg"));
        logo.setAlignment(Element.ALIGN_RIGHT);
        logo.scalePercent(30); //Bildgrösse anpassen

        Font font1 = new Font(Font.HELVETICA, 25, Font.BOLD); //Fett und grösser
        Chunk title = new Chunk (content, font1);
        
        header.add(title);
        header.add(logo);
        header.setAlignment(Element.ALIGN_BOTTOM);
        
        document.add(header);
        document.setPageCount(1);
        // step 5
        document.close();
        
        // return the document as bytearray

		return outputStream.toByteArray();
    }
}
