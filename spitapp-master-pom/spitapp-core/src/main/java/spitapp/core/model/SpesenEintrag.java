package spitapp.core.model;

public class SpesenEintrag {
	private long SpesenEintID;
	
	private String Desc;
	private double Price;
	
	public long getSpesenEintID() {
		return SpesenEintID;
	}
	
	public void setSpesenEintID(long spesenEintID) {
		SpesenEintID = spesenEintID;
	}
	
	public String getDesc() {
		return Desc;
	}
	
	public void setDesc(String desc) {
		Desc = desc;
	}
	
	public double getPrice() {
		return Price;
	}
	
	public void setPrice(double price) {
		Price = price;
	}

	

}
