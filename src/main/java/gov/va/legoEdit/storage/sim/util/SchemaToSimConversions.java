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
import gov.va.legoEdit.model.sim.measurement.Bound;
import gov.va.legoEdit.model.sim.measurement.Interval;
import gov.va.legoEdit.model.sim.measurement.Point;
import gov.va.legoEdit.storage.wb.WBUtility;
import gov.va.legoEdit.util.Utility;
import gov.va.sim.act.AssertionRefBI;
import gov.va.sim.act.expression.ExpressionBI;
import gov.va.sim.act.expression.ExpressionRelBI;
import gov.va.sim.act.expression.node.ExpressionNodeBI;
import gov.va.sim.act.expression.node.MeasurementNodeBI;
import gov.va.sim.measurement.BoundBI;
import gov.va.sim.measurement.IntervalBI;
import gov.va.sim.measurement.MeasurementBI;
import gov.va.sim.measurement.PointBI;
import java.beans.PropertyVetoException;
import java.io.IOException;
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

	public static Assertion convert(gov.va.legoEdit.model.schemaModel.Assertion schemaAssertion) throws PropertyVetoException, IOException
	{
		ExpressionBI discernible = convert(schemaAssertion.getDiscernible().getExpression());
		ExpressionBI qualifier = convert(schemaAssertion.getQualifier().getExpression());
		ExpressionBI value = convertToExpression(schemaAssertion.getValue());
		// long[] timing = convertToLong(schemaAssertion.getTiming());
		MeasurementBI<?> timing = convert(schemaAssertion.getTiming());
		Collection<AssertionRefBI> assertionRefList = convert(schemaAssertion.getAssertionComponent());
		Assertion simAssertion = new Assertion(UUID.fromString(schemaAssertion.getAssertionUUID()), discernible, qualifier, value, timing, assertionRefList);
		return simAssertion;
	}

	// public static long[] convertToLong(gov.va.legoEdit.model.schemaModel.Measurement schemaMeasurement)
	// {
	// if (schemaMeasurement == null)
	// {
	// return new long[] {};
	// }
	// gov.va.legoEdit.model.schemaModel.Point p = schemaMeasurement.getPoint();
	// gov.va.legoEdit.model.schemaModel.Interval i = schemaMeasurement.getInterval();
	// gov.va.legoEdit.model.schemaModel.Bound b = schemaMeasurement.getBound();
	// //Units aren't supported by sim-api for the timing field
	// //TODO - I really think Keith is going to have to fix this.... if not, figure out how to do conversions
	//
	// ArrayList<Long> values = new ArrayList<Long>();
	//
	// if (p != null)
	// {
	// values.add(pointToLong(p));
	// }
	// else if (b != null)
	// {
	// values.add(pointToLong(b.getLowerPoint()));
	// values.add(pointToLong(b.getUpperPoint()));
	// }
	// else if (i != null)
	// {
	// values.add(pointToLong(i.getLowerBound().getLowerPoint()));
	// values.add(pointToLong(i.getLowerBound().getUpperPoint()));
	// values.add(pointToLong(i.getUpperBound().getLowerPoint()));
	// values.add(pointToLong(i.getUpperBound().getUpperPoint()));
	// }
	// Iterator<Long> iterator = values.iterator();
	// while (iterator.hasNext())
	// {
	// if (iterator.next() == null)
	// {
	// iterator.remove();
	// }
	// }
	// long[] result = new long[values.size()];
	// for (int y = 0; y < values.size(); y++)
	// {
	// result[y] = values.get(y);
	// }
	// return result;
	// }
	//
	// public static Long pointToLong(gov.va.legoEdit.model.schemaModel.Point p)
	// {
	// if (p == null)
	// {
	// return null;
	// }
	// if (p.getNumericValue() != null)
	// {
	// return p.getNumericValue().longValue();
	// }
	// else if (p.getStringConstant() != null)
	// {
	// //TODO constants
	// return new Long(Long.MIN_VALUE - (long)p.getStringConstant().ordinal());
	// }
	// else
	// {
	// return null;
	// }
	// }

	public static ExpressionBI convert(gov.va.legoEdit.model.schemaModel.Expression schemaExpression) throws PropertyVetoException, IOException
	{
		return new Expression(convertToNode(schemaExpression));
	}

	public static ExpressionNodeBI<?> convertToNode(gov.va.legoEdit.model.schemaModel.Expression schemaExpression) throws PropertyVetoException, IOException
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

	public static ExpressionNodeBI<?> convertToNode(gov.va.legoEdit.model.schemaModel.Destination schemaDestination) throws PropertyVetoException, IOException
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

	public static ConceptVersionBI convert(gov.va.legoEdit.model.schemaModel.Concept schemaConcept) throws IOException
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

	public static ExpressionBI convertToExpression(gov.va.legoEdit.model.schemaModel.Value schemaValue) throws PropertyVetoException, IOException
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

	public static MeasurementBI<?> convert(gov.va.legoEdit.model.schemaModel.Measurement schemaMeasurement) throws IOException
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

	public static MeasurementNodeBI<?> convertToNode(gov.va.legoEdit.model.schemaModel.Measurement schemaMeasurement) throws IOException
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

	public static Point convert(gov.va.legoEdit.model.schemaModel.Point schemaPoint, gov.va.legoEdit.model.schemaModel.Concept schemaUnits) throws IOException
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
			return new Point(Long.MIN_VALUE + (long) ((PointMeasurementConstant) schemaPoint).getValue().ordinal(), cv);
		}
		else
		{
			throw new IllegalArgumentException("Invalid measurement");
		}
	}

	public static BoundBI convert(gov.va.legoEdit.model.schemaModel.Bound bound, gov.va.legoEdit.model.schemaModel.Concept schemaUnits) throws IOException
	{
		return new Bound(convert(bound.getUpperPoint(), schemaUnits), bound.isUpperPointInclusive(), convert(bound.getLowerPoint(), schemaUnits),
				bound.isLowerPointInclusive());
	}

	public static IntervalBI convert(gov.va.legoEdit.model.schemaModel.Interval interval, gov.va.legoEdit.model.schemaModel.Concept schemaUnits) throws IOException
	{
		return new Interval(convert(interval.getUpperBound(), schemaUnits), convert(interval.getLowerBound(), schemaUnits));
	}

	public static Collection<AssertionRefBI> convert(Collection<gov.va.legoEdit.model.schemaModel.AssertionComponent> schemaCompositeAssertions) throws IOException
	{
		ArrayList<AssertionRefBI> result = new ArrayList<>(schemaCompositeAssertions.size());
		for (gov.va.legoEdit.model.schemaModel.AssertionComponent ac : schemaCompositeAssertions)
		{
			result.add(convert(ac));
		}
		return result;
	}
	
	public static AssertionRefBI convert(gov.va.legoEdit.model.schemaModel.AssertionComponent schemaCompositeAssertion) throws IOException
	{
		return new AssertionRef(UUID.fromString(schemaCompositeAssertion.getAssertionUUID()), convert(schemaCompositeAssertion.getType().getConcept()));
	}
}
