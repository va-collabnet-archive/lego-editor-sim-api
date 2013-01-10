package gov.va.legoEdit.model.sim.act.expression;

import gov.va.sim.act.expression.ExpressionRelBI;
import gov.va.sim.act.expression.ExpressionRelGroupBI;
import gov.va.sim.act.expression.node.ExpressionNodeBI;
import org.ihtsdo.tk.api.concept.ConceptVersionBI;

/**
 * Lego Implementation of the SIM-API
 * @author darmbrust
 */
public class ExpressionRel extends ExpressionComponent implements ExpressionRelBI
{
    private ExpressionNodeBI<?> origin_, destination_;
    private ConceptVersionBI type_;
    private ExpressionRelGroupBI relGroup_;
    
    public ExpressionRel(ExpressionNodeBI<?> origin, ExpressionNodeBI<?> destination, ConceptVersionBI type)
    {
        this(origin, destination, type, null);
    }
    
    public ExpressionRel(ExpressionNodeBI<?> origin, ExpressionNodeBI<?> destination, ConceptVersionBI type, ExpressionRelGroupBI relGroup)
    {
        this.origin_ = origin;
        this.destination_ = destination;
        this.type_ = type;
        this.relGroup_ = relGroup;
    }

    @Override
    public ExpressionNodeBI<?> getOrigin()
    {
        return origin_;
    }

    @Override
    public ConceptVersionBI getType()
    {
        return type_;
    }

    @Override
    public ExpressionNodeBI<?> getDestination()
    {
     return destination_;
    }

    @Override
    public ExpressionRelGroupBI getRelGroup()
    {
        return relGroup_;
    }
    
    @Override
    public void setRelGroup(ExpressionRelGroupBI relGroup)
    {
        this.relGroup_ = relGroup;
    }

    @Override
    public String toString()
    {
        //don't print origin
        return "Rel: " + type_.toUserString() + " : " + destination_ 
                + (relGroup_ == null ? "" : " : Rel Group Member");  //Not doing toString on relGroup, as it would make an infinite loop
    }
}
