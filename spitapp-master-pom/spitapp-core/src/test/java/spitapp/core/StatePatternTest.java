package spitapp.core;

import java.util.Date;
import spitapp.core.model.Appointment;
import spitapp.core.model.state.IrrelevantState;
import spitapp.core.model.state.RelevantState;
import spitapp.core.model.state.TerminState;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class StatePatternTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public StatePatternTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( StatePatternTest.class );
    }

    public void testIrrelevantState()
    {
    	TerminState state = new IrrelevantState(null);
    	assertFalse(state.isRelevant());
    }
    
    public void testRelevantState()
    {
    	TerminState state = new RelevantState(null);
    	assertTrue(state.isRelevant());
    }
    
    public void testSettingOfPattern(){
    	Appointment termin = new Appointment();
    	assertFalse(termin.isRelevant());
    	
    	Date date = new Date();
    	termin.setTerminDate(date);
    	assertFalse(termin.isRelevant());
    	
    	termin.updateState(date);
    	assertTrue(termin.isRelevant());
    }
}