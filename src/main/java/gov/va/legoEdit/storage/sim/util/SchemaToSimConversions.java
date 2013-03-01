package gov.va.legoEdit.storage.sim.util;

import gov.va.legoEdit.model.schemaModel.PointDouble;
import gov.va.legoEdit.model.schemaModel.PointLong;
import gov.va.legoEdit.model.schemaModel.PointMeasurementConstant;
import gov.va.legoEdit.model.sim.act.Assertion;
import gov.va.legoEdit.model.sim.act.AssertionRef;
import gov.va.legoEdit.model.sim.act.expression.Expression;
import gov.va.legoEdit.model.sim.act.expression.ExpressionRel;
import gov.va.legoEdit.model.sim.act.expression.ExpressionRelGroup;
import gov.va.legoEdit.model.sim.act.expression.node.BooleanNode;
import gov.va.legoEdit.model.sim.act.expression.node.BoundedNode;
import gov.va.legoEdit.model.sim.act.expression.node.ConceptNode;
import gov.va.legoEdit.model.sim.act.expression.node.ConjunctionNode;
import gov.va.legoEdit.model.sim.act.expression.node.IntervalNode;
import gov.va.legoEdit.model.sim.act.expression.node.PointNode;
import gov.va.legoEdit.model.sim.act.expression.node.TextNode;
import gov.va.legoEdit.model.sim.lego.Lego;
import gov.va.legoEdit.model.sim.lego.LegoList;
import gov.va.legoEdit.model.sim.lego.LegoStamp;
import gov.va.legoEdit.model.sim.lego.Pncs;
import gov.va.legoEdit.model.sim.measurement.Bound;
import gov.va.legoEdit.model.sim.measurement.Interval;
import gov.va.legoEdit.model.sim.measurement.Point;
import gov.va.legoEdit.storage.DataStoreException;
import gov.va.legoEdit.storage.wb.WBUtility;
import gov.va.legoEdit.util.TimeConvert;
import gov.va.legoEdit.util.Utility;
import gov.va.sim.act.AssertionBI;
import gov.va.sim.act.AssertionRefBI;
import gov.va.sim.act.expression.ExpressionBI;
import gov.va.sim.act.expression.ExpressionRelBI;
import gov.va.sim.act.expression.node.ExpressionNodeBI;
import gov.va.sim.act.expression.node.MeasurementNodeBI;
import gov.va.sim.lego.LegoBI;
import gov.va.sim.lego.LegoListBI;
import gov.va.sim.lego.LegoStampBI;
import gov.va.sim.lego.PncsBI;
import gov.va.sim.measurement.BoundBI;
import gov.va.sim.measurement.IntervalBI;
import gov.va.sim.measurement.MeasurementBI;
import gov.va.sim.measurement.PointBI;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import org.ihtsdo.tk.api.concept.ConceptVersionBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converting methods to go from the Lego Schema to the SIM-API
 * 
 * @author darmbrust
 */
public class SchemaToSimConversions
{
	private static Logger logger = LoggerFactory.getLogger(SchemaToSimConversions.class);

	public static Assertion convert(gov.va.legoEdit.model.schemaModel.Assertion schemaAssertion) throws PropertyVetoException
	{
		ExpressionBI discernible = convert(schemaAssertion.getDiscernible().getExpression());
		ExpressionBI qualifier = convert(schemaAssertion.getQualifier().getExpression());
		ExpressionBI value = convertToExpression(schemaAssertion.getValue());
		MeasurementBI<?> timing = convert(schemaAssertion.getTiming());
		Collection<AssertionRefBI> assertionRefList = convert(schemaAssertion.getAssertionComponent());
		Assertion simAssertion = new Assertion(UUID.fromString(schemaAssertion.getAssertionUUID()), discernible, qualifier, value, timing, assertionRefList);
		return simAssertion;
	}

	public static ExpressionBI convert(gov.va.legoEdit.model.schemaModel.Expression schemaExpression) throws PropertyVetoException
	{
		return new Expression(convertToNode(schemaExpression));
	}

