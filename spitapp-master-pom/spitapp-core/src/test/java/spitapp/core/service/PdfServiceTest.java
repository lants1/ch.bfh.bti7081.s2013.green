package spitapp.core.service;

import java.io.IOException;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;

import spitapp.core.service.PdfService;

import org.junit.*;
import static org.junit.Assert.*;


/**
 * Simple tests to check the functions... Of the pdf service
 */
public class PdfServiceTest 
{

    @Test
    public void testPdfCreation() throws DocumentException, IOException
    {	
    	PdfService pdfService = new PdfService();
    	byte[] docArray = pdfService.createPdf("test");
    	assertTrue(docArray.length>1);
    }
    
    @Test
    public void testPdfConversionToDocument() throws DocumentException, IOException
    {	
    	PdfService pdfService = new PdfService();
    	byte[] docArray = pdfService.createPdf("test");
    	PdfReader reader = new PdfReader(docArray);
    	assertEquals(1,reader.getNumberOfPages());
    }
}