package spitapp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import spitapp.core.model.Appointment;
import spitapp.core.model.ExpensesEntry;
import spitapp.core.model.Patient;
import spitapp.core.model.Task;
import spitapp.core.service.UiServiceFacade;
import spitapp.util.DateUtil;

/**
 * Controls the appointment listing, selection and data manipulation
 * @author jaggr2
 * 
 */
public class AppointmentController {
	
	private final static Logger logger = 
			Logger.getLogger(AppointmentController.class.getName());

	/**
	 * ControllerCodes
	 */
	public enum Codes {
		 SUCCESS(1, "Erfolgreich!"), 
		 NOTHING_DONE(0, "Nichts passiert!"),
		 ERROR_ON_SAVE(-1, "Ooops. Beim Speichern gings in die Hose!"),
		 DATE_IS_NULL(-10, "Das Datum ist leer!"),
		 NO_APPOINTMENTS_FOUND(-11, "Es sind keine Patiententermine geplant."),
		 NO_PATIENT_FOUND(-12, "Kein Patient gefunden!"), 
		 NO_EXPENSES_FOUND(-13, "Speseneintrag nicht gefunden!"),
		 NO_TASK_FOUND(-14, "Aufgabe nicht gefunden!"),
		 INVALID_AMOUNT(-15, "Der eingegebene Betrag ist ungültig!"),
		 INVALID_DESCRIPTION(-16, "Spesenart darf nicht leer sein!"),
		 STARTTIME_IS_EMPTY(-17,"Die Startzeit muss angegeben werden!"),
		 DURATION_IS_EMPTY(-18, "Die Dauer muss angegeben werden!"),
		 INVALID_STARTTIME_FORMAT(-19, "Die eingegebene Startzeit ist ungültig!"),
		 INVALID_DURATION_FORMAT(-20, "Die eingegebene Dauer ist ungültig!");
		 
		 private int internalCode;
		 private String message;
		 
		 private Codes(int code, String message) {
			 this.internalCode = code;
			 this.message = message;
		 }
		 
		 public int getCode() {
			 return internalCode;
		 }
		 
		 public String getMessage() {
			 return message;
		 }
		 
		 public boolean isSuccessfull() {
			 return internalCode > 0;
		 }
	}
	
	/**
	 * the list of appointments of a day
	 */
	protected List<Appointment> appointments = new ArrayList<Appointment>();


	/**
	 * the GUI Id of the current GUI selection
	 */
	protected Appointment currentSelectedAppointment = null;

	/**
	 * the list of Listeners for the AppointmentChangedEvent
	 */
	private List<AppointmentChangedListener> listeners = new ArrayList<AppointmentChangedListener>();


	/**
	 * the constructor
	 * 
	 * @param dbservice
	 *            The reference to the DatabaseService to use
	 */
	public AppointmentController() {
	}
	
	/**
	 * Get's the appointment list of the current selected day
	 * @return List of Appointments and an empty list if no appointments are available
	 */
	public List<Appointment> getAppointments() {
		return this.appointments;
	}


	/**
	 * Gets the current selected appointment 
	 * @return an appointment object or null if not found
	 */
	public Appointment getCurrentAppointment() {
		return this.currentSelectedAppointment;
	}

	/**
	 * sets the new current appointment
	 * 
	 * @param appointmentId
	 *            the new appointment id or null to select none
	 */
	public void changeAppointmentById(Long appointmentId) {
		this.currentSelectedAppointment = null;
		
		if(appointmentId != null && appointmentId.longValue() > 0) {
			
			for(Appointment entry : this.appointments) {
				if(entry.getTerminId().equals(appointmentId)) {
					this.currentSelectedAppointment = entry;
					break;
				}
			}
		}
		
		// notify Listeners if the appointment has changed
		fireAppointmentChangedEvent();
	}

	/**
	 * Loads appointments by a given date
	 * 
	 * @param dateToLoad
	 *            the date from that appointments should be loaded
	 * @return the count of appointments loaded
	 */
	public Codes loadAppointmentsByDate(Date dateToLoad) {
		if(dateToLoad == null) {
			return Codes.DATE_IS_NULL;
		}
		
		this.appointments = UiServiceFacade.getInstance().getAppointments(dateToLoad);
		if (this.appointments == null) {
			this.appointments = new ArrayList<Appointment>();
			return Codes.NO_APPOINTMENTS_FOUND;
		}
		return Codes.SUCCESS;
	}

