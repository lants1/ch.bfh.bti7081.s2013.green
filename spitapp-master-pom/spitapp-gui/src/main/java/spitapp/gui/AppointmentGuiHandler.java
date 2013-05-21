package spitapp.gui;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.HashMap;

import spitapp.core.model.Appointment;
import spitapp.core.service.DatabaseService;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class AppointmentGuiHandler extends CustomComponent {

	private AbsoluteLayout mainLayout;
	private Button forward;
	private PopupDateField datePopup;
	private Button backward;
	private Table appointments;
	
	
	public HashMap<Integer, Appointment> entries = new HashMap<Integer, Appointment>();
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public AppointmentGuiHandler() {

		buildMainLayout();
		setCompositionRoot(mainLayout);

		// initialize with actual date
		DateChanged(datePopup.getValue());
	}
	
	/**
	 * method is called when an appointment selection changes
	 * @param newTerminId The ID of the selected appointment
	 */
	public void TerminEintragChanged(Integer newTerminId) {
		appointments.setCaption("Selected: " + appointments.getValue());	
	}
	
	/**
	 * method is called when the date changes
	 * @return true if method succeeded
	 */
	public boolean clearTermine() {
		if(appointments.removeAllItems()) {
			this.entries.clear();
			return true;
		}
		return false;
	}
	
	public boolean addTermin(Appointment termin) {
		Integer pos = (Integer)appointments.addItem(new Object[] { termin.getPatient().getFirstName() + " " + termin.getPatient().getLastName(), termin.getTerminDate().toString() }, null);
		if( pos != null) {
			this.entries.put(pos, termin);
			return true;
		}
		return false;
	}
	
	public void DateChanged(Date newDate) {
		clearTermine();
		
		DatabaseService dbservice = new DatabaseService();
		List<Appointment> termine = dbservice.getTermine(newDate);
		
		for (Appointment termin: termine) {
			this.addTermin(termin);
		}
		
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
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
		appointments.setImmediate(false);
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
		    	TerminEintragChanged((Integer)appointments.getValue());
		        
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
