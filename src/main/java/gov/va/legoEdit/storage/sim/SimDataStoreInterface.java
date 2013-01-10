package gov.va.legoEdit.storage.sim;

import gov.va.legoEdit.storage.CloseableIterator;
import gov.va.sim.act.AssertionBI;

/**
 * An interface to define database methods that may be used to read and write Legos using the SIM-API.
 * @author darmbrust
 */
public interface SimDataStoreInterface
{
    
    /**
     * Return all assertions in the database
     */
    public CloseableIterator<AssertionBI> getAssertions();
}
