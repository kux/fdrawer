package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import model.FunctionEvaluator;
import model.Matrix;

import org.antlr.runtime.RecognitionException;

import parser.UncheckedParserException;

@SuppressWarnings("serial")
public class FDrawComponent extends JLabel {

	private boolean drawingStarted = false;

	/**
	 * start drawing in this component
	 * 
	 * @param functions
	 *            functions to draw
	 * @param xleft
	 *            left margin on x axis
	 * @param xright
	 *            right margin on x axis
	 * @param yleft
	 *            TODO
	 * @param yright
	 *            TODO
	 * @param precision
	 *            drawing precision
	 */
	public void startDrawing(List<String> functions, double xleft,
			double xright, double yleft, double yright, int precision) {

		// in case this is a actually a "redraw"
		stopDrawing();

		splitIntervals(xleft, xright, yleft, yright, precision);

		LinkedHashMap<String, double[]> varMap = new LinkedHashMap<String, double[]>();
		varMap.put("x", this.xvalues);
		varMap.put("y", this.yvalues);

		cworker = new CalculateWorker(createEvaluators(functions), varMap);
		cworker.execute();

		this.drawingStarted = true;

	}

	/**
	 * modify drawing precsion<br>
	 * can be done while drawing is in progress
	 * 
	 * @param precision
	 */
	public void modifyPrecsion(int precision) {
		// splitIntervals(xvalues[0], xvalues[xvalues.length - 1], precision);
		// TODO
		cworker.modifyXValues(xvalues);
	}

	/**
	 * if working, stop the calculating worker
	 */
	public void stopDrawing() {
		if (cworker != null)
			cworker.stop();
		this.drawingStarted = false;

	}

	/**
	 * 
	 * @param xdif
	 * @param ydif
	 * @throws IllegalStateException
	 *             if no drawing is in progress
	 */
	public void moveWith(int xdif, int ydif) {
		checkIfDrawing();
		double pixelRange = (xvalues[xvalues.length - 1] - xvalues[0])
				/ getWidth();
		double xwith = xdif * pixelRange;
		double ywith = ydif * pixelRange;

		ybottom += ywith;

		// splitIntervals(xvalues[0] - xwith, xvalues[xvalues.length - 1]
		// - xwith, xvalues.length); TODO
		cworker.modifyXValues(xvalues);

	}

	/**
	 * @throws IllegalStateException
	 *             if no drawing is in progress
	 */
	public void zoomOut() {
		checkIfDrawing();
		double with = (xvalues[xvalues.length - 1] - xvalues[0]) / REDCOEF;
		// splitIntervals(xvalues[0] - with, xvalues[xvalues.length - 1] + with,
		// xvalues.length); TODO

		cworker.modifyXValues(xvalues);

	}

	/**
	 * @throws IllegalStateException
	 *             if no drawing is in progress
	 */
	public void zoomIn() {

		double with = (xvalues[xvalues.length - 1] - xvalues[0]) / REDCOEF;
		// splitIntervals(xvalues[0] + with, xvalues[xvalues.length - 1] - with,
		// xvalues.length); TODO

		cworker.modifyXValues(xvalues);
	}

	/**
	 * @throws IllegalStateException
	 *             if no drawing is in progress
	 */
	public void moveLeft() {
		double with = (xvalues[xvalues.length - 1] - xvalues[0]) / REDCOEF;
		// splitIntervals(xvalues[0] - with, xvalues[xvalues.length - 1] - with,
		// xvalues.length); TODO

		cworker.modifyXValues(xvalues);
	}

	/**
	 * @throws IllegalStateException
	 *             if no drawing is in progress
	 */
	public void moveRight() {
		double with = (xvalues[xvalues.length - 1] - xvalues[0]) / REDCOEF;
		// splitIntervals(xvalues[0] + with, xvalues[xvalues.length - 1] + with,
		// xvalues.length); TODO

		cworker.modifyXValues(xvalues);
	}

