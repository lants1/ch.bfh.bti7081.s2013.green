package spitapp.core.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
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
 * Tests the Databaseservice. Use it to generate also testdata.
 */
public class DatabaseServiceTest 
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
    	@SuppressWarnings("unchecked")
		List<Appointment> appointmentList = session.createCriteria(Appointment.class).list();
		for(Appointment appointment: appointmentList){
			if(StringUtils.equals(appointment.getAppointmentDescription(), "testermin2")){
				session.delete(appointment);
				session.delete(appointment.getPatient());
			}
		}
		
		// Create a new Testtermin
		Appointment termin = new Appointment();
		termin.setAppointmentDescription("testermin2");
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.MINUTE, -60);
		termin.setFromDate(calendar.getTime());
		calendar.add(Calendar.MINUTE, -20);
		termin.setToDate(calendar.getTime());

		String patientFirstName = String.valueOf("Pascal");
		String patientLastName = String.valueOf("von Ow");
		Patient patient = new Patient();
		patient.setAge(18);
		patient.setCareLevel(CareLevel.B2);
		patient.setHobbies("Joga");
		patient.setStreet("Tschamerie 18");
		patient.setCity("3415 Hasle b. Burgdorf");
		patient.setFirstName(patientFirstName);
		patient.setLastName(patientLastName);


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
		task.setDescription("Swen's Schuhe putzen");
		task.setDuration(70);
		task.setStarttime(new Date());
		Task task2 = new Task();
		task2.setDescription("Mojito für Swen");
		task2.setDuration(20);
	
		
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DAY_OF_WEEK, -2);
		task2.setStarttime(cal.getTime());
		
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(task);
		tasks.add(task2);
		boolean done = false;
		for(int i = 0; i < 30; i++){
			Task taskRandom = new Task();
			taskRandom.setDescription(i+" Bier für Swen");
			done = !done;
			taskRandom.setDone(done);
			taskRandom.setDuration((int) (Math.random()*1000));
			tasks.add(taskRandom);
		}

		ExpensesEntry spesen = new ExpensesEntry();
		spesen.setExpensesDescription("Pizza");
		spesen.setPrice(20);
		List<ExpensesEntry> expensesList = new ArrayList<ExpensesEntry>();
		expensesList.add(spesen);

		patient.setTasks(tasks);
		patient.setDocuments(docList);
		patient.setExpenses(expensesList);

		termin.setPatient(patient);
		session.saveOrUpdate(termin);

		tx.commit();
   }
    
    /**
     * Is it possible to get the correct Appointments for GUI.
     */
    @Test
    public void testGetAppointments()
    {	
    	Date date = new Date();
    	DatabaseService dbService = new DatabaseService();
    	List<Appointment> appointments = dbService.getAppointment(date);
    	assertTrue(appointments.size()>0);
    	for(Appointment appointment : appointments){
    		assertTrue(DateUtils.isSameDay(appointment.getFromDate(), date));
    		assertNotNull(appointment.getPatient());
    	}
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