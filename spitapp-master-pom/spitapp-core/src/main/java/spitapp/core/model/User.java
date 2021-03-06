package spitapp.core.model;

/**
 * Hibernate mapping Class for Table User
 * 
 * @author lants1
 *
 */
public class User implements SpitappSaveable{

	private Long userId;
	
	private String userName;

	private byte[] password;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}
}
