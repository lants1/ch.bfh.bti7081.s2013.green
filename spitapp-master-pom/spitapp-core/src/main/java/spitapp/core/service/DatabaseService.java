package spitapp.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import spitapp.core.model.Document;
import spitapp.core.model.Patient;
import spitapp.core.model.ExpensesEntry;
import spitapp.core.model.Task;
import spitapp.core.model.Appointment;

/**
 * Single Point for every DB-Call
 * 
 * @author green
 *
 */
public class DatabaseService {

	/**
	 * General method to save or update something
	 * 
	 * @param Object somethingToSave
	 */
	public void saveOrUpdate(Object somethingToSave) {
		SessionFactory sessionFactory = new AnnotationConfiguration()
		.configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();

		session.saveOrUpdate(somethingToSave);

		tx.commit();
	}
	
	/**
	 * Get all "Termine" from Database with in this case useless State Pattern.
	 */
	public List<Appointment> getTermine(Date date){
		SessionFactory sessionFactory = new AnnotationConfiguration()
		.configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		// Get all Appointment from db without restriction o_O evil thing
		List<Appointment> terminEintraege = session.createCriteria(Appointment.class).list();
		List<Appointment> result = new ArrayList<Appointment>();
		for(Appointment termin : terminEintraege){
			// Call the Statepattern mechanism on each termin
			termin.updateState(date);
			// Only add the termin if, according to the statepattern, relevant...
			if(termin.isRelevant()){
			result.add(termin);
			}
		}

		tx.commit();
		
		return result;
	}
	
	/**
	 * Only here to generate Testdata easily:)
	 * @param args
	 */
	public static void main(String[] args) {
	      initTestData();
	}
	
	/**
	 * Only here to generate Testdata easily:)
	 */
	private static void initTestData(){
		SessionFactory sessionFactory = new AnnotationConfiguration()
		.configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();

		Appointment termin = new Appointment();
		termin.setBeschreibung("testermin");
		termin.setTerminDate(new Date());

		Patient patient = new Patient();
		patient.setFirstName("Swen");
		patient.setLastName("Lanthemann");

		Document dok = new Document();
		dok.setFileName("test");
		dok.setFilePath("path");
		List<Document> doks = new ArrayList<Document>();
		doks.add(dok);

		Task task = new Task();
		task.setDescription("test2");
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(task);

		ExpensesEntry spesen = new ExpensesEntry();
		spesen.setSpesenDesc("test3");
		List<ExpensesEntry> spesenList = new ArrayList<ExpensesEntry>();
		spesenList.add(spesen);

		patient.setTasks(tasks);
		patient.setDokumente(doks);
		patient.setSpesenEintraege(spesenList);

		termin.setPatient(patient);
		session.save(termin);

		tx.commit();
	}
	
}