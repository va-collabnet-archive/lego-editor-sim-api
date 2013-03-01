package gov.va.legoEdit.storage.sim.util;

import gov.va.legoEdit.model.schemaModel.LegoList;
import gov.va.legoEdit.storage.CloseableIterator;
import gov.va.legoEdit.storage.IteratorClosedException;
import gov.va.sim.lego.LegoListBI;
import java.beans.PropertyVetoException;

/**
 * An iterator shim that converts from LegoSchema to SIM-API.
 * 
 * @author darmbrust
 */
public class SchemaToSimLegoListConvertingIterator implements CloseableIterator<LegoListBI>
{
	private CloseableIterator<LegoList> ll_;

	public SchemaToSimLegoListConvertingIterator(CloseableIterator<LegoList> legos)
	{
		this.ll_ = legos;
	}

	@Override
	public void remove()
	{
		ll_.remove();
	}

	@Override
	public void close()
	{
		CloseableIterator<LegoList> temp = ll_;
		if (temp != null)
		{
			temp.close();
		}
		ll_ = null;
	}

	@Override
	public LegoListBI next() throws IteratorClosedException
	{
		try
		{
			return SchemaToSimConversions.convert(ll_.next());
		}
		catch (PropertyVetoException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean hasNext() throws IteratorClosedException
	{
		return ll_.hasNext();
	}
}
