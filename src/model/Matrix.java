package model;

import java.util.ArrayList;

/**
 * class modeling a n dimensional matrix
 * 
 * @author cucuioan
 * 
 * @param <T>
 */
public class Matrix<T> {

	private ArrayList<T> vmatrix;

	private int[] matrixSize;
	private int dimNo;
	private int flatten;

	/**
	 * the number of passed parameters defines number of dimensions the matrix
	 * has
	 * <p>
	 * ex: <code>new Matrix(4,2,3)</code> <br>
	 * creates a 3-dimensional matrix of size 4*2*3
	 * 
	 * @param sizes
	 */
	public Matrix(int... sizes) {

		this.matrixSize = sizes;
		this.dimNo = sizes.length;
		int matrixSize = 1;
		for (int i : sizes)
			matrixSize *= i;

		flatten = matrixSize;
		vmatrix = new ArrayList<T>(flatten);

		for (int i = 0; i < flatten; ++i) {
			vmatrix.add(null);
		}
	}

	/**
	 * @return number of dimensions the matrix has
	 */
	public int getDimNo() {
		return dimNo;
	}

	/**
	 * 
	 * @return this matrix's size
	 *         <p>
	 *         ex: a 3d matrix will return a 3 element array
	 */
	public int[] getMatrixSize() {
		return matrixSize;
	}

	/**
	 * @param indices
	 *            coordinates from where to retrieve the element
	 * @return element at the requested coordinates
	 */
	public T getAt(int... indices) {
		return vmatrix.get(getFlattenIndice(indices));
	}

	/**
	 * @param value
	 * @param indices
	 * @throws IndexOutOfBoundsException
	 *             if more indices are supplied than the matrix's dimension or
	 *             if one of the indices is greater or smaller than the size
	 *             provided when the matrix was constructed
	 */
	public void setAt(T value, int... indices) {
		vmatrix.set(getFlattenIndice(indices), value);
	}

	public void setAtFlatIndex(T value, int flat) {
		vmatrix.set(flat, value);
	}

	private int getFlattenIndice(int... indices) {
		if (indices.length != dimNo)
			throw new IllegalArgumentException("Number of indices don't match matrix dimmension");

		int flattenIndice = 0;
		int fcoef = flatten;
		for (int i = 0; i < indices.length; ++i) {
			if (indices[i] > matrixSize[i] || indices[i] < 0)
				throw new IndexOutOfBoundsException();

			fcoef /= matrixSize[i];
			flattenIndice += indices[i] * fcoef;
		}
		return flattenIndice;
	}

}
