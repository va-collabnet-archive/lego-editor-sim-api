package gov.va.legoEdit.storage.sim.util;

import gov.va.legoEdit.model.SchemaEquals;
import gov.va.legoEdit.model.schemaModel.Assertion;
import gov.va.legoEdit.model.schemaModel.Bound;
import gov.va.legoEdit.model.schemaModel.Destination;
import gov.va.legoEdit.model.schemaModel.Discernible;
import gov.va.legoEdit.model.schemaModel.Expression;
import gov.va.legoEdit.model.schemaModel.Interval;
import gov.va.legoEdit.model.schemaModel.Measurement;
import gov.va.legoEdit.model.schemaModel.MeasurementConstant;
import gov.va.legoEdit.model.schemaModel.Point;
import gov.va.legoEdit.model.schemaModel.Qualifier;
import gov.va.legoEdit.model.schemaModel.Relation;
import gov.va.legoEdit.model.schemaModel.RelationGroup;
import gov.va.legoEdit.model.schemaModel.Type;
import gov.va.legoEdit.model.schemaModel.Units;
import gov.va.legoEdit.model.schemaModel.Value;
import gov.va.legoEdit.storage.wb.WBUtility;
import gov.va.sim.act.AssertionBI;
import gov.va.sim.act.expression.ExpressionBI;
import gov.va.sim.act.expression.ExpressionRelBI;
import gov.va.sim.act.expression.node.BooleanNodeBI;
import gov.va.sim.act.expression.node.ConceptNodeBI;
import gov.va.sim.act.expression.node.ConjunctionNodeBI;
import gov.va.sim.act.expression.node.ExpressionNodeBI;
import gov.va.sim.act.expression.node.MeasurementNodeBI;
import gov.va.sim.act.expression.node.TextNodeBI;
import gov.va.sim.measurement.BoundBI;
import gov.va.sim.measurement.IntervalBI;
import gov.va.sim.measurement.MeasurementBI;
import gov.va.sim.measurement.PointBI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.ihtsdo.tk.api.concept.ConceptVersionBI;

/**
 * Converting methods to go from SIM-API to Lego Schema
 * @author darmbrust
 *
 */

public class SimToSchemaConversions
{
    public static TreeMap<Float, MeasurementConstant> measurementConstantValues_= new TreeMap<>();
    static
    {
        //TODO this is a hack that needs addressing
        for (MeasurementConstant mc : MeasurementConstant.values())
        {
            measurementConstantValues_.put((Float.MIN_VALUE + (long)mc.ordinal()), mc);
        }
    }
    
    public static Assertion convert(AssertionBI simAssertion) throws IOException
    {
        Assertion assertion = new Assertion();
        
        assertion.setDiscernible(convertToDiscernible(simAssertion.getDiscernable()));
        assertion.setQualifier(convertToQualifier(simAssertion.getQualifier()));
        assertion.setValue(convertToValue(simAssertion.getValue()));
        assertion.setTiming(convert(simAssertion.getTiming()));
        return assertion;
    }
    
    public static Measurement convert(long[] values)
    {
        if (values.length == 0)
        {
            return null;
        }
        Measurement m = new Measurement();
        //sim-api currently doesn't support units
        if (values.length == 1)
        {
            m.setPoint(makePoint(values[0]));
        }
        else if (values.length == 2)
        {
            Bound b = new Bound();
            b.setLowerPoint(makePoint(values[0]));
            b.setUpperPoint(makePoint(values[1]));
            m.setBound(b);
        }
        else if (values.length == 4)
        {
            Interval i = new Interval();
            Bound low = new Bound();
            low.setLowerPoint(makePoint(values[0]));
            low.setUpperPoint(makePoint(values[1]));
            i.setLowerBound(low);
            Bound high = new Bound();
            high.setLowerPoint(makePoint(values[2]));
            high.setUpperPoint(makePoint(values[3]));
            i.setUpperBound(high);
            m.setInterval(i);
        }
        else
        {
            throw new IllegalArgumentException("Illegal timing measurement");
        }
        
        return m;
    }
    
    public static Point makePoint(Number number)
    {
        Point p = new Point();
        if (number.floatValue() <= measurementConstantValues_.lastKey() && number.floatValue() >= measurementConstantValues_.firstKey())
        {
            p.setStringConstant(measurementConstantValues_.get(number.floatValue()));
            if (p.getStringConstant() == null)
            {
                throw new IllegalArgumentException("Unknown measurement constant");
            }
        }
        else
        {
            p.setNumericValue(number.floatValue());
        }
        return p;
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
            e.setConcept(WBUtility.convertConcept(((ConceptNodeBI)simExpressionNode).getValue()));
        }
        else if (simExpressionNode instanceof ConjunctionNodeBI)
        {
            for (ExpressionNodeBI<?> expressionNode : ((ConjunctionNodeBI)simExpressionNode).getValue())
            {
                e.getExpression().add(convert(expressionNode));
            }
        }
        else
        {
            throw new RuntimeException("Illegal node type for expression");
        }

        HashMap<String, List<ExpressionRelBI>> simRels = new HashMap<>();
        
        //What an unpleasant API
        for (ExpressionRelBI simRel : simExpressionNode.getAllRels())
        {
            String group = (simRel.getRelGroup() == null ? "null" : ((Object)simRel.getRelGroup()).toString());
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
                //no group
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
            //literal
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
                d.setBoolean(((BooleanNodeBI)expression).getValue());
            }
            else if (expression instanceof MeasurementNodeBI<?>)
            {
                d.setMeasurement(convert((MeasurementNodeBI<MeasurementBI<?>>)expression));
            }
            else if (expression instanceof TextNodeBI)
            {
                d.setText(((TextNodeBI)expression).getValue());
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
            //literal

            if (focus.getAllRels().length > 0)
            {
                throw new IllegalArgumentException("Rels are not allowed on literals");
            }
            if (focus instanceof BooleanNodeBI)
            {
                //lego doesn't support rels, etc, on literals.  Neither does the UML model.
                v.setBoolean(((BooleanNodeBI)focus).getValue());
            }
            else if (focus instanceof MeasurementNodeBI<?>)
            {
                v.setMeasurement(convert((MeasurementNodeBI<MeasurementBI<?>>)focus));
            }
            else if (focus instanceof TextNodeBI)
            {
                //lego doesn't support rels, etc, on literals.  Neither does the UML model.
                v.setText(((TextNodeBI)focus).getValue());
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
        Measurement m = new Measurement();
        MeasurementBI<?> mbi = simMeasurement.getValue();
        //lego doesn't support rels, etc, on literals.  Neither does the UML model.
        if (mbi instanceof PointBI)
        {
            PointBI simPoint = (PointBI)mbi;
            m.setPoint(makePoint(simPoint.getPointValue()));
            m.setUnits(convertToUnits(simPoint.getUnitsOfMeasure()));
        }
        else if (mbi instanceof BoundBI)
        {
            BoundBI simBound = (BoundBI)mbi;
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
        else if (mbi instanceof IntervalBI)
        {
            IntervalBI simInterval = (IntervalBI)mbi;
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
        Units u = new Units();
        u.setConcept(WBUtility.convertConcept(concept));
        return u;
    }
    
    public static Type convertToType(ConceptVersionBI concept)
    {
        Type t = new Type();
        t.setConcept(WBUtility.convertConcept(concept));
        return t;
    }
}
