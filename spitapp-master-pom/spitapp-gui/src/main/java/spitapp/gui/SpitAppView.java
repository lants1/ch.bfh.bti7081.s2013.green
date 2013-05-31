package spitapp.gui;

import java.util.Locale;

import spitapp.controller.AppointmentController;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.themes.Reindeer;

public class SpitAppView extends CustomComponent implements View {

    /**
	 * generated serial
	 */
	private static final long serialVersionUID = 922715202810106398L;

	public static final String NAME = "";
    
    Label logininfo = null;
    
    public AppointmentController controller = new AppointmentController();

    public SpitAppView() {
    	setSizeFull();
    	
    	// Set the root layout for the UI
    	VerticalLayout content = new VerticalLayout();
    	setCompositionRoot(content);
    	content.setLocale(Locale.GERMAN);
    	 
    	// Add the topmost component.
    	HorizontalLayout top = new HorizontalLayout();
    	content.addComponent(top);
    	
    	// Login Information Label
    	logininfo = new Label("Eingeloggt mit Benutzer: unbekannt");
    	top.addComponent(logininfo);
    	top.setComponentAlignment(logininfo, Alignment.MIDDLE_LEFT);
    	
    	// Logout button
    	Button logout = new Button("Logout");
    	logout.setImmediate(true);
    	logout.setWidth("-1px");
    	logout.setHeight("-1px");
    	logout.addClickListener(new Button.ClickListener() {
			/**
			 * generated serial
			 */
			private static final long serialVersionUID = -7471963909251042883L;

			public void buttonClick(ClickEvent event) {
	            // "Logout" the user
	            getSession().setAttribute("user", null);

	            // Refresh this view, should redirect to login view
	            getUI().getNavigator().navigateTo(NAME);
			}
		}); 
    	top.addComponent(logout);
    	 
    	// Add a horizontal layout for the bottom part.
    	HorizontalLayout bottom = new HorizontalLayout();
    	content.addComponent(bottom);
        
        // Add a tabsheet with the other GUI components
        TabSheet tabsheet = new TabSheet();
        tabsheet.setHeight(600, Unit.PIXELS);
        tabsheet.setWidth(600, Unit.PIXELS);
        tabsheet.addTab(new DocumentGuiHandler(controller), "Dokumente");
        tabsheet.addTab(new ExpensesGuiHandler(controller) , "Spesen");
        tabsheet.addTab(new TaskListGuiHandler(controller), "ToDo's");
        tabsheet.addTab(new TaskTimeGuiHandler(controller), "Zeitrapporte");
        tabsheet.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {			
			/**
			 * generated serial
			 */
			private static final long serialVersionUID = -1075153625011799903L;

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				controller.fireAppointmentChangedEvent();
			}
		});
 
    	// Add the appointments
        AppointmentGuiHandler appointments = new AppointmentGuiHandler(controller, tabsheet);
        appointments.setWidth(300, Unit.PIXELS);
        appointments.setHeight(600, Unit.PIXELS);
        
        // Add the components to the buttom layout
    	bottom.addComponent(appointments);
    	bottom.addComponent(tabsheet);
    	
    	Label title = new Label("SpitApp Webfrontend made with Vaadin by Swen, Pascal[1], Pascal[2] and Roger");
    	title.setWidth(800, Unit.PIXELS);
    	title.setHeight(50, Unit.PIXELS);
    	title.setStyleName(Reindeer.LABEL_SMALL);
    	content.addComponent(title);
    	content.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
    }
    
	@Override
	public void enter(ViewChangeEvent event) {
		 // Get the user name from the session
        String username = String.valueOf(getSession().getAttribute("user"));

        // And show the username
        logininfo.setValue("Eingeloggt mit Benutzer: " + username);
	}
}