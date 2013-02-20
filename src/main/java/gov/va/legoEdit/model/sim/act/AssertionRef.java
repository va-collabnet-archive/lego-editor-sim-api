package gov.va.legoEdit.model.sim.act;

import java.util.UUID;
import org.ihtsdo.tk.api.concept.ConceptVersionBI;
import gov.va.sim.act.AssertionRefBI;

/**
 * Lego implementation of the sim-api
 * 
 * @author darmbrust
 */
public class AssertionRef implements AssertionRefBI
{
	private UUID assertionReference_;
	private ConceptVersionBI type_;

	public AssertionRef(UUID assertionReference, ConceptVersionBI type)
	{
		this.assertionReference_ = assertionReference;
		this.type_ = type;
	}

	@Override
	public UUID getAssertionInstanceUuid()
	{
		return assertionReference_;
	}

	@Override
	public ConceptVersionBI getType()
	{
		return type_;
	}

	@Override
	public String toString()
	{
		return "Type: " + type_.toUserString() + " : " + assertionReference_.toString();
	}

}
