package richTea.core.attribute;

import java.util.ArrayList;
import java.util.List;

import richTea.core.attribute.modifier.AttributeModifier;
import richTea.core.node.TreeNode;

public class ArrayAttribute extends AbstractAttribute {
	
	private List<Attribute> attributes;
	
	public ArrayAttribute(String name, List<Attribute> attributes) {
		super(name);
		
		this.attributes = attributes;
	}
	
	protected List<Attribute> getAttributes() {
		return attributes;
	}
	
	@Override
	public void setOwner(TreeNode owner) {
		super.setOwner(owner);
		
		for(Attribute attribute : getAttributes()) {
			attribute.setOwner(owner);
		}
	}
	
	@Override
	public List<Object> getValue() {	
		List<Attribute> attributes = getAttributes();
		
		List<Object> values = new ArrayList<Object>(attributes.size());
		
		for(Attribute attribute : attributes) {
			values.add(attribute.getValue());
		}
		
		return values;
	}
	
	@Override	
	public Object modify(AttributeModifier modifier) {
		for(Attribute attribute : getAttributes()) {
			attribute.modify(modifier);
		}
		
		return getValue();
	}
}