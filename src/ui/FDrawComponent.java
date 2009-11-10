package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import model.FunctionEvaluator;
import model.Matrix;

import org.antlr.runtime.RecognitionException;

import parser.UncheckedParserException;

@SuppressWarnings("serial")
public class FDrawComponent extends JLabel implements DrawsFunctions {

	private boolean drawingStarted = false;

	/**
	 * 
	 * @param functions
	 * @param distance
	 * @param rotation
	 * @param precision
	 */
	public void startDrawing(List<String> functions, double distance,
			double[] rotation, int precision) {

		// in case this is a actually a "redraw"
		stopDrawing();

		this.rotation = rotation;
		this.distance = distance;

		calculate3D();
		splitIntervals(precision);

		LinkedHashMap<String, double[]> varMap = new LinkedHashMap<String, double[]>();
		varMap.put("x", this.xvalues);
		varMap.put("y", this.yvalues);

		cworker = new CalculatingWorker(createEvaluators(functions), varMap, 0,
				0.1, this);
		cworker.execute();

		this.drawingStarted = true;

	}

	private void calculate3D() {
		this.eye = new double[] { 0, 0, distance };

		camera[2] = Math.sqrt(Math.pow(distance, 2)
				/ (1 + Math.pow(Math.tan(rotation[0]), 2) + Math.pow(Math
						.tan(rotation[1]), 2)));

		camera[0] = camera[2] * Math.tan(rotation[0]);
		camera[1] = camera[2] * Math.tan(rotation[1]);

	}

	/**
	 * modify drawing precsion<br>
	 * can be done while drawing is in progress
	 * 
	 * @param precision
	 */
	public void modifyPrecsion(int precision) {
		if (cworker != null) {
			splitIntervals(precision);

			cworker.modifyIntervals(xvalues, yvalues);
		}
	}

	/**
	 * if working, stop the calculating worker
	 */
	public void stopDrawing() {
		if (cworker != null)
			cworker.stop();
		this.drawingStarted = false;

	}

	public void modifAngles(double x, double y) {
		if (cworker != null) {
			rotation[0] += x;
			rotation[1] += y;

			calculate3D();
		}

	}

	/**
	 * @throws IllegalStateException
	 *             if no drawing is in progress
	 */
	public void zoomOut() {
		if (cworker != null) {
			distance += distance / REDCOEF;
			calculate3D();
			splitIntervals(xvalues.length);
			cworker.modifyIntervals(xvalues, yvalues);
		}

	}

	/**
	 * @throws IllegalStateException
	 *             if no drawing is in progress
	 */
	public void zoomIn() {

		if (cworker != null) {
			distance -= distance / REDCOEF;
			calculate3D();
			splitIntervals(xvalues.length);
			cworker.modifyIntervals(xvalues, yvalues);
		}
	}

	public void setCamera(double[] camera) {
		this.camera = camera;
	}

	public void setRotation(double[] rotation) {
		this.rotation = rotation;
	}

	public void setEye(double[] eye) {
		this.eye = eye;
	}

	public void drawMatrixes(List<Matrix<Double>> toDraw) {
		this.matrixesToDraw = toDraw;
		repaint();
	}

