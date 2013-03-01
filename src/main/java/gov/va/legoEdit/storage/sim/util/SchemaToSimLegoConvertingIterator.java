package gov.va.legoEdit.storage.sim.util;

import java.beans.PropertyVetoException;
import gov.va.legoEdit.model.schemaModel.Lego;
import gov.va.legoEdit.storage.CloseableIterator;
import gov.va.legoEdit.storage.IteratorClosedException;
import gov.va.sim.lego.LegoBI;

/**
 * An iterator shim that converts from LegoSchema to SIM-API.
 * 
 * @author darmbrust
 */
public class SchemaToSimLegoConvertingIterator implements CloseableIterator<LegoBI>
{
	private CloseableIterator<Lego> legos_;

	public SchemaToSimLegoConvertingIterator(CloseableIterator<Lego> legos)
	{
		this.legos_ = legos;
	}

	@Override
	public void remove()
	{
		legos_.remove();
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
	public LegoBI next() throws IteratorClosedException
	{
		try
		{
			return SchemaToSimConversions.convert(legos_.next());
		}
		catch (PropertyVetoException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean hasNext() throws IteratorClosedException
	{
		return legos_.hasNext();
	}
}
