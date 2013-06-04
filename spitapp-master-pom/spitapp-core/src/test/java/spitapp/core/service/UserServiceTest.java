package spitapp.core.service;

import java.io.IOException;
import com.lowagie.text.DocumentException;

import spitapp.core.service.DatabaseService;

import org.junit.*;
import static org.junit.Assert.*;


/**
 * Check the Login functionality. And is also used to generate Testdata.
 */
public class UserServiceTest 
{

    @Test
    public void testUsernamePasswordValidation() throws DocumentException, IOException
    {	 
    	DatabaseService dbService = new DatabaseService();
    	if(dbService.getUserByUsername("swen")!= null){
    		dbService.delete(dbService.getUserByUsername("swen"));
    	}
    	
    	UiServiceFacade userService = UiServiceFacade.getInstance();
    	userService.createUserLogin("swen", "correct");
    	
    	assertFalse(userService.validateLogin("swen", "wrong"));
    	assertTrue(userService.validateLogin("swen", "correct"));
    	assertFalse(userService.validateLogin("bla", "bla"));
    }
}