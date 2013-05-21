package spitapp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import spitapp.core.model.Appointment;
import spitapp.core.service.DatabaseService;

/**
 * controlls the appointment listing and fires an event, when an appointment changes
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
	 * @param dbservice The reference to the DatabaseService to use
	 */
	public AppointmentController(DatabaseService dbservice) {
		this.dbservice = dbservice;
	}
	
	/**
	 * Returns the appointment list with their associeted gui-IDs
	 * @return map of appointments
	 */
	public HashMap<Integer, Appointment> getAppointments() {
		return this.appointments;
	}

	/**
	 * Gets an appointment object by it's GUI id
	 * @param guiid the gui id
	 * @return an appointment object or null if not found
	 */
	public Appointment getCurrentAppointment() {
		return this.appointments.get(this.currentSelectionGuiId);
	}
	
	/**
	 * sets the new current appointment
	 * @param newguiid the new gui-ID
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
	 * @return true if method succeeded
	 */
	public void addAppointment(Appointment entry) {
		this.latestId += 1;
		this.appointments.put(this.latestId, entry);
	}	
	
	/**
	 * Loads appointments by a given date
	 * @param datetoload the date from that appointments should be loaded
	 * @return the count of appointments loaded
	 */
	public Integer loadAppointmentsByDate(Date datetoload) {
		this.appointments.clear();
		
		List<Appointment> entries = this.dbservice.getAppointment(datetoload);
		
		for (Appointment entry: entries) {
			this.addAppointment(entry);
		}		
		
		return entries.size();
	}

	/**
	 * adds a further event listener
	 * @param listener the listener to add
	 */
	public synchronized void addAppointmentChangedEventListener( AppointmentChangedListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Removes an Event Listener
	 * @param listener the Listener to remove
	 */
	public synchronized void removeAppointmentChangedEventListener( AppointmentChangedListener listener) {
		this.listeners.remove(listener);
	}

	/**
	 * this method can be called whenever you want to notify the event listeners of the AppointmentChangedEvent
	 */
	private synchronized void fireAppointmentChangedEvent() {
		AppointmentChangedEvent event = new AppointmentChangedEvent(this);
		Iterator<AppointmentChangedListener> i = this.listeners.iterator();
		while (i.hasNext()) {
			i.next().handleAppointmentChangedEvent(event);
		}
	}
}