	private void splitIntervals(int precision) {

		xvalues = new double[precision];
		yvalues = new double[precision];
		double xincrement = (2 * distance) / (precision - 1);
		double yincrement = (2 * distance) / (precision - 1);

		for (int i = 0; i < precision; ++i) {
			this.xvalues[i] = -distance + xincrement * i;
			this.yvalues[i] = -distance + yincrement * i;
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw3dGraphs(g);
	}

	private double[] translate(double a[], double[] c, double[] o) {

		double dx = Math.cos(o[1])
				* (Math.sin(o[2]) * (a[1] - c[1]) + Math.cos(o[2])
						* (a[0] - c[0])) - Math.sin(o[1]) * (a[2] - c[2]);

		double dy = Math.sin(o[0])
				* (Math.cos(o[1]) * (a[2] - c[2]) + Math.sin(o[1])
						* (Math.sin(o[2]) * (a[1] - c[1]) + Math.cos(o[2])
								* (a[0] - c[0])))
				+ Math.cos(o[0])
				* (Math.cos(o[2]) * (a[1] - c[1]) - Math.sin(o[2])
						* (a[0] - c[0]));

		double dz = Math.cos(o[0])
				* (Math.cos(o[1]) * (a[2] - c[2]) + Math.sin(o[1])
						* (Math.sin(o[2]) * (a[1] - c[1]) + Math.cos(o[2])
								* (a[0] - c[0])))
				- Math.sin(o[0])
				* (Math.cos(o[2]) * (a[1] - c[1]) - Math.sin(o[2])
						* (a[0] - c[0]));

		return new double[] { dx, dy, dz };

	}

	private double[] project(double d[], double[] e) {
		double bx = (d[0] - e[0]) * (e[2] / d[2]);
		double by = (d[1] - e[1]) * (e[2] / d[2]);

		return new double[] { bx, by };
	}

	private int[] calculateOnScreen(double[] projected, double pixelRangeX,
			double pixelRangeY) {
		int coordx = (int) (projected[0] / pixelRangeX)
				- (int) (xvalues[0] / pixelRangeX);
		int coordy = getHeight()
				- ((int) (projected[1] / pixelRangeY) - (int) (yvalues[0] / pixelRangeY));
		return new int[] { coordx, coordy };
	}

	private void draw3dGraphs(Graphics g) {

		// if drawing not started yet, do nothing
		if (!drawingStarted)
			return;

		// cworker.stop();

		double pixelRangeX = (xvalues[xvalues.length - 1] - xvalues[0])
				/ getWidth();

		double pixelRangeY = (yvalues[yvalues.length - 1] - yvalues[0])
				/ getHeight();

		drawAxis(g, pixelRangeX, pixelRangeY);

		Random rand = new Random(10);

		int[][] coordsx = new int[xvalues.length][yvalues.length];
		int[][] coordsy = new int[xvalues.length][yvalues.length];

		for (Matrix<Double> matrix : matrixesToDraw) {

			// matrix.getMatrixSize()[0] - x axis values on the result matrix
			// skip if precision was changed and matrixes received from the
			// calculating worker are no
			// longer consistent
			if (matrix.getMatrixSize()[0] != xvalues.length)
				break;

			g.setColor(new Color(rand.nextFloat(), rand.nextFloat(), rand
					.nextFloat()));

			for (int i = 0; i < xvalues.length; ++i) {
				for (int j = 0; j < yvalues.length; ++j) {

					double translated[] = translate(new double[] { xvalues[i],
							yvalues[j], matrix.getAt(i, j, 0) }, camera,
							rotation);

					double projected[] = project(translated, eye);

					int[] coord = calculateOnScreen(projected, pixelRangeX,
							pixelRangeY);

					coordsx[i][j] = coord[0];
					coordsy[i][j] = coord[1];

				}
			}

			for (int i = 0; i < coordsx.length; ++i) {
				for (int j = 1; j < coordsy.length; ++j) {
					if (onScreen(coordsx[i][j - 1], coordsy[i][j - 1],
							coordsx[i][j], coordsy[i][j]))
						g.drawLine(coordsx[i][j - 1], coordsy[i][j - 1],
								coordsx[i][j], coordsy[i][j]);
				}
			}

			for (int i = 1; i < coordsy.length; ++i) {
				for (int j = 0; j < coordsx.length; ++j) {
					if (onScreen(coordsx[i - 1][j], coordsy[i - 1][j],
							coordsx[i][j], coordsy[i][j])) {
						g.drawLine(coordsx[i - 1][j], coordsy[i - 1][j],
								coordsx[i][j], coordsy[i][j]);
					}

				}
			}
		}

	}

	private void drawAxis(Graphics g, double pixelRangeX, double pixelRangeY) {

		int[] origin = calculateOnScreen(project(translate(new double[] { 0, 0,
				0 }, camera, rotation), eye), pixelRangeX, pixelRangeY);

		int[] xmargin = calculateOnScreen(project(translate(new double[] {
				distance / 2, 0, 0 }, camera, rotation), eye), pixelRangeX,
				pixelRangeY);

		int[] ymargin = calculateOnScreen(project(translate(new double[] { 0,
				distance / 2, 0 }, camera, rotation), eye), pixelRangeX,
				pixelRangeY);

		int[] zmargin = calculateOnScreen(project(translate(new double[] { 0,
				0, distance / 2 }, camera, rotation), eye), pixelRangeX,
				pixelRangeY);

		g.drawRect(origin[0], origin[1], 2, 2);
		g.setColor(Color.RED);
		g.drawLine(origin[0], origin[1], xmargin[0], xmargin[1]);
		g.setColor(Color.GREEN);
		g.drawLine(origin[0], origin[1], ymargin[0], ymargin[1]);
		g.setColor(Color.BLUE);
		g.drawLine(origin[0], origin[1], zmargin[0], zmargin[1]);

	}

	private boolean onScreen(int x1, int y1, int x2, int y2) {
		final int asimtCheck = 3;

		return (Math.abs(x1 - x2) < getWidth() / asimtCheck
				&& Math.abs(y1 - y2) < getHeight() / asimtCheck
				&& x1 < getWidth() && x1 > 0 && x2 < getWidth() && x2 > 0
				&& y1 < getHeight() && y1 > 0 && y2 < getHeight() && y2 > 0);

	}

	private List<FunctionEvaluator> createEvaluators(List<String> functions) {

		List<FunctionEvaluator> evaluators = new ArrayList<FunctionEvaluator>();

		for (String function : functions) {
			try {
				evaluators.add(new FunctionEvaluator(function));
			} catch (UncheckedParserException e) {
				JOptionPane.showMessageDialog(this, "Incorrect function: "
						+ function + "\n" + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);

			} catch (RecognitionException e) {
				JOptionPane.showMessageDialog(this, "Incorrect function: "
						+ function + "\n" + e.getMessage());
			}
		}
		return evaluators;
	}

	private static final double REDCOEF = 50;

	// private int xvalues.length = 1000;

	private double xvalues[];
	private double yvalues[];
	private double distance;

	private double camera[] = new double[] { 0, 0, 0 };
	private double rotation[] = new double[] { 0, 0, 0 };
	private double eye[] = new double[] { 0, 0, 5 };

	private List<Matrix<Double>> matrixesToDraw = new ArrayList<Matrix<Double>>();
	private CalculatingWorker cworker = null;

}
