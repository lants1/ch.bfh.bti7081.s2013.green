package spitapp.gui;

import spitapp.controller.*;

import com.vaadin.ui.CustomComponent;

/**
 * Base class for detail views
 * @author Roger Jaggi jaggr2
 *
 */
public abstract class DetailGuiHandler extends CustomComponent implements AppointmentChangedListener  {
	
	/**
	 * generated serial
	 */
	private static final long serialVersionUID = -4845867157608520500L;
	
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
