package spitapp.core.facade;

import java.util.Date;
import java.util.List;

import spitapp.core.model.Appointment;
import spitapp.core.model.SpitappSaveable;
import spitapp.core.model.User;
import spitapp.core.service.DatabaseService;
import spitapp.core.service.UserService;

/**
 * The UiServiceFacade is a additional abstraction layer.
 * The only thing who calls our services, the services shouldn't know
 * each other.
 * 
 * Because of this layering out gui team don't need to know the concrecte implementation.
 * 
 * @author green
 *
 */
public class UiServiceFacade {
	private DatabaseService dbService;
	//private PdfService pdfService;
	private UserService userService;

	public UiServiceFacade(){
		this.dbService = new DatabaseService();
		//this.pdfService = new PdfService();
		this.userService = new UserService();
	}

	public List<Appointment> getAppointments(Date date){
		return dbService.getAppointment(date);
	}
	
	/**
	 * Save something on Spitapp
	 * 
	 * @param somethingToSave
	 */
	public void saveModel(SpitappSaveable somethingToSave){
		dbService.saveOrUpdate(somethingToSave);
	}
	
	/**
	 * Delete Something on Spitapp
	 * 
	 * @param somethingToDelete
	 */
	public void deleteModel(SpitappSaveable somethingToDelete){
		dbService.delete(somethingToDelete);
	}
	
	/**
	 * Validates a username, password combination on spitapp
	 * 
	 * @param username
	 * @param password
	 * @return true if username and password matches
	 */
	public boolean validateLogin(String username, String password){
		User user = dbService.getUserByUsername(username);
		return userService.validateUserAndPasswort(user, password);
	}
	
	/**
	 * Creates a userLogin on spitapp.
	 * 
	 * @param username
	 * @param password
	 */
	public void createUserLogin(String username, String password){
		User user = userService.storeUserAndPassword(username, password);
		dbService.saveOrUpdate(user);
	}
}
