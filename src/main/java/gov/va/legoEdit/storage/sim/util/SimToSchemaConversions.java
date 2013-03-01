package gov.va.legoEdit.storage.sim.util;

import gov.va.legoEdit.model.SchemaEquals;
import gov.va.legoEdit.model.schemaModel.Assertion;
import gov.va.legoEdit.model.schemaModel.AssertionComponent;
import gov.va.legoEdit.model.schemaModel.Bound;
import gov.va.legoEdit.model.schemaModel.Concept;
import gov.va.legoEdit.model.schemaModel.Destination;
import gov.va.legoEdit.model.schemaModel.Discernible;
import gov.va.legoEdit.model.schemaModel.Expression;
import gov.va.legoEdit.model.schemaModel.Interval;
import gov.va.legoEdit.model.schemaModel.Lego;
import gov.va.legoEdit.model.schemaModel.LegoList;
import gov.va.legoEdit.model.schemaModel.Measurement;
import gov.va.legoEdit.model.schemaModel.MeasurementConstant;
import gov.va.legoEdit.model.schemaModel.Pncs;
import gov.va.legoEdit.model.schemaModel.Point;
import gov.va.legoEdit.model.schemaModel.PointDouble;
import gov.va.legoEdit.model.schemaModel.PointLong;
import gov.va.legoEdit.model.schemaModel.PointMeasurementConstant;
import gov.va.legoEdit.model.schemaModel.Qualifier;
import gov.va.legoEdit.model.schemaModel.Relation;
import gov.va.legoEdit.model.schemaModel.RelationGroup;
import gov.va.legoEdit.model.schemaModel.Stamp;
import gov.va.legoEdit.model.schemaModel.Type;
import gov.va.legoEdit.model.schemaModel.Units;
import gov.va.legoEdit.model.schemaModel.Value;
import gov.va.legoEdit.storage.wb.WBUtility;
import gov.va.legoEdit.util.TimeConvert;
import gov.va.sim.act.AssertionBI;
import gov.va.sim.act.AssertionRefBI;
import gov.va.sim.act.expression.ExpressionBI;
import gov.va.sim.act.expression.ExpressionRelBI;
import gov.va.sim.act.expression.node.BooleanNodeBI;
import gov.va.sim.act.expression.node.ConceptNodeBI;
import gov.va.sim.act.expression.node.ConjunctionNodeBI;
import gov.va.sim.act.expression.node.ExpressionNodeBI;
import gov.va.sim.act.expression.node.MeasurementNodeBI;
import gov.va.sim.act.expression.node.TextNodeBI;
import gov.va.sim.lego.LegoBI;
import gov.va.sim.lego.LegoListBI;
import gov.va.sim.lego.LegoStampBI;
import gov.va.sim.lego.PncsBI;
import gov.va.sim.measurement.BoundBI;
import gov.va.sim.measurement.IntervalBI;
import gov.va.sim.measurement.MeasurementBI;
import gov.va.sim.measurement.PointBI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.ihtsdo.tk.api.concept.ConceptVersionBI;

/**
 * Converting methods to go from SIM-API to Lego Schema
 * 
 * @author darmbrust
 * 
 */

public class SimToSchemaConversions
{
	public static TreeMap<Long, MeasurementConstant> measurementConstantValues_ = new TreeMap<>();
	static
	{
		for (MeasurementConstant mc : MeasurementConstant.values())
		{
			measurementConstantValues_.put((Long.MIN_VALUE + (long) mc.ordinal()), mc);
		}
	}

	public static Assertion convert(AssertionBI simAssertion) throws IOException
	{
		Assertion assertion = new Assertion();

		assertion.setDiscernible(convertToDiscernible(simAssertion.getDiscernable()));
		assertion.setQualifier(convertToQualifier(simAssertion.getQualifier()));
		assertion.setValue(convertToValue(simAssertion.getValue()));
		assertion.setTiming(convert(simAssertion.getTiming()));
		assertion.setAssertionUUID(simAssertion.getInstanceUuid().toString());
		assertion.getAssertionComponent().addAll(convert(simAssertion.getCompositeAssertionComponents()));
		return assertion;
	}

