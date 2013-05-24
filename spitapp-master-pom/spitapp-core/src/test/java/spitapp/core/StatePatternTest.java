package spitapp.core;

import java.util.Date;
import spitapp.core.model.Appointment;
import spitapp.core.model.state.AppointmentState;
import spitapp.core.model.state.IrrelevantState;
import spitapp.core.model.state.RelevantState;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class StatePatternTest
{

	@Test
    public void testIrrelevantState()
    {
    	AppointmentState state = new IrrelevantState(null);
    	assertFalse(state.isRelevant());
    }
    
	@Test
    public void testRelevantState()
    {
    	AppointmentState state = new RelevantState(null);
    	assertTrue(state.isRelevant());
    }
    
	@Test
    public void testSettingOfPattern(){
    	Appointment termin = new Appointment();
    	assertFalse(termin.isRelevant());
    	
    	Date date = new Date();
    	termin.setFromDate(date);
    	assertFalse(termin.isRelevant());
    	
    	termin.updateState(date);
    	assertTrue(termin.isRelevant());
    }
}