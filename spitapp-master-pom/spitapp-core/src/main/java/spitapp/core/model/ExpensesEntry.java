package spitapp.core.model;

/**
 * Hibernate Mappingclass for ExpensesEntry table
 * 
 * @author green
 *
 */
public class ExpensesEntry {
	private long expensesId;
	
	private String expensesDescription;
	private double price;
	

	public long getExpensesId() {
		return expensesId;
	}

	public void setExpensesId(long expensesId) {
		this.expensesId = expensesId;
	}

	public String getExpensesDescription() {
		return expensesDescription;
	}

	public void setExpensesDescription(String expensesDescription) {
		this.expensesDescription = expensesDescription;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
