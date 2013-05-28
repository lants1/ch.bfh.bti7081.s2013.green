package spitapp.core.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import spitapp.core.model.Appointment;
import spitapp.core.model.User;

/**
 * UserService
 * 
 * @author green
 *
 */
public class UserService {

	// Should be in a keystore file...
	private final String salt = "hoschiç%&/()bim%&/(poschi";
	DatabaseService dbService = new DatabaseService();
	
	public void storeUserAndPassword(String username, String password) {
		User user = new User();
		byte[] hash = getHash(password);
		user.setUserName(username);
		user.setPassword(hash);
		dbService.saveOrUpdate(user);
	}
	
	public boolean validateUserAndPasswort(String username, String password){
		User user = dbService.getUserByUsername(username);
		if(user != null){
			byte[] givenPw = getHash(password);
			return Arrays.equals(givenPw, user.getPassword());
		}
		else
		{
			return false;
		}
	}
	
	private byte[] getHash(String password){
		return DigestUtils.md5(password + salt);
	}
}