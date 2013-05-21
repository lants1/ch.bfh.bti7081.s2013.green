package spitapp.core.model;

import java.util.List;

import org.hibernate.annotations.Entity;

/**
 * Hibernate mapping Class for Table Patient
 * 
 * @author green
 *
 */
@Entity
public class Patient {

	private Long patId;

	private String firstName;

	private String lastName;
	
	private List<Task> tasks;
	
	private List<ExpensesEntry> spesenEintraege;
	
	private List<Document> dokumente;

	public Long getPatId() {
		return patId;
	}

	public void setPatId(Long patId) {
		this.patId = patId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<ExpensesEntry> getSpesenEintraege() {
		return spesenEintraege;
	}

	public void setSpesenEintraege(List<ExpensesEntry> spesenEintraege) {
		this.spesenEintraege = spesenEintraege;
	}

	public List<Document> getDokumente() {
		return dokumente;
	}

	public void setDokumente(List<Document> dokumente) {
		this.dokumente = dokumente;
	}
	
}
