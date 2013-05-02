package spitapp.core.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import spitapp.core.model.Patient;


public class DatabaseService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SessionFactory sessionFactory = new AnnotationConfiguration()
				.configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		Transaction tx = session.beginTransaction();

		Patient patient = new Patient();
		patient.setFirstName("Swen");
		patient.setLastName("Lanthemann");
		
		session.save(patient);

		tx.commit();
	}
}