package spitapp.core;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import spitapp.core.model.Dokument;
import spitapp.core.model.Patient;
import spitapp.core.model.SpesenEintrag;
import spitapp.core.model.Task;
import spitapp.core.model.TerminEintrag;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class DatabaseTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DatabaseTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DatabaseTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testDemoData()
    {
    	
		/*SessionFactory sessionFactory = new AnnotationConfiguration()
		.configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		Transaction tx = session.beginTransaction();

		TerminEintrag termin = new TerminEintrag();
		termin.setBeschreibung("testermin");

		Patient patient = new Patient();
		patient.setFirstName("Swen");
		patient.setLastName("Lanthemann");

		Dokument dok = new Dokument();
		dok.setFileName("test");
		dok.setFilePath("path");
		List<Dokument> doks = new ArrayList<Dokument>();
		doks.add(dok);

		Task task = new Task();
		task.setDescription("test2");
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(task);

		SpesenEintrag spesen = new SpesenEintrag();
		spesen.setSpesenDesc("test3");
		List<SpesenEintrag> spesenList = new ArrayList<SpesenEintrag>();
		spesenList.add(spesen);

		patient.setTasks(tasks);
		patient.setDokumente(doks);
		patient.setSpesenEintraege(spesenList);

		List<Patient> patienten = new ArrayList<Patient>();
		patienten.add(patient);
		termin.setPatienten(patienten);
		session.save(termin);

		tx.commit();
		session.close();
		sessionFactory.close();
		assertTrue(true);
   */ }
    
   /* public void testIsDbReadable()
    {
    	
		SessionFactory sessionFactory = new AnnotationConfiguration()
		.configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		Transaction tx = session.beginTransaction();
		List<Patient> patienten = session.createCriteria(Patient.class).add( Restrictions.like("firstName", "S%"))
			    .setMaxResults(50)
			    .list();
		
		assertEquals(patienten.size(), 1);
		tx.commit();
		session.close();
    }*/
}