package gov.va.legoEdit.model.sim.act.expression.node;

import gov.va.sim.act.expression.node.PointNodeBI;
import gov.va.sim.measurement.PointBI;

/**
 * Lego Implementation of the SIM-API
 * @author darmbrust
 */
public class PointNode extends ExpressionNode<PointBI> implements PointNodeBI
{
    public PointNode(PointBI point)
    {
        value_ = point;
    }
}
