package org.openprovenance.model;
import java.io.File;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.JAXBException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit test for simple graph manipulation operations.
 */
public class Example2Test 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Example2Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( Example2Test.class );
    }

    /** Creates and serialises an OPM graph. */

    public void testOPMGraph1() throws JAXBException
    {
        OPMFactory oFactory=new OPMFactory();

        Account acc1=oFactory.newAccount("green");
        Account acc2=oFactory.newAccount("green");
        Account acc3=oFactory.newAccount("orange");

        assertTrue( acc1.equals(acc1) );
        assertTrue( acc1.equals(acc2) );
        assertTrue( acc2.equals(acc1) );
        assertTrue( acc2.equals(acc2) );

        assertFalse( acc2.equals(acc3) );
        assertFalse( acc1.equals(acc3) );
        assertFalse( acc3.equals(acc1) );
        assertFalse( acc3.equals(acc2) );
    }


    public void testOPMGraph2() throws JAXBException
    {
        OPMFactory oFactory=new OPMFactory();

        Account acc1=oFactory.newAccount("green");
        acc1.setValue("hello");
        Account acc2=oFactory.newAccount("green");
        acc2.setValue("bye");
        Account acc3=oFactory.newAccount("orange");
        acc3.setValue("bonjour");

        assertTrue( acc1.equals(acc1) );
        assertTrue( acc1.equals(acc2) );
        assertTrue( acc2.equals(acc1) );
        assertTrue( acc2.equals(acc2) );

        assertFalse( acc2.equals(acc3) );
        assertFalse( acc1.equals(acc3) );
        assertFalse( acc3.equals(acc1) );
        assertFalse( acc3.equals(acc2) );
    }


}
