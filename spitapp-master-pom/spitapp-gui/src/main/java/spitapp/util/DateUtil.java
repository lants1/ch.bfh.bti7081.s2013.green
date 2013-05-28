/**
 * 
 */
package spitapp.util;

import java.util.Date;


/**
 * @author Roger Jaggi
 *
 */
public class DateUtil {
	
	/**
	 * returns todays date with the submitted time
	 * @param timeinput the time in the format hh:mm
	 * @return the date if input is valid or null on failure
	 */
	@SuppressWarnings("deprecation")
	public static Date getTodayWithSpecificTime(String timeinput) {
		// Let's prepare the input
		String[] parts = timeinput.split(":");
		
		if(parts.length != 2) {
			return null;
		}
		
		Integer hours = null;
		Integer minutes = null;
		try {
			hours = Integer.parseInt(parts[0]);
			minutes = Integer.parseInt(parts[1]);
		} 
		catch(NumberFormatException ex) {
			return null;
		}
		
		if(hours > 24 || hours < 0 || minutes > 59 || minutes < 0) {
			return null;
		}
		
		Date theDate = new Date();
		theDate.setHours(hours);
		theDate.setMinutes(minutes);
		
		return theDate;
	}
}
