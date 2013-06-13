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
	private Button addTask;
	private TextField txtDuration;
	private TextField txtStartTime;
	private Window parentWindow;
	
	/**
	 * reference to the Appointment Controller
	 */
	private AppointmentController controller;
	
	/**
	 * to current task id
	 */
	private Long taskId;
	
	/**
	 * Initializes a new TaskDoneSubGui
	 * @param controller a reference to the appointment controller
	 * @param taskId the id of the task to complete
	 * @param parentwindow a reference to the parent window
	 */
	public TaskDoneSubGui(AppointmentController controller, Long taskId, Window parentwindow) {
		this.controller = controller;
		this.taskId = taskId;
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
		
		// Textfield txtStartTime
		txtStartTime = new TextField("Aufgabe gestartet um (hh:mm)");
		txtStartTime.setImmediate(false);
		txtStartTime.setWidth("80px");
		txtStartTime.setHeight("-1px");
		txtStartTime.addValidator(new TimeValidator());
		txtStartTime.setRequired(true);
		txtStartTime.setInvalidAllowed(false);
		txtStartTime.setInputPrompt("hh:mm");
		mainLayout.addComponent(txtStartTime, "top:37.0px;left:9.0px;");
		
		// Textfield txtDuration
		txtDuration = new TextField("Benötigte Zeit in Minuten");
		txtDuration.setImmediate(false);
		txtDuration.setWidth("80px");
		txtDuration.setHeight("-1px");
		txtDuration.addValidator(new DurationValidator());
		txtDuration.setRequired(true);
		txtDuration.setInvalidAllowed(false);
		txtDuration.setInputPrompt("z.B. 30");
		mainLayout.addComponent(txtDuration, "top:37.0px;left:200.0px;");
		
		// button addTask
		addTask = new Button();
		addTask.setCaption("Eintragen");
		addTask.setImmediate(true);
		addTask.setWidth("-1px");
		addTask.setHeight("-1px");
		addTask.addClickListener(new Button.ClickListener() {
			/**
			 * generated serial
			 */
			private static final long serialVersionUID = 1245454534534L;

			public void buttonClick(ClickEvent event) {
				
				// check if Input Validator accept the input
				if (!txtStartTime.isValid() || !txtDuration.isValid()) {
		            return;
		        }
				
				String startTime = txtStartTime.getValue();
				String duration = txtDuration.getValue();
								
				AppointmentController.Codes returnValue = controller.completeTaskOfCurrentPatient(taskId, startTime, duration);
				switch(returnValue) {
				case SUCCESS:
					parentWindow.close();
					break;
					
				case DURATION_IS_EMPTY:
				case INVALID_DURATION_FORMAT:
				case STARTTIME_IS_EMPTY:
				case INVALID_STARTTIME_FORMAT:
					txtStartTime.setComponentError(new UserError(returnValue.getMessage()));
					break;
				default:
					addTask.setComponentError(new SystemError("Ooops, ein Systemfehler ist aufgetreten: " + returnValue.getMessage()));
					break;					
				}
			}
		}); 
		mainLayout.addComponent(addTask, "top:67.0px;left:200.0px;");
		
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
        	
            if (DateUtil.getTodayWithSpecificTime(value) == null) {
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
