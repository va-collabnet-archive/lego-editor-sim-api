package gov.va.legoEdit.storage.sim;

import gov.va.legoEdit.model.schemaModel.Lego;
import gov.va.legoEdit.model.schemaModel.Pncs;
import gov.va.legoEdit.storage.BDBDataStoreImpl;
import gov.va.legoEdit.storage.CloseableIterator;
import gov.va.legoEdit.storage.DataStoreException;
import gov.va.legoEdit.storage.DataStoreInterface;
import gov.va.legoEdit.storage.WriteException;
import gov.va.legoEdit.storage.sim.util.SchemaToSimConversions;
import gov.va.legoEdit.storage.sim.util.SchemaToSimLegoConvertingIterator;
import gov.va.legoEdit.storage.sim.util.SchemaToSimLegoListConvertingIterator;
import gov.va.legoEdit.storage.sim.util.SchemaToSimPncsConvertingIterator;
import gov.va.legoEdit.storage.sim.util.SimToSchemaConversions;
import gov.va.sim.lego.LegoBI;
import gov.va.sim.lego.LegoListBI;
import gov.va.sim.lego.LegoStampBI;
import gov.va.sim.lego.PncsBI;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * An implementation of the @see {@link SimDataStoreInterface} which is backed by the
 * Lego Schema BDB based datastore.
 * 
 * @author darmbrust
 */
