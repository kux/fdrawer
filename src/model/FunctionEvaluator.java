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

public class FunctionEvaluator {

	private final Eval evaluator;

	public FunctionEvaluator(String function)
			throws org.antlr.runtime.RecognitionException {

		ANTLRStringStream input = new ANTLRStringStream(function);
		ExprLexer lexer = new ExprLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ExprParser parser = new ExprParser(tokens);
		ExprParser.entry_return r = parser.entry();
		CommonTree tree = (CommonTree) r.getTree();
		BufferedTreeNodeStream bTree = new BufferedTreeNodeStream(tree);
		evaluator = new Eval(bTree);
	}

	/**
	 * 
	 * @return result matrix holding the result of evaluating the function with
	 *         all value combinations received as input
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
				memory
						.put(variable,
								varMap.get(variable)[currentPozition[j++]]);
				
			}
			
			
			try {
				evaluator.reset();
				evaluator.setMemory(memory);
				double rez = evaluator.entry();
				rezultMatrix.setAtFlatIndex(Double.valueOf(rez), flatPoz);
			} catch (RecognitionException e) {
				//this can't be reached as the function was already parsed ??			
			}

		}
		return rezultMatrix;
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
