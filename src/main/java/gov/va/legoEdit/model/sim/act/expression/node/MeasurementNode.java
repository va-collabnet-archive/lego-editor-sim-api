package gov.va.legoEdit.model.sim.act.expression.node;

import gov.va.sim.act.expression.node.MeasurementNodeBI;
import gov.va.sim.measurement.MeasurementBI;

/**
 * Lego Implementation of the SIM-API
 * @author darmbrust
 */
public abstract class MeasurementNode<T extends MeasurementBI<T>> extends ExpressionNode<MeasurementBI<T>> implements MeasurementNodeBI<MeasurementBI<T>>
{
    //Well, this is an easy one.
}
