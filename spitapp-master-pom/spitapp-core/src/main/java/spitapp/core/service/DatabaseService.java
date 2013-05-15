package spitapp.core.service;

import java.util.ArrayList;
import java.util.Date;
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


public class DatabaseService {

	/**
	 * 
	 * @param args
	 */
	public void saveOrUpdate(TerminEintrag termin) {
		SessionFactory sessionFactory = new AnnotationConfiguration()
		.configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();

		session.saveOrUpdate(termin);

		tx.commit();
	}
	
	public List<TerminEintrag> getTermine(Date date){
		SessionFactory sessionFactory = new AnnotationConfiguration()
		.configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<TerminEintrag> terminEintraege = session.createCriteria(TerminEintrag.class).list();
		List<TerminEintrag> result = new ArrayList<TerminEintrag>();
		System.out.println(terminEintraege.size());
		for(TerminEintrag termin : terminEintraege){
			termin.updateState(date);
			if(termin.isRelevant()){
			result.add(termin);
			}
		}

		tx.commit();
		
		return result;
	}
	
	public static void main(String[] args) {
	      initTestData();
	}
	
	private static void initTestData(){
		SessionFactory sessionFactory = new AnnotationConfiguration()
		.configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();

		TerminEintrag termin = new TerminEintrag();
		termin.setBeschreibung("testermin");
		termin.setTerminDate(new Date());

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

		termin.setPatient(patient);
		session.save(termin);

		tx.commit();
	}
	
}