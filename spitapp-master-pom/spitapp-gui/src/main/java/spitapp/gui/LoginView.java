package spitapp.gui;

import spitapp.core.service.UiServiceFacade;

import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.themes.Reindeer;

public class LoginView extends CustomComponent implements View, Button.ClickListener {

    /**
	 * generated serial
	 */
	private static final long serialVersionUID = 596706977145258460L;

	public static final String NAME = "login";

    private final TextField user;

    private final PasswordField password;

    private final Button loginButton;

    public LoginView() {
        setSizeFull();
        // Create the user input field
        user = new TextField("User:");
        user.setWidth("300px");
        user.setRequired(true);
        user.setInputPrompt("Ihr Benutzername (z.B. swen [PW: correct] )");
        user.addValidator(new StringLengthValidator("Der Benutzername muss mindestens 3 Zeichen lang sein!", 3, null, false));
        user.setInvalidAllowed(false);

        // Create the password input field
        password = new PasswordField("Passwort:");
        password.setWidth("300px");
        password.addValidator(new StringLengthValidator("Das Passwort muss mindestens 4 Zeichen lang sein!", 4, null, false));
        password.setRequired(true);
        password.setValue("");
        password.setNullRepresentation("");

        // Create login button
        loginButton = new Button("Login", this);
        loginButton.setImmediate(true);
        
        // Add both to a panel
        VerticalLayout fields = new VerticalLayout(user, password, loginButton);
        fields.setCaption("Bitte loggen Sie sich ein, um auf die SpitApp zuzugreifen.");
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

        // The view root layout
        VerticalLayout viewLayout = new VerticalLayout(fields);
        viewLayout.setSizeFull();
        viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
        viewLayout.setStyleName(Reindeer.LAYOUT_WHITE);
        setCompositionRoot(viewLayout);
    }
    
	@Override
	public void enter(ViewChangeEvent event) {
        // focus the username field when user arrives to the login view
        //user.focus();
	}

    @Override
    public void buttonClick(ClickEvent event) {

         //
         // Validate the fields using the navigator. By using validors for the
         // fields we reduce the amount of queries we have to use to the database
         // for wrongly entered passwords
         //
        if (!user.isValid() || !password.isValid()) {
            return;
        }

        String username = user.getValue();
        String password = this.password.getValue();

         //
         // Validate username and password with database
         //
        if(UiServiceFacade.getInstance().validateLogin(username, password)){
        	this.password.setComponentError(null);
        	
            // Store the current user in the service session
            getSession().setAttribute("user", username);

            // Navigate to main view
            getUI().getNavigator().navigateTo(SpitAppView.NAME);

        } else {

            // Wrong password clear the password field and refocuses it
        	this.password.setComponentError(new UserError("Das eingegebene Passwort ist falsch!"));
            this.password.setValue(null);
            this.password.focus();
        }
    }
}