	/**
	 * adds a further event listener
	 * 
	 * @param listener
	 *            the listener to add
	 */
	public synchronized void addAppointmentChangedEventListener(
			AppointmentChangedListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Removes an Event Listener
	 * 
	 * @param listener
	 *            the Listener to remove
	 */
	public synchronized void removeAppointmentChangedEventListener(
			AppointmentChangedListener listener) {
		this.listeners.remove(listener);
	}

	/**
	 * this method can be called whenever you want to notify the event listeners
	 * of the AppointmentChangedEvent
	 */
	private synchronized void fireAppointmentChangedEvent() {
		AppointmentChangedEvent event = new AppointmentChangedEvent(this);
		Iterator<AppointmentChangedListener> i = this.listeners.iterator();
		while (i.hasNext()) {
			i.next().handleAppointmentChangedEvent(event);
		}
	}
	
	/**
	 * Adds a new expense entry to current selected patient
	 * @param description the description of the expense
	 * @param amount the amount as decimal value in a string
	 * @return SUCCESS or a failure code
	 */
	public Codes addExpensetoCurrentPatient(String description, String amount) {
		Double value = null;
		
		if(amount == null ) {
			return Codes.INVALID_AMOUNT;
		}
		try {
			value = Double.parseDouble(amount);
		} 
		catch(NumberFormatException ex) {
			return Codes.INVALID_AMOUNT;
		}
		
		if(description == null || description.isEmpty()) {
			return Codes.INVALID_DESCRIPTION;
		}
		
		try {
			Appointment appointment = this.getCurrentAppointment();
			
			Patient patient = appointment.getPatient();
			
			ExpensesEntry newexpense = new ExpensesEntry();
			newexpense.setExpensesDescription(description);
			newexpense.setPrice(value);
			
			patient.getExpenses().add(newexpense);
			
			UiServiceFacade.getInstance().saveModel(appointment);
		}
		catch(NumberFormatException ex) {
			return Codes.ERROR_ON_SAVE;
		}
		
		return Codes.SUCCESS;
	}
	
	/**
	 * Deletes an expense on the current selected patient
	 * @param expense_id the id of the expense to delete
	 * @return SUCCESS or a failure code
	 */
	public Codes deleteExepenseOfCurrentPatient(Long expense_id) {
		if(expense_id == null) {
			logger.log(Level.SEVERE, "Argument expense_id is null!");
			return Codes.NO_EXPENSES_FOUND;  
		}
		
		Patient patient = this.getCurrentAppointment().getPatient();
		if(patient == null) {
			return Codes.NO_PATIENT_FOUND;
		}
		List<ExpensesEntry> expenses = patient.getExpenses();
		if(expenses == null) {
			return Codes.NO_EXPENSES_FOUND;
		}
		else {
			try { 
				for(ExpensesEntry entry : expenses) {
					if(entry.getExpensesId() == expense_id) {
						expenses.remove(entry);
						UiServiceFacade.getInstance().saveModel(patient);
						return Codes.SUCCESS;
					}
				}
			}
			catch(Exception ex) {
				return Codes.ERROR_ON_SAVE;
			}
		}
		
		return Codes.NOTHING_DONE;
	}
	
	/**
	 * Get's a task from the CURRENT PATIENT by Id
	 * @param task_id the id of the task
	 * @return The Task or null if not found
	 */
	public Task getTaskById(Long task_id) {
		if(task_id == null) {
			return null;
		}
		
		Patient patient = this.getCurrentAppointment().getPatient();
		if(patient == null) {
			return null;
		}
		List<Task> tasks = patient.getTasks();
		if(tasks == null) {
			return null;
		}
		else {
				for(Task entry : tasks) {
					if(entry.getTaskId().equals(task_id)) {
						return entry;
					}
				}
		}
		
		return null;
	}

	/**
	 * Marks a task on the current selected patient as completed and adds a time record
	 * @param taskId the id of the expense to delete
	 * @return SUCCESS or a failure code
	 */
	public Codes completeTaskOfCurrentPatient(Long taskId, String startTimeInput, String durationInput) {
		
		if(startTimeInput == null || startTimeInput.isEmpty()) {
			return Codes.STARTTIME_IS_EMPTY;
		}
				
		if(durationInput == null || durationInput.isEmpty()) {
			return Codes.DURATION_IS_EMPTY;
		}


		Date startTime = DateUtil.getTodayWithSpecificTime(startTimeInput);
		if(startTime == null) {
			return Codes.INVALID_STARTTIME_FORMAT;
		}
		
		Integer duration = null;
		try {
			duration = Integer.parseInt(durationInput);
		} 
		catch(NumberFormatException ex) {
			return Codes.INVALID_DURATION_FORMAT;
		}
		
		Task theTask = getTaskById(taskId);
		if(theTask == null) {
			return Codes.NO_TASK_FOUND;
		}
		
		theTask.setDone(true);
		theTask.setStarttime(startTime);
		theTask.setDuration(duration);
		
		
		UiServiceFacade.getInstance().saveModel(theTask);
		
		return Codes.SUCCESS;
	}
	
	/**
	 * Reactivates a task of the current patient
	 * @param taskId the id of the task
	 * @return SUCCESS or a failure code
	 */
	public Codes reactivateTaskOfCurrentPatient(Long taskId) {
				
		Task theTask = getTaskById(taskId);
		if(theTask == null) {
			return Codes.NO_TASK_FOUND;
		}
		
		theTask.setDone(false);
		theTask.setStarttime(null);
		theTask.setDuration(0);
		
		UiServiceFacade.getInstance().saveModel(theTask);
		
		return Codes.SUCCESS;
	}
}
