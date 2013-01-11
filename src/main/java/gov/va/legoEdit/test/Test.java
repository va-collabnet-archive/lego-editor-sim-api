package gov.va.legoEdit.test;

import gov.va.legoEdit.model.SchemaEquals;
import gov.va.legoEdit.model.schemaModel.Assertion;
import gov.va.legoEdit.model.schemaModel.Lego;
import gov.va.legoEdit.storage.BDBDataStoreImpl;
import gov.va.legoEdit.storage.sim.SimDataStoreImpl;
import gov.va.legoEdit.storage.sim.util.SchemaToSimConversions;
import gov.va.legoEdit.storage.sim.util.SimToSchemaConversions;
import gov.va.legoEdit.storage.wb.WBDataStore;
import gov.va.sim.act.AssertionBI;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

/**
 * Just some test code for playing with the sim-api / Lego conversions.
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
        //start the thing
        WBDataStore.Ts().getConcept(UUID.randomUUID());
        SimDataStoreImpl data = new SimDataStoreImpl();
        Iterator<AssertionBI> iterator = data.getAssertions();
        while (iterator.hasNext())
        {
            System.out.println("============Assertion===================");
            System.out.println(iterator.next());
            System.out.println("========================================");
        }
        
        
        System.out.println("============Round Trip Checks===================");
        Iterator<Lego> iter = BDBDataStoreImpl.getInstance().getLegos();
        while (iter.hasNext())
        {
            Lego l = iter.next();
            
            for (Assertion a : l.getAssertion())
            {
                
                 Assertion converted = SimToSchemaConversions.convert(SchemaToSimConversions.convert(a));
                 boolean equal = SchemaEquals.equals(a, converted);
                 System.out.println((equal ? "good" : "failed"));
            }
        }
        
        BDBDataStoreImpl.getInstance().shutdown();
        WBDataStore.shutdown();
    }
}
