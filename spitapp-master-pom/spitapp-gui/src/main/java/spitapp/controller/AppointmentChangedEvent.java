package spitapp.controller;

/**
 * Class for the Event when an Appointment changes
 * @author jaggr2
 *
 */
public class AppointmentChangedEvent extends java.util.EventObject {
     
	/**
	 * Class Serial number
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Initializes a new AppointmentChangedEvent
	 * @param source the event source, typically the AppointmentController
	 */
     public AppointmentChangedEvent(Object source) {
         super(source);
     }
}