	public static ExpressionNodeBI<?> convertToNode(gov.va.legoEdit.model.schemaModel.Expression schemaExpression) throws PropertyVetoException
	{
		ExpressionNodeBI<?> node;
		if (schemaExpression.getConcept() != null)
		{
			node = new ConceptNode(convert(schemaExpression.getConcept()));
		}
		else if (schemaExpression.getExpression().size() > 0)
		{
			ArrayList<ExpressionNodeBI<?>> nodes = new ArrayList<ExpressionNodeBI<?>>(schemaExpression.getExpression().size());
			for (gov.va.legoEdit.model.schemaModel.Expression nestedSchemaExpression : schemaExpression.getExpression())
			{
				nodes.add(convertToNode(nestedSchemaExpression));
			}
			node = new ConjunctionNode(nodes);
		}
		else
		{
			throw new IllegalArgumentException("missing concept");
		}

		for (gov.va.legoEdit.model.schemaModel.Relation schemaRel : schemaExpression.getRelation())
		{
			node.addRel(convert(schemaRel.getType().getConcept()), convertToNode(schemaRel.getDestination()));
		}

		for (gov.va.legoEdit.model.schemaModel.RelationGroup schemaRelGroup : schemaExpression.getRelationGroup())
		{
			ArrayList<ExpressionRelBI> relGroupMembers = new ArrayList<>();
			for (gov.va.legoEdit.model.schemaModel.Relation schemaRel : schemaRelGroup.getRelation())
			{
				ExpressionRelBI createdRel = node.addRel(convert(schemaRel.getType().getConcept()), convertToNode(schemaRel.getDestination()));
				relGroupMembers.add(createdRel);
			}
			ExpressionRelGroup relGroup = new ExpressionRelGroup(relGroupMembers.toArray(new ExpressionRel[relGroupMembers.size()]));
			for (ExpressionRelBI er : relGroup.getRelsInGroup())
			{
				er.setRelGroup(relGroup);
			}
		}

		return node;
	}

	public static ExpressionNodeBI<?> convertToNode(gov.va.legoEdit.model.schemaModel.Destination schemaDestination) throws PropertyVetoException
	{
		if (schemaDestination.getExpression() != null)
		{
			return convertToNode(schemaDestination.getExpression());
		}
		else if (schemaDestination.getMeasurement() != null)
		{
			return convertToNode(schemaDestination.getMeasurement());
		}
		else if (schemaDestination.getText() != null)
		{
			return new TextNode(schemaDestination.getText());
		}
		else if (schemaDestination.isBoolean() != null)
		{
			return new BooleanNode(schemaDestination.isBoolean());
		}
		else
		{
			throw new IllegalArgumentException("Invalid Destination");
		}
	}

	public static ConceptVersionBI convert(gov.va.legoEdit.model.schemaModel.Concept schemaConcept)
	{
		try
		{
			ConceptVersionBI result = WBUtility.lookupSnomedIdentifierAsCV((Utility.isEmpty(schemaConcept.getUuid()) ? schemaConcept.getSctid() + "" : schemaConcept
					.getUuid()));
			if (result == null)
			{
				logger.error("Lookup failed for concept " + schemaConcept.getDesc() + " : " + schemaConcept.getSctid() + " : " + schemaConcept.getUuid());
				result = new ConceptVersion(schemaConcept);
			}
			return result;
		}
		//This is a hack for junit testing - where we run without a snomed DB.
		catch (DataStoreException e)
		{
			return new ConceptVersion(schemaConcept);
		}
	}

	public static ExpressionBI convertToExpression(gov.va.legoEdit.model.schemaModel.Value schemaValue) throws PropertyVetoException
	{
		if (schemaValue.getExpression() != null)
		{
			return convert(schemaValue.getExpression());
		}
		else if (schemaValue.getMeasurement() != null)
		{
			return new Expression(convertToNode(schemaValue.getMeasurement()));
		}
		else if (schemaValue.getText() != null)
		{
			return new Expression(new TextNode(schemaValue.getText()));
		}
		else if (schemaValue.isBoolean() != null)
		{
			return new Expression(new BooleanNode(schemaValue.isBoolean()));
		}
		else
		{
			throw new IllegalArgumentException("Missing value");
		}
	}

	public static MeasurementBI<?> convert(gov.va.legoEdit.model.schemaModel.Measurement schemaMeasurement)
	{
		if (schemaMeasurement == null)
		{
			return null;
		}
		if (schemaMeasurement.getPoint() != null)
		{
			return convert(schemaMeasurement.getPoint(), (schemaMeasurement.getUnits() == null ? null : schemaMeasurement.getUnits().getConcept()));
		}
		else if (schemaMeasurement.getBound() != null)
		{
			return convert(schemaMeasurement.getBound(), (schemaMeasurement.getUnits() == null ? null : schemaMeasurement.getUnits().getConcept()));
		}
		else if (schemaMeasurement.getInterval() != null)
		{
			return convert(schemaMeasurement.getInterval(), (schemaMeasurement.getUnits() == null ? null : schemaMeasurement.getUnits().getConcept()));
		}
		else
		{
			throw new IllegalArgumentException("Missing measurement");
		}
	}

