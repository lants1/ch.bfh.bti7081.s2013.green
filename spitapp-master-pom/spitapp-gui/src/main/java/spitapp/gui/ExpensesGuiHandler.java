package spitapp.gui;

import spitapp.controller.AppointmentChangedEvent;
import spitapp.controller.AppointmentController;
import spitapp.core.model.ExpensesEntry;
import spitapp.core.model.Patient;

import java.util.*;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextField;

public class ExpensesGuiHandler extends DetailGuiHandler {

	/**
	 * the generated Serial
	 */
	private static final long serialVersionUID = 3873228414539845811L;
	
	private AbsoluteLayout mainLayout;
	private Label label_new;
	private Label label_currency;
	private Button button_add;
	private Button button_delete;
	private TextField textfield_amount;
	private TextField textfield_expensetype;
	private Table table_expenses;
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public ExpensesGuiHandler(AppointmentController controller) {
		super(controller);
		buildMainLayout();
		setCompositionRoot(mainLayout);

	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// table_expenses
		table_expenses = new Table();
		table_expenses.setWidth("400px");
		table_expenses.setHeight("303px");
		table_expenses.addContainerProperty("Spesenart", String.class,  null);
		table_expenses.addContainerProperty("Betrag", Double.class,  null);
		// Allow selecting items from the table.
		table_expenses.setSelectable(true);
		// Send changes in selection immediately to server.
		table_expenses.setImmediate(true);
		// Handle selection change.
		table_expenses.addValueChangeListener(new ValueChangeListener() {
		    /**
			 * generated serial
			 */
			private static final long serialVersionUID = -3062037745866406218L;

			public void valueChange(ValueChangeEvent event) {
		    	button_delete.setEnabled(table_expenses.getValue() != null);
		    }
		});		
		
		mainLayout.addComponent(table_expenses, "top:17.0px;right:58.0px;left:0.0px;");
		
		// textfield_expensetype
		textfield_expensetype = new TextField("Spesenart");
		textfield_expensetype.setImmediate(false);
		textfield_expensetype.setWidth("240px");
		textfield_expensetype.setHeight("-1px");
		mainLayout.addComponent(textfield_expensetype, "top:371.0px;left:9.0px;");
		
		// textfield_amount
		textfield_amount = new TextField("Betrag");
		textfield_amount.setImmediate(false);
		textfield_amount.setWidth("80px");
		textfield_amount.setHeight("-1px");
		mainLayout.addComponent(textfield_amount, "top:371.0px;left:260.0px;");
		
		// button_add
		button_add = new Button();
		button_add.setCaption("+");
		button_add.setImmediate(true);
		button_add.setWidth("-1px");
		button_add.setHeight("-1px");
		button_add.addClickListener(new Button.ClickListener() {
			/**
			 * generated serial
			 */
			private static final long serialVersionUID = 8687670334388521170L;

			public void buttonClick(ClickEvent event) {
				
				String expensetype = null;
				if( textfield_expensetype.getValue() != null ) {
					expensetype = textfield_expensetype.getValue().toString();
				}
			
				String value = textfield_amount.getValue();
				
				
				Integer returnvalue = controller.addExpensetoCurrentPatient(expensetype, value);
				switch(returnvalue) {
				case 0:  // all ok, reload expenses
					textfield_amount.setValue("");
					textfield_expensetype.setValue("");
					label_new.setValue("Neuer Speseneintrag:");
					reload_expenses();
					break;
				case -1: // amount value in
					label_new.setValue("Ungültiger Betrag!");
					break;
				case -2:
					label_new.setValue("Spesenart darf nicht leer sein!");
					break;
				case -4:
					label_new.setValue("Ooops. Beim Speichern gings in die Hose!");
					break;
				default: // amount value in
					label_new.setValue("Unbekannter Fehler!");
					break;					
				}
			}
		}); 
		mainLayout.addComponent(button_add, "top:371.0px;left:420.0px;");
		
		// label_waehrung
		label_currency = new Label();
		label_currency.setImmediate(false);
		label_currency.setWidth("-1px");
		label_currency.setHeight("-1px");
		label_currency.setValue("CHF");
		mainLayout.addComponent(label_currency, "top:373.0px;left:342.0px;");
		
		// label_new
		label_new = new Label();
		label_new.setImmediate(false);
		label_new.setWidth("-1px");
		label_new.setHeight("-1px");
		label_new.setValue("Neuer Speseneintrag:");
		mainLayout.addComponent(label_new, "top:330.0px;left:9.0px;");

		// button_delete
		button_delete = new Button();
		button_delete.setCaption("Auswahl löschen");
		button_delete.setImmediate(true);
		button_delete.setEnabled(false);
		button_delete.setWidth("-1px");
		button_delete.setHeight("-1px");
		button_delete.addClickListener(new Button.ClickListener() {
			/**
			 * generated serial
			 */
			private static final long serialVersionUID = 4045911914165531751L;

			public void buttonClick(ClickEvent event) {
				Long id_to_delete = (Long)table_expenses.getValue();
				
				Integer returnvalue = controller.deleteExepenseOfCurrentPatient(id_to_delete);
				switch(returnvalue) {
				case 1: // delete successfull
					reload_expenses();
					break;
				case 0: // no entry found
					//break;
				default:
					label_new.setValue("Löschen fehlgeschlagen! Fehlercode: " + returnvalue.toString());
					break;
				}
			}
		}); 
		mainLayout.addComponent(button_delete, "top:17.0px;left:420.0px;");
		
		return mainLayout;
	}

	@Override
	public void handleAppointmentChangedEvent(AppointmentChangedEvent e) {
		
		this.controller = (AppointmentController)e.getSource();
		
		this.reload_expenses();
	}
	
	public void reload_expenses() {
		Patient patient = this.controller.getCurrentAppointment().getPatient();
		
		table_expenses.removeAllItems();
		
		List<ExpensesEntry> expenses = patient.getExpenses();
		if(expenses == null) {
			table_expenses.setCaption("Ungültige Speseneinträge!");
		}
		else {
			try { 
				table_expenses.setCaption("Speseneinträge: " + Integer.toString(expenses.size()));
		
				for(ExpensesEntry entry : expenses) {
					// Add a row into the table as object array
					table_expenses.addItem(new Object[] { entry.getExpensesDescription(), entry.getPrice() }, entry.getExpensesId());
				}	
			}
			catch(Exception ex) {
				label_new.setValue(ex.toString());
			}
		}
	}

}