	public static Point makePoint(Number number)
	{
		if (number == null)
		{
			return null;
		}
		// TODO constants
		if (number.longValue() <= measurementConstantValues_.lastKey() && number.longValue() >= measurementConstantValues_.firstKey())
		{
			PointMeasurementConstant p = new PointMeasurementConstant();
			p.setValue(measurementConstantValues_.get(number.longValue()));
			if (p.getValue() == null)
			{
				throw new IllegalArgumentException("Unknown measurement constant");
			}
			return p;
		}
		else if (number instanceof Double || number instanceof Float)
		{
			PointDouble p = new PointDouble();
			p.setValue(number.doubleValue());
			return p;
		}
		else
		{
			PointLong p = new PointLong();
			p.setValue(number.longValue());
			return p;
		}
	}

	public static Discernible convertToDiscernible(ExpressionBI simExpression) throws IOException
	{
		Discernible d = new Discernible();
		d.setExpression(convert(simExpression));
		return d;
	}

	public static Qualifier convertToQualifier(ExpressionBI simExpression) throws IOException
	{
		Qualifier q = new Qualifier();
		q.setExpression(convert(simExpression));
		return q;
	}

	public static Expression convert(ExpressionBI simExpression) throws IOException
	{
		return convert(simExpression.getFocus());
	}

	public static Expression convert(ExpressionNodeBI<?> simExpressionNode) throws IOException
	{
		Expression e = new Expression();
		if (simExpressionNode instanceof ConceptNodeBI)
		{
			e.setConcept(WBUtility.convertConcept(((ConceptNodeBI) simExpressionNode).getValue()));
		}
		else if (simExpressionNode instanceof ConjunctionNodeBI)
		{
			for (ExpressionNodeBI<?> expressionNode : ((ConjunctionNodeBI) simExpressionNode).getValue())
			{
				e.getExpression().add(convert(expressionNode));
			}
		}
		else
		{
			throw new RuntimeException("Illegal node type for expression");
		}

		HashMap<String, List<ExpressionRelBI>> simRels = new HashMap<>();

		// What an unpleasant API
		for (ExpressionRelBI simRel : simExpressionNode.getAllRels())
		{
			String group = (simRel.getRelGroup() == null ? "null" : ((Object) simRel.getRelGroup()).toString());
			List<ExpressionRelBI> list = simRels.get(group);
			if (list == null)
			{
				list = new ArrayList<ExpressionRelBI>();
				simRels.put(group, list);
			}
			list.add(simRel);
		}

		for (Entry<String, List<ExpressionRelBI>> simRelList : simRels.entrySet())
		{
			List<Relation> listToAddTo = null;
			if (simRelList.getKey().equals("null"))
			{
				// no group
				listToAddTo = e.getRelation();
			}
			else
			{
				RelationGroup relGroup = new RelationGroup();
				e.getRelationGroup().add(relGroup);
				listToAddTo = relGroup.getRelation();
			}

			for (ExpressionRelBI simRel : simRelList.getValue())
			{
				listToAddTo.add(convert(simRel));
			}
		}

		return e;
	}

	public static Relation convert(ExpressionRelBI simRel) throws IOException
	{
		Relation r = new Relation();
		r.setType(convertToType(simRel.getType()));
		r.setDestination(convertToDestination(simRel.getDestination()));
		return r;
	}

