package spitapp.core.model;

/**
 * Hibernate Mappingclass for ExpensesEntry table
 * 
 * @author green
 *
 */
public class ExpensesEntry {
	private long SpesenEintID;
	
	private String SpesenDesc;
	private double Price;
	
	

	public long getSpesenEintID() {
		return SpesenEintID;
	}

	public void setSpesenEintID(long SpesenEintID) {
		this.SpesenEintID = SpesenEintID;
	}

	public double getPrice() {
		return Price;
	}

	public void setPrice(double price) {
		this.Price = price;
	}

	public String getSpesenDesc() {
		return this.SpesenDesc;
	}

	public void setSpesenDesc(String spesenDesc) {
		this.SpesenDesc = spesenDesc;
	}

	

}
