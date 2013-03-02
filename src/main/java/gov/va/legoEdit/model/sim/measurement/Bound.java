package gov.va.legoEdit.model.sim.measurement;

import gov.va.sim.measurement.BoundBI;
import gov.va.sim.measurement.PointBI;

/**
 * Lego Implementation of the SIM-API
 * 
 * @author darmbrust
 */
public class Bound extends Measurement<BoundBI> implements BoundBI
{
	private PointBI upperLimit_, lowerLimit_;
	private boolean upperLimitInclusive_, lowerLimitInclusive_;

	/**
	 * Create with both inclusive.
	 */
	public Bound(PointBI upperLimit, PointBI lowerLimit)
	{
		this(upperLimit, true, lowerLimit, true);
	}

	/**
	 * Inclusives default to true, if null specified.
	 */
	public Bound(PointBI upperLimit, Boolean upperLimitInclusive, PointBI lowerLimit, Boolean lowerLimitInclusive)
	{
		upperLimit_ = upperLimit;
		lowerLimit_ = lowerLimit;
		upperLimitInclusive_ = (upperLimitInclusive == null ? true : upperLimitInclusive.booleanValue());
		lowerLimitInclusive_ = (lowerLimitInclusive == null ? true : lowerLimitInclusive.booleanValue());
		value_ = this;
	}

	@Override
	public PointBI getUpperLimit()
	{
		return upperLimit_;
	}

	@Override
	public PointBI getLowerLimit()
	{
		return lowerLimit_;
	}

	@Override
	public void setUpperLimit(PointBI upperLimit)
	{
		this.upperLimit_ = upperLimit;
	}

	@Override
	public void setLowerLimit(PointBI lowerLimit)
	{
		this.lowerLimit_ = lowerLimit;
	}

	@Override
	public boolean isUpperLimitInclusive()
	{
		return upperLimitInclusive_;
	}

	@Override
	public boolean isLowerLimitInclusive()
	{
		return lowerLimitInclusive_;
	}

	@Override
	public void setUpperLimitInclusive(boolean upperLimitInclusive)
	{
		this.upperLimitInclusive_ = upperLimitInclusive;
	}

	@Override
	public void setLowerLimitInclusive(boolean lowerLimitInclusive)
	{
		this.lowerLimitInclusive_ = lowerLimitInclusive;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Bound (");
		if (lowerLimit_ != null && upperLimit_ != null)
		{
			sb.append(lowerLimit_.toString());
			sb.append(lowerLimitInclusive_ ? " <= X " : " < X ");
			sb.append(upperLimitInclusive_ ? " <= " : " < ");
			sb.append(upperLimit_.toString());
		}
		else if (upperLimit_ != null)
		{
			sb.append("X ");
			sb.append(upperLimitInclusive_ ? " <= " : " < ");
			sb.append(upperLimit_.toString());
		}
		else if (lowerLimit_ != null)
		{
			sb.append("X ");
			sb.append(lowerLimitInclusive_ ? " >= " : " > ");
			sb.append(lowerLimit_.toString());
		}
		else
		{
			sb.append("No Bound");
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public void appendStringForUuidHash(StringBuilder sb)
	{
		sb.append(":");
		sb.append(upperLimitInclusive_);
		upperLimit_.appendStringForUuidHash(sb);
		sb.append(":");
		sb.append(lowerLimitInclusive_);
		lowerLimit_.appendStringForUuidHash(sb);
	}
}
