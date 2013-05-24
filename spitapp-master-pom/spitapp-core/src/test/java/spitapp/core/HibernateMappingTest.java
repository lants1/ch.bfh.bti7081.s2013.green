package spitapp.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import spitapp.core.model.CareLevel;
import spitapp.core.model.Document;
import spitapp.core.model.Patient;
import spitapp.core.model.ExpensesEntry;
import spitapp.core.model.Task;
import spitapp.core.model.Appointment;
import spitapp.core.service.DatabaseService;
import spitapp.core.service.PdfService;

import org.junit.*;
import static org.junit.Assert.*;


/**
 * Unit test for simple App.
 */
public class HibernateMappingTest 
{

	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
   
	
	/**
     * Initmethod to generate Testdata
     * @throws IOException 
     * @throws DocumentException 
     */
    @BeforeClass
    public static void initDemoData() throws DocumentException, IOException
    {
    	configureSessionFactory();
		Session session = sessionFactory.getCurrentSession();
    	Transaction tx = session.beginTransaction();
		
    	// Delete oldTesttermin
    	List<Appointment> appointmentList = session.createCriteria(Appointment.class).list();
		for(Appointment appointment: appointmentList){
			if(StringUtils.equals(appointment.getAppointmentDescription(), "testermin")){
				session.delete(appointment);
				session.delete(appointment.getPatient());
			}
		}
		
		// Create a new Testtermin
		Appointment termin = new Appointment();
		termin.setAppointmentDescription("testermin");
		termin.setFromDate(new Date());
		termin.setToDate(new Date());

		Patient patient = new Patient();
		patient.setAge(18);
		patient.setCareLevel(CareLevel.A1);
		patient.setHobbies("Kong-Fu fighting");
		patient.setFirstName("Swen");
		patient.setLastName("Lanthemann");

		Document dok = new Document();
		PdfService pdfService = new PdfService();
		String fileName = "test";
		dok.setFileName(fileName);
		dok.setFile(pdfService.createPdf(fileName));
		List<Document> docList = new ArrayList<Document>();
		docList.add(dok);

		Task task = new Task();
		task.setDescription("test2");
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(task);

		ExpensesEntry spesen = new ExpensesEntry();
		spesen.setExpensesDescription("test3");
		List<ExpensesEntry> expensesList = new ArrayList<ExpensesEntry>();
		expensesList.add(spesen);

		patient.setTasks(tasks);
		patient.setDocuments(docList);
		patient.setExpenses(expensesList);

		termin.setPatient(patient);
		session.saveOrUpdate(termin);

		tx.commit();
   }
    
    @Test
    public void testIsDbReadable()
    {	
		Session session = sessionFactory.getCurrentSession();

		Transaction tx = session.beginTransaction();
		List<Patient> patienten = session.createCriteria(Patient.class).add( Restrictions.like("firstName", "S%"))
			    .setMaxResults(50)
			    .list();
		
		assertTrue((patienten.size()>0));
		tx.commit();
    }

    @Test
    public void testLazyLoadingDisabled()
    {	
		Session session = sessionFactory.getCurrentSession();

		Transaction tx = session.beginTransaction();
		List<Appointment> appointments = session.createCriteria(Appointment.class)
			    .list();
		
		assertTrue((appointments.size()>0));
		
		tx.commit();
		
		for(Appointment appointment : appointments){
			Patient patient =appointment.getPatient();
			assertNotNull(patient);
			List<Document> docs = patient.getDocuments();
			assertTrue(docs.size()>0);
			Document doc = docs.get(0);
			assertFalse(StringUtils.isEmpty(doc.getFileName()));
		}
    }
    
    @Test
    public void testGetDocumentsPossible()
    {	
		Session session = sessionFactory.getCurrentSession();

		Transaction tx = session.beginTransaction();
		List<Appointment> appointments = session.createCriteria(Appointment.class)
			    .list();
		
		assertTrue((appointments.size()>0));
		
		for(Appointment appointment : appointments){
			Patient patient =appointment.getPatient();
			assertNotNull(patient);
			List<Document> docs = patient.getDocuments();
			assertTrue(docs.size()>0);
			Document doc = docs.get(0);
			assertFalse(StringUtils.isEmpty(doc.getFileName()));
		}
		tx.commit();
    }
    
	/**
	 * Register Sessionfactory duplicated from DatabaseService
	 * 
	 * Duplicated because the modifiers from Databaservice should stay private
	 * 
	 * @return
	 * @throws HibernateException
	 */
	private static SessionFactory configureSessionFactory() throws HibernateException {
	    Configuration configuration = new Configuration();
	    configuration.configure();
	    serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	    return sessionFactory;
	}
}