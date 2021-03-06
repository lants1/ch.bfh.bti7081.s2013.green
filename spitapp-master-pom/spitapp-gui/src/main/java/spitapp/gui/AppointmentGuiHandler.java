package spitapp.gui;

import java.util.Date;

import spitapp.controller.AppointmentChangedEvent;
import spitapp.controller.AppointmentController;
import spitapp.core.model.Appointment;
import spitapp.util.DateUtil;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

/**
 * class to handle the appointment GUI
 * @author jaggr2, vonop1
 */
public class AppointmentGuiHandler extends DetailGuiHandler {
	
	/**
	 * Generated serial
	 */
	private static final long serialVersionUID = -8309924806551458350L;
	
	/**
	 * the GUI components
	 */
	private VerticalLayout mainLayout;
	private Button forward;
	private PopupDateField datePopup;
	private Button backward;
	private Table appointments;
	private SpitAppView spitAppParentView = null;	
	
	/**
	 * The constructor who initializes the layout
	 */
	public AppointmentGuiHandler(AppointmentController controller, SpitAppView partentView) {
		super(controller);
		this.spitAppParentView = partentView;
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// initialize with actual date
		dateChanged(datePopup.getValue());
	}
	
	/**
	 * method is called when the date changes to handle the new appointments
	 * @param newDate the chosen date
	 */
	public void dateChanged(Date newDate) {
		//spitAppParentView.tabs.setVisible(false);
		controller.changeAppointmentById(null);
		
		if(appointments.removeAllItems()) {
			AppointmentController.Codes returnvalue = this.controller.loadAppointmentsByDate(newDate);
			
			
			if(returnvalue.isSuccessfull()) {
				appointments.setCaption("Es sind " + String.valueOf(controller.getAppointments().size()) + " Patiententermine geplant:");
				
				for(Appointment entry : controller.getAppointments()) {
					
					// Add a row into the table as object array
					appointments.addItem(new Object[] { 
							entry.getPatient().getFirstName() + " " + 
							entry.getPatient().getLastName(), 
							DateUtil.formatDate(entry.getFromDate(), "HH:mm") + " - " + 
							DateUtil.formatDate(entry.getToDate(), "HH:mm")}, 
							entry.getTerminId());
					
				}
			}
			else {
				appointments.setCaption("Es sind keine Patiententermine geplant.");
			}
		}
	}

	/**
	 * Builds the main layout
	 * @return the mainLayout
	 */
	private VerticalLayout buildMainLayout() {
		
		/**
		 * create the top layout
		 */
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("-1");
		// top-level component properties
		setWidth("300px");
		setHeight("100.0%");

		/**
		 * table appointments
		 */
		appointments = new Table("Termine");
		appointments.setWidth("100.0%");
		appointments.setHeight("100.0%");
		/* Define the names and data types of columns.
		 * The "default value" parameter is meaningless here. */
		appointments.addContainerProperty("Patient", String.class,  null);
		appointments.addContainerProperty("Uhrzeit",  String.class,  null);
		// Allow selecting items from the table.
		appointments.setSelectable(true);
		// Send changes in selection immediately to server.
		appointments.setImmediate(true);
		// Handle selection change
		appointments.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 3950076798774688747L;
			public void valueChange(ValueChangeEvent event) {
				
				// change the appointment
				// Important: getValue() can be null, which is also a valid value
				controller.changeAppointmentById((Long)appointments.getValue());
		    }
		});
		
		/**
		 * button backward
		 */
		backward = new Button();
		backward.setCaption("Rückwärts");
		backward.setImmediate(true);
		backward.setWidth("-1px");
		backward.setHeight("-1px");
		backward.addClickListener(new Button.ClickListener() {
			
			private static final long serialVersionUID = 8404210712006504006L;
			
			public void buttonClick(ClickEvent event) {
				// Subtract 1 day
				datePopup.setValue(DateUtil.addDays(datePopup.getValue(), -1));
			}
		}); 	
		
		/**
		 * PopupDateField datePopup
		 */
		datePopup = new PopupDateField();
		datePopup.setImmediate(true);
		datePopup.setWidth("-1px");
		datePopup.setHeight("-1px");
		datePopup.setDateFormat("dd-MM-yyyy");
		datePopup.setValue(new Date());
		datePopup.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 939476374869668350L;
			
			public void valueChange(ValueChangeEvent event) {
				//date changed: custom day
				dateChanged(datePopup.getValue());
		    }
		});
		
		/**
		 * button forward
		 */
		forward = new Button();
		forward.setCaption("Vorwärts");
		forward.setImmediate(true);
		forward.setWidth("-1px");
		forward.setHeight("-1px");
		forward.addClickListener(new Button.ClickListener() {
			
			private static final long serialVersionUID = 8339349572929865124L;
			
			public void buttonClick(ClickEvent event) {
				//date changed: +1 day
				datePopup.setValue(DateUtil.addDays(datePopup.getValue(), 1));
			}
		}); 		
		
		/**
		 * layout for the buttons forward, date, backward
		 */
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setHeight("100px");
		buttonLayout.setWidth("100%");
		buttonLayout.setSpacing(true);
		buttonLayout.addComponent(backward);
		buttonLayout.addComponent(datePopup);
		buttonLayout.addComponent(forward);
		
		/**
		 * layout for the appointments
		 */
		HorizontalLayout appointmentLayout = new HorizontalLayout();
		appointmentLayout.setHeight("400px");
		appointmentLayout.setWidth("100%");
		appointmentLayout.addComponent(appointments);
		
		/**
		 * Set the layout together
		 */
		mainLayout.addComponent(appointmentLayout);
		mainLayout.addComponent(buttonLayout);
		mainLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
		return mainLayout;
	}
	
	/**
	 * fires when the user changes the appointment
	 */
	@Override
	public void handleAppointmentChangedEvent(AppointmentChangedEvent e) {
		
		boolean visibleSetting = controller.getCurrentAppointment() != null;
		
		if(spitAppParentView.tabs.isVisible() != visibleSetting) {
			
			// Display tab-controller only, if there is a valid appointment selected
			spitAppParentView.tabs.setVisible(visibleSetting);
		}
	}
}