public class SimDataStoreImpl implements SimDataStoreInterface
{
	DataStoreInterface ds = BDBDataStoreImpl.getInstance();

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#getLegoListByName(java.lang.String)
	 */
	@Override
	public LegoListBI getLegoListByName(String legoListGroupName) throws DataStoreException
	{
		try
		{
			return SchemaToSimConversions.convert(ds.getLegoListByName(legoListGroupName));
		}
		catch (PropertyVetoException e)
		{
			throw new DataStoreException(e);
		}
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#getLegoListByID(java.util.UUID)
	 */
	@Override
	public LegoListBI getLegoListByID(UUID legoListUUID) throws DataStoreException
	{
		try
		{
			return SchemaToSimConversions.convert(ds.getLegoListByID(legoListUUID.toString()));
		}
		catch (PropertyVetoException e)
		{
			throw new DataStoreException(e);
		}
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#getLegoLists()
	 */
	@Override
	public CloseableIterator<LegoListBI> getLegoLists() throws DataStoreException
	{
		return new SchemaToSimLegoListConvertingIterator(ds.getLegoLists());
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#getLegos(java.util.UUID)
	 */
	@Override
	public List<LegoBI> getLegos(UUID legoUUID) throws DataStoreException
	{
		try
		{
			ArrayList<LegoBI> legos = new ArrayList<>();
			for (Lego l : ds.getLegos(legoUUID.toString()))
			{
				legos.add(SchemaToSimConversions.convert(l));
			}
			return legos;
		}
		catch (PropertyVetoException e)
		{
			throw new DataStoreException(e);
		}
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#getLego(java.util.UUID, java.util.UUID)
	 */
	@Override
	public LegoBI getLego(UUID legoUUID, UUID stampUUID) throws DataStoreException
	{
		try
		{
			return SchemaToSimConversions.convert(ds.getLego(legoUUID.toString(), stampUUID.toString()));
		}
		catch (PropertyVetoException e)
		{
			throw new DataStoreException(e);
		}
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#getLegosContainingAssertion(java.util.UUID)
	 */
	@Override
	public List<LegoBI> getLegosContainingAssertion(UUID assertionUUID)
	{
		try
		{
			ArrayList<LegoBI> legos = new ArrayList<>();
			for (Lego l : ds.getLegosContainingAssertion(assertionUUID.toString()))
			{
				legos.add(SchemaToSimConversions.convert(l));
			}
			return legos;
		}
		catch (PropertyVetoException e)
		{
			throw new DataStoreException(e);
		}
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#getLegosUsingAssertion(java.util.UUID)
	 */
	@Override
	public List<LegoBI> getLegosUsingAssertion(UUID assertionUUID)
	{
		try
		{
			ArrayList<LegoBI> legos = new ArrayList<>();
			for (Lego l : ds.getLegosUsingAssertion(assertionUUID.toString()))
			{
				legos.add(SchemaToSimConversions.convert(l));
			}
			return legos;
		}
		catch (PropertyVetoException e)
		{
			throw new DataStoreException(e);
		}
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#getLegosContainingConceptIdentifiers(java.lang.String[])
	 */
	@Override
	public List<LegoBI> getLegosContainingConceptIdentifiers(String... conceptUuidOrSCTId) throws DataStoreException
	{
		try
		{
			ArrayList<LegoBI> legos = new ArrayList<>();
			for (Lego l : ds.getLegosContainingConceptIdentifiers(conceptUuidOrSCTId))
			{
				legos.add(SchemaToSimConversions.convert(l));
			}
			return legos;
		}
		catch (PropertyVetoException e)
		{
			throw new DataStoreException(e);
		}
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#getLegos()
	 */
	@Override
	public CloseableIterator<LegoBI> getLegos() throws DataStoreException
	{
		return new SchemaToSimLegoConvertingIterator(ds.getLegos());
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#getLegosForPncs(int, java.lang.String)
	 */
	@Override
	public List<LegoBI> getLegosForPncs(int id, String value) throws DataStoreException
	{
		try
		{
			ArrayList<LegoBI> legos = new ArrayList<>();
			for (Lego l : ds.getLegosForPncs(id, value))
			{
				legos.add(SchemaToSimConversions.convert(l));
			}
			return legos;
		}
		catch (PropertyVetoException e)
		{
			throw new DataStoreException(e);
		}
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#getLegosForPncs(int)
	 */
	@Override
	public List<LegoBI> getLegosForPncs(int id) throws DataStoreException
	{
		try
		{
			ArrayList<LegoBI> legos = new ArrayList<>();
			for (Lego l : ds.getLegosForPncs(id))
			{
				legos.add(SchemaToSimConversions.convert(l));
			}
			return legos;
		}
		catch (PropertyVetoException e)
		{
			throw new DataStoreException(e);
		}
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#getPncs()
	 */
	@Override
	public CloseableIterator<PncsBI> getPncs() throws DataStoreException
	{
		return new SchemaToSimPncsConvertingIterator(ds.getPncs());
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#getPncs(int)
	 */
	@Override
	public List<PncsBI> getPncs(int id) throws DataStoreException
	{
		ArrayList<PncsBI> pncs = new ArrayList<>();
		for (Pncs p : ds.getPncs(id))
		{
			pncs.add(SchemaToSimConversions.convert(p));
		}
		return pncs;
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#getPncs(int, java.lang.String)
	 */
	@Override
	public PncsBI getPncs(int id, String value)
	{
		return SchemaToSimConversions.convert(ds.getPncs(id, value));
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#getLegoListByLego(java.util.UUID)
	 */
	@Override
	public List<String> getLegoListByLego(UUID legoUUID) throws DataStoreException
	{
		return ds.getLegoListByLego(legoUUID.toString());
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#createLegoList(java.util.UUID, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public LegoListBI createLegoList(UUID uuid, String groupName, String groupDescription, String comments) throws WriteException
	{
		try
		{
			return SchemaToSimConversions.convert(ds.createLegoList(uuid.toString(), groupName, groupDescription, comments));
		}
		catch (PropertyVetoException e)
		{
			throw new DataStoreException(e);
		}
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#updateLegoListMetadata(java.util.UUID, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void updateLegoListMetadata(UUID legoListUUID, String groupName, String groupDescription, String comments) throws WriteException
	{
		ds.updateLegoListMetadata(legoListUUID.toString(), groupName, groupDescription, comments);
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#importLegoList(gov.va.sim.lego.LegoListBI)
	 */
	@Override
	public void importLegoList(LegoListBI legoList) throws WriteException
	{
		try
		{
			ds.importLegoList(SimToSchemaConversions.convert(legoList));
		}
		catch (IOException e)
		{
			throw new WriteException(e);
		}
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#deleteLegoList(java.util.UUID)
	 */
	@Override
	public void deleteLegoList(UUID legoListUUID) throws WriteException
	{
		ds.deleteLegoList(legoListUUID.toString());
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#deleteLego(java.util.UUID, java.util.UUID, java.util.UUID)
	 */
	@Override
	public void deleteLego(UUID legoListUUID, UUID legoUUID, UUID stampUUID) throws WriteException
	{
		ds.deleteLego(legoListUUID.toString(), legoUUID.toString(), stampUUID.toString());
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#commitLego(gov.va.sim.lego.LegoBI, java.util.UUID)
	 */
	@Override
	public LegoStampBI commitLego(LegoBI lego, UUID legoListUUID) throws WriteException
	{
		try
		{
			return SchemaToSimConversions.convert(ds.commitLego(SimToSchemaConversions.convert(lego), legoListUUID.toString()));
		}
		catch (IOException e)
		{
			throw new WriteException(e);
		}
	}

	/**
	 * @see gov.va.legoEdit.storage.sim.SimDataStoreInterface#shutdown()
	 */
	@Override
	public void shutdown()
	{
		ds.shutdown();
	}
}
