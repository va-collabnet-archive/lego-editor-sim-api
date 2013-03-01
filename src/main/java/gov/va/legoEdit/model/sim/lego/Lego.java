package gov.va.legoEdit.model.sim.lego;

import gov.va.sim.act.AssertionBI;
import gov.va.sim.lego.LegoBI;
import gov.va.sim.lego.LegoStampBI;
import gov.va.sim.lego.PncsBI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Lego - LegoEditor Implementation of the SIM-API
 * 
 * @author Dan Armbrust
 */
public class Lego implements LegoBI
{
	private String comment_;
	private List<AssertionBI> assertions_;
	private UUID instance_;
	private PncsBI pncs_;
	private LegoStampBI stamp_;

	public Lego(UUID instanceUuid, PncsBI pncs, LegoStampBI stamp, String comment, Collection<AssertionBI> assertions)
	{
		comment_ = comment;
		assertions_ = new ArrayList<>();
		assertions_.addAll(assertions);
		instance_ = instanceUuid;
		pncs_ = pncs;
		stamp_ = stamp;
	}

	@Override
	public UUID getInstanceUuid()
	{
		return instance_;
	}

	@Override
	public LegoStampBI getStamp()
	{
		return stamp_;
	}

	@Override
	public PncsBI getPncs()
	{
		return pncs_;
	}

	@Override
	public Collection<AssertionBI> getAssertions()
	{
		return assertions_;
	}

	@Override
	public String getComment()
	{
		return comment_;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Lego: " + comment_ + " : " + instance_.toString() + " : " + pncs_.toString() + stamp_.toString() + " Assertions:(");
		for (AssertionBI a : assertions_)
		{
			sb.append(a.toString());
			sb.append(System.getProperty("line.separator"));
		}

		sb.append(")");
		return sb.toString();
	}
}
