package gov.va.legoEdit.storage.sim;

import gov.va.legoEdit.storage.CloseableIterator;
import gov.va.legoEdit.storage.DataStoreException;
import gov.va.legoEdit.storage.WriteException;
import gov.va.sim.lego.LegoBI;
import gov.va.sim.lego.LegoListBI;
import gov.va.sim.lego.LegoStampBI;
import gov.va.sim.lego.PncsBI;
import java.util.List;
import java.util.UUID;

/**
 * An interface to define database methods that may be used to read and write Legos using the SIM-API.
 * These methods come directly from gov.va.legoEdit.storage.DataStoreInterface - but translated
 * to sim-api.
 * 
 * @author darmbrust
 */
public interface SimDataStoreInterface
{
	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#getLegoListByName(String)
	 */
	public LegoListBI getLegoListByName(String legoListGroupName) throws DataStoreException;

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#getLegoListByID(String)
	 */
	public LegoListBI getLegoListByID(UUID legoListUUID) throws DataStoreException;

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#getLegoLists()
	 */
	public CloseableIterator<LegoListBI> getLegoLists() throws DataStoreException;

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#getLegos(String)
	 */
	public List<LegoBI> getLegos(UUID legoUUID) throws DataStoreException;

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#getLego(String, String)
	 */
	public LegoBI getLego(UUID legoUUID, UUID stampUUID) throws DataStoreException;

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#getLegosContainingAssertion(String)
	 */
	public List<LegoBI> getLegosContainingAssertion(UUID assertionUUID);

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#getLegosUsingAssertion(String)
	 */
	public List<LegoBI> getLegosUsingAssertion(UUID assertionUUID);

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#getLegosContainingConceptIdentifiers(String...)
	 */
	public List<LegoBI> getLegosContainingConceptIdentifiers(String... conceptUuidOrSCTId) throws DataStoreException;

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#getLegos()
	 */
	public CloseableIterator<LegoBI> getLegos() throws DataStoreException;

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#getLegosForPncs(int, String)
	 */
	public List<LegoBI> getLegosForPncs(int id, String value) throws DataStoreException;

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#getLegosForPncs(int)
	 */
	public List<LegoBI> getLegosForPncs(int id) throws DataStoreException;

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#getPncs()
	 */
	public CloseableIterator<PncsBI> getPncs() throws DataStoreException;

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#getPncs(int)
	 */
	public List<PncsBI> getPncs(int id) throws DataStoreException;

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#getPncs(int, String)
	 */
	public PncsBI getPncs(int id, String value);

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#getLegoListByLego(String)
	 */
	public List<String> getLegoListByLego(UUID legoUUID) throws DataStoreException;

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#createLegoList(String, String, String, String)
	 */
	public LegoListBI createLegoList(UUID uuid, String groupName, String groupDescription, String comments) throws WriteException;

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#updateLegoListMetadata(String, String, String, String)
	 */
	public void updateLegoListMetadata(UUID legoListUUID, String groupName, String groupDescription, String comments) throws WriteException;

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#importLegoList(gov.va.legoEdit.model.schemaModel.LegoList)
	 */
	public void importLegoList(LegoListBI legoList) throws WriteException;

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#deleteLegoList(String)
	 */
	public void deleteLegoList(UUID legoListUUID) throws WriteException;

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#deleteLego(String, String, String)
	 */
	public void deleteLego(UUID legoListUUID, UUID legoUUID, UUID stampUUID) throws WriteException;

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#commitLego(gov.va.legoEdit.model.schemaModel.Lego, String)
	 */
	public LegoStampBI commitLego(LegoBI lego, UUID legoListUUID) throws WriteException;

	/**
	 * @see gov.va.legoEdit.storage.DataStoreInterface#shutdown()
	 */
	public void shutdown();

}
