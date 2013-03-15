package gov.va.legoEdit.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.va.legoEdit.formats.LegoXMLUtils;
import gov.va.legoEdit.model.SchemaEquals;
import gov.va.legoEdit.model.schemaModel.LegoList;
import gov.va.legoEdit.storage.BDBDataStoreImpl;
import gov.va.legoEdit.storage.CloseableIterator;
import gov.va.legoEdit.storage.WriteException;
import gov.va.legoEdit.storage.sim.util.ConceptVersion;
import gov.va.legoEdit.storage.sim.util.SchemaToSimConversions;
import gov.va.legoEdit.storage.sim.util.SimToSchemaConversions;
import gov.va.legoEdit.storage.wb.WBUtility;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.bind.JAXBException;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author darmbrust
 */
public class SimAPITest
{
	@BeforeClass
	public static void oneTimeSetUp() throws Exception
	{
		BDBDataStoreImpl.dbFolderPath = new File("testDB");
		FileUtils.deleteDirectory(BDBDataStoreImpl.dbFolderPath);
		WBUtility.snomedIdTypeNid = ConceptVersion.authorityNid;
	}

	@AfterClass
	public static void oneTimeTearDown() throws Exception
	{
		BDBDataStoreImpl.getInstance().shutdown();
	}

	@Before
	public void setUp() throws Exception
	{
		clearDB();
		assertFalse("Datastore isn't empty!", BDBDataStoreImpl.getInstance().getLegoLists().hasNext());
	}

	@After
	public void tearDown() throws Exception
	{
		clearDB();
	}

	private void clearDB() throws WriteException
	{
		CloseableIterator<LegoList> iterator = BDBDataStoreImpl.getInstance().getLegoLists();
		ArrayList<String> toDelete = new ArrayList<>();
		while (iterator.hasNext())
		{
			LegoList ll = iterator.next();
			toDelete.add(ll.getLegoListUUID());
		}
		for (String s : toDelete)
		{
			BDBDataStoreImpl.getInstance().deleteLegoList(s);
		}
	}

	@Test
	public void testSimRoundtrip() throws WriteException, JAXBException, IOException, PropertyVetoException
	{
		LegoList initial = LegoXMLUtils.readLegoList(SimAPITest.class.getResourceAsStream("badDay.xml"));
		BDBDataStoreImpl.getInstance().importLegoList(initial);

		// reread, just incase
		initial = LegoXMLUtils.readLegoList(SimAPITest.class.getResourceAsStream("badDay.xml"));
		LegoList readBack = BDBDataStoreImpl.getInstance().getLegoListByID(initial.getLegoListUUID());

		assertTrue(SchemaEquals.equals(initial, readBack));

		// Ok, now we have known content in the DB. Lets try roundtripping it through the sim-api implementation.
		CloseableIterator<LegoList> iter = BDBDataStoreImpl.getInstance().getLegoLists();
		try
		{
			while (iter.hasNext())
			{
				LegoList ll = iter.next();

				LegoList converted = SimToSchemaConversions.convert(SchemaToSimConversions.convert(ll));
				assertTrue("LegoList from sim-api did not equal legoList prior to sim-api", SchemaEquals.equals(ll, converted));
			}
		}
		finally
		{
			iter.close();
		}
	}
}
