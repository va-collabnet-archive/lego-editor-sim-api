package gov.va.legoEdit.model.sim.lego;

import gov.va.sim.lego.LegoBI;
import gov.va.sim.lego.LegoListBI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * LegoList - LegoEditor Implementation of the SIM-API
 * 
 * @author Dan Armbrust
 */
public class LegoList implements LegoListBI
{
	private String comment_, groupDescription_, groupName_;
	private UUID instanceUuid_;
	private List<LegoBI> legos_;

	public LegoList(String groupName, UUID instanceUUID, String groupDescription, String comment, Collection<LegoBI> legos)
	{
		comment_ = comment;
		groupDescription_ = groupDescription;
		groupName_ = groupName;
		instanceUuid_ = instanceUUID;
		legos_ = new ArrayList<>();
		legos_.addAll(legos);
	}

	@Override
	public UUID getInstanceUuid()
	{
		return instanceUuid_;
	}

	@Override
	public String getGroupName()
	{
		return groupName_;
	}

	@Override
	public String getGroupDescription()
	{
		return groupDescription_;
	}

	@Override
	public Collection<LegoBI> getLego()
	{
		return legos_;
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
		sb.append("LegoList: " + groupName_ + " : " + groupDescription_ + " : " + comment_ + " : " + instanceUuid_.toString() + " Legos:(");
		for (LegoBI l : legos_)
		{
			sb.append(l.toString());
			sb.append(System.getProperty("line.separator"));
		}

		sb.append(")");
		return sb.toString();
	}
}
