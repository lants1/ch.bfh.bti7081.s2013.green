package spitapp.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;

import spitapp.core.model.CareLevel;
import spitapp.core.model.Document;
import spitapp.core.model.Patient;
import spitapp.core.model.ExpensesEntry;
import spitapp.core.model.Task;
import spitapp.core.model.Appointment;
import spitapp.core.model.User;
import spitapp.core.service.DatabaseService;
import spitapp.core.service.PdfService;
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