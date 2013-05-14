package spitapp.core.model;

import java.util.List;

import org.hibernate.annotations.Entity;

@Entity
public class TerminEintrag {

	private Long terminId;
	
	private String beschreibung;
	
	private List<Patient> patienten;

	public Long getTerminId() {
		return terminId;
	}

	public void setTerminId(Long terminId) {
		this.terminId = terminId;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public List<Patient> getPatienten() {
		return patienten;
	}

	public void setPatienten(List<Patient> patienten) {
		this.patienten = patienten;
	}

}
