package spitapp.gui;

import spitapp.controller.*;

import com.vaadin.ui.CustomComponent;

/**
 * Base class for detail views
 * @author Roger Jaggi jaggr2
 *
 */
@SuppressWarnings("serial")
public abstract class DetailGuiHandler extends CustomComponent implements AppointmentChangedListener  {
	
	/**
	 * reference to the AppointmentController
	 */
	protected AppointmentController controller = null;
	
	/**
	 * Constructor of Base Class for Detail views
	 */
	public DetailGuiHandler(AppointmentController ctrl) {
		if( ctrl != null ) {
			this.controller = ctrl;
			this.controller.addAppointmentChangedEventListener(this);
		}
	}

}
