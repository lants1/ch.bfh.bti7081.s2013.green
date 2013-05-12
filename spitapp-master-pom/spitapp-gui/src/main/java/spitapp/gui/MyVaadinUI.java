package spitapp.gui;

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
    	 
    	// Add the topmost component.
    	Label titletext = new Label("SpitApp Webfrontend made with Vaadin by Swen, Pascal[1], Pascal[2] and Roger");
    	titletext.setWidth(800, Unit.PIXELS);
    	titletext.setHeight(50, Unit.PIXELS);
    	
    	content.addComponent(titletext);
    	content.setComponentAlignment(titletext, Alignment.MIDDLE_LEFT);
    	 
    	// Add a horizontal layout for the bottom part.
    	HorizontalLayout bottom = new HorizontalLayout();
    	content.addComponent(bottom);
    	
        
        TerminEintragGuiHandler termine = new TerminEintragGuiHandler();
        termine.setWidth(300, Unit.PIXELS);
        termine.setHeight(600, Unit.PIXELS);
        
        TabSheet tabsheet = new TabSheet();
        tabsheet.setHeight(600, Unit.PIXELS);
        tabsheet.setWidth(600, Unit.PIXELS);

        tabsheet.addTab(new DokumentGuiHandler(), "Dokumente");
        tabsheet.addTab(new SpesenGuiHandler() , "Spesen");
        tabsheet.addTab(new TaskListeGuiHandler(), "ToDo's");
        tabsheet.addTab(new ZeitAnpassungGuiHandler(), "Zeitrapporte");
       
     
    	bottom.addComponent(termine);
    	bottom.addComponent(tabsheet);
    	
    	
//        final VerticalLayout layout = new VerticalLayout();
//        layout.setSizeFull();
//        layout.setMargin(true);
//        setContent(layout);
//        
//        DokumentGuiHandler docs = new DokumentGuiHandler();
//        docs.setSizeFull();
//        //this.addWindow(window)
//        
////        Button button = new Button("Spitapp rules");
////        button.addClickListener(new Button.ClickListener() {
////            public void buttonClick(ClickEvent event) {
////                layout.addComponent(new Label("Thank you for clicking"));
////            }
////        });
//        layout.addComponent(docs);
    }

}
