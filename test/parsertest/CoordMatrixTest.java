package parsertest;

import model.CoordMatrix;

import org.junit.Assert;
import org.junit.Test;

public class CoordMatrixTest {

	@Test
	public void dimNrTest() {

		CoordMatrix cm = new CoordMatrix(4, 5, 2);
		Assert.assertTrue(cm.getDimNo() == 3);
	}

	@Test
	public void getTest() {
		CoordMatrix cm = new CoordMatrix(4, 5, 2);
		double[] coords = cm.getAt(1, 1, 1);
		Assert.assertTrue(Double.compare(coords[0], 0) == 0
				&& Double.compare(coords[1], 0) == 0
				&& Double.compare(coords[2], 0) == 0);
	}

	@Test
	public void set_get_test() {
		CoordMatrix cm = new CoordMatrix(4, 5, 2);
		double[] val = { 1.1, 1.2, 3 };
		cm.setAt(val, 3, 2, 1);
		double coord[] = cm.getAt(3, 2, 1);
		Assert.assertEquals(coord[0], 1.1, 0.001);
		Assert.assertEquals(coord[1], 1.2, 0.001);
		Assert.assertEquals(coord[2], 3, 0.001);
	}

}

