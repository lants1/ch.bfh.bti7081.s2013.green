package spitapp.gui;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;

public class TerminEintragGuiHandler extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button button_vorwaerts;
	@AutoGenerated
	private PopupDateField dateField_datum;
	@AutoGenerated
	private Button button_rueckwaerts;
	@AutoGenerated
	private Table table_termine;
	
	private Date date;
	private Calendar calendar;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public TerminEintragGuiHandler() {
		date = new Date();
		calendar = new GregorianCalendar();
		calendar.setTime(date);
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here	
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
		
		// table_termine
		table_termine = new Table();
		table_termine.setImmediate(false);
		table_termine.setWidth("80.0%");
		table_termine.setHeight("-1px");
		mainLayout.addComponent(table_termine, "top:17.0px;left:20.0px;");
		
		// button_rueckwaerts
		button_rueckwaerts = new Button();
		button_rueckwaerts.setCaption("Rückwärts");
		button_rueckwaerts.setImmediate(true);
		button_rueckwaerts.setWidth("-1px");
		button_rueckwaerts.setHeight("-1px");
		button_rueckwaerts.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				calendar.setTime(dateField_datum.getValue());
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				date = calendar.getTime();
				dateField_datum.setValue(date);
			}
		}); 
		mainLayout.addComponent(button_rueckwaerts, "top:357.0px;left:20.0px;");
		
		// dateField_datum
		dateField_datum = new PopupDateField();
		dateField_datum.setImmediate(true);
		dateField_datum.setWidth("-1px");
		dateField_datum.setHeight("-1px");
		dateField_datum.setDateFormat("dd-MM-yyyy");
		dateField_datum.setValue(date);
		mainLayout.addComponent(dateField_datum, "top:357.0px;left:106.0px;");
		
		// button_vorwaerts
		button_vorwaerts = new Button();
		button_vorwaerts.setCaption("Vorwärts");
		button_vorwaerts.setImmediate(true);
		button_vorwaerts.setWidth("-1px");
		button_vorwaerts.setHeight("-1px");
		button_vorwaerts.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				calendar.setTime(dateField_datum.getValue());
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				date = calendar.getTime();
				dateField_datum.setValue(date);
			}
		}); 
		mainLayout.addComponent(button_vorwaerts, "top:357.0px;left:208.0px;");
		
		return mainLayout;
	}
}
