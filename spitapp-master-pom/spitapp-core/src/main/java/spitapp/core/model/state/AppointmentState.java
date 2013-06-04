package spitapp.core.model.state;

import java.util.Date;

/**
 * State Interface for concret States in Pattern
 * @author lants1, bohnp1
 *
 */
public interface AppointmentState {

	/**
	 * Is relevant Method returns the State of the Termin according to the concret State.
	 * 
	 * @return
	 */
	public boolean isRelevant();

	/**
	 * Method to update the State according to a Date
	 * 
	 * @param date
	 */
	public void updateState(Date date);
}
