package spitapp.core.model;

import java.util.List;

/**
 * Hibernate mapping Class for Table Patient
 * 
 * @author green
 *
 */
public class Patient implements SpitappSaveable{

	private Long patId;
	
	private int age;
	
	private String hobbies;

	private String firstName;

	private String lastName;
	
	private String street;
	
	private String city;
	
	private List<Task> tasks;
	
	private List<ExpensesEntry> expenses;
	
	private List<Document> documents;
	
	private CareLevel careLevel;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public CareLevel getCareLevel() {
		return careLevel;
	}

	public void setCareLevel(CareLevel careLevel) {
		this.careLevel = careLevel;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}
	
	public Long getPatId() {
		return patId;
	}

	public void setPatId(Long patId) {
		this.patId = patId;
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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
