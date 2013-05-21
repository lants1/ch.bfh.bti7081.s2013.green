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
	
	private List<ExpensesEntry> expenses;
	
	private List<Document> documents;

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

	/**
	 * @return the expenses
	 */
	public List<ExpensesEntry> getExpenses() {
		return expenses;
	}

	/**
	 * @param expenses the expenses to set
	 */
	public void setExpenses(List<ExpensesEntry> expenses) {
		this.expenses = expenses;
	}
	

	/**
	 * @return the documents
	 */
	public List<Document> getDocuments() {
		return documents;
	}

	/**
	 * @param documents the documents to set
	 */
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
	


}
