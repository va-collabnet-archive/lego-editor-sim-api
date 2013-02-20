package gov.va.legoEdit.model.sim.act;

import gov.va.sim.act.AssertionBI;
import gov.va.sim.act.AssertionRefBI;
import gov.va.sim.act.expression.ExpressionBI;
import gov.va.sim.measurement.BoundBI;
import gov.va.sim.measurement.IntervalBI;
import gov.va.sim.measurement.MeasurementBI;
import gov.va.sim.measurement.PointBI;
import java.util.Collection;
import java.util.UUID;

/**
 * Lego Implementation of the SIM-API
 * 
 * @author darmbrust
 */
public class Assertion extends Act implements AssertionBI
{
	ExpressionBI discernable_, qualifier_, value_;
	MeasurementBI<? extends MeasurementBI<?>> timing_;
	Collection<AssertionRefBI> compositeAssertions_;

	public Assertion(UUID assertionUUID, ExpressionBI discernable, ExpressionBI qualifier, ExpressionBI value)
	{
		this(assertionUUID, discernable, qualifier, value, null, null);
	}

	public Assertion(UUID assertionUUID, ExpressionBI discernable, ExpressionBI qualifier, ExpressionBI value, MeasurementBI<? extends MeasurementBI<?>> timing,
			Collection<AssertionRefBI> compositeAssertions)
	{
		this.instanceUUID_ = assertionUUID;
		this.discernable_ = discernable;
		this.qualifier_ = qualifier;
		this.value_ = value;
		verifyTiming(timing);
		this.timing_ = timing;
		this.compositeAssertions_ = compositeAssertions;
	}

	private void verifyTiming(MeasurementBI<? extends MeasurementBI<?>> timing)
	{
		if (timing != null)
		{
			// All timing values should be longs, and units concept should be (not yet determined) if it is provided.
			if (timing instanceof PointBI)
			{
				verifyPoint((PointBI) timing);
			}
			else if (timing instanceof BoundBI)
			{
				verifyBound((BoundBI) timing);
			}
			else if (timing instanceof IntervalBI)
			{
				IntervalBI interval = (IntervalBI) timing;
				verifyBound(interval.getLowerBound());
				verifyBound(interval.getUpperBound());
			}
		}
	}

	private void verifyBound(BoundBI bound)
	{
		if (bound != null)
		{
			verifyPoint(bound.getLowerLimit());
			verifyPoint(bound.getUpperLimit());
		}
	}

	private void verifyPoint(PointBI point)
	{
		if (point != null)
		{
			if (point.getUnitsOfMeasure() != null)
			{
				// TODO units of measure needs to be a (yet unspecified) snomed concept
			}
			if (point.getPointValue() != null && !(point.getPointValue() instanceof Long))
			{
				throw new IllegalArgumentException("Timings must be long values - got: " + point);
			}
		}
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
	public MeasurementBI<?> getTiming()
	{
		return timing_;
	}

	@Override
	public ExpressionBI getValue()
	{
		return value_;
	}

	@Override
	public Collection<AssertionRefBI> getCompositeAssertionComponents()
	{
		return compositeAssertions_;
	}

	@Override
	public String toString()
	{
		return "Discernible: " + discernable_.toString() + " Qualifier: " + qualifier_.toString() + " Value: " + value_.toString()
				+ (timing_ != null ? " Timing: " + timing_.toString() : "");
	}
}
