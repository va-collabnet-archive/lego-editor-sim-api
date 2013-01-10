package gov.va.legoEdit.model.sim.measurement;

import gov.va.sim.measurement.BoundBI;
import gov.va.sim.measurement.IntervalBI;

/**
 * Lego Implementation of the SIM-API
 * @author darmbrust
 */
public class Interval extends Measurement<IntervalBI> implements IntervalBI
{
    private BoundBI upperBound_, lowerBound_;

    public Interval(BoundBI upperBound, BoundBI lowerBound)
    {
        this.upperBound_ = upperBound;
        this.lowerBound_ = lowerBound;
        value_ = this;
    }
    
    @Override
    public BoundBI getUpperBound()
    {
        return upperBound_;
    }

    @Override
    public BoundBI getLowerBound()
    {
        return lowerBound_;
    }

    @Override
    public void setUpperBound(BoundBI upperBound)
    {
        this.upperBound_ = upperBound;
    }

    @Override
    public void setLowerBound(BoundBI lowerBound)
    {
        this.lowerBound_ = lowerBound;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Interval (");
        if (upperBound_ != null && lowerBound_ != null)
        {
            sb.append(lowerBound_.toString());
            sb.append(" - ");
            sb.append(upperBound_.toString());
        }
        else if (upperBound_ != null)
        {
            sb.append("< ");
            sb.append(upperBound_.toString());
        }
        else if (lowerBound_ != null)
        {
            sb.append("> ");
            sb.append(lowerBound_.toString());
        }
        else
        {
            sb.append("No Interval");
        }
        sb.append(")");
        return sb.toString();
    }

}
