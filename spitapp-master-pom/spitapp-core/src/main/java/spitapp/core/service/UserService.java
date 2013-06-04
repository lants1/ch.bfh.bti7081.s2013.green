package spitapp.core.service;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.digest.DigestUtils;

import spitapp.core.model.User;

/**
 * UserService handles every login things...
 * 
 * @author lants1
 *
 */
class UserService {
	
	private final static Logger logger =
	          Logger.getLogger(UserService.class.getName());

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
			logger.log(Level.WARNING, "Login failed for User:"+user+" and PW:"+password);
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