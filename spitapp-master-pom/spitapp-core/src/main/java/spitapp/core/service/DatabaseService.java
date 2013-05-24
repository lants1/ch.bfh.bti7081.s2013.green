package spitapp.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

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
}