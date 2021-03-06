package gov.va.legoEdit.model.sim.act.expression.node;

import gov.va.sim.act.expression.node.BooleanNodeBI;

/**
 * Lego Implementation of the SIM-API
 * 
 * @author darmbrust
 */
public class BooleanNode extends ExpressionNode<Boolean> implements BooleanNodeBI
{
	public BooleanNode(Boolean value)
	{
		value_ = value;
	}

	@Override
	public void appendStringForUuidHash(StringBuilder sb)
	{
		sb.append(":");
		sb.append(value_);
		//node not allowed to have rels
	}
}
