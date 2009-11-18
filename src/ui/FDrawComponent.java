package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;

import model.Matrix;

import org.apache.log4j.Logger;

/**
 * //TODO extract some methods with code duplicated in draw3dGraphs and
 * draw2dGraphs, or make a hierarchy
 * 
 * @author kux
 * 
 */
@SuppressWarnings("serial")
public class FDrawComponent extends JLabel implements DrawsFunctions {
	private static Logger logger = Logger.getLogger(FDrawComponent.class);

	public enum Type {
		DRAW2D, DRAW3D
	};

	private Type type;

	private void setType(Type type) {
		if (type != this.type) {
			this.type = type;
			this.removeMouseListener(this.getMouseListeners()[0]);
			this.removeMouseMotionListener(this.getMouseMotionListeners()[0]);
			this.removeMouseWheelListener(this.getMouseWheelListeners()[0]);
			DrawerMouseListener dml = null;
			if (type == Type.DRAW3D) {
				dml = new DrawerMouseListener3d();
			} else {
				dml = new DrawerMouseListener2d();
			}

			this.addMouseListener(dml);
			this.addMouseMotionListener(dml);
			this.addMouseWheelListener(dml);
		}
	}

	public Type getType() {
		return this.type;
	}

	private FDrawComponent(CalculatingWorker cworker) {
		this.cworker = cworker;
		nformatter.setMaximumFractionDigits(2);
	}

	public static FDrawComponent createInstance(CalculatingWorker cworker) {
		FDrawComponent drawComponent = new FDrawComponent(cworker);
		DrawerMouseListener dml = null;
		dml = drawComponent.new DrawerMouseListener2d();

		drawComponent.addMouseListener(dml);
		drawComponent.addMouseMotionListener(dml);
		drawComponent.addMouseWheelListener(dml);

		drawComponent.setType(Type.DRAW3D);
		return drawComponent;
	}

	/**
	 * 
	 * @param functions
	 * @param distance
	 * @param rotation
	 * @param precision
	 */
	public void set3dDrawingProperties(double distance, double[] rotation, int precision) {
		this.setType(Type.DRAW3D);

		this.rotation = rotation;
		this.distance = distance;

		calculate3D();
		splitIntervals(precision);

		cworker.modifyVarMap("x", this.xvalues);
		cworker.modifyVarMap("y", this.yvalues);

	}

	public void set2dDrawingProperties(double xleft, double xright, int precision) {
		this.setType(Type.DRAW2D);

		calculateXValues(xleft, xright, precision);

		cworker.modifyVarMap("x", this.xvalues);
	}

	public void drawMatrixes(List<Matrix<Double>> toDraw) {
		this.matrixesToDraw = toDraw;
		repaint();
	}

	/**
	 * modify drawing precsion<br>
	 * can be done while drawing is in progress
	 * 
	 * @param precision
	 */
	public void modifyPrecsion3d(int precision) {
		if (type == Type.DRAW3D) {
			splitIntervals(precision);

			cworker.modifyVarMap("x", xvalues);
			cworker.modifyVarMap("y", yvalues);
			cworker.resetDrawingQueue();
		}
	}

	public void modifyPrecsion2d(int precision) {
		if (type == Type.DRAW2D) {
			calculateXValues(xvalues[0], xvalues[xvalues.length - 1], precision);
			cworker.modifyVarMap("x", xvalues);
			cworker.resetDrawingQueue();
		}
	}

	public void setDistance(double distance) {
		this.distance = distance;
		calculate3D();
		splitIntervals(xvalues.length);
		cworker.modifyVarMap("x", xvalues);
		cworker.modifyVarMap("y", yvalues);
	}

	private void calculate3D() {
		this.eye = new double[] { 0, 0, distance };

		camera[2] = Math.sqrt(Math.pow(distance, 2)
				/ (1 + Math.pow(Math.tan(rotation[0]), 2) + Math.pow(Math.tan(rotation[1]), 2)));

		camera[0] = camera[2] * Math.tan(rotation[0]);
		camera[1] = camera[2] * Math.tan(rotation[1]);
	}