	@SuppressWarnings("unchecked")
	public static Destination convertToDestination(ExpressionNodeBI<?> expression) throws IOException
	{
		Destination d = new Destination();
		if (expression instanceof ConceptNodeBI || expression instanceof ConjunctionNodeBI)
		{
			d.setExpression(convert(expression));
		}
		else
		{
			// literal
			try
			{
				if (expression.getAllRels().length > 0)
				{
					throw new IllegalArgumentException("Rels are not allowed on literals");
				}
			}
			catch (IOException e)
			{
				throw new RuntimeException("Thats not expected", e);
			}

			if (expression instanceof BooleanNodeBI)
			{
				d.setBoolean(((BooleanNodeBI) expression).getValue());
			}
			else if (expression instanceof MeasurementNodeBI<?>)
			{
				d.setMeasurement(convert((MeasurementNodeBI<MeasurementBI<?>>) expression));
			}
			else if (expression instanceof TextNodeBI)
			{
				d.setText(((TextNodeBI) expression).getValue());
			}
			else
			{
				throw new IllegalArgumentException("Unknown destination type");
			}
		}
		return d;
	}

	@SuppressWarnings("unchecked")
	public static Value convertToValue(ExpressionBI expression) throws IOException
	{
		ExpressionNodeBI<?> focus = expression.getFocus();
		Value v = new Value();
		if (focus instanceof ConceptNodeBI || focus instanceof ConjunctionNodeBI)
		{
			v.setExpression(convert(focus));
		}
		else
		{
			// literal
			// lego doesn't support rels, etc, on literals. Neither does the UML model.
			if (focus.getAllRels().length > 0)
			{
				throw new IllegalArgumentException("Rels are not allowed on literals");
			}
			if (focus instanceof BooleanNodeBI)
			{
				v.setBoolean(((BooleanNodeBI) focus).getValue());
			}
			else if (focus instanceof MeasurementNodeBI<?>)
			{
				v.setMeasurement(convert((MeasurementNodeBI<MeasurementBI<?>>) focus));
			}
			else if (focus instanceof TextNodeBI)
			{
				v.setText(((TextNodeBI) focus).getValue());
			}
			else
			{
				throw new IllegalArgumentException("Unknown value type: " + focus.getClass().getName() + " : " + focus);
			}
		}
		return v;
	}

	public static Measurement convert(MeasurementNodeBI<MeasurementBI<?>> simMeasurement)
	{
		// lego doesn't support rels, etc, on literals. Neither does the UML model. So we ignore them.
		return convert(simMeasurement.getValue());
	}

	public static Measurement convert(MeasurementBI<?> measurement)
	{
		if (measurement == null)
		{
			return null;
		}
		Measurement m = new Measurement();

		if (measurement instanceof PointBI)
		{
			PointBI simPoint = (PointBI) measurement;
			m.setPoint(makePoint(simPoint.getPointValue()));
			m.setUnits(convertToUnits(simPoint.getUnitsOfMeasure()));
		}
		else if (measurement instanceof BoundBI)
		{
			BoundBI simBound = (BoundBI) measurement;
			Bound b = new Bound();
			b.setLowerPoint(makePoint(simBound.getLowerLimit().getPointValue()));
			b.setUpperPoint(makePoint(simBound.getUpperLimit().getPointValue()));
			Units u1 = convertToUnits(simBound.getLowerLimit().getUnitsOfMeasure());
			Units u2 = convertToUnits(simBound.getUpperLimit().getUnitsOfMeasure());
			if (!SchemaEquals.equals(u1, u2))
			{
				throw new IllegalArgumentException("Different units not supported within a single measurement");
			}
			m.setUnits(u1);
			m.setBound(b);
		}
		else if (measurement instanceof IntervalBI)
		{
			IntervalBI simInterval = (IntervalBI) measurement;
			Interval i = new Interval();

			Bound low = new Bound();
			low.setLowerPoint(makePoint(simInterval.getLowerBound().getLowerLimit().getPointValue()));
			low.setUpperPoint(makePoint(simInterval.getLowerBound().getUpperLimit().getPointValue()));
			Units u1 = convertToUnits(simInterval.getLowerBound().getLowerLimit().getUnitsOfMeasure());
			Units u2 = convertToUnits(simInterval.getLowerBound().getUpperLimit().getUnitsOfMeasure());
			if (!SchemaEquals.equals(u1, u2))
			{
				throw new IllegalArgumentException("Different units not supported within a single measurement");
			}

			Bound high = new Bound();
			high.setLowerPoint(makePoint(simInterval.getUpperBound().getLowerLimit().getPointValue()));
			high.setUpperPoint(makePoint(simInterval.getUpperBound().getUpperLimit().getPointValue()));
			Units u3 = convertToUnits(simInterval.getUpperBound().getLowerLimit().getUnitsOfMeasure());
			Units u4 = convertToUnits(simInterval.getUpperBound().getUpperLimit().getUnitsOfMeasure());
			if (!SchemaEquals.equals(u3, u4))
			{
				throw new IllegalArgumentException("Different units not supported within a single measurement");
			}
			if (!SchemaEquals.equals(u1, u3))
			{
				throw new IllegalArgumentException("Different units not supported within a single measurement");
			}
			m.setInterval(i);
			m.setUnits(u1);
		}
		else
		{
			throw new IllegalArgumentException("invalid measurement");
		}

		return m;
	}

