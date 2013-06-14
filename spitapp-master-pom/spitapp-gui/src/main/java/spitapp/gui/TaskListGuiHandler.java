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
	private Table tableTask;
	private Button buttonMarkAsDone;
	
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
		tableTask = new Table();
		tableTask.setImmediate(false);
		tableTask.setWidth("400px");
		tableTask.setHeight("303px");
		tableTask.addContainerProperty("Aufgabe", String.class,  null);
		tableTask.addContainerProperty("Erledigt", String.class,  null);
		// Allow selecting items from the table.
		tableTask.setSelectable(true);
		// Send changes in selection immediately to server.
		tableTask.setImmediate(true);
		// Handle selection change.
		tableTask.addValueChangeListener(new ValueChangeListener() {
		    /**
			 * generated serial
			 */
			private static final long serialVersionUID = 1136199457931977289L;

			public void valueChange(ValueChangeEvent event) {
				
				// only activate the Button if the task is valid and not done
				if(tableTask.getValue() != null) {
					Task entry = controller.getTaskById((Long)tableTask.getValue() );
					if(entry != null && !entry.isDone()) {
						buttonMarkAsDone.setEnabled(true);
						return;
					}
				}
				
		    	buttonMarkAsDone.setEnabled(false);
		    }
		});
		mainLayout.addComponent(tableTask, "top:17.0px;left:0.0px;");

		// button_markasdone
		buttonMarkAsDone = new Button();
		buttonMarkAsDone.setCaption("Als erledigt markieren");
		buttonMarkAsDone.setImmediate(true);
		buttonMarkAsDone.setEnabled(false);
		buttonMarkAsDone.setWidth("-1px");
		buttonMarkAsDone.setHeight("-1px");
		buttonMarkAsDone.addClickListener(new Button.ClickListener() {
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
				subWindow.setContent(new TaskDoneSubGui(controller, (Long)tableTask.getValue(), subWindow));
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
		mainLayout.addComponent(buttonMarkAsDone, "top:17.0px;left:420.0px;");
		
		return mainLayout;
	}

	/**
	 * reload all tasks from a patient from database
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
				tableTask.setCaption("Aufgaben: " + Integer.toString(tasks.size()));
		
				for(Task entry : tasks) {
					// Add a row into the table as object array
					tableTask.addItem(new Object[] { entry.getDescription(), ( entry.isDone() ? "Ja" : "Nein" ) }, entry.getTaskId());
				}	
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
