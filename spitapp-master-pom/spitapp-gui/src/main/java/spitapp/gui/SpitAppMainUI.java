package spitapp.gui;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;	
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * he Application's "main" class
 * @author jaggr2, vonop1
 *
 */
public class SpitAppMainUI extends UI
{
	
    /**
	 * generated serial
	 */
	private static final long serialVersionUID = 3997896120089646410L;

	/**
	 * is executed when the Vaadin application starts up
	 */
	@Override
    protected void init(VaadinRequest request) {
    	
        //
        // Create a new instance of the navigator. The navigator will attach
        // itself automatically to this view.
        //
        new Navigator(this, this);

        //
        // The initial log view where the user can login to the application
        //
        getNavigator().addView(LoginView.NAME, LoginView.class);

        //
        // Add the main view of the application
        //
        getNavigator().addView(SpitAppView.NAME, SpitAppView.class);
                       
        //
        // We use a view change handler to ensure the user is always redirected
        // to the login view if the user is not logged in.
        //
        getNavigator().addViewChangeListener(new ViewChangeListener() {
            
            /**
			 * generated serial 
			 */
			private static final long serialVersionUID = 6743879075044376864L;

			@Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                
                // Check if a user has logged in
                boolean isLoggedIn = getSession().getAttribute("user") != null;
                boolean isLoginView = event.getNewView() instanceof LoginView;

                if (!isLoggedIn && !isLoginView) {
                    // Redirect to login view always if a user has not yet
                    // logged in
                    getNavigator().navigateTo(LoginView.NAME);
                    return false;

                } else if (isLoggedIn && isLoginView) {
                    // If someone tries to access to login view while logged in,
                    // then cancel
                    return false;
                }

                return true;
            }
            
            @Override
            public void afterViewChange(ViewChangeEvent event) {
                // nothing to do here
            }
        });    	

    }
}
