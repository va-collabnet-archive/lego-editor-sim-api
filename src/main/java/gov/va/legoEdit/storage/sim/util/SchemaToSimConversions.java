package gov.va.legoEdit.storage.sim.util;

import gov.va.legoEdit.model.sim.act.Assertion;
import gov.va.legoEdit.model.sim.act.expression.Expression;
import gov.va.legoEdit.model.sim.act.expression.ExpressionRel;
import gov.va.legoEdit.model.sim.act.expression.ExpressionRelGroup;
import gov.va.legoEdit.model.sim.act.expression.node.BooleanNode;
import gov.va.legoEdit.model.sim.act.expression.node.BoundedNode;
import gov.va.legoEdit.model.sim.act.expression.node.ConceptNode;
import gov.va.legoEdit.model.sim.act.expression.node.ConjunctionNode;
import gov.va.legoEdit.model.sim.act.expression.node.ExpressionNode;
import gov.va.legoEdit.model.sim.act.expression.node.IntervalNode;
import gov.va.legoEdit.model.sim.act.expression.node.PointNode;
import gov.va.legoEdit.model.sim.act.expression.node.TextNode;
import gov.va.legoEdit.model.sim.measurement.Bound;
import gov.va.legoEdit.model.sim.measurement.Interval;
import gov.va.legoEdit.model.sim.measurement.Point;
import gov.va.legoEdit.storage.wb.WBUtility;
import gov.va.legoEdit.util.Utility;
import gov.va.sim.act.expression.ExpressionBI;
import gov.va.sim.act.expression.ExpressionRelBI;
import gov.va.sim.act.expression.node.ExpressionNodeBI;
import gov.va.sim.measurement.BoundBI;
import gov.va.sim.measurement.IntervalBI;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.ihtsdo.tk.api.concept.ConceptVersionBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converting methods to go from the Lego Schema to the SIM-API
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
        long[] timing = convertToLong(schemaAssertion.getTiming());
        Assertion simAssertion = new Assertion(discernible, qualifier, value, timing);
        //TODO there is no place to store the assertionUUID.  Think there should be.
        //TODO there is no place to store the assertionComponents.
        return simAssertion;
    }
    
    public static long[] convertToLong(gov.va.legoEdit.model.schemaModel.Measurement schemaMeasurement)
    {
        if (schemaMeasurement == null)
        {
            return new long[] {};
        }
        gov.va.legoEdit.model.schemaModel.Point p = schemaMeasurement.getPoint();
        gov.va.legoEdit.model.schemaModel.Interval i = schemaMeasurement.getInterval();
        gov.va.legoEdit.model.schemaModel.Bound b = schemaMeasurement.getBound();
        //Units aren't supported by sim-api for the timing field
        //TODO - I really think Keith is going to have to fix this.... if not, figure out how to do conversions

        ArrayList<Long> values = new ArrayList<Long>();
        
        if (p != null)
        {
            values.add(pointToLong(p));
        }
        else if (b != null)
        {
            values.add(pointToLong(b.getLowerPoint()));
            values.add(pointToLong(b.getUpperPoint()));
        }
        else if (i != null)
        {
            values.add(pointToLong(i.getLowerBound().getLowerPoint()));
            values.add(pointToLong(i.getLowerBound().getUpperPoint()));
            values.add(pointToLong(i.getUpperBound().getLowerPoint()));
            values.add(pointToLong(i.getUpperBound().getUpperPoint()));
        }
        Iterator<Long> iterator = values.iterator();
        while (iterator.hasNext())
        {
            if (iterator.next() == null)
            {
                iterator.remove();
            }
        }
        long[] result = new long[values.size()];
        for (int y = 0; y < values.size(); y++)
        {
            result[y] = values.get(y);
        }
        return result;
    }
    
    public static Long pointToLong(gov.va.legoEdit.model.schemaModel.Point p)
    {
        if (p == null)
        {
            return null;
        }
        if (p.getNumericValue() != null)
        {
            return p.getNumericValue().longValue();
        }
        else if (p.getStringConstant() != null)
        {
            //TODO constants
            return new Long(Long.MIN_VALUE - p.getStringConstant().ordinal());
        }
        else
        {
            return null;
        }
    }
    
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
            return convert(schemaDestination.getMeasurement());
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
        ConceptVersionBI result = WBUtility.lookupSnomedIdentifierAsCV((Utility.isEmpty(schemaConcept.getUuid()) ? schemaConcept.getSctid() + "" : schemaConcept.getUuid()));
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
            return new Expression(convert(schemaValue.getMeasurement()));
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
    
    public static ExpressionNode<?> convert(gov.va.legoEdit.model.schemaModel.Measurement schemaMeasurement)
    {
        if (schemaMeasurement.getPoint() != null)
        {
            return new PointNode(convert(schemaMeasurement.getPoint()));
        }
        else if (schemaMeasurement.getBound() != null)
        {
            return new BoundedNode(convert(schemaMeasurement.getBound()));
        }
        else if (schemaMeasurement.getInterval() != null)
        {
            return new IntervalNode(convert(schemaMeasurement.getInterval()));
        }
        else
        {
            throw new IllegalArgumentException("Missing measurement");
        }
    }
    
    public static Point convert(gov.va.legoEdit.model.schemaModel.Point schemaPoint)
    {
        if (schemaPoint.getNumericValue() != null)
        {
            return new Point(schemaPoint.getNumericValue());
        }
        else if (schemaPoint.getStringConstant() != null)
        {
            //TODO deal with constants
            return new Point(Float.MIN_VALUE + (float)schemaPoint.getStringConstant().ordinal());
        }
        else
        {
            return null;
        }
    }
    
    public static BoundBI convert(gov.va.legoEdit.model.schemaModel.Bound bound)
    {
        return new Bound(convert(bound.getUpperPoint()), bound.isUpperPointInclusive(), 
                convert(bound.getLowerPoint()), bound.isLowerPointInclusive());
    }
    
    public static IntervalBI convert(gov.va.legoEdit.model.schemaModel.Interval interval)
    {
        return new Interval(convert(interval.getUpperBound()), convert(interval.getLowerBound()));
    }
}
