package spitapp.gui;

import spitapp.controller.AppointmentController;
import spitapp.util.DateUtil;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.server.SystemError;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextField;

/**
 * The TaskDone Dialog GUI
 * @author jaggr2, vonop1
 *
 */
public class TaskDoneSubGui extends CustomComponent {

	/**
	 * generated serial
	 */
	private static final long serialVersionUID = 15094564446L;
	
	/**
	 * the components
	 */
	private AbsoluteLayout mainLayout;
	private Button button_add;
	private TextField textfield_duration;
	private TextField textfield_starttime;
	private Window parentWindow;
	
	/**
	 * reference to the Appointment Controller
	 */
	private AppointmentController controller;
	
	/**
	 * to current task id
	 */
	private Long task_id;
	
	/**
	 * Initializes a new TaskDoneSubGui
	 * @param controller a reference to the appointment controller
	 * @param task_id the id of the task to complete
	 * @param parentwindow a reference to the parent window
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
		textfield_starttime.addValidator(new TimeValidator());
		textfield_starttime.setRequired(true);
		textfield_starttime.setInvalidAllowed(false);
		textfield_starttime.setInputPrompt("hh:mm");
		mainLayout.addComponent(textfield_starttime, "top:37.0px;left:9.0px;");
		
		// textfield_duration
		textfield_duration = new TextField("Benötigte Zeit in Minuten");
		textfield_duration.setImmediate(false);
		textfield_duration.setWidth("80px");
		textfield_duration.setHeight("-1px");
		textfield_duration.addValidator(new DurationValidator());
		textfield_duration.setRequired(true);
		textfield_duration.setInvalidAllowed(false);
		textfield_duration.setInputPrompt("z.B. 30");
		mainLayout.addComponent(textfield_duration, "top:37.0px;left:200.0px;");
		
		// button_add
		button_add = new Button();
		button_add.setCaption("Eintragen");
		button_add.setImmediate(true);
		button_add.setWidth("-1px");
		button_add.setHeight("-1px");
		button_add.addClickListener(new Button.ClickListener() {
			/**
			 * generated serial
			 */
			private static final long serialVersionUID = 1245454534534L;

			public void buttonClick(ClickEvent event) {
				
				// check if Input Validators accept the input
				if (!textfield_starttime.isValid() || !textfield_duration.isValid()) {
		            return;
		        }
				
				String starttime = textfield_starttime.getValue();
				String duration = textfield_duration.getValue();
								
				AppointmentController.Codes returnvalue = controller.completeTaskOfCurrentPatient(task_id, starttime, duration);
				switch(returnvalue) {
				case SUCCESS:
					parentWindow.close();
					break;
					
				case DURATION_IS_EMPTY:
				case INVALID_DURATION_FORMAT:
				case STARTTIME_IS_EMPTY:
				case INVALID_STARTTIME_FORMAT:
					textfield_starttime.setComponentError(new UserError(returnvalue.getMessage()));
					break;
				default:
					button_add.setComponentError(new SystemError("Ooops, ein Systemfehler ist aufgetreten: " + returnvalue.getMessage()));
					break;					
				}
			}
		}); 
		mainLayout.addComponent(button_add, "top:67.0px;left:200.0px;");
		
		return mainLayout;
	}

	/**
	 * Validator for validating the time values
	 * @author jaggr2
	 *
	 */
    private static final class TimeValidator extends AbstractValidator<String> {

        /**
		 * generated serial
		 */
		private static final long serialVersionUID = 11234131234123L;

		public TimeValidator() {
            super("Die eingegebene Zeit ist ungültig! Gültiges Beispiel: 11:30");
        }

        @Override
        protected boolean isValidValue(String value) {
            if(value == null) {
            	return true;
            }
        	
            if (DateUtil.getTodayWithSpecificTime( value ) == null) {
                return false;
            }
            return true;
        }

        @Override
        public Class<String> getType() {
            return String.class;
        }
    }
    
	/**
	 * Validator for validating the duration values
	 * @author jaggr2
	 *
	 */
    private static final class DurationValidator extends AbstractValidator<String> {

        /**
		 * generated serial
		 */
		private static final long serialVersionUID = 23459269284L;

		public DurationValidator() {
            super("Die eingegebene Minutenanzahl ist ungültig! Gültiges Beispiel: 30");
        }

        @Override
        protected boolean isValidValue(String value) {
    		// Regex looks for Minimum one digit
        	// Nulls values have to be accepted
    		if (value != null && !value.matches("\\d+")) {
                return false;
            }
    		
            return true;
        }

        @Override
        public Class<String> getType() {
            return String.class;
        }
    }
}
