package richTea.runtime.attribute.variable;

import richTea.runtime.attribute.Attribute;
import richTea.runtime.attribute.AttributeSet;
import richTea.runtime.attribute.modifier.AttributeModifier;
import richTea.runtime.execution.ExecutionContext;
import richTea.runtime.execution.VariableScope;

public class PropertyLookup extends LookupChainElement {

	private Attribute property;
	
	public PropertyLookup(Attribute property, Attribute lookupChain) {
		this(property, lookupChain, true);
	}
	
	public PropertyLookup(Attribute property, Attribute lookupChain, boolean resolveAttributeValue) {
		super(lookupChain, resolveAttributeValue);
		
		this.property = property;
	}
	
	public String getPropertyName(ExecutionContext context) {
		return property.getValue(context).toString();
	}
	
	public Object getPropertyHolder(ExecutionContext context) {
		return getLookupChain().getValue(context);
	}

	@Override
	protected Object performLookup(ExecutionContext context, Object lookupContext) {
		return resolveAttribute(context, lookupContext);
	}
	
	@Override
	public Object modify(ExecutionContext context, AttributeModifier modifier) {
		Attribute attribute = resolveAttribute(context, getLookupChain().getValue(context));
		attribute = resolveAttributeReference(attribute, context);
		
		return attribute.modify(context, modifier);
	}
	
	private Attribute resolveAttribute(ExecutionContext context, Object lookupContext) {
		Attribute attribute = null;
		String attributeName = getPropertyName(context);
		
		if(lookupContext instanceof AttributeSet) {
			AttributeSet attributeSet = (AttributeSet) lookupContext;
			
			if(attributeSet instanceof VariableScope) {
				attribute = ((VariableScope) attributeSet).resolveAttribute(attributeName);
			} else {
				attribute = attributeSet.getAttribute(attributeName);
			}
		}
		
		if(attribute == null) {
			attribute = new BeanLookup(attributeName, lookupContext);
		}
		
		return attribute;
	}
}