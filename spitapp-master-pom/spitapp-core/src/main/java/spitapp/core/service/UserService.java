package spitapp.core.service;

import java.util.Arrays;
import org.apache.commons.codec.digest.DigestUtils;

import spitapp.core.model.User;

/**
 * UserService handles every login things...
 * 
 * @author green
 *
 */
class UserService {

	// Should be in a keystore file...
	private final String salt = "hoschiç%&/()bim%&/(poschi";
	
	/**
	 * Creates a new UserLogin
	 * 
	 * @param username
	 * @param password
	 */
	User storeUserAndPassword(String username, String password) {
		User user = new User();
		byte[] hash = getHash(password);
		user.setUserName(username);
		user.setPassword(hash);
		return user;
	}
	
	/**
	 * Used to validate username/password input
	 * 
	 * @param username
	 * @param password
	 * @return true if user and password are succesfully validated...
	 */
	boolean validateUserAndPasswort(User user, String password){
		if(user != null){
			byte[] givenPw = getHash(password);
			return Arrays.equals(givenPw, user.getPassword());
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Helpermethod, hashes a value with a salt...
	 * 
	 * @param password
	 * @return
	 */
	private byte[] getHash(String password){
		return DigestUtils.md5(password + salt);
	}
}