package spitapp.gui;

import java.util.Locale;

import spitapp.controller.AppointmentController;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
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
import com.vaadin.client.ui.layout.Margins;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.themes.Reindeer;


public class SpitAppView extends CustomComponent implements View {

    /**
	 * generated serial
	 */
	private static final long serialVersionUID = 922715202810106398L;

	public static final String NAME = "";
    
	
	private VerticalLayout mainLayout;
	private TabSheet tabs;
	private Label user;
	
	protected Window logout ;
	protected Window help;
    
    public AppointmentController controller = new AppointmentController();

    public SpitAppView() {
    	buildMainView();
    	
  //  	setSizeFull();
    	setCompositionRoot(mainLayout);
    	
//    	// Set the root layout for the UI
//    	VerticalLayout content = new VerticalLayout();
//    	setCompositionRoot(content);
//    	content.setLocale(Locale.GERMAN);
//    	 
//    	// Add the topmost component.
//    	HorizontalLayout top = new HorizontalLayout();
//    	content.addComponent(top);
//    	
//    	// Login Information Label
//    	label_loginInfo = new Label("Eingeloggt mit Benutzer: unbekannt");
//    	top.addComponent(label_loginInfo);
//    	top.setComponentAlignment(label_loginInfo, Alignment.MIDDLE_LEFT);
//    	
//    	// Logout button
//    	Button logout = new Button("Logout");
//    	logout.setImmediate(true);
//    	logout.setWidth("-1px");
//    	logout.setHeight("-1px");
//    	logout.addClickListener(new Button.ClickListener() {
//			/**
//			 * generated serial
//			 */
//			private static final long serialVersionUID = -7471963909251042883L;
//
//			public void buttonClick(ClickEvent event) {
//	            // "Logout" the user
//	            getSession().setAttribute("user", null);
//
//	            // Refresh this view, should redirect to login view
//	            getUI().getNavigator().navigateTo(NAME);
//			}
//		}); 
//    	top.addComponent(logout);
//    	 
//    	// Add a horizontal layout for the bottom part.
//    	HorizontalLayout bottom = new HorizontalLayout();
//    	content.addComponent(bottom);
//        
//        // Add a tabsheet with the other GUI components
//        tabs = new TabSheet();
//        tabs.setHeight(600, Unit.PIXELS);
//        tabs.setWidth(600, Unit.PIXELS);
//        tabs.addTab(new DocumentGuiHandler(controller), "Dokumente");
//        tabs.addTab(new ExpensesGuiHandler(controller) , "Spesen");
//        tabs.addTab(new TaskListGuiHandler(controller), "ToDo's");
//        tabs.addTab(new TaskTimeGuiHandler(controller), "Zeitrapporte");
//        tabs.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {			
//			/**
//			 * generated serial
//			 */
//			private static final long serialVersionUID = -1075153625011799903L;
//
//			@Override
//			public void selectedTabChange(SelectedTabChangeEvent event) {
//				controller.fireAppointmentChangedEvent();
//			}
//		});
// 
//    	// Add the appointments
//        AppointmentGuiHandler appointments = new AppointmentGuiHandler(controller, tabs);
//        appointments.setWidth(300, Unit.PIXELS);
//        appointments.setHeight(600, Unit.PIXELS);
//        
//        // Add the components to the buttom layout
//    	bottom.addComponent(appointments);
//    	bottom.addComponent(tabs);
//    	
//    	Label title = new Label("SpitApp Webfrontend made with Vaadin by Swen, Pascal[1], Pascal[2] and Roger");
//    	title.setWidth(800, Unit.PIXELS);
//    	title.setHeight(50, Unit.PIXELS);
//    	title.setStyleName(Reindeer.LABEL_SMALL);
//    	content.addComponent(title);
//    	content.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
    }
    
    void buildMainView() {
    	mainLayout = new VerticalLayout();
        //mainLayout.setSizeFull();
        mainLayout.addComponent(getTopBar());
        mainLayout.addComponent(getHeader());
        mainLayout.addComponent(getContent());
        mainLayout.addComponent(getFooterBar());
    }
    
    Layout getTopBar() {
        HorizontalLayout topbar = new HorizontalLayout();
        topbar.setWidth("100%");
        topbar.setHeight(20, Unit.PIXELS);
    	
        return topbar;
    }

    Layout getFooterBar() {
        HorizontalLayout footerbar = new HorizontalLayout();
        footerbar.setWidth("100%");
        footerbar.setHeight(20, Unit.PIXELS);
    	footerbar.setStyleName(Reindeer.LAYOUT_BLUE);
    	
    	Label title = new Label("Copyright 2013 by Swen, Pascal[1], Pascal[2] and Roger. Powered by Vaadin Framework.");
    	//title.setWidth(800, Unit.PIXELS);
    	title.setHeight(20, Unit.PIXELS);
    	title.setStyleName(Reindeer.LABEL_SMALL);
    	
    	footerbar.addComponent(title);
    	footerbar.setComponentAlignment(title, Alignment.MIDDLE_LEFT); 
    	
        return footerbar;
    }
    