	public static Units convertToUnits(ConceptVersionBI concept)
	{
		Concept c = WBUtility.convertConcept(concept);
		Units units = null;
		if (c != null)
		{
			units = new Units();
			units.setConcept(c);
		}

		return units;
	}

	public static Type convertToType(ConceptVersionBI concept)
	{
		Type t = new Type();
		t.setConcept(WBUtility.convertConcept(concept));
		return t;
	}

	public static Collection<AssertionComponent> convert(Collection<AssertionRefBI> assertionComponents)
	{
		ArrayList<AssertionComponent> result = new ArrayList<>(assertionComponents.size());
		
		for (AssertionRefBI ar : assertionComponents)
		{
			result.add(convert(ar));
		}
		return result;
	}
	
	public static AssertionComponent convert(AssertionRefBI assertionComponent)
	{
		AssertionComponent ac = new AssertionComponent();
		ac.setAssertionUUID(assertionComponent.getAssertionInstanceUuid().toString());
		ac.setType(convertToType(assertionComponent.getType()));
		return ac;
	}
	
	public static Pncs convert(PncsBI pncs)
	{
		Pncs legoPncs = new Pncs();
		legoPncs.setId(pncs.getId());
		legoPncs.setName(pncs.getName());
		legoPncs.setValue(pncs.getValue());
		return legoPncs;
	}
	
	public static Stamp convert(LegoStampBI stamp)
	{
		Stamp legoStamp = new Stamp();
		legoStamp.setAuthor(stamp.getAuthor());
		legoStamp.setModule(stamp.getModule());
		legoStamp.setPath(stamp.getPath());
		legoStamp.setStatus(stamp.getStatus());
		legoStamp.setTime(TimeConvert.convert(stamp.getTime()));
		legoStamp.setUuid(stamp.getInstanceUuid().toString());
		return legoStamp;
	}
	
	public static LegoList convert(LegoListBI legoList) throws IOException
	{
		LegoList ll = new LegoList();
		ll.setComment(legoList.getComment());
		ll.setGroupDescription(legoList.getGroupDescription());
		ll.setGroupName(legoList.getGroupName());
		ll.setLegoListUUID(legoList.getInstanceUuid().toString());
		
		ArrayList<Lego> legos = new ArrayList<>(legoList.getLego().size());
		for (LegoBI lbi : legoList.getLego())
		{
			legos.add(convert(lbi));
		}
		ll.getLego().addAll(legos);
		return ll;
	}
	
	public static Lego convert(LegoBI lego) throws IOException
	{
		Lego l = new Lego();
		l.setComment(lego.getComment());
		l.setLegoUUID(lego.getInstanceUuid().toString());
		l.setPncs(convert(lego.getPncs()));
		l.setStamp(convert(lego.getStamp()));
		
		ArrayList<Assertion> assertions = new ArrayList<>(lego.getAssertions().size());
		for (AssertionBI abi : lego.getAssertions())
		{
			assertions.add(convert(abi));
		}
		l.getAssertion().addAll(assertions);
		return l;
	}
}
