package spitapp.core.model.state;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import spitapp.core.model.TerminEintrag;

public class IrrelevantState implements TerminState {

	private TerminEintrag termin;
	
	public IrrelevantState(TerminEintrag termin){
		this.termin = termin;
	}
	
	public boolean isRelevant() {
		// TODO Auto-generated method stub
		return false;
	}

	public void updateState(Date date) {
		if(DateUtils.isSameDay(this.termin.getTerminDate(),date)){
			termin.setState(new RelevantState(termin));
		}
		
	}

}
