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
	 * Get all appointments from Database with in this case useless State Pattern.
	 */
	public List<Appointment> getAppointment(Date date){
		SessionFactory sessionFactory = new AnnotationConfiguration()
		.configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		// Get all Appointment from db without restriction o_O evil thing
		List<Appointment> appointmentList = session.createCriteria(Appointment.class).list();
		List<Appointment> resultList = new ArrayList<Appointment>();
		for(Appointment appointment : appointmentList){
			// Call the Statepattern mechanism on each termin
			appointment.updateState(date);
			// Only add the appointment if, according to the statepattern, relevant...
			if(appointment.isRelevant()){
			resultList.add(appointment);
			}
		}

		tx.commit();
		
		return resultList;
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
		termin.setAppointmentDescription("testermin");
		termin.setFromDate(new Date());
		termin.setToDate(new Date());

		Patient patient = new Patient();
		patient.setFirstName("Swen");
		patient.setLastName("Lanthemann");

		Document dok = new Document();
		dok.setFileName("test");
		dok.setFilePath("path");
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
		session.save(termin);

		tx.commit();
	}
	
}