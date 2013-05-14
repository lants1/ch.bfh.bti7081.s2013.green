package spitapp.core.model;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class RelevantState implements TerminState {

	private TerminEintrag termin;
	
	public RelevantState(TerminEintrag termin){
		this.termin = termin;
	}
	
	public boolean isRelevant() {
		// TODO Auto-generated method stub
		return false;
	}

	public void updateState(Date date) {
		if(!DateUtils.isSameDay(this.termin.getTerminDate(),date)){
			termin.setState(new IrrelevantState(termin));
		}

	}

}
