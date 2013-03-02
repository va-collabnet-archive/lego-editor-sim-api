package gov.va.legoEdit.model.sim.act;

import gov.va.sim.act.ActBI;
import gov.va.sim.act.expression.ExpressionBI;
import java.util.Collection;
import java.util.UUID;

/**
 * Lego Implementation of the SIM-API
 * 
 * @author darmbrust
 */
public class Act implements ActBI
{
	protected UUID instanceUUID_;

	@Override
	public boolean equivalent(ActBI another)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean subsumedBy(ActBI another)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean subsumes(ActBI another)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public ExpressionBI getActSubject()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<ExpressionBI> getSections()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setActSubject(ExpressionBI subject)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public UUID getInstanceUuid()
	{
		return instanceUUID_;
	}

	@Override
	public Collection<UUID> getChildrenUuids()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<UUID> getParentUuids()
	{
		throw new UnsupportedOperationException();
	}
}