    Layout getHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setMargin(true);
        header.setSpacing(true);
        header.setStyleName(Reindeer.LAYOUT_BLUE);

        CssLayout titleLayout = new CssLayout();
        Label title = new Label("SpitApp Web Application");
        title.setStyleName(Reindeer.LABEL_H1);
        titleLayout.addComponent(title);
        
        Label description = new Label("Verwalte deine täglichen Patienten-Besuche");
        description.setSizeUndefined();
        description.setStyleName(Reindeer.LABEL_SMALL);
        titleLayout.addComponent(description);

        header.addComponent(titleLayout);

        titleLayout = new CssLayout();
        Label user = new Label("Hallo, Gast!");
        user.setSizeUndefined();
        titleLayout.addComponent(user);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        Button help = new Button("Hilfe", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                //openHelpWindow();
            }
        });
        help.setStyleName(Reindeer.BUTTON_SMALL);
        buttons.addComponent(help);
        buttons.setComponentAlignment(help, Alignment.BOTTOM_LEFT);

        Button logout = new Button("Logout", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                openLogoutWindow();
            }
        });
        logout.setStyleName(Reindeer.BUTTON_SMALL);
        buttons.addComponent(logout);
        titleLayout.addComponent(buttons);

        header.addComponent(titleLayout);
        buttons.setComponentAlignment(help, Alignment.BOTTOM_RIGHT);

        return header;
    }
    
    void openLogoutWindow() {
        logout = new Window("Logout");
        logout.setModal(true);
        logout.setStyleName(Reindeer.WINDOW_BLACK);
        logout.setWidth("260px");
        logout.setResizable(false);
        logout.setClosable(false);
        logout.setDraggable(false);
        logout.setCloseShortcut(KeyCode.ESCAPE, null);

        VerticalLayout windowcontent = new VerticalLayout();
        Label helpText = new Label("Möchtest du dich wirklich ausloggen?");
        windowcontent.addComponent(helpText);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        Button yes = new Button("Logout", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	logout.close();
            	
            	//getMainWindow().open(new ExternalResource("http://vaadin.com"));
	            // "Logout" the user
	            getSession().setAttribute("user", null);

	            // Refresh this view, should redirect to login view
	            getUI().getNavigator().navigateTo(NAME);
            }
        });
        yes.setStyleName(Reindeer.BUTTON_DEFAULT);
        yes.focus();
        
        buttons.addComponent(yes);
        Button no = new Button("Cancel", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                logout.close();
            }
        });
        buttons.addComponent(no);

        windowcontent.addComponent(buttons);
        windowcontent.setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);
        windowcontent.setSpacing(true);

        logout.setContent(windowcontent);
        
        getUI().addWindow(logout);
    }
    
    void openHelpWindow() {
        if (!"initialized".equals(help.getData())) {
            help.setData("initialized");
            help.setCloseShortcut(KeyCode.ESCAPE, null);

            help.center();
            // help.setStyleName(Reindeer.WINDOW_LIGHT);
            help.setWidth("400px");
            help.setResizable(false);

            Label helpText = new Label(
                    "<strong>How To Use This Application</strong><p>Click around, explore. The purpose of this app is to show you what is possible to achieve with the Reindeer theme and its different styles.</p><p>Most of the UI controls that are visible in this application don't actually do anything. They are purely for show, like the menu items and the components that demostrate the different style names assosiated with the components.</p><strong>So, What Then?</strong><p>Go and use the styles you see here in your own application and make them beautiful!",
                    Label.CONTENT_XHTML);
            help.setContent(helpText);

        }
        
        if (!getUI().getWindows().contains(help)) {
        	getUI().addWindow(help);
        }
    }
    
    Layout getContent() {
    	 
    	// Add a horizontal layout for the bottom part.
    	HorizontalLayout content = new HorizontalLayout();
    	content.setMargin(true);
    	content.setSizeFull();
        
        // Add a tabsheet with the other GUI components
    	HorizontalLayout margin = new HorizontalLayout();
        margin.setMargin(new MarginInfo(true, true, false, true));
        margin.setSizeFull();
        
        tabs = new TabSheet();
        tabs.setHeight(600, Unit.PIXELS);
        tabs.setWidth(600, Unit.PIXELS);
        margin.addComponent(tabs);
        
        tabs.addTab(new DocumentGuiHandler(controller), "Dokumente");
        tabs.addTab(new ExpensesGuiHandler(controller) , "Spesen");
        tabs.addTab(new TaskListGuiHandler(controller), "ToDo's");
        tabs.addTab(new TaskTimeGuiHandler(controller), "Zeitrapporte");
        tabs.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {			
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
        AppointmentGuiHandler appointments = new AppointmentGuiHandler(controller, tabs);
        
        // Add the components to the buttom layout
    	content.addComponent(appointments);
    	content.addComponent(margin);
    	content.setExpandRatio(margin, 1); 
    	
    	return content;
    }
    
	@Override
	public void enter(ViewChangeEvent event) {
		 // Get the user name from the session
        String username = String.valueOf(getSession().getAttribute("user"));

        // And show the username
        user.setValue("Hallo, " + username);
	}
}