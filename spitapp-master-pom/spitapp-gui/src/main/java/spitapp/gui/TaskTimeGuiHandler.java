package spitapp.gui;

import java.util.Date;
import java.util.List;

import spitapp.controller.AppointmentChangedEvent;
import spitapp.controller.AppointmentController;
import spitapp.core.model.Task;
import spitapp.core.model.Patient;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.SystemError;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;

/**
 * Displays the task times
 * @author jaggr2, vonop1
 *
 */
public class TaskTimeGuiHandler extends DetailGuiHandler {
	
	/**
	 * generated serial 
	 */
	private static final long serialVersionUID = 6514457886849736293L;
	
	/**
	 * the GUI components
	 */
	private AbsoluteLayout mainLayout;
	private Table tableTask;
	private Button buttonReactivate;
	
	/**
	 * The constructor
	 */
	public TaskTimeGuiHandler(AppointmentController controller) {
		super(controller);
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}

	/**
	 * Generates the Main Layout
	 * @return the Layout as AbsoluteLayout
	 */
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// table_tasks
		tableTask = new Table();
		tableTask.setImmediate(false);
		tableTask.setWidth("400px");
		tableTask.setHeight("303px");
		tableTask.addContainerProperty("Aufgabe", String.class,  null);
		tableTask.addContainerProperty("Begonnen am", Date.class,  null);
		tableTask.addContainerProperty("Dauer (min)", Integer.class,  null);
		// Allow selecting items from the table.
		tableTask.setSelectable(true);
		// Send changes in selection immediately to server.
		tableTask.setImmediate(true);
		// Handle selection change.
		tableTask.addValueChangeListener(new ValueChangeListener() {
		    /**
			 * generated serial
			 */
			private static final long serialVersionUID = 7929535357941491648L;

			public void valueChange(ValueChangeEvent event) {
		    	buttonReactivate.setEnabled(tableTask.getValue() != null);
		    }
		});
		mainLayout.addComponent(tableTask, "top:17.0px;left:0.0px;");

		// button_reactivate
		buttonReactivate = new Button();
		buttonReactivate.setCaption("Reaktivieren");
		buttonReactivate.setImmediate(true);
		buttonReactivate.setEnabled(false);
		buttonReactivate.setWidth("-1px");
		buttonReactivate.setHeight("-1px");
		buttonReactivate.addClickListener(new Button.ClickListener() {
			/**
			 * generated serial
			 */
			private static final long serialVersionUID = 3711851523654603355L;

			public void buttonClick(ClickEvent event) {
				AppointmentController.Codes returnvalue = controller.reactivateTaskOfCurrentPatient((Long)tableTask.getValue());
				
				switch(returnvalue) {
				case SUCCESS:
					Notification.show("Der Zeiteintrag wurde zurückgesetzt!",
			                  "Unter ToDo's kann nun eine neue Zeit erfasst werden.",
			                  Notification.Type.TRAY_NOTIFICATION);
					
					reload_tasks();
					break;
				default:
					buttonReactivate.setComponentError(new SystemError("Löschen fehlgeschlagen! Fehlercode: " + returnvalue.getMessage()));
					break;
				}
			}
		}); 
		mainLayout.addComponent(buttonReactivate, "top:17.0px;left:420.0px;");
		
		return mainLayout;
	}

	/**
	 * reloads the task table
	 */
	public void reload_tasks() {
		
		Patient patient = this.controller.getCurrentAppointment().getPatient();
		
		tableTask.removeAllItems();
		
		List<Task> tasks = patient.getTasks();
		if(tasks == null) {
			tableTask.setCaption("Ungültige Aufgabenwerte!");
		}
		else {
			try { 
				int count = 0;
				for(Task entry : tasks) {
					// Add a row into the table as object array
					if(entry.isDone()) {
						tableTask.addItem(new Object[] { entry.getDescription(), entry.getStarttime(), entry.getDuration() }, entry.getTaskId());
						count += 1;
					}
				}
				
				tableTask.setCaption("Aufgaben: " + Integer.toString(count) + "/" + Integer.toString(tasks.size()));
			}
			catch(Exception ex) {
				tableTask.setCaption(ex.toString());
			}
		}
	}
	
	/**
	 * fires when the user changes the appointment
	 */
	@Override
	public void handleAppointmentChangedEvent(AppointmentChangedEvent e) {
		if(controller.getCurrentAppointment() != null)
		{
			this.reload_tasks();
		}
	}

}
