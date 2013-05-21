package spitapp.controller;

/**
 * @author Roger Jaggi
 *
 */
public interface AppointmentChangedListener {
	
	/**
	 * implement this method to receive the appointment changed event
	 * @param e the appointment changed event
	 */
	public void handleAppointmentChangedEvent(AppointmentChangedEvent e);
}
