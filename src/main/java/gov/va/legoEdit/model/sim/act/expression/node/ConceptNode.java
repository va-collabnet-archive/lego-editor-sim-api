package gov.va.legoEdit.model.sim.act.expression.node;

import gov.va.sim.act.expression.node.ConceptNodeBI;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;
import org.ihtsdo.tk.api.concept.ConceptVersionBI;
import org.ihtsdo.tk.api.description.DescriptionVersionBI;

/**
 * Lego Implementation of the SIM-API
 * @author darmbrust
 */
public class ConceptNode extends ExpressionNode<ConceptVersionBI> implements ConceptNodeBI
{
    public ConceptNode(ConceptVersionBI concept)
    {
        value_ = concept;
    }

    @Override
    public UUID getConceptUuid()
    {
        return (value_ == null ? null : value_.getPrimUuid());
    }

    @Override
    public String getPreferredDesc() throws IOException
    {
        if (value_ == null)
        {
            return null;
        }

        @SuppressWarnings("rawtypes")
        Collection<? extends DescriptionVersionBI> desc = value_.getPrefDescsActive();
        if (desc != null && desc.size() > 0)
        {
            return desc.iterator().next().getText();
        }
        else
        {
            return "MISSING PREFERRED DESCRIPTION: (" + value_.toLongString() + ")";
        }
    }

    @Override
    public String getFullySpecifiedDesc() throws IOException
    {
        if (value_ == null)
        {
            return null;
        }
        @SuppressWarnings("rawtypes")
        Collection<? extends DescriptionVersionBI> desc = value_.getFsnDescsActive();
        if (desc != null && desc.size() > 0)
        {
            return desc.iterator().next().getText();
        }
        else
        {
            return "MISSING FSN: (" + value_.toLongString() + ")";
        }
    }

    @Override
    public String getFullySpecifiedText() throws IOException
    {
        return getFullySpecifiedDesc();
    }

    @Override
    public String getPreferredText() throws IOException
    {
        return getPreferredDesc();
    }
}
