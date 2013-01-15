package gov.va.legoEdit.model.sim.act.expression.node;

import gov.va.legoEdit.model.sim.act.expression.ExpressionComponent;
import gov.va.legoEdit.model.sim.act.expression.ExpressionRel;
import gov.va.sim.act.expression.ExpressionRelBI;
import gov.va.sim.act.expression.node.ExpressionNodeBI;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.ihtsdo.tk.api.concept.ConceptVersionBI;

/**
 * Lego Implementation of the SIM-API
 * @author darmbrust
 */
public abstract class ExpressionNode<T> extends ExpressionComponent implements ExpressionNodeBI<T>
{
    protected T value_;
    protected ArrayList<ExpressionRelBI> relations_ = new ArrayList<>();

    @Override
    public ExpressionRelBI addRel(ConceptVersionBI relType, ExpressionNodeBI<?> relDestination)
            throws PropertyVetoException
    {
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

    @Override
    public void appendStringForUuidHash(StringBuilder sb) throws IOException
    {
        //TODO probably need this
        throw new UnsupportedOperationException();
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
