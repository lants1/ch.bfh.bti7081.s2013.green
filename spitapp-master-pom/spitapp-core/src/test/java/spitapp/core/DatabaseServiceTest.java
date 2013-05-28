package spitapp.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
 * Unit test for simple App.
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
		termin.setFromDate(new Date());
		termin.setToDate(new Date());

		String patientFirstName = new String ("Pascal");
		String patientLastName = new String ("von Ow");
		Patient patient = new Patient();
		patient.setAge(18);
		patient.setCareLevel(CareLevel.B2);
		patient.setHobbies("Joga");
		patient.setFirstName(patientFirstName);
		patient.setLastName(patientLastName);


		PdfService pdfService = new PdfService();
		List<Document> docList = new ArrayList<Document>();
		
		Document dok = new Document();
		dok.setFileName("Krankenakte");
		dok.setFile(pdfService.createPdf("Krankenakte" + patientFirstName + patientLastName));
		docList.add(dok);

		Document dok2 = new Document();
		dok2.setFileName("Allgemeine Infos");
		dok2.setFile(pdfService.createPdf("Allgemeine Infos" + patientFirstName + patientLastName));
		docList.add(dok2);

		Task task = new Task();
		task.setDescription("HighPrio");
		task.setDuration(70);
		task.setStarttime(new Date());
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(task);

		ExpensesEntry spesen = new ExpensesEntry();
		spesen.setExpensesDescription("Pizza");
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