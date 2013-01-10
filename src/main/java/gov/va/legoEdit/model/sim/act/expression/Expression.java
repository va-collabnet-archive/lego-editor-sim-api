package gov.va.legoEdit.model.sim.act.expression;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import gov.va.sim.act.expression.ExpressionBI;
import gov.va.sim.act.expression.node.ExpressionNodeBI;

/**
 * Lego Implementation of the SIM-API
 * @author darmbrust
 */
public class Expression implements ExpressionBI
{
    private ExpressionNodeBI<?> focus_;
    
    public Expression(ExpressionNodeBI<?> focus)
    {
        this.focus_ = focus;
    }

    @Override
    public ExpressionNodeBI<?> getFocus()
    {
        return focus_;
    }

    @Override
    public void setFocus(ExpressionNodeBI<?> focus) throws PropertyVetoException
    {
        this.focus_ = focus;
    }

    @Override
    public UUID getUuid() throws IOException, UnsupportedEncodingException, NoSuchAlgorithmException
    {
        // TODO implement getUUID
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equivalent(ExpressionBI another)
    {
        // TODO implement equivalent
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean subsumes(ExpressionBI another)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean subsumedBy(ExpressionBI another)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString()
    {
        return "Expression: " + focus_;
    }
}
