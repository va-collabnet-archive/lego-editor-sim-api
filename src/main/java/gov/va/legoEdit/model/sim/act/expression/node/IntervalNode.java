package gov.va.legoEdit.model.sim.act.expression.node;

import gov.va.sim.act.expression.node.IntervalNodeBI;
import gov.va.sim.measurement.IntervalBI;

/**
 * Lego Implementation of the SIM-API
 * @author darmbrust
 */
public class IntervalNode extends ExpressionNode<IntervalBI> implements IntervalNodeBI
{
    public IntervalNode(IntervalBI interval)
    {
        value_ = interval;
    }
}