	/**
	 * @throws IllegalStateException
	 *             if no drawing is in progress
	 */
	public void moveUp() {
		double with = (xvalues[xvalues.length - 1] - xvalues[0]) / REDCOEF;
		double ywith = with * getHeight() / getWidth();
		ybottom += ywith;
	}

	/**
	 * @throws IllegalStateException
	 *             if no drawing is in progress
	 */
	public void moveDown() {
		double with = (xvalues[xvalues.length - 1] - xvalues[0]) / REDCOEF;
		double ywith = with * getHeight() / getWidth();
		ybottom -= ywith;
	}

	/**
	 * @throws IllegalStateException
	 *             if no drawing is in progress
	 */
	private void checkIfDrawing() {
		if (!this.drawingStarted)
			throw new IllegalStateException("Drawing not started");
	}

	private static final double REDCOEF = 50;

	// private int xvalues.length = 1000;

	private double ybottom = -2;
	private double xvalues[];
	private double yvalues[];

	private double timeStart = 0;
	private double timeIncrement = 0.1;

	private List<Matrix<Double>> matrixesToDraw = new ArrayList<Matrix<Double>>();
	private CalculateWorker cworker = null;

	private void splitIntervals(double xleft, double xright, double yleft,
			double yright, int precision) {

		xvalues = new double[precision];
		yvalues = new double[precision];
		double xincrement = (xright - xleft) / precision;
		double yincrement = (yright - yleft) / precision;

		for (int i = 0; i < precision; ++i) {
			this.xvalues[i] = xleft + xincrement * i;
			this.yvalues[i] = yleft + yincrement * i;
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw3dGraphs(g);
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

		Random rand = new Random(10);

		int[][] coordsx = new int[xvalues.length][yvalues.length];
		int[][] coordsy = new int[xvalues.length][yvalues.length];

		double ax, ay, az, cx, cy, cz, ox, oy, oz, dx, dy, dz, ex, ey, ez, bx, by;

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
					ax = xvalues[i];
					ay = yvalues[j];
					az = matrix.getAt(i, j, 0);

					cx = 1;
					cy = 1;
					cz = 1;

					ox = Math.PI / 32;
					oy = Math.PI / 32;
					oz = Math.PI / 32;

					dx = Math.cos(oy)
							* (Math.sin(oz) * (ay - cy) + Math.cos(oz)
									* (ax - cx)) - Math.sin(oy) * (az - cz);

					dy = Math.sin(ox)
							* (Math.cos(oy) * (az - cz) + Math.sin(oy)
									* (Math.sin(oz) * (ay - cy) + Math.cos(oz)
											* (ax - cx)))
							+ Math.cos(ox)
							* (Math.cos(oz) * (ay - cy) - Math.sin(oz)
									* (ax - cx));

					dz = Math.cos(ox)
							* (Math.cos(oy) * (az - cz) + Math.sin(oy)
									* (Math.sin(oz) * (ay - cy) + Math.cos(oz)
											* (ax - cx)))
							- Math.sin(ox)
							* (Math.cos(oz) * (ay - cy) - Math.sin(oz)
									* (ax - cx));

					ex = 0;
					ey = 0;
					ez = 5;

					bx = (dx - ex) * (1 / (ez - dz));
					by = (dy - ey) * (1 / (ez - dz));

					int coordx = (int) (bx / pixelRangeX)
							- (int) (xvalues[0] / pixelRangeX);
					int coordy = getHeight()
							- ((int) (by / pixelRangeY) - (int) (yvalues[0] / pixelRangeY));

					coordsx[i][j] = coordx;
					coordsy[i][j] = coordy;

				}
			}
			for (int i = 0; i < coordsx.length; ++i) {
				for (int j = 1; j < coordsy.length; ++j) {
					g.drawLine(coordsx[i][j - 1], coordsy[i][j - 1],
							coordsx[i][j], coordsy[i][j]);
				}
			}

