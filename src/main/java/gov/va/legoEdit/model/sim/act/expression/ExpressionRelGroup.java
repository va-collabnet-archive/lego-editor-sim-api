package gov.va.legoEdit.model.sim.act.expression;

import gov.va.sim.act.expression.ExpressionRelBI;
import gov.va.sim.act.expression.ExpressionRelGroupBI;

/**
 * Lego Implementation of the SIM-API
 * @author darmbrust
 */
public class ExpressionRelGroup implements ExpressionRelGroupBI
{
    private ExpressionRelBI[] relsInGroup_;
    
    public ExpressionRelGroup(ExpressionRelBI[] relsInGroup)
    {
        this.relsInGroup_ = relsInGroup;
    }
    
    @Override
    public ExpressionRelBI[] getRelsInGroup()
    {
        return relsInGroup_;
    }

    @Override
    public void setRelsInGroup(ExpressionRelBI[] relsInGroup)
    {
        this.relsInGroup_ = relsInGroup;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (ExpressionRelBI e : relsInGroup_)
        {
            sb.append(e.toString());
            sb.append(", ");
        }
        
        if (sb.length() > 2)
        {
            sb.setLength(sb.length() - 2);
        }
                
        return "RelGroup(" + sb.toString() + ")";
    }
}
