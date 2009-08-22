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

	private static final int PRE = 2;

	private static final double REDCOEF = 10;

	private double xleft = -5;
	private double xright = 5;
	private double ybottom = -2;

	private double timeStart = 0;

	private double timeNow;
	private double timeIncrement = 0.1;

	private List<FunctionEvaluator> fevals = new ArrayList<FunctionEvaluator>();

	

	private Thread redrawingThread;
	private boolean redraw;

	public synchronized boolean isRedraw() {
		return redraw;
	}

	public synchronized void setRedraw(boolean redraw) {
		this.redraw = redraw;
	}

	public void startDrawing(List<String> functions)
			throws RecognitionException {

		if (redrawingThread != null) {
			setRedraw(true);
			try {
				redrawingThread.join();
			} catch (InterruptedException e) {
				// can't be reached, no thread can interrupt this thread
			}
		}

		fevals.clear();
		for (String func : functions) {
			fevals.add(new FunctionEvaluator(func));
		}

		redrawingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				setRedraw(false);

				timeNow = timeStart;
				while (true) {
					if (isRedraw())
						return;
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// can't be reached, nobody will interrupt this thread
					}
					timeNow += timeIncrement;
					repaint();
				}
			}
		});

		redrawingThread.start();

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawAxis(g);

		double increment = (this.xright - this.xleft) / getWidth();
		double[] xvalues = new double[getWidth() * PRE];

		for (int i = 0; i < getWidth() * PRE; ++i) {
			xvalues[i] = xleft + increment / PRE * i;
		}

		LinkedHashMap<String, double[]> varMap = new LinkedHashMap<String, double[]>();
		varMap.put("x", xvalues);
		varMap.put("t", new double[] { timeNow });

		Random rand = new Random(10);
		for (FunctionEvaluator feval : fevals) {

			g.setColor(new Color(rand.nextFloat(), rand.nextFloat(), rand
					.nextFloat()));

			Matrix<Double> result = feval.calculate(varMap);

			for (int x = 0; x < this.getWidth() * PRE; x++) {
				double rez = result.getAt(x, 0);
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
		xleft += with;
		xright -= with;
		double ywith = with * getHeight() / getWidth();
		ybottom += ywith;
	}

	public void zoomOut() {
		double with = (xright - xleft) / REDCOEF;
		xleft -= with;
		xright += with;
		double ywith = with * getHeight() / getWidth();
		ybottom -= ywith;
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
		ybottom -= ywith;
	}

	public void moveDown() {
		double with = (xright - xleft) / REDCOEF;
		double ywith = with * getHeight() / getWidth();
		ybottom += ywith;
	}


	public void setMatrixsToDraw(List<Matrix<Double>> drawNow) {
		
	}

}
