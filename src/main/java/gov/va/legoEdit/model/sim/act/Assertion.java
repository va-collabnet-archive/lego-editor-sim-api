package gov.va.legoEdit.model.sim.act;

import gov.va.sim.act.AssertionBI;
import gov.va.sim.act.expression.ExpressionBI;
import java.util.ArrayList;
import java.util.Date;

/**
 * Lego Implementation of the SIM-API
 * @author darmbrust
 */
public class Assertion extends Act implements AssertionBI
{
    ExpressionBI discernable_, qualifier_, value_;
    long[] timing_;
    
    public Assertion(ExpressionBI discernable, ExpressionBI qualifier, ExpressionBI value)
    {
        this(discernable, qualifier, value, new long[] {});
    }
    
    public Assertion(ExpressionBI discernable, ExpressionBI qualifier, ExpressionBI value, long[] timing)
    {
        this.discernable_ = discernable;
        this.qualifier_ = qualifier;
        this.value_ = value;
        
        if (timing.length == 3 || timing.length > 4)
        {
            throw new IllegalArgumentException("Timing must be 0, 1, 2, or 4 values");
        }
        
        this.timing_ = timing;
    }
    
    @Override
    public ExpressionBI getDiscernable()
    {
        return discernable_;
    }

    @Override
    public ExpressionBI getQualifier()
    {
        return qualifier_;
    }

    @Override
    public long[] getTiming()
    {
        return timing_;
    }

    @Override
    public ExpressionBI getValue()
    {
        return value_;
    }
    
    @Override 
    public String toString()
    {
        return "Discernible: " + discernable_.toString() + " Qualifier: " + qualifier_.toString() + " Value: " + value_.toString() 
                + (timing_.length > 0 ? " : " + formatTiming() : "");
    }

    public String formatTiming()
    {
        //valid timings would be 1, 2, or 4 values.
        ArrayList<String> strings = new ArrayList<>();
        for (long l : timing_)
        {
            strings.add(new Date(l).toString());
        }
        
        switch (strings.size())
        {
            case 0:
                return null;
            case 1: return strings.get(0);
            case 2: return strings.get(0) + " - " + strings.get(1);
            case 4: return "[" + strings.get(0) + " - " + strings.get(1) + "] - [" + strings.get(2) + " - " + strings.get(3) + "]";
            default:
                throw new IllegalArgumentException("Invalid timing value");
        }
    }
}
