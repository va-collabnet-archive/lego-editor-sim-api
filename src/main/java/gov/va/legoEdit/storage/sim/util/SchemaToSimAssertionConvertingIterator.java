package gov.va.legoEdit.storage.sim.util;

import gov.va.legoEdit.model.schemaModel.Lego;
import gov.va.legoEdit.model.sim.act.Assertion;
import gov.va.legoEdit.storage.CloseableIterator;
import gov.va.legoEdit.storage.IteratorClosedException;
import gov.va.sim.act.AssertionBI;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An iterator implementation that reads Legos (via an iterator) from the lego schema format 
 * and returns kicks out Assertions, as defined by the SIM-API.
 * @author darmbrust
 */
public class SchemaToSimAssertionConvertingIterator implements CloseableIterator<AssertionBI>
{
    private Logger logger = LoggerFactory.getLogger(SchemaToSimAssertionConvertingIterator.class);
    private CloseableIterator<Lego> legos_;
    private ArrayList<Assertion> assertionBuffer_ = new ArrayList<Assertion>();
    
    public SchemaToSimAssertionConvertingIterator(CloseableIterator<Lego> legos)
    {
        this.legos_ = legos;
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void close()
    {
        CloseableIterator<Lego> temp = legos_;
        if (temp != null)
        {
            temp.close();
        }
        legos_ = null;
    }

    @Override
    public Assertion next() throws IteratorClosedException
    {
        if (hasNext())
        {
            return assertionBuffer_.remove(0);
        }
        else
        {
            throw new NoSuchElementException();
        }
    }

    @Override
    public boolean hasNext() throws IteratorClosedException
    {
        if (assertionBuffer_.size() > 0)
        {
            return true;
        }
        else if (legos_.hasNext())
        {
            try
            {
                fillBuffer(legos_.next());
            }
            catch (PropertyVetoException | IOException e)
            {
                logger.error("Conversion failure", e);
                throw new RuntimeException("Conversion failure", e);
            }
        }
        
        return assertionBuffer_.size() > 0;
    }
    
    private void fillBuffer(Lego l) throws PropertyVetoException, IOException
    {
        for (gov.va.legoEdit.model.schemaModel.Assertion schemaAssertion : l.getAssertion())
        {
            assertionBuffer_.add(SchemaToSimConversions.convert(schemaAssertion));
        }
    }
}
