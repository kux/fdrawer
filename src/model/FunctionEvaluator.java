package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.BufferedTreeNodeStream;
import org.antlr.runtime.tree.CommonTree;

import parser.Eval;
import parser.ExprLexer;
import parser.ExprParser;
import parser.UncheckedParserException;

/**
 * wrapper over {@link parser.Eval}
 * <p>
 * usage example: <code>
 * 
 * FunctionEvaluator evaluator = new FunctionEvaluator("x * y"); // f(x,y) = x*y;
 * 
 * HashMap<String, double[]> variableMap = new HashMap<String, double[]>();
 * variableMap.put("x", new double[]{3, 5});
 * variableMap.put("y", new double[]{4, 6});
 * 
 * Matrix<Double> results = evaluator.calculate(variableMap);
 * 
 * assert results.getAt(0,0) == 12; // f(3,4) = 3*4 = 12
 * assert results.getAt(0,1) == 18; // f(3,6) = 18
 * assert results.getAt(1,0) == 20; // f(5,4) = 20
 * assert results.getAt(1,1) == 30; // f(5,6) = 30
 * 
 * </code>
 * 
 * @author kux
 * 
 */
public class FunctionEvaluator {

	private final Eval evaluator;
	private final String function;

	public FunctionEvaluator(String function) throws org.antlr.runtime.RecognitionException,
			UncheckedParserException {

		this.function = function;
		ANTLRStringStream input = new ANTLRStringStream(function);
		ExprLexer lexer = new ExprLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ExprParser parser = new ExprParser(tokens);
		ExprParser.entry_return r = parser.entry();
		CommonTree tree = (CommonTree) r.getTree();
		BufferedTreeNodeStream bTree = new BufferedTreeNodeStream(tree);
		evaluator = new Eval(bTree);
	}

	public String getFunction() {
		return function;
	}

	/**
	 * 
	 * @return
	 */
	public Set<String> getVariables() {
		try {
			this.evaluator.reset();
			this.evaluator.expr();
		} catch (RecognitionException ignored) {
		}
		Set<String> variables = this.evaluator.getUndefinedVariables();
		this.evaluator.reset();
		return variables;
	}

	/**
	 * 
	 * @param varMap
	 *            LinkedHashMap is used because the order it imposes on it's
	 *            entries ( when iterating you receive the contained entries in
	 *            the same order in that they were added )
	 * 
	 * 
	 * 
	 * @return matrix holding the calculation's result
	 */
	public Matrix<Double> calculate(final LinkedHashMap<String, double[]> varMap) {

		HashMap<String, Double> memory = new HashMap<String, Double>();
		Set<String> variables = varMap.keySet();
		int flatSize = getMatrixSize(varMap.values());
		int sideSize[] = getSides(varMap.values());

		Matrix<Double> rezultMatrix = new Matrix<Double>(sideSize);

		for (int flatPoz = 0; flatPoz < flatSize; ++flatPoz) {
			int[] currentPozition = unflatten(flatPoz, flatSize, sideSize);

			int j = 0;
			for (String variable : variables) {
				memory.put(variable, varMap.get(variable)[currentPozition[j++]]);

			}

			try {
				evaluator.reset();
				evaluator.setMemory(memory);
				double rez = evaluator.entry();
				rezultMatrix.setAtFlatIndex(Double.valueOf(rez), flatPoz);
			} catch (RecognitionException e) {
				// this can't be reached as the function was already parsed ??
				assert true;
			}

		}
		return rezultMatrix;
	}

	@Override
	public String toString() {
		return function;
	}

	private int[] unflatten(int flatPoz, int flatSize, int[] sideSize) {
		int[] unflattenPoz = new int[sideSize.length];
		for (int i = 0; i < sideSize.length; ++i) {
			flatSize /= sideSize[i];
			unflattenPoz[i] = flatPoz / flatSize;
			flatPoz %= flatSize;
		}
		return unflattenPoz;
	}

	private int[] getSides(Collection<double[]> values) {
		int[] sizes = new int[values.size()];
		int i = 0;
		for (double[] da : values)
			sizes[i++] += da.length;
		return sizes;
	}

	private int getMatrixSize(Collection<double[]> values) {
		int sz = 1;
		for (double[] da : values)
			sz *= da.length;
		return sz;
	}

}
