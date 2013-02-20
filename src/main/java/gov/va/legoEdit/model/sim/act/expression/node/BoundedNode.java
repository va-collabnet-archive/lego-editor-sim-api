package gov.va.legoEdit.model.sim.act.expression.node;

import gov.va.sim.measurement.BoundBI;

/**
 * Lego Implementation of the SIM-API
 * @author darmbrust
 */
public class BoundedNode extends MeasurementNode<BoundBI>
{
    public BoundedNode(BoundBI bound)
    {
        value_ = bound;
    }
}
