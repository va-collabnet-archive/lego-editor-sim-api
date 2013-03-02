package gov.va.legoEdit.model.sim.act.expression.node;

import gov.va.sim.measurement.PointBI;

/**
 * Lego Implementation of the SIM-API
 * 
 * @author darmbrust
 */
public class PointNode extends MeasurementNode<PointBI>
{
	public PointNode(PointBI point)
	{
		value_ = point;
	}

	public void appendStringForUuidHash(StringBuilder sb)
	{
		if (value_ != null)
		{
			value_.getValue().appendStringForUuidHash(sb);
		}
		else
		{
			sb.append("null");
		}
		//no rels on point
	}
}
