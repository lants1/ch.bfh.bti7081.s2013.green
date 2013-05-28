package spitapp.core;

import java.io.IOException;
import com.lowagie.text.DocumentException;

import spitapp.core.facade.UiServiceFacade;
import spitapp.core.service.DatabaseService;

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
    	UiServiceFacade userService = new UiServiceFacade();
    	userService.createUserLogin("swen", "correct");
    	
    	assertFalse(userService.validateLogin("swen", "wrong"));
    	assertTrue(userService.validateLogin("swen", "correct"));
    	assertFalse(userService.validateLogin("bla", "bla"));
    
    	DatabaseService dbService = new DatabaseService();
    	dbService.delete(dbService.getUserByUsername("swen"));
    }
}