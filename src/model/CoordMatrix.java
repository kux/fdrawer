package model;

public class CoordMatrix {
	private int[] sizes;
	private int dimNo;
	private double[][] matrix = null;
	private int flatten;

	public CoordMatrix(int... sizes) {
		this.sizes = sizes;
		this.dimNo = sizes.length;
		int matrixSize = 1;
		for (int i : sizes)
			matrixSize *= i;

		flatten = matrixSize;
		matrix = new double[flatten][dimNo];
	}

	public int getDimNo() {
		return dimNo;
	}

	public double[] getAt(int... indices) {
		if (indices.length != dimNo)
			throw new IndexOutOfBoundsException();

		int flattenIndice = 0;
		int fcoef = flatten;
		for (int i = 0; i < indices.length; ++i) {
			fcoef /= sizes[i];
			flattenIndice += indices[i] * fcoef;
		}
		return matrix[flattenIndice];

	}

	public void setAt(double[] values, int... indices) {
		if (indices.length != dimNo)
			throw new IndexOutOfBoundsException();

		int flattenIndice = 0;
		int fcoef = flatten;
		for (int i = 0; i < indices.length; ++i) {
			fcoef /= sizes[i];
			flattenIndice += indices[i] * fcoef;
		}
		matrix[flattenIndice] = values;
	}
}
