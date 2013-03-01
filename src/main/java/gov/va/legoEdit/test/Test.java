package gov.va.legoEdit.test;

import gov.va.legoEdit.model.SchemaEquals;
import gov.va.legoEdit.model.schemaModel.LegoList;
import gov.va.legoEdit.storage.BDBDataStoreImpl;
import gov.va.legoEdit.storage.sim.SimDataStoreImpl;
import gov.va.legoEdit.storage.sim.util.SchemaToSimConversions;
import gov.va.legoEdit.storage.sim.util.SimToSchemaConversions;
import gov.va.legoEdit.storage.wb.WBDataStore;
import gov.va.sim.lego.LegoListBI;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

/**
 * Just some test code for playing with the sim-api / Lego conversions.
 * 
 * @author darmbrust
 */
public class Test
{

	/**
	 * @param args
	 * @throws IOException
	 * @throws PropertyVetoException
	 */
	public static void main(String[] args) throws PropertyVetoException, IOException
	{
		// start the thing
		WBDataStore.Ts().getConcept(UUID.randomUUID());
		SimDataStoreImpl data = new SimDataStoreImpl();
		Iterator<LegoListBI> iterator = data.getLegoLists();
		while (iterator.hasNext())
		{
			System.out.println("============Assertion===================");
			System.out.println(iterator.next());
			System.out.println("========================================");
		}

		System.out.println("============Round Trip Checks===================");
		Iterator<LegoList> iter = BDBDataStoreImpl.getInstance().getLegoLists();
		while (iter.hasNext())
		{
			LegoList ll = iter.next();

			LegoList converted = SimToSchemaConversions.convert(SchemaToSimConversions.convert(ll));
			boolean equal = SchemaEquals.equals(ll, converted);
			System.out.println((equal ? "good" : "failed"));
		}

		BDBDataStoreImpl.getInstance().shutdown();
		WBDataStore.shutdown();
	}
}
