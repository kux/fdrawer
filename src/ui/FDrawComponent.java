package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;

import model.FunctionEvaluator;
import model.Matrix;

import org.antlr.runtime.RecognitionException;

public class FDrawComponent extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int PRE = 5;

	private double xleft = -5;
	private double xright = 5;

	private double ybottom = -5;

	private double timeStart = 0;

	private double timeNow;
	private double timeIncrement = 0.1;

	private List<FunctionEvaluator> fevals = new ArrayList<FunctionEvaluator>();


	public FDrawComponent(List<String> functions) throws RecognitionException {

		super();
		for (String func : functions) {
			fevals.add(new FunctionEvaluator(func));
		}

		Thread time = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					timeNow = timeStart;
					while (true) {
						Thread.sleep(100);
						timeNow += timeIncrement;
						syncRepaint();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		time.start();

	}

	private synchronized void syncRepaint() {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		double increment = (this.xright - this.xleft) / getWidth();
		double[] xvalues = new double[getWidth() * PRE];

		for (int i = 0; i < getWidth()*PRE; ++i) {
			xvalues[i] = xleft + increment/PRE * i;
		}

		LinkedHashMap<String, double[]> varMap = new LinkedHashMap<String, double[]>();
		varMap.put("x", xvalues);
		varMap.put("t", new double[] { timeNow });

		Random rand = new Random(10);
		for (FunctionEvaluator feval : fevals) {

			g.setColor(new Color(rand.nextFloat(), rand.nextFloat(), rand
					.nextFloat()));

			Matrix<Double> result = feval.calculate(varMap);

			for (int x = 0; x < this.getWidth()*PRE; x++) {
				double rez = result.getAt(x, 0);
				int y = getHeight()
						- ((int) (rez / increment) - (int) (this.ybottom / increment));

				g.drawRect(x/PRE, y,1, 1);

			}
		}

	}

	public double getXleft() {
		return xleft;
	}

	public void setXleft(double xleft) {
		this.xleft = xleft;
	}

	public double getXright() {
		return xright;
	}

	public void setXright(double xright) {
		this.xright = xright;
	}

	public double getYbottom() {
		return ybottom;
	}

	public void setYbottom(double ybottom) {
		this.ybottom = ybottom;
	}

	public double getTimeIncrement() {
		return timeIncrement;
	}

	public void setTimeIncrement(double timeIncrement) {
		this.timeIncrement = timeIncrement;
	}

}
