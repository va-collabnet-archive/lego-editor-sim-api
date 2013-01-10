package gov.va.legoEdit.model.sim.act.expression.node;

import gov.va.sim.act.expression.node.BoundedNodeBI;
import gov.va.sim.measurement.BoundBI;

/**
 * Lego Implementation of the SIM-API
 * @author darmbrust
 */
public class BoundedNode extends ExpressionNode<BoundBI> implements BoundedNodeBI
{
    public BoundedNode(BoundBI bound)
    {
        value_ = bound;
    }
}
