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

/**
 * class to handle the document gui
 * @author vonop1
 */
public class DocumentGuiHandler extends DetailGuiHandler {

	/**
	 * generated serial
	 */
	private static final long serialVersionUID = -5744285594661791699L;
	
	/**
	 * the GUI components
	 */
	private AbsoluteLayout mainLayout;
	private Label name;
	private Label nameData;
	private Label address;
	private Label addressData;
	private Label city;
	private Label cityData;
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
		
		/**
		 * create the top layout
		 */
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		/**
		 * Name/surname title
		 */
		name = new Label();
		name.setImmediate(false);
		name.setWidth("100px");
		name.setHeight("-1px");
		name.setValue("Vorname/Name:");
		mainLayout.addComponent(name, "top:20.0px;left:20.0px;");
		
		/**
		 * Name/surname data
		 */
		nameData = new Label();
		nameData.setImmediate(false);
		nameData.setWidth("300px");
		nameData.setHeight("-1px");
		nameData.setValue("");
		mainLayout.addComponent(nameData, "top:20.0px;left:130.0px;");
		
		/**
		 * Address title
		 */
		address = new Label();
		address.setImmediate(false);
		address.setWidth("100px");
		address.setHeight("-1px");
		address.setValue("Adresse:");
		mainLayout.addComponent(address, "top:40.0px;left:20.0px;");
		
		/**
		 * Address data
		 */
		addressData = new Label();
		addressData.setImmediate(false);
		addressData.setWidth("300px");
		addressData.setHeight("-1px");
		addressData.setValue("");
		mainLayout.addComponent(addressData, "top:40.0px;left:130.0px;");
		
		/**
		 * ZIP/City title
		 */
		city = new Label();
		city.setImmediate(false);
		city.setWidth("100px");
		city.setHeight("-1px");
		city.setValue("PLZ/Ort");
		mainLayout.addComponent(city, "top:60.0px;left:20.0px;");
		
		/**
		 * ZIP/City data
		 */
		cityData = new Label();
		cityData.setImmediate(false);
		cityData.setWidth("300px");
		cityData.setHeight("-1px");
		cityData.setValue("");
		mainLayout.addComponent(cityData, "top:60.0px;left:130.0px;");
		
		/**
		 * GoogleMaps button
		 */
		btnGoogleMaps = new Button();
		btnGoogleMaps.setCaption("Auf Karte zeigen");
		btnGoogleMaps.setImmediate(true);
		btnGoogleMaps.setWidth("-1px");
		btnGoogleMaps.setHeight("-1px");
		opener = new BrowserWindowOpener(new ExternalResource("http://maps.google.com"));
		opener.extend(btnGoogleMaps);
		mainLayout.addComponent(btnGoogleMaps, "top:18.0px;left:440.0px;");
		
		/**
		 * Age title
		 */
		age = new Label();
		age.setImmediate(false);
		age.setWidth("100px");
		age.setHeight("-1px");
		age.setValue("Alter:");
		mainLayout.addComponent(age, "top:100.0px;left:20.0px;");
		
		/**
		 * Age data
		 */
		ageData = new Label();
		ageData.setImmediate(false);
		ageData.setWidth("300px");
		ageData.setHeight("-1px");
		ageData.setValue("");
		mainLayout.addComponent(ageData, "top:100.0px;left:130.0px;");
		
		/**
		 * CareLevel title
		 */
		careLevel = new Label();
		careLevel.setImmediate(false);
		careLevel.setWidth("100px");
		careLevel.setHeight("-1px");
		careLevel.setValue("Pflegestufe:");
		mainLayout.addComponent(careLevel, "top:120.0px;left:20.0px;");
		
		/**
		 * CareLevel data
		 */
		careLevelData = new Label();
		careLevelData.setImmediate(false);
		careLevelData.setWidth("300px");
		careLevelData.setHeight("-1px");
		careLevelData.setValue("");
		mainLayout.addComponent(careLevelData, "top:120.0px;left:130.0px;");
		
		/**
		 * Hobbies title
		 */
		hobbies = new Label();
		hobbies.setImmediate(false);
		hobbies.setWidth("100px");
		hobbies.setHeight("-1px");
		hobbies.setValue("Hobbies:");
		mainLayout.addComponent(hobbies, "top:140.0px;left:20.0px;");
		
		/**
		 * Hobbies data
		 */
		hobbiesData = new Label();
		hobbiesData.setImmediate(false);
		hobbiesData.setWidth("300px");
		hobbiesData.setHeight("-1px");
		hobbiesData.setValue("");
		mainLayout.addComponent(hobbiesData, "top:140.0px;left:130.0px;");
		
		/**
		 * Document label
		 */
		lblDocument = new Label();
		lblDocument.setImmediate(false);
		lblDocument.setWidth("300px");
		lblDocument.setHeight("-1px");
		lblDocument.setValue("Verfügbare Partienten-Dokumente:");
		mainLayout.addComponent(lblDocument, "top:180.0px;left:20.0px;");
		
		/**
		 * ComboBox for pdf-documents
		 */
		cbxDocuments = new ComboBox();
		cbxDocuments.setImmediate(false);
		cbxDocuments.setWidth("-1px");
		cbxDocuments.setHeight("-1px");
		cbxDocuments.setInvalidAllowed(false);
		mainLayout.addComponent(cbxDocuments, "top:200.0px;left:20.0px;");
		
		/**
		 * Document open button
		 */
		btnDocument = new Button();
		btnDocument.setCaption("Öffnen");
		btnDocument.setImmediate(true);
		btnDocument.setWidth("-1px");
		btnDocument.setHeight("-1px");
		btnDocument.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -8378559164582148290L;

			public void buttonClick(ClickEvent event) {
				
				// get the current document and document name for further use
				Document currentDocument = controller.getCurrentAppointment().getPatient().getDocuments().get((int)cbxDocuments.getValue());
				String docFileName = currentDocument.getFileName();
				
				// get the current document as pdf stream for further use
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
		
		return mainLayout;
	}


	/**
	 * fires when the user changes the appointment
	 */
	@Override
	public void handleAppointmentChangedEvent(AppointmentChangedEvent e) {
		if(controller.getCurrentAppointment() != null)
		{
			Patient patient = controller.getCurrentAppointment().getPatient();
			
			String urlquery = patient.getStreet() + ",+" + patient.getCity();
			
			btnGoogleMaps.removeExtension(opener);
			opener = new BrowserWindowOpener(new ExternalResource("http://maps.google.com?q=" + urlquery + "&views=satellite,traffic&zoom=15"));
			opener.extend(btnGoogleMaps);
			
			// Fill up the data labels
			nameData.setValue(patient.getFirstName() + " " + patient.getLastName());
			addressData.setValue(patient.getStreet());
			cityData.setValue(patient.getCity());
			ageData.setValue(Integer.toString(patient.getAge()));
			careLevelData.setValue(patient.getCareLevel().name());
			hobbiesData.setValue(patient.getHobbies());
			
			// Fill up the document comboBox
			cbxDocuments.removeAllItems();
			int i = 0;
			for(Document doc : patient.getDocuments()) {
				cbxDocuments.addItem(i);
				cbxDocuments.setItemCaption(i, doc.getFileName() + ".pdf");
				++i;
			}	
		}
	}
}
