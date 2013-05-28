package spitapp.gui;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import spitapp.controller.AppointmentChangedEvent;
import spitapp.controller.AppointmentController;
import spitapp.core.model.Patient;
import spitapp.core.model.Document;
import spitapp.util.PdfStream;

import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class DocumentGuiHandler extends DetailGuiHandler {

	// the gui components
	private AbsoluteLayout mainLayout;
	private Label age;
	private Label ageData;
	private Label careLevel;
	private Label careLevelData;
	private Label hobbies;
	private Label hobbiesData;
	private Label lblDocument;
	private Button btnDocument;
	private ComboBox cbxDocuments;
	
	/**
	 * Constructor for DocumentGuiHandler
	 * @param controller The reference to the AppointmentController
	 */
	public DocumentGuiHandler(AppointmentController controller) {
		super(controller);
		buildMainLayout();
		setCompositionRoot(mainLayout);	
	}

	/**
	 * Builds the main layout
	 * @return the mainLayout
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
		
		// Age title
		age = new Label();
		age.setImmediate(false);
		age.setWidth("-1px");
		age.setHeight("-1px");
		age.setValue("Alter:");
		mainLayout.addComponent(age, "top:24.0px;left:20.0px;");
		
		// Age data
		ageData = new Label();
		ageData.setImmediate(false);
		ageData.setWidth("90px");
		ageData.setHeight("-1px");
		ageData.setValue("");
		mainLayout.addComponent(ageData, "top:24.0px;left:110.0px;");
		
		// Carelevel title
		careLevel = new Label();
		careLevel.setImmediate(false);
		careLevel.setWidth("-1px");
		careLevel.setHeight("-1px");
		careLevel.setValue("Pflegestufe:");
		mainLayout.addComponent(careLevel, "top:60.0px;left:20.0px;");
		
		// Carelevel data
		careLevelData = new Label();
		careLevelData.setImmediate(false);
		careLevelData.setWidth("110px");
		careLevelData.setHeight("-1px");
		careLevelData.setValue("8b");
		mainLayout.addComponent(careLevelData, "top:60.0px;left:110.0px;");
		
		// Hobbies title
		hobbies = new Label();
		hobbies.setImmediate(false);
		hobbies.setWidth("-1px");
		hobbies.setHeight("-1px");
		hobbies.setValue("Hobbies:");
		mainLayout.addComponent(hobbies, "top:100.0px;left:20.0px;");
		
		// Hobbies data
		hobbiesData = new Label();
		hobbiesData.setImmediate(false);
		hobbiesData.setWidth("100.0%");
		hobbiesData.setHeight("40px");
		hobbiesData.setValue("");
		mainLayout.addComponent(hobbiesData, "top:120.0px;left:20.0px;");
		
		// ComboBox for pdf-documents
		cbxDocuments = new ComboBox();
		cbxDocuments.setImmediate(false);
		cbxDocuments.setWidth("-1px");
		cbxDocuments.setHeight("-1px");
		cbxDocuments.setInvalidAllowed(false);
		mainLayout.addComponent(cbxDocuments, "top:200.0px;left:22.0px;");
		
		// Document button
		btnDocument = new Button();
		btnDocument.setCaption("Öffnen");
		btnDocument.setImmediate(true);
		btnDocument.setWidth("-1px");
		btnDocument.setHeight("-1px");
		btnDocument.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				PdfStream pdf = new PdfStream();
		        pdf.setResource(controller.getCurrentAppointment().getPatient().getDocuments().get(0).getFile());
		        
				// A resource reference to some object
				StreamResource resource = new StreamResource(pdf, "test.pdf?" + System.currentTimeMillis());
				 
				// Display the object
				Embedded object = new Embedded("My PDF", resource);
				object.setMimeType("application/pdf"); // Unnecessary
				
				Window window = new Window();
		        window.setResizable(true);
		        window.setWidth("800");
		        window.setHeight("600");
		        window.center();
		        BrowserFrame frame = new BrowserFrame();
		        frame.setSizeFull();
		        frame.setSource(resource);
		        window.setContent(frame);
		        getUI().addWindow(window);
				//cbxDocuments.getValue();

			}
		}); 
		mainLayout.addComponent(btnDocument, "top:198.0px;left:214.0px;");
		
		// Document label
		lblDocument = new Label();
		lblDocument.setImmediate(false);
		lblDocument.setWidth("-1px");
		lblDocument.setHeight("-1px");
		lblDocument.setValue("Verfügbare Partienten-Dokumente:");
		mainLayout.addComponent(lblDocument, "top:180.0px;left:22.0px;");
		
		return mainLayout;
	}

	/**
	 * method is called when the appointment changes to handle the new entries
	 */
	public void handleAppointmentChangedEvent(AppointmentChangedEvent e) {
		
		AppointmentController ctrl = (AppointmentController)e.getSource();
		Patient patient = ctrl.getCurrentAppointment().getPatient();
		
		ageData.setValue(Integer.toString(patient.getAge()));
		careLevelData.setValue(patient.getCareLevel().toString());
		hobbiesData.setValue(patient.getHobbies());
		cbxDocuments.removeAllItems();
		for(Document doc : patient.getDocuments()) {
			cbxDocuments.addItem(doc.getFileName());
		}
		
	}
}
