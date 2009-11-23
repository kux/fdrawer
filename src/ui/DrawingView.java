package ui;

import java.util.LinkedHashMap;
import java.util.List;

import model.Matrix;

public interface DrawingView {

	/**
	 * 
	 * @param time
	 */
	void setTime(double time);

	/**
	 * @param progress
	 */
	void setProgress(int progress);

	/**
	 * 
	 * @param variableMap
	 * @param toDraw
	 */
	void drawMatrixes(LinkedHashMap<String, double[]> variableMap, List<Matrix<Double>> toDraw);

}
