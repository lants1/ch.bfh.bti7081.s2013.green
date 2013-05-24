package spitapp.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import spitapp.controller.AppointmentChangedEvent;
import spitapp.controller.AppointmentController;
import spitapp.core.model.Appointment;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class AppointmentGuiHandler extends DetailGuiHandler {
	
	// the gui components
	private AbsoluteLayout mainLayout;
	private Button forward;
	private PopupDateField datePopup;
	private Button backward;
	private Table appointments;
	
	private TabSheet referenced_detailtab = null;
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public AppointmentGuiHandler(AppointmentController controller, TabSheet detailtab) {
		super(controller);
		
		this.referenced_detailtab = detailtab;

		buildMainLayout();
		setCompositionRoot(mainLayout);

		// initialize with actual date
		DateChanged(datePopup.getValue());
	}
	

	@Override
	public void handleAppointmentChangedEvent(AppointmentChangedEvent e) {
		// do nothing here
	}
	
	/**
	 * method is called when the date changes to handle the new appointments
	 * @param newDate the chosen date
	 */
	public void DateChanged(Date newDate) {
		referenced_detailtab.setVisible(false);
		
		if(appointments.removeAllItems()) {
			
			
			Integer itemcount = this.controller.loadAppointmentsByDate(newDate);
			appointments.setCaption("Es wurden " + itemcount.toString() + " Einträge gefunden.");
			
			if( itemcount > 0 ) {

				Iterator<Map.Entry<Integer, Appointment>> it = controller.getAppointments().entrySet().iterator();
			    while (it.hasNext()) {
			    	Map.Entry<Integer, Appointment> entry = it.next();
			    	
					Date fullDate = entry.getValue().getFromDate();
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
					String shortTimeStr = sdf.format(fullDate);
					
					// Add a row into the table as object array
					Integer pos = (Integer)appointments.addItem(new Object[] { entry.getValue().getPatient().getFirstName() + 
							" " + entry.getValue().getPatient().getLastName(), shortTimeStr }, entry.getKey());
					
			        //it.remove(); // avoids a ConcurrentModificationException
			    }
			}
			else {
				
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	private AbsoluteLayout buildMainLayout() {
		// create the top layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		//layout for Appointment section
		VerticalLayout terminLayout = new VerticalLayout();
		terminLayout.setMargin(true);
		
		//layout for the buttons forward, date, backward
		HorizontalLayout buttonLayout = new HorizontalLayout();

		// table_termine
		appointments = new Table("Termine");
		appointments.setWidth("100.0%");
		appointments.setHeight("-1px");
		/* Define the names and data types of columns.
		 * The "default value" parameter is meaningless here. */
		appointments.addContainerProperty("Patient", String.class,  null);
		appointments.addContainerProperty("Uhrzeit",  String.class,  null);
		// Allow selecting items from the table.
		appointments.setSelectable(true);
		// Send changes in selection immediately to server.
		appointments.setImmediate(true);
		// Handle selection change.
		appointments.addValueChangeListener(new ValueChangeListener() {
		    public void valueChange(ValueChangeEvent event) {
		    	// appointment changed
		    	appointments.setCaption("Selected: " + appointments.getValue());
		    	
		    	if( appointments.getValue() != null) {
		    		referenced_detailtab.setVisible(true);
		    		controller.changeAppointment((Integer)appointments.getValue());
		    	}
		    	else {
		    		referenced_detailtab.setVisible(false);
		    	}
		    }
		});
		
		// button_rueckwaerts
		backward = new Button();
		backward.setCaption("Rückwärts");
		backward.setImmediate(true);
		backward.setWidth("-1px");
		backward.setHeight("-1px");
		backward.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(datePopup.getValue());
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				datePopup.setValue(calendar.getTime());
			}
		}); 	
		
		// dateField_datum
		datePopup = new PopupDateField();
		datePopup.setImmediate(true);
		datePopup.setWidth("-1px");
		datePopup.setHeight("-1px");
		datePopup.setDateFormat("dd-MM-yyyy");
		datePopup.setValue(new Date());
		datePopup.addValueChangeListener(new ValueChangeListener() {
		    public void valueChange(ValueChangeEvent event) {
		        DateChanged(datePopup.getValue());
		    }
		});
		
		// buttonForward
		forward = new Button();
		forward.setCaption("Vorwärts");
		forward.setImmediate(true);
		forward.setWidth("-1px");
		forward.setHeight("-1px");
		forward.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(datePopup.getValue());
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				datePopup.setValue(calendar.getTime());
			}
		}); 		
		
		//Set the layout together
		buttonLayout.addComponent(backward);
		buttonLayout.addComponent(datePopup);
		buttonLayout.addComponent(forward);
		terminLayout.addComponent(appointments);
		terminLayout.addComponent(buttonLayout);
		mainLayout.addComponent(terminLayout);
		
		return mainLayout;
	}

}
