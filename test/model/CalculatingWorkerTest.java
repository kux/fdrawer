package model;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.RecognitionException;
import org.junit.Before;
import org.junit.Test;

import parser.UncheckedParserException;

public class CalculatingWorkerTest {

	private CalculatingWorker cw;

	@Before
	public void setUp() {
		cw = new CalculatingWorker();
	}

	@Test(expected = IllegalArgumentException.class)
	public void setDrawnFunctions_different_variables() throws UncheckedParserException,
			RecognitionException {
		List<FunctionEvaluator> lfe = new ArrayList<FunctionEvaluator>();
		lfe.add(new FunctionEvaluator("x+y+z"));
		lfe.add(new FunctionEvaluator("x+y"));
		cw.setDrawnFunctions(lfe);
	}

	@Test
	public void setDrawnFunctions_different_variables_t_ignored() throws UncheckedParserException,
			RecognitionException {
		List<FunctionEvaluator> lfe = new ArrayList<FunctionEvaluator>();
		lfe.add(new FunctionEvaluator("x+t"));
		lfe.add(new FunctionEvaluator("x"));
		cw.setDrawnFunctions(lfe);
	}

	@Test
	public void setDrawnFunctions_different_variables_t_ignored_2()
			throws UncheckedParserException, RecognitionException {
		List<FunctionEvaluator> lfe = new ArrayList<FunctionEvaluator>();
		lfe.add(new FunctionEvaluator("x"));
		lfe.add(new FunctionEvaluator("x+t"));
		cw.setDrawnFunctions(lfe);
	}

}
