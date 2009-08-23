package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.SwingWorker;

import model.FunctionEvaluator;
import model.Matrix;

import org.antlr.runtime.RecognitionException;

public class FDrawComponent extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int PRE = 3;

	private static final double REDCOEF = 10;

	private double xleft = -5;
	private double xright = 5;
	private double ybottom = -2;

	private double timeStart = 0;
	private double timeIncrement = 0.1;

	private List<Matrix<Double>> matrixes = new ArrayList<Matrix<Double>>();
	private CalculateWorker cworker = null;

	public void startDrawing(List<String> functions)
			throws RecognitionException {

		if(cworker!=null)cworker.stop();
		List<FunctionEvaluator> evaluators = new ArrayList<FunctionEvaluator>();
		for(String function: functions){
			evaluators.add( new FunctionEvaluator(function));
		}
			
		
		LinkedHashMap<String, double[]> varmap = calculateVarMap();
		cworker = new CalculateWorker(this, evaluators, varmap,
				timeStart, timeIncrement);
		cworker.execute();

	}
	
	public void stopDrawing(){
		matrixes.clear();
		cworker.stop();
		repaint();
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
	
	public void setMatrixsToDraw(List<Matrix<Double>> drawNow) {
		matrixes = drawNow;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawAxis(g);

		double increment = (this.xright - this.xleft) / getWidth();
		Random rand = new Random(10);

		for (Matrix<Double> matrix : matrixes) {

			g.setColor(new Color(rand.nextFloat(), rand.nextFloat(), rand
					.nextFloat()));

			for (int x = 0; x < this.getWidth() * PRE; x++) {
				double rez = matrix.getAt(x, 0);
				int y = getHeight()
						- ((int) (rez / increment) - (int) (this.ybottom / increment));

				g.drawRect(x / PRE, y, 1, 1);

			}
		}
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

}
