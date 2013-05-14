package spitapp.core.model;

import java.util.List;

import org.hibernate.annotations.Entity;

@Entity
public class Patient {

	private Long patId;

	private String firstName;

	private String lastName;
	
	private List<Task> tasks;
	
	private List<SpesenEintrag> spesenEintraege;
	
	private List<Dokument> dokumente;

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

	public List<SpesenEintrag> getSpesenEintraege() {
		return spesenEintraege;
	}

	public void setSpesenEintraege(List<SpesenEintrag> spesenEintraege) {
		this.spesenEintraege = spesenEintraege;
	}

	public List<Dokument> getDokumente() {
		return dokumente;
	}

	public void setDokumente(List<Dokument> dokumente) {
		this.dokumente = dokumente;
	}
	
}
