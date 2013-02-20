package gov.va.legoEdit.model.sim.act.expression.node;

import gov.va.sim.measurement.IntervalBI;

/**
 * Lego Implementation of the SIM-API
 * @author darmbrust
 */
public class IntervalNode extends MeasurementNode<IntervalBI>
{
    public IntervalNode(IntervalBI interval)
    {
        value_ = interval;
    }
}
