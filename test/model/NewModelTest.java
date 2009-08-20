package model;

import java.util.LinkedHashMap;

import model.FunctionEvaluator;
import model.Matrix;

import org.antlr.runtime.RecognitionException;
import org.junit.Assert;
import org.junit.Test;

public class NewModelTest {
	
	@Test
	public void design() throws RecognitionException{
		LinkedHashMap<String, double[]> varMap = new LinkedHashMap<String, double[]>();
		double[] xar = {1, 1.5, 2};
		double[] yar = {0,1};
		double[] tar = {0,3};
		varMap.put("x", xar);
		varMap.put("y", yar);
		varMap.put("t", tar);
		FunctionEvaluator evl = new FunctionEvaluator("x+y+t");
		
		Matrix<Double> m = evl.calculate(varMap);
		Assert.assertEquals(1, m.getAt(0,0,0).doubleValue(), 0.0001);
		Assert.assertEquals(4, m.getAt(0,0,1).doubleValue(), 0.0001);
		Assert.assertEquals(2, m.getAt(0,1,0).doubleValue(), 0.0001);
	}
	
//	@Test
//	public void state_trans_test 

	
}
