package gov.va.legoEdit.model.sim.act.expression.node;

import gov.va.sim.act.expression.node.ConjunctionNodeBI;
import gov.va.sim.act.expression.node.ExpressionNodeBI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Lego Implementation of the SIM-API
 * 
 * @author darmbrust
 */
public class ConjunctionNode extends ExpressionNode<Collection<ExpressionNodeBI<?>>> implements ConjunctionNodeBI
{
	public ConjunctionNode(Collection<ExpressionNodeBI<?>> values)
	{
		value_ = values;
	}

	@Override
	public void appendStringForUuidHash(StringBuilder sb) throws IOException
	{
		if (value_ == null)
		{
			sb.append("null");
		}
		else
		{
			ArrayList<String> tempResults = new ArrayList<>();
			StringBuilder tempBuilder = new StringBuilder();
			
			//Sort these so the generated ID is the same regardless of order of conjunction.
			for (ExpressionNodeBI<?> node : value_)
			{
				tempBuilder.setLength(0);
				node.appendStringForUuidHash(tempBuilder);
				tempResults.add(tempBuilder.toString());
			}
			
			Collections.sort(tempResults);
			
			for (String s : tempResults)
			{
				sb.append(s);
			}
		}
		
		//get the rels
		super.appendStringForUuidHash(sb);
	}
}
