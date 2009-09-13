package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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

	public void startDrawing(List<String> functions, double xleft, double xright) {

		if (cworker != null)
			cworker.stop();

		calculateXValues(xleft, xright);

		LinkedHashMap<String, double[]> varMap = new LinkedHashMap<String, double[]>();
		varMap.put("x", this.xvalues);

		cworker = new CalculateWorker(createEvaluators(functions), varMap);
		cworker.execute();

	}

	private void calculateXValues(double xleft, double xright) {
		double increment = (xright - xleft) / PRECISION;

		for (int i = 0; i < PRECISION; ++i) {
			this.xvalues[i] = xleft + increment * i;
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		draw2dGraphs(g);

		drawAxis(g);
	}

	private void draw2dGraphs(Graphics g) {
		double pixelRange = (xvalues[PRECISION - 1] - xvalues[0]) / getWidth();
		Random rand = new Random(10);

		for (Matrix<Double> matrix : matrixesToDraw) {

			g.setColor(new Color(rand.nextFloat(), rand.nextFloat(), rand
					.nextFloat()));

			int xant = -1000, yant = -1000;
			for (int i = 0; i < PRECISION; i++) {
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

	public void zoomOut() {
		double with = (xvalues[PRECISION - 1] - xvalues[0]) / REDCOEF;
		calculateXValues(xvalues[0] - with, xvalues[PRECISION - 1] + with);

		cworker.modifyXValues(xvalues);

	}

	public void zoomIn() {

		double with = (xvalues[PRECISION - 1] - xvalues[0]) / REDCOEF;
		calculateXValues(xvalues[0] + with, xvalues[PRECISION - 1] - with);

		cworker.modifyXValues(xvalues);
	}

	public void moveLeft() {
		double with = (xvalues[PRECISION - 1] - xvalues[0]) / REDCOEF;
		calculateXValues(xvalues[0] - with, xvalues[PRECISION - 1] - with);

		cworker.modifyXValues(xvalues);
	}

	public void moveRight() {
		double with = (xvalues[PRECISION - 1] - xvalues[0]) / REDCOEF;
		calculateXValues(xvalues[0] + with, xvalues[PRECISION - 1] + with);

		cworker.modifyXValues(xvalues);
	}

	public void moveUp() {
		double with = (xvalues[PRECISION - 1] - xvalues[0]) / REDCOEF;
		double ywith = with * getHeight() / getWidth();
		ybottom += ywith;
	}

	public void moveDown() {
		double with = (xvalues[PRECISION - 1] - xvalues[0]) / REDCOEF;
		double ywith = with * getHeight() / getWidth();
		ybottom -= ywith;
	}

	private static final int PRECISION = 1000;
	private static final double REDCOEF = 50;

	private double ybottom = -2;
	private double xvalues[] = new double[PRECISION];

	private double timeStart = 0;
	private double timeIncrement = 0.1;

	private List<Matrix<Double>> matrixesToDraw = new ArrayList<Matrix<Double>>();
	private CalculateWorker cworker = null;

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
			for (String var : mapToCopy.keySet()) {
				newMap.put(var, mapToCopy.get(var).clone());
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
				clearDrawing();

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

	/**
	 * doesn't seem to work though
	 */
	public void clearDrawing() {
		matrixesToDraw.clear();
		repaint();

	}

	private void drawAxis(Graphics g) {
		g.setColor(Color.GRAY);
		double inc = (xvalues[PRECISION - 1] - xvalues[0]) / getWidth();
		if (xvalues[0] < 0 && xvalues[PRECISION - 1] > 0) {
			int yaxis = (int) ((-xvalues[0]) / inc);
			g.drawLine(yaxis, 0, yaxis, getHeight());
		}

		if (ybottom + inc * getHeight() > 0) {
			int xaxis = getHeight() + (int) (ybottom / inc);
			g.drawLine(0, xaxis, getWidth(), xaxis);
		}
	}

	public void moveWith(int xdif, int ydif) {
		double pixelRange = (xvalues[PRECISION - 1] - xvalues[0]) / getWidth();
		double xwith = xdif * pixelRange;
		double ywith = ydif * pixelRange;

		ybottom += ywith;

		calculateXValues(xvalues[0] - xwith, xvalues[PRECISION - 1] - xwith);
		cworker.modifyXValues(xvalues);

	}

}