	public static MeasurementNodeBI<?> convertToNode(gov.va.legoEdit.model.schemaModel.Measurement schemaMeasurement)
	{
		MeasurementBI<?> m = convert(schemaMeasurement);
		if (m != null)
		{
			if (m instanceof PointBI)
			{
				return new PointNode((PointBI) m);
			}
			else if (m instanceof BoundBI)
			{
				return new BoundedNode((BoundBI) m);
			}
			else if (m instanceof IntervalBI)
			{
				return new IntervalNode((IntervalBI) m);
			}
			else
			{
				throw new IllegalArgumentException("Unexpected");
			}
		}
		else
		{
			return null;
		}
	}

	public static Point convert(gov.va.legoEdit.model.schemaModel.Point schemaPoint, gov.va.legoEdit.model.schemaModel.Concept schemaUnits)
	{
		if (schemaPoint == null)
		{
			return null;
		}
		ConceptVersionBI cv = null;
		if (schemaUnits != null)
		{
			cv = convert(schemaUnits);
		}
		if (schemaPoint instanceof PointLong)
		{
			return new Point(((PointLong) schemaPoint).getValue(), cv);
		}
		else if (schemaPoint instanceof PointDouble)
		{
			return new Point(((PointDouble) schemaPoint).getValue(), cv);
		}
		else if (schemaPoint instanceof PointMeasurementConstant)
		{
			// TODO constants
			return new Point(Long.MIN_VALUE + (long) ((PointMeasurementConstant) schemaPoint).getValue().ordinal(), cv);
		}
		else
		{
			throw new IllegalArgumentException("Invalid measurement");
		}
	}

	public static BoundBI convert(gov.va.legoEdit.model.schemaModel.Bound bound, gov.va.legoEdit.model.schemaModel.Concept schemaUnits)
	{
		return new Bound(convert(bound.getUpperPoint(), schemaUnits), bound.isUpperPointInclusive(), convert(bound.getLowerPoint(), schemaUnits),
				bound.isLowerPointInclusive());
	}

	public static IntervalBI convert(gov.va.legoEdit.model.schemaModel.Interval interval, gov.va.legoEdit.model.schemaModel.Concept schemaUnits)
	{
		return new Interval(convert(interval.getUpperBound(), schemaUnits), convert(interval.getLowerBound(), schemaUnits));
	}

	public static Collection<AssertionRefBI> convert(Collection<gov.va.legoEdit.model.schemaModel.AssertionComponent> schemaCompositeAssertions)
	{
		ArrayList<AssertionRefBI> result = new ArrayList<>(schemaCompositeAssertions.size());
		for (gov.va.legoEdit.model.schemaModel.AssertionComponent ac : schemaCompositeAssertions)
		{
			result.add(convert(ac));
		}
		return result;
	}

	public static AssertionRefBI convert(gov.va.legoEdit.model.schemaModel.AssertionComponent schemaCompositeAssertion)
	{
		return new AssertionRef(UUID.fromString(schemaCompositeAssertion.getAssertionUUID()), convert(schemaCompositeAssertion.getType().getConcept()));
	}

	public static PncsBI convert(gov.va.legoEdit.model.schemaModel.Pncs pncs)
	{
		return new Pncs(pncs.getId(), pncs.getName(), pncs.getValue());
	}

	public static LegoStampBI convert(gov.va.legoEdit.model.schemaModel.Stamp stamp)
	{
		return new LegoStamp(UUID.fromString(stamp.getUuid()), stamp.getStatus(), TimeConvert.convert(stamp.getTime()), stamp.getAuthor(), stamp.getModule(),
				stamp.getPath());
	}

	public static LegoListBI convert(gov.va.legoEdit.model.schemaModel.LegoList legoList) throws PropertyVetoException
	{
		ArrayList<LegoBI> legos = new ArrayList<>(legoList.getLego().size());
		for (gov.va.legoEdit.model.schemaModel.Lego l : legoList.getLego())
		{
			legos.add(convert(l));
		}
		return new LegoList(legoList.getGroupName(), UUID.fromString(legoList.getLegoListUUID()), legoList.getGroupDescription(), legoList.getComment(), legos);
	}

	public static LegoBI convert(gov.va.legoEdit.model.schemaModel.Lego lego) throws PropertyVetoException
	{
		ArrayList<AssertionBI> assertions = new ArrayList<>(lego.getAssertion().size());
		for (gov.va.legoEdit.model.schemaModel.Assertion a : lego.getAssertion())
		{
			assertions.add(convert(a));
		}
		return new Lego(UUID.fromString(lego.getLegoUUID()), convert(lego.getPncs()), convert(lego.getStamp()), lego.getComment(), assertions);
	}
}
