package spitapp.core.model;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Entity;

import spitapp.core.model.state.IrrelevantState;
import spitapp.core.model.state.TerminState;

@Entity
public class TerminEintrag {

	private Long terminId;
	
	private String beschreibung;

	private Date terminDate;
	
	private Patient patient;
	
	private TerminState state;

	public TerminEintrag(){
		state = new IrrelevantState(this);
	}
	
	public Long getTerminId() {
		return terminId;
	}
	

	public TerminState getState() {
		return state;
	}

	public void setState(TerminState state) {
		this.state = state;
	}

	public void setTerminId(Long terminId) {
		this.terminId = terminId;
	}

	public String getBeschreibung() {
		return beschreibung;
	}
	

	public Date getTerminDate() {
		return terminDate;
	}

	public void setTerminDate(Date terminDate) {
		this.terminDate = terminDate;
	}

	public void updateState(Date date){
		state.updateState(date);
	}
	

	public boolean isRelevant(){
		return state.isRelevant();
	}
	
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	

}
