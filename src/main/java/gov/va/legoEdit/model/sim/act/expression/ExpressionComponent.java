package gov.va.legoEdit.model.sim.act.expression;

import java.io.IOException;
import gov.va.sim.act.expression.ExpressionComponentBI;

/**
 * Lego Implementation of the SIM-API
 * @author darmbrust
 */
public abstract class ExpressionComponent implements ExpressionComponentBI
{

    @Override
    public String getPreferredText() throws IOException
    {
        return toString();
    }

    @Override
    public String getFullySpecifiedText() throws IOException
    {
        return toString();
    }
}
