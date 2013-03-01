package gov.va.legoEdit.storage.sim.util;

import gov.va.legoEdit.model.schemaModel.Pncs;
import gov.va.legoEdit.storage.CloseableIterator;
import gov.va.legoEdit.storage.IteratorClosedException;
import gov.va.sim.lego.PncsBI;

/**
 * An iterator shim that converts from LegoSchema to SIM-API.
 * 
 * @author darmbrust
 */
public class SchemaToSimPncsConvertingIterator implements CloseableIterator<PncsBI>
{
	private CloseableIterator<Pncs> pncs_;

	public SchemaToSimPncsConvertingIterator(CloseableIterator<Pncs> legos)
	{
		this.pncs_ = legos;
	}

	@Override
	public void remove()
	{
		pncs_.remove();
	}

	@Override
	public void close()
	{
		CloseableIterator<Pncs> temp = pncs_;
		if (temp != null)
		{
			temp.close();
		}
		pncs_ = null;
	}

	@Override
	public PncsBI next() throws IteratorClosedException
	{
		return SchemaToSimConversions.convert(pncs_.next());
	}

	@Override
	public boolean hasNext() throws IteratorClosedException
	{
		return pncs_.hasNext();
	}
}