	private void modifAngles(double x, double y) {
		rotation[0] += x;
		rotation[1] += y;
		calculate3D();

	}

	private void zoomOut3d() {
		distance += distance / REDCOEF;
		calculate3D();
		splitIntervals(xvalues.length);
		cworker.modifyVarMap("x", xvalues);
		cworker.modifyVarMap("y", yvalues);
	}

	private void zoomIn3d() {
		distance -= distance / REDCOEF;
		calculate3D();
		splitIntervals(xvalues.length);
		cworker.modifyVarMap("x", xvalues);
		cworker.modifyVarMap("y", yvalues);
	}

	private void zoomOut2d() {
		double with = (xvalues[xvalues.length - 1] - xvalues[0]) / REDCOEF;
		calculateXValues(xvalues[0] - with, xvalues[xvalues.length - 1] + with, xvalues.length);

		cworker.modifyVarMap("x", xvalues);

	}

	private void zoomIn2d() {

		double with = (xvalues[xvalues.length - 1] - xvalues[0]) / REDCOEF;
		calculateXValues(xvalues[0] + with, xvalues[xvalues.length - 1] - with, xvalues.length);
		cworker.modifyVarMap("x", xvalues);
	}

	/**
	 * 
	 * @param xdif
	 * @param ydif
	 * @throws IllegalStateException
	 *             if no drawing is in progress
	 */
	private void moveWith(int xdif, int ydif) {
		double pixelRange = (xvalues[xvalues.length - 1] - xvalues[0]) / getWidth();
		double xwith = xdif * pixelRange;
		double ywith = ydif * pixelRange;

		ybottom += ywith;

		calculateXValues(xvalues[0] - xwith, xvalues[xvalues.length - 1] - xwith, xvalues.length);
		cworker.modifyVarMap("x", xvalues);

	}

	private void calculateXValues(double xleft, double xright, int precision) {

		xvalues = new double[precision];
		double increment = (xright - xleft) / precision;

		for (int i = 0; i < precision; ++i) {
			this.xvalues[i] = xleft + increment * i;
		}

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
		if (type == Type.DRAW3D)
			draw3dGraphs(g);
		else
			draw2dGraphs(g);
	}

	private double[] translate(double a[], double[] c, double[] o) {

		double dx = Math.cos(o[1])
				* (Math.sin(o[2]) * (a[1] - c[1]) + Math.cos(o[2]) * (a[0] - c[0]))
				- Math.sin(o[1]) * (a[2] - c[2]);

		double dy = Math.sin(o[0])
				* (Math.cos(o[1]) * (a[2] - c[2]) + Math.sin(o[1])
						* (Math.sin(o[2]) * (a[1] - c[1]) + Math.cos(o[2]) * (a[0] - c[0])))
				+ Math.cos(o[0])
				* (Math.cos(o[2]) * (a[1] - c[1]) - Math.sin(o[2]) * (a[0] - c[0]));

		double dz = Math.cos(o[0])
				* (Math.cos(o[1]) * (a[2] - c[2]) + Math.sin(o[1])
						* (Math.sin(o[2]) * (a[1] - c[1]) + Math.cos(o[2]) * (a[0] - c[0])))
				- Math.sin(o[0])
				* (Math.cos(o[2]) * (a[1] - c[1]) - Math.sin(o[2]) * (a[0] - c[0]));

		return new double[] { dx, dy, dz };

	}

	private double[] project(double d[], double[] e) {
		double bx = (d[0] - e[0]) * (e[2] / d[2]);
		double by = (d[1] - e[1]) * (e[2] / d[2]);

		return new double[] { bx, by };
	}

	private int[] calculateOnScreen(double[] projected, double pixelRangeX, double pixelRangeY) {
		int coordx = (int) (projected[0] / pixelRangeX) - (int) (xvalues[0] / pixelRangeX);
		int coordy = getHeight()
				- ((int) (projected[1] / pixelRangeY) - (int) (yvalues[0] / pixelRangeY));
		return new int[] { coordx, coordy };
	}

