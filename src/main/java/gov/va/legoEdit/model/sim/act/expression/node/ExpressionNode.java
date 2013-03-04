package gov.va.legoEdit.model.sim.act.expression.node;

import gov.va.legoEdit.model.sim.act.expression.ExpressionComponent;
import gov.va.legoEdit.model.sim.act.expression.ExpressionRel;
import gov.va.sim.act.expression.ExpressionRelBI;
import gov.va.sim.act.expression.ExpressionRelGroupBI;
import gov.va.sim.act.expression.node.ExpressionNodeBI;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;
import org.ihtsdo.tk.api.concept.ConceptVersionBI;

/**
 * Lego Implementation of the SIM-API
 * 
 * @author darmbrust
 */
public abstract class ExpressionNode<T> extends ExpressionComponent implements ExpressionNodeBI<T>
{
	protected T value_;
	protected ArrayList<ExpressionRelBI> relations_ = new ArrayList<>();

	@Override
	public ExpressionRelBI addRel(ConceptVersionBI relType, ExpressionNodeBI<?> relDestination) throws PropertyVetoException
	{
		if (!(this instanceof ConceptNode || this instanceof ConjunctionNode))
		{
			throw new PropertyVetoException("Literals cannot have rels!", null);
		}
		ExpressionRel er = new ExpressionRel(this, relDestination, relType);
		relations_.add(er);
		return er;
	}

	@Override
	public ExpressionRelBI[] getAllRels() throws IOException
	{
		return relations_.toArray(new ExpressionRel[relations_.size()]);
	}

	@Override
	public T getValue()
	{
		return value_;
	}

	@Override
	public void generateXml(StringBuilder sb, boolean verbose) throws IOException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void generateHtml(StringBuilder sb, boolean verbose) throws IOException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void generateHtml(StringBuilder sb, boolean verbose, int depth) throws IOException
	{
		throw new UnsupportedOperationException();
	}
	

	/**
	 * Note - children should override this as well, this only does the rels (not the value) 
	 */
	@Override
	public void appendStringForUuidHash(StringBuilder sb) throws IOException
	{
		if (relations_ != null && relations_.size() > 0)
		{
			//First, need to generate an ID for each relGroup (if any)
			HashMap<ExpressionRelGroupBI, String> relGroupIds = new HashMap<>();
			for (ExpressionRelBI rel : relations_)
			{
				ExpressionRelGroupBI relGroup = rel.getRelGroup();
				if (relGroup != null)
				{
					if (!relGroupIds.containsKey(relGroup))
					{
						relGroupIds.put(relGroup, buildIdHashStringForRels(Arrays.asList(relGroup.getRelsInGroup()), null));
					}
				}
			}
			
			sb.append(buildIdHashStringForRels(relations_, relGroupIds));
		}
	}
	
	
	private String buildIdHashStringForRels(Collection<ExpressionRelBI> rels, HashMap<ExpressionRelGroupBI, String> relGroupHashes) throws IOException
	{
		TreeSet<String> temp = new TreeSet<String>();  //Want these to be sorted.
		StringBuilder tempBuilder = new StringBuilder();
		for (ExpressionRelBI rel : rels)
		{
			tempBuilder.setLength(0);
			if (relGroupHashes != null && rel.getRelGroup() != null)
			{
				//Add the relgroup hash, if any.
				tempBuilder.append(":");
				String relGroupHash = relGroupHashes.get(rel.getRelGroup());
				if (relGroupHash == null)
				{
					throw new IOException("Design failure");
				}
				tempBuilder.append(relGroupHash);
			}
			tempBuilder.append(":");
			tempBuilder.append(rel.getType() == null ? null : (rel.getType().getPrimUuid() == null ? null : rel.getType().getPrimUuid().toString()));
			tempBuilder.append(":");
			rel.getDestination().appendStringForUuidHash(tempBuilder);
			temp.add(tempBuilder.toString());
		}
		//Sort so the ID is the same, even if the order of the rels changes
		tempBuilder.setLength(0);
		for (String s : temp)
		{
			tempBuilder.append(s);
		}
		return tempBuilder.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Rels(");
		for (ExpressionRelBI e : relations_)
		{
			sb.append(e.toString());
			sb.append(", ");
		}
		if (sb.length() > 7)
		{
			sb.setLength(sb.length() - 2);
		}
		sb.append(")");
		sb.append(" " + this.getClass().getSimpleName() + "(");
		if (value_ == null)
		{
			sb.append("null");
		}
		else if (value_ instanceof Collection<?>)
		{
			sb.append("Conjunction(");
			for (ExpressionNode<?> en : ((Collection<ExpressionNode<?>>) value_))
			{
				sb.append(en.toString());
				sb.append(", ");
			}

			if (((Collection<?>) value_).size() > 0)
			{
				sb.setLength(sb.length() - 2);
			}
			sb.append(")");
		}
		else if (value_ instanceof ConceptVersionBI)
		{
			try
			{
				sb.append(getFullySpecifiedText());
			}
			catch (IOException e)
			{
				sb.append(value_.toString());
			}
		}
		else
		{
			sb.append(value_.toString());
		}
		sb.append(")");

		return sb.toString();
	}
}
