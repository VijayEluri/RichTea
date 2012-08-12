package richTea.core.attribute.variable;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import richTea.core.attribute.AbstractAttribute;
import richTea.core.attribute.modifier.AttributeModifier;
import richTea.core.execution.ExecutionContext;

public class BeanLookup extends AbstractAttribute {

	private Object bean;
	
	public BeanLookup(String name, Object bean) {
		super(name);
		
		this.bean = bean;
	}
	
	public Object getBean() {
		return bean;
	}

	@Override
	public Object getValue(ExecutionContext context) {
		try {
			return PropertyUtils.getProperty(getBean(), getName());
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public Object modify(ExecutionContext context, AttributeModifier modifier) {
		Object value = modifier.modify(modifier);
		
		try {
			BeanUtils.setProperty(getBean(), getName(), value);
			
			return value;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}