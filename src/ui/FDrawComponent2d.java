package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;

import model.Matrix;

@SuppressWarnings("serial")
public class FDrawComponent2d extends JLabel implements DrawsFunctions {

	private FDrawComponent2d() {

	}

	public static FDrawComponent2d createInstance() {
		FDrawComponent2d drawComponent = new FDrawComponent2d();
		DrawerMouseListener dml = drawComponent.new DrawerMouseListener();
		drawComponent.addMouseListener(dml);
		drawComponent.addMouseMotionListener(dml);
		drawComponent.addMouseWheelListener(dml);
		return drawComponent;
	}

	public void startDrawing(CalculatingWorker cworker, double xleft,
			double xright, int precision) {

		this.cworker = cworker;
		calculateXValues(xleft, xright, precision);

		cworker.modifyVarMap("x", this.xvalues);

	}

	/**
	 * modify drawing precsion<br>
	 * can be done while drawing is in progress
	 * 
	 * @param precision
	 */
	public void modifyPrecsion(int precision) {
		calculateXValues(xvalues[0], xvalues[xvalues.length - 1], precision);
		cworker.modifyVarMap("x", xvalues);
	}

	@Override
	public void drawMatrixes(List<Matrix<Double>> toDraw) {
		this.matrixesToDraw = toDraw;
		repaint();
	}

	/**
	 * 
	 * @param xdif
	 * @param ydif
	 * @throws IllegalStateException
	 *             if no drawing is in progress
	 */
	private void moveWith(int xdif, int ydif) {
		double pixelRange = (xvalues[xvalues.length - 1] - xvalues[0])
				/ getWidth();
		double xwith = xdif * pixelRange;
		double ywith = ydif * pixelRange;

		ybottom += ywith;

		calculateXValues(xvalues[0] - xwith, xvalues[xvalues.length - 1]
				- xwith, xvalues.length);
		cworker.modifyVarMap("x", xvalues);

	}

	/**
	 * @throws IllegalStateException
	 *             if no drawing is in progress
	 */
	private void zoomOut() {
		double with = (xvalues[xvalues.length - 1] - xvalues[0]) / REDCOEF;
		calculateXValues(xvalues[0] - with, xvalues[xvalues.length - 1] + with,
				xvalues.length);

		cworker.modifyVarMap("x", xvalues);

	}

	/**
	 * @throws IllegalStateException
	 *             if no drawing is in progress
	 */
	private void zoomIn() {

		double with = (xvalues[xvalues.length - 1] - xvalues[0]) / REDCOEF;
		calculateXValues(xvalues[0] + with, xvalues[xvalues.length - 1] - with,
				xvalues.length);
		cworker.modifyVarMap("x", xvalues);
	}

	private static final double REDCOEF = 50;

	// private int xvalues.length = 1000;

	private double ybottom = -2;
	private double xvalues[];

	private List<Matrix<Double>> matrixesToDraw = new ArrayList<Matrix<Double>>();
	private CalculatingWorker cworker = null;

	private void calculateXValues(double xleft, double xright, int precision) {

		xvalues = new double[precision];
		double increment = (xright - xleft) / precision;

		for (int i = 0; i < precision; ++i) {
			this.xvalues[i] = xleft + increment * i;
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw2dGraphs(g);
	}

	private void draw2dGraphs(Graphics g) {

		if (cworker == null)
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

	private class DrawerMouseListener implements MouseWheelListener,
			MouseMotionListener, MouseListener {
		int x = -1;
		int y = -1;

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			int rotDir = e.getWheelRotation();
			if (rotDir == 1)
				zoomIn();
			else
				zoomOut();

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			int xant = x;
			int yant = y;
			x = e.getX();
			y = e.getY();
			if (xant != -1 && yant != -1
					&& (Math.abs(xant - x) > 1 || Math.abs(yant - y) > 1)) {
				moveWith(x - xant, y - yant);
			}

		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			x = -1;
			y = -1;
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			this.x = -1;
			this.y = -1;
		}
	}

}
