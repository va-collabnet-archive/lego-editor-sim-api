package gov.va.legoEdit.model.sim.lego;

import gov.va.sim.lego.PncsBI;

/**
 * Pncs - LegoEditor Implementation of the SIM-API
 * 
 * @author Dan Armbrust
 */
public class Pncs implements PncsBI
{
	private int id_;
	private String name_, value_;

	public Pncs(int id, String name, String value)
	{
		id_ = id;
		name_ = name;
		value_ = value;
	}

	@Override
	public int getId()
	{
		return id_;
	}

	@Override
	public String getName()
	{
		return name_;
	}

	@Override
	public String getValue()
	{
		return value_;
	}
	
	@Override
	public String toString()
	{
		return "Pncs: " + id_ + " : " + name_ + " : " + value_;
	}
}