	private void draw3dGraphs(Graphics g) {

		double pixelRangeX = (xvalues[xvalues.length - 1] - xvalues[0]) / getWidth();

		double pixelRangeY = (yvalues[yvalues.length - 1] - yvalues[0]) / getHeight();

		// draw3dAxis(g, pixelRangeX, pixelRangeY);

		Random rand = new Random(10);

		int[][] coordsx = new int[xvalues.length][yvalues.length];
		int[][] coordsy = new int[xvalues.length][yvalues.length];

		for (Matrix<Double> matrix : matrixesToDraw) {
			/*
			 * matrix.getMatrixSize()[1] - x axis values on the result matrix
			 * skip if precision was changed and matrixes received from the
			 * calculating worker are no longer consistent
			 */
			if (matrix.getDimNo() != 3 || matrix.getMatrixSize()[0] != xvalues.length)
				break;

			g.setColor(new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));

			for (int i = 0; i < xvalues.length; ++i) {
				for (int j = 0; j < yvalues.length; ++j) {

					double translated[] = translate(new double[] { xvalues[i], yvalues[j],
							matrix.getAt(i, j, 0) }, camera, rotation);

					double projected[] = project(translated, eye);

					int[] coord = calculateOnScreen(projected, pixelRangeX, pixelRangeY);

					coordsx[i][j] = coord[0];
					coordsy[i][j] = coord[1];

				}
			}

			for (int i = 0; i < coordsx.length; ++i) {
				for (int j = 1; j < coordsy.length; ++j) {
					if (onScreen(coordsx[i][j - 1], coordsy[i][j - 1], coordsx[i][j], coordsy[i][j]))
						g.drawLine(coordsx[i][j - 1], coordsy[i][j - 1], coordsx[i][j],
								coordsy[i][j]);
				}
			}

			for (int i = 1; i < coordsy.length; ++i) {
				for (int j = 0; j < coordsx.length; ++j) {
					if (onScreen(coordsx[i - 1][j], coordsy[i - 1][j], coordsx[i][j], coordsy[i][j])) {
						g.drawLine(coordsx[i - 1][j], coordsy[i - 1][j], coordsx[i][j],
								coordsy[i][j]);
					}

				}
			}
		}

	}

	@SuppressWarnings("unused")
	private void draw3dAxis(Graphics g, double pixelRangeX, double pixelRangeY) {

		int[] origin = calculateOnScreen(project(translate(new double[] { 0, 0, 0 }, camera,
				rotation), eye), pixelRangeX, pixelRangeY);

		int[] xmargin = calculateOnScreen(project(translate(new double[] { distance / 2, 0, 0 },
				camera, rotation), eye), pixelRangeX, pixelRangeY);

		int[] ymargin = calculateOnScreen(project(translate(new double[] { 0, distance / 2, 0 },
				camera, rotation), eye), pixelRangeX, pixelRangeY);

		int[] zmargin = calculateOnScreen(project(translate(new double[] { 0, 0, distance / 2 },
				camera, rotation), eye), pixelRangeX, pixelRangeY);

		g.drawRect(origin[0], origin[1], 2, 2);
		g.setColor(Color.RED);
		g.drawLine(origin[0], origin[1], xmargin[0], xmargin[1]);
		g.setColor(Color.GREEN);
		g.drawLine(origin[0], origin[1], ymargin[0], ymargin[1]);
		g.setColor(Color.BLUE);
		g.drawLine(origin[0], origin[1], zmargin[0], zmargin[1]);

	}

	private void draw2dGraphs(Graphics g) {

		double pixelRange = (xvalues[xvalues.length - 1] - xvalues[0]) / getWidth();

		draw2dAxis(g, pixelRange);

		Random rand = new Random(10);

		for (Matrix<Double> matrix : matrixesToDraw) {

			if (matrix.getDimNo() != 2 || matrix.getMatrixSize()[0] != xvalues.length) {
				logger.info("old cfg matrix received");
				break;
			}

			g.setColor(new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));

			int xant = -1000, yant = -1000;

			for (int i = 0; i < xvalues.length - 1; i++) {
				int x = (int) ((xvalues[i] - xvalues[0]) / pixelRange);
				double rez = matrix.getAt(i, 0);
				int y = getHeight()
						- ((int) (rez / pixelRange) - (int) (this.ybottom / pixelRange));

				if (Math.abs(xant - x) < this.getWidth() / 3
						&& Math.abs(yant - y) < getHeight() / 3) {
					g.drawLine(xant, yant, x, y);
				}
				xant = x;
				yant = y;
			}
		}
	}

	private void draw2dAxis(Graphics g, double pixelRange) {
		g.setColor(Color.GRAY);
		double inc = (xvalues[xvalues.length - 1] - xvalues[0]) / getWidth();
		if (xvalues[0] < 0 && xvalues[xvalues.length - 1] > 0) {
			int yaxis = (int) ((-xvalues[0]) / inc);
			g.drawLine(yaxis, 0, yaxis, getHeight());
			g.drawString(nformatter.format(ybottom), yaxis, getHeight() - 10);
			g.drawString(nformatter.format(ybottom + getHeight() * pixelRange), yaxis, 10);
		}

		if (ybottom + inc * getHeight() > 0) {
			int xaxis = getHeight() + (int) (ybottom / inc);
			g.drawLine(0, xaxis, getWidth(), xaxis);
			g.drawString(nformatter.format(xvalues[0]), 0, xaxis);
			String xright = nformatter.format(xvalues[xvalues.length - 1]);
			g.drawString(xright, getWidth() - xright.length() * 11, xaxis);
		}
	}

	private boolean onScreen(int x1, int y1, int x2, int y2) {
		final int asimtCheck = 3;

		return (Math.abs(x1 - x2) < getWidth() / asimtCheck
				&& Math.abs(y1 - y2) < getHeight() / asimtCheck && x1 < getWidth() && x1 > 0
				&& x2 < getWidth() && x2 > 0 && y1 < getHeight() && y1 > 0 && y2 < getHeight() && y2 > 0);

	}

	private static final double REDCOEF = 50;

	private double xvalues[] = new double[] { 0 };
	private double yvalues[] = new double[] { 0 };

	private double ybottom = -2;

	private double distance;

	private double camera[] = new double[] { 0, 0, 0 };
	private double rotation[] = new double[] { 0, 0, 0 };
	private double eye[] = new double[] { 0, 0, 5 };

	private List<Matrix<Double>> matrixesToDraw = new ArrayList<Matrix<Double>>();

	private CalculatingWorker cworker = null;

	private int xPointer = -1;
	private int yPointer = -1;

	private NumberFormat nformatter = NumberFormat.getInstance();

	private interface DrawerMouseListener extends MouseWheelListener, MouseMotionListener,
			MouseListener {

	}

	private class DrawerMouseListener3d implements DrawerMouseListener {

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			int rotDir = e.getWheelRotation();
			if (rotDir == 1)
				FDrawComponent.this.zoomIn3d();
			else
				FDrawComponent.this.zoomOut3d();

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			int xant = xPointer;
			int yant = yPointer;
			xPointer = e.getX();
			yPointer = e.getY();
			if (xant != -1 && yant != -1) {
				FDrawComponent.this.modifAngles((double) (yPointer - yant) / 100,
						(double) (xPointer - xant) / 100);
			}

		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			xPointer = -1;
			yPointer = -1;
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
			FDrawComponent.this.xPointer = -1;
			FDrawComponent.this.yPointer = -1;
		}
	}

	private class DrawerMouseListener2d implements DrawerMouseListener {
		int x = -1;
		int y = -1;

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			int rotDir = e.getWheelRotation();
			if (rotDir == 1)
				zoomIn2d();
			else
				zoomOut2d();

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			int xant = x;
			int yant = y;
			x = e.getX();
			y = e.getY();
			if (xant != -1 && yant != -1 && (Math.abs(xant - x) > 1 || Math.abs(yant - y) > 1)) {
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
