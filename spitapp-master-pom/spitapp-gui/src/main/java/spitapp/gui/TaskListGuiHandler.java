package spitapp.gui;

import java.util.List;

import spitapp.controller.AppointmentChangedEvent;
import spitapp.controller.AppointmentController;
import spitapp.core.model.Task;
import spitapp.core.model.Patient;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window;

/**
 * Class for displaying the tasks
 * @author jaggr2, vonop1
 *
 */
public class TaskListGuiHandler extends DetailGuiHandler {
	
	/**
	 * generated serial
	 */
	private static final long serialVersionUID = -9094411487283778870L;
	
	/**
	 * the GUI components
	 */
	private AbsoluteLayout mainLayout;
	private Table table_task;
	private Button button_markasdone;
	
	/**
	 * The constructor
	 */
	public TaskListGuiHandler(AppointmentController controller) {
		super(controller);
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}

	/**
	 * generates the Layout
	 * @return the Layout as AbsolutLayout
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
		table_task.addContainerProperty("Erledigt", String.class,  null);
		// Allow selecting items from the table.
		table_task.setSelectable(true);
		// Send changes in selection immediately to server.
		table_task.setImmediate(true);
		// Handle selection change.
		table_task.addValueChangeListener(new ValueChangeListener() {
		    /**
			 * generated serial
			 */
			private static final long serialVersionUID = 1136199457931977289L;

			public void valueChange(ValueChangeEvent event) {
				
				// only activate the Button if the task is valid and not done
				if(table_task.getValue() != null) {
					Task entry = controller.getTaskById((Long)table_task.getValue() );
					if(entry != null && !entry.isDone()) {
						button_markasdone.setEnabled(true);
						return;
					}
				}
				
		    	button_markasdone.setEnabled(false);
		    }
		});
		mainLayout.addComponent(table_task, "top:17.0px;left:0.0px;");

		// button_markasdone
		button_markasdone = new Button();
		button_markasdone.setCaption("Als erledigt markieren");
		button_markasdone.setImmediate(true);
		button_markasdone.setEnabled(false);
		button_markasdone.setWidth("-1px");
		button_markasdone.setHeight("-1px");
		button_markasdone.addClickListener(new Button.ClickListener() {
			/**
			 * generated serial
			 */
			private static final long serialVersionUID = -6903210547693151759L;

			public void buttonClick(ClickEvent event) {
				
				Window subWindow = new Window("Bitte benötigte Zeit angeben");
				subWindow.setModal(true);
				subWindow.setWidth(400, Unit.PIXELS);
				subWindow.setHeight(100, Unit.PIXELS);
				subWindow.setPositionX(300);
				subWindow.setPositionY(200);
				subWindow.setContent(new TaskDoneSubGui(controller, (Long)table_task.getValue(), subWindow));
				subWindow.addCloseListener(new Window.CloseListener() {	
					/**
					 * generated serial
					 */
					private static final long serialVersionUID = -4404474000034908974L;

					@Override
					public void windowClose(CloseEvent e) {
						reload_tasks();
					}
				});
				        
				getUI().addWindow(subWindow);
			}
		}); 
		mainLayout.addComponent(button_markasdone, "top:17.0px;left:420.0px;");
		
		return mainLayout;
	}

	/**
	 * reload all tasks from a patient from database
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
				table_task.setCaption("Aufgaben: " + Integer.toString(tasks.size()));
		
				for(Task entry : tasks) {
					// Add a row into the table as object array
					table_task.addItem(new Object[] { entry.getDescription(), ( entry.isDone() ? "Ja" : "Nein" ) }, entry.getTaskId());
				}	
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
