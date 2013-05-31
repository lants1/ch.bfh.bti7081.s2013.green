package spitapp.gui;

import spitapp.controller.AppointmentChangedEvent;
import spitapp.controller.AppointmentController;
import spitapp.core.model.Patient;
import spitapp.core.model.Document;
import spitapp.util.PdfStream;

import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;


public class DocumentGuiHandler extends DetailGuiHandler {

	/**
	 * generated serial
	 */
	private static final long serialVersionUID = -5744285594661791699L;
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
	private Button btnGoogleMaps;
	private BrowserWindowOpener opener;
	
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

		// GoogleMaps button
		btnGoogleMaps = new Button();
		btnGoogleMaps.setCaption("Auf Karte zeigen");
		btnGoogleMaps.setImmediate(true);
		btnGoogleMaps.setWidth("-1px");
		btnGoogleMaps.setHeight("-1px");
		
		opener = new BrowserWindowOpener(new ExternalResource("http://maps.google.com"));
		opener.extend(btnGoogleMaps);
		
//		
//		btnGoogleMaps.addClickListener(new Button.ClickListener() {
//			/**
//			 * generated serial
//			 */
//			private static final long serialVersionUID = -8378559264582148290L;
//
//			public void buttonClick(ClickEvent event) {
//				
//				Patient patient = controller.getCurrentAppointment().getPatient();
//				
//
//				//getUI().showNotification("Title", "Testnachricht");
//            	//getMainWindow().open(new ExternalResource("http://vaadin.com"));
//			}
//		}); 
		mainLayout.addComponent(btnGoogleMaps, "top:24.0px;left:280.0px;");
		
		// Document button
		btnDocument = new Button();
		btnDocument.setCaption("Öffnen");
		btnDocument.setImmediate(true);
		btnDocument.setWidth("-1px");
		btnDocument.setHeight("-1px");
		btnDocument.addClickListener(new Button.ClickListener() {
			/**
			 * generated serial
			 */
			private static final long serialVersionUID = -8378559164582148290L;

			public void buttonClick(ClickEvent event) {
				
				Document currentDocument = controller.getCurrentAppointment().getPatient().getDocuments().get((int)cbxDocuments.getValue());
				String docFileName = currentDocument.getFileName();
				PdfStream pdf = new PdfStream();
		        pdf.setResource(currentDocument.getFile());
		        
				// A resource reference to the pdf
				StreamResource resource = new StreamResource(pdf, docFileName + ".pdf?" + System.currentTimeMillis());
				 
				// Embedded object for the pdf view
				Embedded object = new Embedded("PDF Object", resource);
				object.setMimeType("application/pdf");
				
				// Popup window
				Window window = new Window(docFileName + ".pdf");
		        window.setResizable(false);
		        window.setWidth("800");
		        window.setHeight("600");
		        window.center();
		        
		        // Browser frame for the embedded pdf object
		        BrowserFrame frame = new BrowserFrame();
		        frame.setWidth(795, Unit.PIXELS);
		        frame.setHeight(555, Unit.PIXELS);
		        frame.setSource(resource);
		        
		        //Display the pdf
		        window.setContent(frame);
		        getUI().addWindow(window);	        
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
		
		Patient patient = controller.getCurrentAppointment().getPatient();
		
		// Hier eventuell noch Strings präparieren....
		String urlquery = patient.getStreet() + ",+" +
							patient.getCity();
		
		btnGoogleMaps.removeExtension(opener);
		opener = new BrowserWindowOpener(new ExternalResource("http://maps.google.com?q=" + urlquery + "&views=satellite,traffic&zoom=15"));
		opener.extend(btnGoogleMaps);
		
		
		ageData.setValue(Integer.toString(patient.getAge()));
		careLevelData.setValue(patient.getCareLevel().toString());
		hobbiesData.setValue(patient.getHobbies());
		cbxDocuments.removeAllItems();
		int i = 0;
		for(Document doc : patient.getDocuments()) {
			cbxDocuments.addItem(i);
			cbxDocuments.setItemCaption(i, doc.getFileName());
			++i;
		}	
	}
}
