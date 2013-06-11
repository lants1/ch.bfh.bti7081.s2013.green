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
	private Table table_task;
	private Button button_reactivate;
	
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
		table_task = new Table();
		table_task.setImmediate(false);
		table_task.setWidth("400px");
		table_task.setHeight("303px");
		table_task.addContainerProperty("Aufgabe", String.class,  null);
		table_task.addContainerProperty("Begonnen am", Date.class,  null);
		table_task.addContainerProperty("Dauer (min)", Integer.class,  null);
		// Allow selecting items from the table.
		table_task.setSelectable(true);
		// Send changes in selection immediately to server.
		table_task.setImmediate(true);
		// Handle selection change.
		table_task.addValueChangeListener(new ValueChangeListener() {
		    /**
			 * generated serial
			 */
			private static final long serialVersionUID = 7929535357941491648L;

			public void valueChange(ValueChangeEvent event) {
		    	button_reactivate.setEnabled(table_task.getValue() != null);
		    }
		});
		mainLayout.addComponent(table_task, "top:17.0px;left:0.0px;");

		// button_reactivate
		button_reactivate = new Button();
		button_reactivate.setCaption("Reaktivieren");
		button_reactivate.setImmediate(true);
		button_reactivate.setEnabled(false);
		button_reactivate.setWidth("-1px");
		button_reactivate.setHeight("-1px");
		button_reactivate.addClickListener(new Button.ClickListener() {
			/**
			 * generated serial
			 */
			private static final long serialVersionUID = 3711851523654603355L;

			public void buttonClick(ClickEvent event) {
				AppointmentController.Codes returnvalue = controller.reactivateTaskOfCurrentPatient((Long)table_task.getValue());
				
				switch(returnvalue) {
				case SUCCESS:
					Notification.show("Der Zeiteintrag wurde zurückgesetzt!",
			                  "Unter ToDo's kann nun eine neue Zeit erfasst werden.",
			                  Notification.Type.TRAY_NOTIFICATION);
					
					reload_tasks();
					break;
				default:
					button_reactivate.setComponentError(new SystemError("Löschen fehlgeschlagen! Fehlercode: " + returnvalue.getMessage()));
					break;
				}
			}
		}); 
		mainLayout.addComponent(button_reactivate, "top:17.0px;left:420.0px;");
		
		return mainLayout;
	}

	/**
	 * reloads the task table
	 */
	public void reload_tasks() {
		
		Patient patient = this.controller.getCurrentAppointment().getPatient();
		
		table_task.removeAllItems();
		
		List<Task> tasks = patient.getTasks();
		if(tasks == null) {
			table_task.setCaption("Ungültige Aufgabenwerte!");
		}
		else {
			try { 
				int count = 0;
				for(Task entry : tasks) {
					// Add a row into the table as object array
					if(entry.isDone()) {
						table_task.addItem(new Object[] { entry.getDescription(), entry.getStarttime(), entry.getDuration() }, entry.getTaskId());
						count += 1;
					}
				}
				
				table_task.setCaption("Aufgaben: " + Integer.toString(count) + "/" + Integer.toString(tasks.size()));
			}
			catch(Exception ex) {
				table_task.setCaption(ex.toString());
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
