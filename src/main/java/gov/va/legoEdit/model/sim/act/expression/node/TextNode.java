package gov.va.legoEdit.model.sim.act.expression.node;

import gov.va.sim.act.expression.node.TextNodeBI;

/**
 * Lego Implementation of the SIM-API
 * @author darmbrust
 */
public class TextNode extends ExpressionNode<String> implements TextNodeBI
{
    public TextNode(String value)
    {
        value_ = value;
    }
}
