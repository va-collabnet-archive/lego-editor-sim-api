package gov.va.legoEdit.model.sim.measurement;

import org.ihtsdo.tk.api.concept.ConceptVersionBI;
import gov.va.sim.measurement.PlaceholderBI;

/**
 * Placeholder
 * 
 * @author Dan Armbrust
 *         Copyright 2013
 */
public class Placeholder extends Point implements PlaceholderBI
{
	public Placeholder(PlaceholderBI.PlaceholderConstant value, ConceptVersionBI units)
	{
		super(value.getId(), units);
	}

	/**
	 * @see gov.va.sim.measurement.PlaceholderBI#getPlaceHolder()
	 */
	@Override
	public PlaceholderConstant getPlaceholder()
	{
		return PlaceholderConstant.valueOf(getPointValue().longValue());
	}

	/**
	 * @see gov.va.sim.measurement.PlaceholderBI#setPlaceholder(gov.va.sim.measurement.PlaceholderBI.PlaceholderConstant)
	 */
	@Override
	public void setPlaceholder(PlaceholderConstant placeholder)
	{
		super.setPointValue(placeholder.getId());
	}
}
