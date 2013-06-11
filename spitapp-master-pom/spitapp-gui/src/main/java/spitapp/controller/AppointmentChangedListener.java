package spitapp.controller;

/**
 * An Interface for AppointmentChangedEvent Listeners
 * @author jaggr2
 *
 */
public interface AppointmentChangedListener {
	
	/**
	 * implement this method to receive the appointment changed event
	 * @param e the appointment changed event
	 */
	public void handleAppointmentChangedEvent(AppointmentChangedEvent e);
}
