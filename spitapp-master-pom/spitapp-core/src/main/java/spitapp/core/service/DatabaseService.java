package spitapp.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import spitapp.core.model.Appointment;
import spitapp.core.model.User;

/**
 * Single Point for every DB-Call.
 * 
 * @author green
 *
 */
public class DatabaseService {

	private SessionFactory sessionFactory;
	private ServiceRegistry serviceRegistry;
	
	public DatabaseService(){
		this.configureSessionFactory();
	}
	
	/**
	 * General method to save or update something
	 * 
	 * @param Object somethingToSave
	 */
	public void saveOrUpdate(Object somethingToSave) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();

		session.saveOrUpdate(somethingToSave);

		tx.commit();
	}
	
	/**
	 * General method to delete something
	 * 
	 * @param Object somethingToDelete
	 */
	public void delete(Object somethingToDelete) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();

		session.delete(somethingToDelete);

		tx.commit();
	}
	
	/**
	 * Get all appointments from Database with in this case useless State Pattern.
	 */
	public List<Appointment> getAppointment(Date date){
		// TODO Lan with disabling the lazy loading we killed our Performance
		// and one of the benefit of using hibernate
		// Is there another better solution for our gui team...???
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		// Get all Appointment from db without restriction o_O evil thing
		@SuppressWarnings("unchecked")
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
	 * Gets a User from db by username as parameter..
	 * @param username
	 * @return User
	 */
	public User getUserByUsername(String username){
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		User user = (User) session.createCriteria(User.class).add( Restrictions.like("userName", username) ).uniqueResult();
		tx.commit();
		
		return user;
	}
	
	/**
	 * Register Sessionfactory
	 * @return
	 * @throws HibernateException
	 */
	private SessionFactory configureSessionFactory() throws HibernateException {
	    Configuration configuration = new Configuration();
	    configuration.configure();
	    serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	    return sessionFactory;
	}
}