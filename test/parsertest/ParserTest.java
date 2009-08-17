package parsertest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import junit.framework.Assert;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.junit.Test;

import parser.Eval;
import parser.ExprLexer;
import parser.ExprParser;

public class ParserTest {

	private Eval initEvaluator(String testFile) {
		Eval eval = null;
		try {
			ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(
					testFile));
			ExprLexer lexer = new ExprLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			ExprParser parser = new ExprParser(tokens);
			ExprParser.entry_return r = parser.entry();
			CommonTree t = (CommonTree) r.getTree(); 
			CommonTreeNodeStream nodes = new CommonTreeNodeStream(t);
			eval =  new Eval(nodes); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return eval;

	}

	@Test
	public void basicTest() throws IOException, RecognitionException {
		Eval walker = initEvaluator("./test/parsertest/correct");
		HashMap<String, Double> memory = new HashMap<String, Double>();
		memory.put("x", Double.valueOf(2));
		walker.setMemory(memory);
		Assert.assertEquals(20.964872, walker.entry(), 0.1);
	}

	@Test
	public void withFloat() throws IOException, RecognitionException {
		
		Eval walker = initEvaluator("./test/parsertest/withfloat");
		HashMap<String, Double> memory = new HashMap<String, Double>();
		memory.put("a", Double.valueOf(2));
		walker.setMemory(memory);
		Assert.assertEquals(9.92, walker.entry(), 0.2);

	}

	@Test
	public void withUnary() throws IOException, RecognitionException {
		Eval walker = initEvaluator("./test/parsertest/withunary");
		HashMap<String, Double> memory = new HashMap<String, Double>();
		memory.put("x", Double.valueOf(1));
		walker.setMemory(memory);
		Assert.assertEquals(1.84147098, walker.entry(), 0.01);

	}

	@Test
	public void unaryNoPar() throws IOException, RecognitionException {
		Eval walker = initEvaluator("./test/parsertest/unarynopar");
		HashMap<String, Double> memory = new HashMap<String, Double>();
		memory.put("b", Double.valueOf(3));
		walker.setMemory(memory);
		Assert.assertEquals(3.42336002, walker.entry(), 0.01);

	}

	@Test
	public void unaryMinus() throws IOException, RecognitionException {
		Eval walker = initEvaluator("./test/parsertest/unaryminus1");
		Assert.assertEquals(3, walker.entry(), 0.01);

	}

	@Test
	public void unaryMinusNoPar() throws IOException, RecognitionException {
		Eval walker = initEvaluator("./test/parsertest/unaryminus2");
		Assert.assertEquals(3, walker.entry(), 0.01);

	}
}
