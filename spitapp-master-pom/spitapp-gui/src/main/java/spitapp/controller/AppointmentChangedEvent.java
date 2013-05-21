package spitapp.controller;

import java.util.EventObject;

/**
 * Class for the Event when an Appoinment changes
 * @author Roger Jaggi
 *
 */
public class AppointmentChangedEvent extends java.util.EventObject {
     
	/**
	 * Class Serial number
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param source the event source, typically the AppointmentController
	 */
     public AppointmentChangedEvent(Object source) {
         super(source);
     }
}