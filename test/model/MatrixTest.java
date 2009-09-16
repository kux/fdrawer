package model;

import model.Matrix;

import org.junit.Assert;
import org.junit.Test;

public class MatrixTest {

	@Test
	public void matrixTest() {
		Matrix<Double> cm = new Matrix<Double>(4, 5, 2);

		cm.setAt(Double.valueOf(3.3), 3, 2, 1);
		Double v = cm.getAt(3, 2, 1);
		Assert.assertEquals(v, 3.3, 0.001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void out_of_bounds_dimension() {
		Matrix<Double> cm = new Matrix<Double>(4, 5, 2);
		cm.setAt(Double.valueOf(3.3), 3, 2, 1, 1);

	}

	@Test(expected = IllegalArgumentException.class)
	public void out_of_bounds_range() {
		Matrix<Double> cm = new Matrix<Double>(4, 5, 2);
		cm.setAt(Double.valueOf(3.3), 3, 1, 1, 5);

	}

}
