package ui;

import java.util.LinkedHashMap;
import java.util.List;

import model.Matrix;

/**
 * interface to be implemented by components that are able to draw functions
 * 
 * @author kux
 * 
 */
public interface DrawsFunctions {

	/**
	 * 
	 * 
	 * @param variableMap
	 * @param toDraw
	 *            each object in the list represents a function's values to be
	 *            drawn stored in a {@link model.Matrix}
	 *            <p>
	 *            example:<br>
	 *            if a two element list with 3d matrixes is received, two
	 *            graphics 3d graphics will be drawn with the x, y, z
	 *            coordinates taken from the matrixes
	 * 
	 */
	void drawMatrixes(LinkedHashMap<String, double[]> variableMap, List<Matrix<Double>> toDraw);

}
