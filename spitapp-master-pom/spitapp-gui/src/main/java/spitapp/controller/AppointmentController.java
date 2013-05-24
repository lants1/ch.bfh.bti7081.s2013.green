package spitapp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import spitapp.core.model.Appointment;
import spitapp.core.model.ExpensesEntry;
import spitapp.core.model.Patient;
import spitapp.core.service.DatabaseService;

/**
 * controlls the appointment listing and fires an event, when an appointment
 * changes
 * 
 * @author Roger Jaggi
 * 
 */
public class AppointmentController {
	
	/**
	 * the list of appointments of a day
	 */
	protected HashMap<Integer, Appointment> appointments = new HashMap<Integer, Appointment>();

	/**
	 * the GUI Id of the current GUI selection
	 */
	protected Integer latestId = 0;

	/**
	 * the GUI Id of the current GUI selection
	 */
	protected Integer currentSelectionGuiId = null;

	/**
	 * the list of Listeners for the AppointmentChangedEvent
	 */
	private List<AppointmentChangedListener> listeners = new ArrayList<AppointmentChangedListener>();

	protected DatabaseService dbservice = null;

	/**
	 * the constructor
	 * 
	 * @param dbservice
	 *            The reference to the DatabaseService to use
	 */
	public AppointmentController(DatabaseService dbservice) {
		this.dbservice = dbservice;
	}

	/**
	 * Returns the appointment list with their associeted gui-IDs
	 * 
	 * @return map of appointments
	 */
	public HashMap<Integer, Appointment> getAppointments() {
		return this.appointments;
	}

	/**
	 * Gets an appointment object by it's GUI id
	 * 
	 * @param guiid
	 *            the gui id
	 * @return an appointment object or null if not found
	 */
	public Appointment getCurrentAppointment() {
		return this.appointments.get(this.currentSelectionGuiId);
	}

	/**
	 * sets the new current appointment
	 * 
	 * @param newguiid
	 *            the new gui-ID
	 * @return true, if successfull, fail, if ID not found
	 */
	public boolean changeAppointment(Integer newguiid) {
		this.currentSelectionGuiId = newguiid;

		// todo: check newguiid if valid

		fireAppointmentChangedEvent();

		return true;
	}

	/**
	 * method is called when the date changes
	 * 
	 * @return true if method succeeded
	 */
	public boolean addAppointment(Appointment entry) {
		if (entry != null) {
			this.latestId += 1;
			this.appointments.put(this.latestId, entry);
			return true;
		}

		return false;
	}

	/**
	 * Loads appointments by a given date
	 * 
	 * @param datetoload
	 *            the date from that appointments should be loaded
	 * @return the count of appointments loaded
	 */
	public Integer loadAppointmentsByDate(Date datetoload) {
		if(datetoload == null) {
			return -1;
		}
		
		this.appointments.clear();

		List<Appointment> entries = this.dbservice.getAppointment(datetoload);
		if (entries == null) {
			return -2;
		}

		for (Appointment entry : entries) {
			this.addAppointment(entry);
		}

		return entries.size();
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
	
	public Integer addExpensetoCurrentPatient(String descpription, String amount) {
		Double value = null;
		
		if(amount == null ) {
			return -1;
		}
		try {
			value = Double.parseDouble(amount);
		} 
		catch(NumberFormatException ex) {
			return -1;
		}
		
		if(descpription == null) {
			return -2;
		}
		if(descpription.isEmpty()) {
			return -2;
		}
		
		try {
			Appointment appointment = this.getCurrentAppointment();
			
			Patient patient = appointment.getPatient();
			
			ExpensesEntry newexpense = new ExpensesEntry();
			newexpense.setExpensesDescription(descpription);
			newexpense.setPrice(value);
			
			patient.getExpenses().add(newexpense);
			
			this.dbservice.saveOrUpdate(appointment);
		}
		catch(NumberFormatException ex) {
			return -4;
		}
		
		return 0;
	}
	
	public Integer deleteExepenseOfCurrentPatient(Long expense_id) {
		if(expense_id == null) {
			return -4;
		}
		
		Patient patient = this.getCurrentAppointment().getPatient();
		if(patient == null) {
			return -3;
		}
		List<ExpensesEntry> expenses = patient.getExpenses();
		if(expenses == null) {
			return -1;
		}
		else {
			try { 
				for(ExpensesEntry entry : expenses) {
					if(entry.getExpensesId() == expense_id) {
						expenses.remove(entry);
						this.dbservice.saveOrUpdate(patient);
						//this.dbservice.delete(entry); doesnt work
						return 1;
					}
				}
			}
			catch(Exception ex) {
				return -2;
			}
		}
		
		return 1;
	}
	
}
