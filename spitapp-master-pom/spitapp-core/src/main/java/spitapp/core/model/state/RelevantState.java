package spitapp.core.model.state;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import spitapp.core.model.TerminEintrag;

/**
 * State for Statepattern
 * 
 * @author green
 *
 */
public class RelevantState implements TerminState {

	private TerminEintrag termin;
	
	public RelevantState(TerminEintrag termin){
		this.termin = termin;
	}
	
	public boolean isRelevant() {
		// TODO Auto-generated method stub
		return true;
	}

	public void updateState(Date date) {
		if(!DateUtils.isSameDay(this.termin.getTerminDate(),date)){
			termin.setState(new IrrelevantState(termin));
		}

	}

}
