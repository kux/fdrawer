package parsertest;

import java.io.IOException;
import java.util.HashMap;

import junit.framework.Assert;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.junit.Test;

import parser.Eval;
import parser.ExprLexer;
import parser.ExprParser;
import parser.UncheckedParserException;

public class ParserTest {

	private Eval initEvaluator(String testFunction) throws RecognitionException {

		ANTLRStringStream input = new ANTLRStringStream(testFunction);
		ExprLexer lexer = new ExprLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ExprParser parser = new ExprParser(tokens);
		ExprParser.entry_return r = parser.entry();
		CommonTree t = (CommonTree) r.getTree();
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(t);
		return new Eval(nodes);
	}

	@Test(expected = UncheckedParserException.class)
	public void badFunction() throws RecognitionException {
		initEvaluator(";123");

	}

	@Test
	public void powFunction() throws RecognitionException {
		Eval walker = initEvaluator("pow(x,3)+2");
		HashMap<String, Double> memory = new HashMap<String, Double>();
		memory.put("x", Double.valueOf(2));
		walker.setMemory(memory);
		Assert.assertEquals(10, walker.entry(), 0.1);
	}

	@Test
	public void basicTest() throws IOException, RecognitionException {
		Eval walker = initEvaluator("cos x /cos (2*x)");
		HashMap<String, Double> memory = new HashMap<String, Double>();
		memory.put("x", Double.valueOf(2));
		walker.setMemory(memory);
		Assert.assertEquals(0.636657076, walker.entry(), 0.1);
	}

	@Test
	public void withFloat() throws IOException, RecognitionException {

		Eval walker = initEvaluator("1.3+1.01*2+a*3.3");
		HashMap<String, Double> memory = new HashMap<String, Double>();
		memory.put("a", Double.valueOf(2));
		walker.setMemory(memory);
		Assert.assertEquals(9.92, walker.entry(), 0.2);

	}

	@Test
	public void withUnary() throws IOException, RecognitionException {
		Eval walker = initEvaluator("sin(x) + 1");
		HashMap<String, Double> memory = new HashMap<String, Double>();
		memory.put("x", Double.valueOf(1));
		walker.setMemory(memory);
		Assert.assertEquals(1.84147098, walker.entry(), 0.01);

	}

	@Test
	public void unaryNoPar() throws IOException, RecognitionException {
		Eval walker = initEvaluator("3+sin 3*b");
		HashMap<String, Double> memory = new HashMap<String, Double>();
		memory.put("b", Double.valueOf(3));
		walker.setMemory(memory);
		Assert.assertEquals(3.42336002, walker.entry(), 0.01);

	}

	@Test
	public void unaryMinus() throws IOException, RecognitionException {
		Eval walker = initEvaluator("5+-2");
		Assert.assertEquals(3, walker.entry(), 0.01);

	}

	@Test
	public void unaryMinusNoPar() throws IOException, RecognitionException {
		Eval walker = initEvaluator("5+(-2)");
		Assert.assertEquals(3, walker.entry(), 0.01);
	}

	@Test
	public void getVariables() throws IOException, RecognitionException {
		Eval walker = initEvaluator("x+(-y)");
		HashMap<String, Double> memory = new HashMap<String, Double>();
		memory.put("x", Double.valueOf(3));
		memory.put("y", Double.valueOf(1));
		walker.entry();
		Assert.assertEquals(2, walker.getVariables().size());
	}

	@Test
	public void getVariablesUndefinedNanResult() throws IOException,
			RecognitionException {
		Eval walker = initEvaluator("x/y)");
		double result = walker.entry();
		System.out.println(result);
		Assert.assertEquals(2, walker.getUndefinedVariables().size());
	}

	@Test
	public void getVariablesUndefined() throws IOException,
			RecognitionException {
		Eval walker = initEvaluator("x+y)");
		double result = walker.entry();
		System.out.println(result);
		Assert.assertEquals(2, walker.getUndefinedVariables().size());
	}
}
