package spitapp.core.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import spitapp.core.service.PdfService;

import org.junit.*;
import static org.junit.Assert.*;


/**
 * This Tests check our hibernate mapping. It is also used to generate Testdata.
 * 
 * If you use this tests the first time you need to change hbm.cfg and change it to create
 * the tables then uncomment it...
 */
public class HibernateMappingTest 
{

	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
	
	private static Date testdate = new Date();
   
	
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
    	@SuppressWarnings("unchecked")
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
		termin.setFromDate((Date) testdate.clone());
		// Testdate um 20 Minuten verlängern
		testdate.setTime((testdate.getTime() + (20 * 60 * 1000)));
		termin.setToDate(testdate);

		Patient patient = new Patient();
		String patientFirstName = String.valueOf("Swen");
		String patientLastName = String.valueOf("Lanthemann");
		patient.setAge(18);
		patient.setCareLevel(CareLevel.A1);
		patient.setHobbies("Kong-Fu fighting");
		patient.setFirstName(patientFirstName);
		patient.setLastName(patientLastName);
		patient.setStreet("Lindenweg 11");
		patient.setCity("4565 Recherswil");

		PdfService pdfService = new PdfService();
		List<Document> docList = new ArrayList<Document>();
		
		Document dok = new Document();
		dok.setFileName("Krankenakte");
		dok.setFile(pdfService.createPdf("Krankenakte " + patientFirstName + " " + patientLastName));
		docList.add(dok);

		Document dok2 = new Document();
		dok2.setFileName("Allgemeine Infos");
		dok2.setFile(pdfService.createPdf("Allgemeine Infos " + patientFirstName+ " " + patientLastName));
		docList.add(dok2);

		Task task = new Task();
		task.setDescription("test2");
		task.setStarttime(new Date());
		task.setDuration(100);
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
		@SuppressWarnings("unchecked")
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
		@SuppressWarnings("unchecked")
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
		@SuppressWarnings("unchecked")
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
     * Test if the Date and Time returned from DB is eqaul with the saved Time,
     * checked on milliseconds
     */
    @Test
    public void testIsDateCorrectStored()
    {	
		Session session = sessionFactory.getCurrentSession();

		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Appointment> appointments = session.createCriteria(Appointment.class)
	    .list();

		assertTrue((appointments.size()>0));
		
		for(Appointment appointment : appointments){
			if (appointment.getAppointmentDescription().equalsIgnoreCase("testtermin"))
			{
				assertEquals(testdate.getTime(), appointment.getFromDate().getTime());
			}
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