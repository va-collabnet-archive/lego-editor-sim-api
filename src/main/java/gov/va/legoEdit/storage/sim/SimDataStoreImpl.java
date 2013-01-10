package gov.va.legoEdit.storage.sim;

import gov.va.legoEdit.storage.BDBDataStoreImpl;
import gov.va.legoEdit.storage.CloseableIterator;
import gov.va.legoEdit.storage.sim.util.LegoToSimAssertionConvertingIterator;
import gov.va.sim.act.AssertionBI;

/**
 * An implementation of the @see {@link SimDataStoreInterface} which is backed by the 
 * Lego Schema BDB based datastore.  
 * @author darmbrust
 */
public class SimDataStoreImpl implements SimDataStoreInterface
{
    @Override
    public CloseableIterator<AssertionBI> getAssertions()
    {
        return new LegoToSimAssertionConvertingIterator(BDBDataStoreImpl.getInstance().getLegos());
    }

}
