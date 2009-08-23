package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import model.FunctionEvaluator;
import model.Matrix;

import org.antlr.runtime.RecognitionException;

import parser.UncheckedParserException;
import parser.UndefinedVariableException;

public class FDrawComponent extends JLabel {

	private static final long serialVersionUID = 1L;
	private List<String> cachedFunctions;

	public void startDrawing(List<String> functions) {

		cachedFunctions = functions;
		if (cworker != null)
			cworker.stop();

		LinkedHashMap<String, double[]> varmap = calculateVarMap();
		cworker = new CalculateWorker(createEvaluators(functions), varmap);
		cworker.execute();

	}

	public void stopDrawing() {
		matrixesToDraw.clear();
		cworker.stop();
		repaint();
	}
	
	public void restartDrawing(){
		stopDrawing();
		startDrawing(cachedFunctions);
	}

	public void setMatrixsToDraw(List<Matrix<Double>> drawNow) {
		matrixesToDraw = drawNow;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawAxis(g);

		double increment = (this.xright - this.xleft) / getWidth();
		Random rand = new Random(10);

		try {
			for (Matrix<Double> matrix : matrixesToDraw) {

				g.setColor(new Color(rand.nextFloat(), rand.nextFloat(), rand
						.nextFloat()));

				for (int x = 0; x < this.getWidth() * PRE; x++) {
					double rez = matrix.getAt(x, 0);
					int y = getHeight()
							- ((int) (rez / increment) - (int) (this.ybottom / increment));

					g.drawRect(x / PRE, y, 1, 1);

				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			//this happens when resizing the window, even if I stop the drawing in the ComponentListner...
			//this happens because the sizes get modified and repaint is still called before
			//the listener get's to run. Anyway nothing bad happens for swallowing the exception 
		}
	}

	public void zoomIn() {
		double with = (xright - xleft) / REDCOEF;
		xleft -= with;
		xright += with;
		double ywith = with * getHeight() / getWidth();
		ybottom -= ywith;
	}

	public void zoomOut() {
		double with = (xright - xleft) / REDCOEF;
		xleft += with;
		xright -= with;
		double ywith = with * getHeight() / getWidth();
		ybottom += ywith;
	}

	public void moveLeft() {
		double with = (xright - xleft) / REDCOEF;
		xleft -= with;
		xright -= with;
	}

	public void moveRight() {
		double with = (xright - xleft) / REDCOEF;
		xleft += with;
		xright += with;
	}

	public void moveUp() {
		double with = (xright - xleft) / REDCOEF;
		double ywith = with * getHeight() / getWidth();
		ybottom += ywith;
	}

	public void moveDown() {
		double with = (xright - xleft) / REDCOEF;
		double ywith = with * getHeight() / getWidth();
		ybottom -= ywith;
	}

	private static final int PRE = 1;
	private static final double REDCOEF = 10;

	private double xleft = -5;
	private double xright = 5;
	private double ybottom = -2;

	private double timeStart = 0;
	private double timeIncrement = 0.1;

	private List<Matrix<Double>> matrixesToDraw = new ArrayList<Matrix<Double>>();
	private CalculateWorker cworker = null;

	private class CalculateWorker extends
			SwingWorker<Void, List<Matrix<Double>>> {

		private List<FunctionEvaluator> evaluators;
		private LinkedHashMap<String, double[]> varMap;
		private boolean calculate = true;

		private CalculateWorker(List<FunctionEvaluator> evaluators,
				LinkedHashMap<String, double[]> varMap) {
			this.evaluators = evaluators;
			this.varMap = varMap;

		}

		@SuppressWarnings("unchecked")
		@Override
		protected Void doInBackground() throws Exception {
			double time = timeStart;

			while (calculate) {
				varMap.put("t", new double[] { time });
				List<Matrix<Double>> results = new ArrayList<Matrix<Double>>();
				for (FunctionEvaluator feval : evaluators) {
					try {
						results.add(feval.calculate(varMap));
					} catch (UndefinedVariableException e) {
						evaluators.remove(feval);
						JOptionPane.showMessageDialog(FDrawComponent.this,

						"Incorrect function: " + feval.getFunction() + "\n"
								+ e.getMessage()
								+ "\nOnly x, t variables are supported !!",
								"Error", JOptionPane.ERROR_MESSAGE);
						stopDrawing();
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

		public void stop() {
			calculate = false;

		}

	}

	private LinkedHashMap<String, double[]> calculateVarMap() {
		double increment = (this.xright - this.xleft) / getWidth();
		double[] xvalues = new double[getWidth() * PRE];

		for (int i = 0; i < getWidth() * PRE; ++i) {
			xvalues[i] = xleft + increment / PRE * i;
		}

		LinkedHashMap<String, double[]> varMap = new LinkedHashMap<String, double[]>();
		varMap.put("x", xvalues);
		return varMap;

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

	private void drawAxis(Graphics g) {
		g.setColor(Color.GRAY);
		double inc = (xright - xleft) / getWidth();
		if (xleft < 0 && xright > 0) {
			int yaxis = (int) ((-xleft) / inc);
			g.drawLine(yaxis, 0, yaxis, getHeight());
		}

		if (ybottom + inc * getHeight() > 0) {
			int xaxis = getHeight() + (int) (ybottom / inc);
			g.drawLine(0, xaxis, getWidth(), xaxis);
		}
	}

}
