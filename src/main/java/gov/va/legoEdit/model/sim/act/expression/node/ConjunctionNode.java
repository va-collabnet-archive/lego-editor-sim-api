package gov.va.legoEdit.model.sim.act.expression.node;

import gov.va.sim.act.expression.node.ConjunctionNodeBI;
import gov.va.sim.act.expression.node.ExpressionNodeBI;
import java.util.Collection;

/**
 * Lego Implementation of the SIM-API
 * @author darmbrust
 */
public class ConjunctionNode extends ExpressionNode<Collection<ExpressionNodeBI<?>>> implements ConjunctionNodeBI
{
    public ConjunctionNode(Collection<ExpressionNodeBI<?>> values)
    {
        value_ = values;
    }
}
