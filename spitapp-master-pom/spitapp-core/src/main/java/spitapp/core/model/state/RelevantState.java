package spitapp.core.model.state;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import spitapp.core.model.Appointment;

/**
 * State for Statepattern
 * 
 * @author lants1, bohnp1
 *
 */
public class RelevantState implements AppointmentState {

	private Appointment termin;
	
	public RelevantState(Appointment termin){
		this.termin = termin;
	}
	
	public boolean isRelevant() {
		return true;
	}

	public void updateState(Date date) {
		if(!DateUtils.isSameDay(this.termin.getFromDate(),date)){
			termin.setState(new IrrelevantState(termin));
		}

	}

}