			for (int i = 1; i < coordsy.length; ++i) {
				for (int j = 0; j < coordsx.length; ++j) {
					g.drawLine(coordsx[i - 1][j], coordsy[i - 1][j],
							coordsx[i][j], coordsy[i][j]);
				}
			}
		}

	}

	private void draw2dGraphs(Graphics g) {

		// if drawing not started yet, do nothing
		if (!drawingStarted)
			return;

		drawAxis(g);

		double pixelRange = (xvalues[xvalues.length - 1] - xvalues[0])
				/ getWidth();
		Random rand = new Random(10);

		for (Matrix<Double> matrix : matrixesToDraw) {

			// matrix.getMatrixSize()[0] - x axis values on the result matrix
			// skip if precision was changed and matrixes received from the
			// calculating worker are no
			// longer consistent
			if (matrix.getMatrixSize()[0] != xvalues.length)
				break;

			g.setColor(new Color(rand.nextFloat(), rand.nextFloat(), rand
					.nextFloat()));

			int xant = -1000, yant = -1000;

			for (int i = 0; i < xvalues.length - 1; i++) {
				int x = (int) ((xvalues[i] - xvalues[0]) / pixelRange);
				double rez = matrix.getAt(i, 0);
				int y = getHeight()
						- ((int) (rez / pixelRange) - (int) (this.ybottom / pixelRange));

				if (Math.abs(xant - x) < this.getWidth() / 3
						&& Math.abs(yant - y) < getHeight() / 3)
					g.drawLine(xant, yant, x, y);
				xant = x;
				yant = y;
			}
		}
	}

	private void drawAxis(Graphics g) {
		g.setColor(Color.GRAY);
		double inc = (xvalues[xvalues.length - 1] - xvalues[0]) / getWidth();
		if (xvalues[0] < 0 && xvalues[xvalues.length - 1] > 0) {
			int yaxis = (int) ((-xvalues[0]) / inc);
			g.drawLine(yaxis, 0, yaxis, getHeight());
		}

		if (ybottom + inc * getHeight() > 0) {
			int xaxis = getHeight() + (int) (ybottom / inc);
			g.drawLine(0, xaxis, getWidth(), xaxis);
		}
	}

	private class CalculateWorker extends
			SwingWorker<Void, List<Matrix<Double>>> {

		private final List<FunctionEvaluator> evaluators;
		private final LinkedHashMap<String, double[]> varMap;
		private volatile boolean calculate = true;

		private CalculateWorker(List<FunctionEvaluator> evaluators,
				LinkedHashMap<String, double[]> varMap) {
			this.evaluators = evaluators;
			// copy the received map, this makes this class completely
			// thread-safe
			this.varMap = deepCopyLinkedHashMap(varMap);
		}

		private LinkedHashMap<String, double[]> deepCopyLinkedHashMap(
				LinkedHashMap<String, double[]> mapToCopy) {
			LinkedHashMap<String, double[]> newMap = new LinkedHashMap<String, double[]>();

			for (Map.Entry<String, double[]> entry : mapToCopy.entrySet()) {
				newMap.put(entry.getKey(), entry.getValue().clone());
			}
			return newMap;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Void doInBackground() throws Exception {
			double time = timeStart;

			while (calculate) {

				synchronized (this) {
					varMap.put("t", new double[] { time });
				}

				List<Matrix<Double>> results = new ArrayList<Matrix<Double>>();
				for (FunctionEvaluator feval : evaluators) {

					synchronized (this) {
						results.add(feval.calculate(varMap));
					}

				}
				publish(results);
				time += timeIncrement;
			}

			return null;
		}

		@Override
		protected void process(List<List<Matrix<Double>>> matrixes) {
			matrixesToDraw = matrixes.get(matrixes.size() - 1);
			repaint();
		}

		@Override
		protected void done() {
			try {
				get();
			} catch (InterruptedException e) {
				e.printStackTrace(); // can't happen
			} catch (ExecutionException e) {
				JOptionPane.showMessageDialog(FDrawComponent.this,
						"Incorrect function\n" + e.getMessage()
								+ "\nOnly x, t variables are supported !!",
						"Error", JOptionPane.ERROR_MESSAGE);
				stopDrawing();

			}
		}

		public void stop() {
			calculate = false;
		}

		public synchronized void modifyXValues(double xvalues[]) {
			varMap.put("x", xvalues.clone());
		}

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

}
