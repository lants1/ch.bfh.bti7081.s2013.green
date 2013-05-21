package spitapp.core.model;

import java.util.Date;

import org.hibernate.annotations.Entity;

import spitapp.core.model.state.AppointmentState;
import spitapp.core.model.state.IrrelevantState;

/**
 * Hibernate Mappingclass for table Appointment
 * 
 * @author green
 *
 */
@Entity
public class Appointment {

	private Long terminId;
	
	private String appointmentDescription;

	private Date toDate;
	
	private Date fromDate;
	
	private Patient patient;
	
	private AppointmentState state;

	public Appointment(){
		state = new IrrelevantState(this);
	}
	
	public Long getTerminId() {
		return terminId;
	}
	

	public AppointmentState getState() {
		return state;
	}

	public void setState(AppointmentState state) {
		this.state = state;
	}

	public void setTerminId(Long terminId) {
		this.terminId = terminId;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public void updateState(Date date){
		state.updateState(date);
	}
	

	public boolean isRelevant(){
		return state.isRelevant();
	}

	public String getAppointmentDescription() {
		return appointmentDescription;
	}

	public void setAppointmentDescription(String appointmentDescription) {
		this.appointmentDescription = appointmentDescription;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
}