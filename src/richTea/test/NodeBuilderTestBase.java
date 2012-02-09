package richTea.test;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.apache.log4j.BasicConfigurator;

import static org.junit.Assert.assertTrue;

import richTea.antlr.RichTeaLexer;
import richTea.antlr.RichTeaParser;
import richTea.antlr.RichTeaTreeAdaptor;
import richTea.antlr.tree.AttributeData;
import richTea.antlr.tree.NodeData;
import richTea.core.attribute.Attribute;
import richTea.core.factory.RichTeaAttributeFactory;
import richTea.core.factory.RichTeaNodeFactory;
import richTea.core.factory.bindings.BootstrapBindingSet;
import richTea.core.node.TreeNode;

public class NodeBuilderTestBase {
	
	private RichTeaNodeFactory nodeFactory;
	private RichTeaAttributeFactory attributeFactory;

	public NodeBuilderTestBase() {
		BasicConfigurator.configure();
		
		nodeFactory = new RichTeaNodeFactory(new BootstrapBindingSet());
		attributeFactory = new RichTeaAttributeFactory(nodeFactory);
	}
	
	protected Attribute buildAttribute(String input) throws RecognitionException {
		CommonTree parseTree = (CommonTree) configureParser(input).attribute_list().getTree();
		AttributeData attributeData = (AttributeData) parseTree.getChild(0);
		
		return attributeFactory.create(attributeData);
	}
	
	protected TreeNode buildNode(String input) throws RecognitionException {		
		NodeData nodeData = (NodeData) configureParser(input).program().getTree();
		
		return nodeFactory.create(nodeData);
	}
	
	private RichTeaParser configureParser(String input) throws RecognitionException {
	   	ANTLRStringStream source = new ANTLRStringStream(input);
		
		RichTeaLexer lexer = new RichTeaLexer(source);
		RichTeaParser parser = new RichTeaParser(new CommonTokenStream(lexer));
		parser.setTreeAdaptor(new RichTeaTreeAdaptor());
		
		return parser;
	} 
	
	protected void testAttribute(String input, Object requiredValue) throws RecognitionException
	{
		assertTrue(buildAttribute(input).getValue().equals(requiredValue));
	}
}