package gov.va.legoEdit.model.sim.measurement;

import gov.va.sim.measurement.MeasurementBI;

/**
 * Lego Implementation of the SIM-API
 * 
 * @author darmbrust
 */
public abstract class Measurement<T extends MeasurementBI<T>> implements MeasurementBI<T>
{
	protected T value_;

	@Override
	public T getValue()
	{
		return value_;
	}

	public abstract void appendStringForUuidHash(StringBuilder sb);
}
