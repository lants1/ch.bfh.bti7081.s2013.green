package spitapp.gui;

import spitapp.controller.AppointmentController;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class TaskDoneSubGui extends CustomComponent {

	private AbsoluteLayout mainLayout;
	private Button button_add;
	private TextField textfield_duration;
	private TextField textfield_starttime;
	private Window parentWindow;
	
	private AppointmentController controller; 
	private Long task_id;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public TaskDoneSubGui(AppointmentController controller, Long task_id, Window parentwindow) {
		this.controller = controller;
		this.task_id = task_id;
		this.parentWindow = parentwindow;
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}

	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// textfield_starttime
		textfield_starttime = new TextField("Aufgabe gestartet um (hh:mm)");
		textfield_starttime.setImmediate(false);
		textfield_starttime.setWidth("80px");
		textfield_starttime.setHeight("-1px");
		mainLayout.addComponent(textfield_starttime, "top:37.0px;left:9.0px;");
		
		// textfield_duration
		textfield_duration = new TextField("Benötigte Zeit in Minuten");
		textfield_duration.setImmediate(false);
		textfield_duration.setWidth("80px");
		textfield_duration.setHeight("-1px");
		mainLayout.addComponent(textfield_duration, "top:37.0px;left:200.0px;");
		
		// button_add
		button_add = new Button();
		button_add.setCaption("Eintragen");
		button_add.setImmediate(true);
		button_add.setWidth("-1px");
		button_add.setHeight("-1px");
		button_add.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				String starttime = textfield_starttime.getValue();
				String duration = textfield_duration.getValue();
								
				Integer returnvalue = controller.completeTaskOfCurrentPatient(task_id, starttime, duration);
				switch(returnvalue) {
				case 1:  // all ok, reload expenses
					textfield_duration.setValue("");
					textfield_starttime.setValue("");
					
					parentWindow.close();
					//label_new.setValue("Neuer Speseneintrag:");
					break;
				case -1: // amount value in
					//label_new.setValue("Ungültiger Betrag!");
					break;
				case -2:
					//label_new.setValue("Spesenart darf nicht leer sein!");
					break;
				case -4:
					//label_new.setValue("Ooops. Beim Speichern gings in die Hose!");
					break;
				default: // amount value in
					//label_new.setValue("Unbekannter Fehler!");
					break;					
				}
			}
		}); 
		mainLayout.addComponent(button_add, "top:67.0px;left:200.0px;");
		
		return mainLayout;
	}
}
