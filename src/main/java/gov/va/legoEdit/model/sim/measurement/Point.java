package gov.va.legoEdit.model.sim.measurement;

import java.io.IOException;
import java.util.Collection;
import org.ihtsdo.tk.api.concept.ConceptVersionBI;
import org.ihtsdo.tk.api.description.DescriptionVersionBI;
import gov.va.sim.measurement.PointBI;

/**
 * Lego Implementation of the SIM-API
 * 
 * @author darmbrust
 */
public class Point extends Measurement<PointBI> implements PointBI
{
	private Number pointValue_;
	protected ConceptVersionBI units_;

	public Point(Number pointValue)
	{
		this.pointValue_ = pointValue;
		value_ = this;
	}

	public Point(Number pointValue, ConceptVersionBI units)
	{
		this.units_ = units;
		this.pointValue_ = pointValue;
		value_ = this;
	}

	@Override
	public Number getPointValue()
	{
		return pointValue_;
	}

	@Override
	public void setPointValue(Number pointValue)
	{
		this.pointValue_ = pointValue;
	}

	@Override
	public ConceptVersionBI getUnitsOfMeasure()
	{
		return units_;
	}

	@Override
	public void setUnitsOfMeasure(ConceptVersionBI unitsOfMeasure)
	{
		this.units_ = unitsOfMeasure;
	}

	@Override
	public String toString()
	{
		return "Point (" + unitsToString() + pointValue_.toString() + ")";
	}

	private String unitsToString()
	{
		if (units_ != null)
		{
			try
			{
				@SuppressWarnings("rawtypes")
				Collection<? extends DescriptionVersionBI> desc = units_.getPrefDescsActive();
				if (desc != null && desc.size() > 0)
				{
					return "Units (" + desc.iterator().next().getText() + ") ";
				}
				else
				{
					return "Units (MISSING PREFERRED DESCRIPTION: (" + units_.toLongString() + ")) ";
				}
			}
			catch (IOException e)
			{
				return "Units (GAK (" + units_.toLongString() + ")) ";
			}
		}
		return "";
	}

	@Override
	public void appendStringForUuidHash(StringBuilder sb)
	{
		sb.append(":");
		sb.append(units_ == null ? null : (units_.getPrimUuid() == null ? null : units_.getPrimUuid().toString()));
		sb.append(":");
		sb.append(pointValue_.toString());
		
	}

}
