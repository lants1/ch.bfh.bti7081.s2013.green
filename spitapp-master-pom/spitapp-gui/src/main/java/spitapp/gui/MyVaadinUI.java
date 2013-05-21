package spitapp.gui;

import java.util.Locale;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{

    @Override
    protected void init(VaadinRequest request) {
    	
    	// Set the root layout for the UI
    	VerticalLayout content = new VerticalLayout();
    	setContent(content);
    	content.setLocale(Locale.GERMAN);
    	 
    	// Add the topmost component.
    	Label title = new Label("SpitApp Webfrontend made with Vaadin by Swen, Pascal[1], Pascal[2] and Roger");
    	title.setWidth(800, Unit.PIXELS);
    	title.setHeight(50, Unit.PIXELS);
    	content.addComponent(title);
    	content.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
    	 
    	// Add a horizontal layout for the bottom part.
    	HorizontalLayout bottom = new HorizontalLayout();
    	content.addComponent(bottom);
    	
    	// Add the appointments
        TerminEintragGuiHandler appointments = new TerminEintragGuiHandler();
        appointments.setWidth(300, Unit.PIXELS);
        appointments.setHeight(600, Unit.PIXELS);
        
        // Add a tabsheet with the other GUI components
        TabSheet tabsheet = new TabSheet();
        tabsheet.setHeight(600, Unit.PIXELS);
        tabsheet.setWidth(600, Unit.PIXELS);
        tabsheet.addTab(new DokumentGuiHandler(), "Dokumente");
        tabsheet.addTab(new SpesenGuiHandler() , "Spesen");
        tabsheet.addTab(new TaskListeGuiHandler(), "ToDo's");
        tabsheet.addTab(new ZeitAnpassungGuiHandler(), "Zeitrapporte");
       
        // Add the components to the buttom layout
    	bottom.addComponent(appointments);
    	bottom.addComponent(tabsheet);
    }
}
