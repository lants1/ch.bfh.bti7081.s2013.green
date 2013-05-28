package spitapp.core;

import java.io.IOException;
import com.lowagie.text.DocumentException;
import spitapp.core.service.DatabaseService;
import spitapp.core.service.UserService;

import org.junit.*;
import static org.junit.Assert.*;


/**
 * Unit test for simple App.
 */
public class UserServiceTest 
{

    @Test
    public void testUsernamePasswordValidation() throws DocumentException, IOException
    {	 	
    	UserService pdfService = new UserService();
    	pdfService.storeUserAndPassword("swen", "correct");
    	
    	assertFalse(pdfService.validateUserAndPasswort("swen", "wrong"));
    	assertTrue(pdfService.validateUserAndPasswort("swen", "correct"));
    	assertFalse(pdfService.validateUserAndPasswort("bla", "bla"));
    
    	DatabaseService dbService = new DatabaseService();
    	dbService.delete(dbService.getUserByUsername("swen"));
    }
}