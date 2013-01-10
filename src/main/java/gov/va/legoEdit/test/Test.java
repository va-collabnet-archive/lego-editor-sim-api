package gov.va.legoEdit.test;

import gov.va.legoEdit.storage.sim.SimDataStoreImpl;
import gov.va.sim.act.AssertionBI;
import java.util.Iterator;

/**
 * Just some test code for playing with the sim-api / Lego conversions.
 * @author darmbrust
 */
public class Test
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        SimDataStoreImpl data = new SimDataStoreImpl();
        Iterator<AssertionBI> iterator = data.getAssertions();
        while (iterator.hasNext())
        {
            System.out.println("============Assertion===================");
            System.out.println(iterator.next());
            System.out.println("========================================");
        }
    }
}
