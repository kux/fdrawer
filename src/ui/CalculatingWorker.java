package ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import model.FunctionEvaluator;
import model.Matrix;

import org.antlr.runtime.RecognitionException;

class CalculatingWorker {

	public CalculatingWorker(DrawingForm form) {
		this.form = form;
	}

	/**
	 * 
	 * @param drawer
	 * @throws IllegalStateException
	 *             if not called from edt
	 */
	public void setDrawer(DrawsFunctions drawer) {
		if (!SwingUtilities.isEventDispatchThread()) {
			throw new IllegalStateException();
		}
		this.drawer = drawer;
	}

	public void stop() {
		this.calculate = false;
	}

	public void pause() {
		this.pauzed = true;
	}

	public void resume() {
		this.pauzed = false;
	}

	public synchronized void modifyVarMap(String variable, double[] values) {
		this.drawingQueue.removeAll(this.drawingQueue);
		this.queueEnabled = false;
		schedueler.schedule(new Runnable() {

			@Override
			public void run() {
				CalculatingWorker.this.queueEnabled = true;
			}

		}, 10, TimeUnit.SECONDS);
		this.varMap.put(variable, values);
	}

	public synchronized void setTime(double time) {
		drawingQueue.removeAll(drawingQueue);
		this.time = time;
	}

	public synchronized void setTimeIncrement(double timeIncrement) {
		this.timeIncrement = timeIncrement;
	}

	/**
	 * 
	 * @param function
	 *            function to add to the ones being evaluated
	 * @return list of variables the function has
	 * @throws RecognitionException
	 */
	public synchronized Set<String> addFunction(FunctionEvaluator evaluator) {
		this.drawingQueue.removeAll(this.drawingQueue);
		Set<String> variables = evaluator.getVariables();
		this.evaluators.add(evaluator);
		return variables;
	}

	public synchronized void removeAllDrawnFunctions() {
		this.drawingQueue.removeAll(this.drawingQueue);
		removeAllVariablesFromMap();
		this.evaluators.removeAll(this.evaluators);
	}

	public void resetDrawingQueue() {
		drawingQueue.removeAll(drawingQueue);
	}

	public void start() {
		Thread calculator = new Thread(new Calculator());
		calculator.start();
		schedueler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {

				if (drawingQueue.size() > 100) {
					waiting = false;
					form.setProgress(100);
				} else {
					form.setProgress(drawingQueue.size());
				}

				if ((!queueEnabled || !waiting) && !pauzed) {

					final MatrixesMomentPair toDraw = drawingQueue.poll();
					if (toDraw == null) {
						waiting = true;
					} else {

						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								drawer.drawMatrixes(toDraw.getMatrixes());
								form.setTime(toDraw.getMoment());
							}
						});
					}

				}
			}
		}, 50, 50, TimeUnit.MILLISECONDS);
	}

	private volatile boolean waiting = true;
	private volatile boolean queueEnabled = false;

	private class Calculator implements Runnable {
		@Override
		public void run() {
			while (calculate && !Thread.currentThread().isInterrupted()) {

				List<Matrix<Double>> results = new ArrayList<Matrix<Double>>();

				synchronized (CalculatingWorker.this) {

					varMap.put("t", new double[] { time });
					for (FunctionEvaluator feval : evaluators) {

						results.add(feval.calculate(varMap));

					}
				}

				try {
					drawingQueue.put(new MatrixesMomentPair(time, results));
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}

				time += timeIncrement;

			}
		}
	}

	private class MatrixesMomentPair {
		private double moment;
		private List<Matrix<Double>> matrixes;

		public MatrixesMomentPair(double moment, List<Matrix<Double>> matrixes) {
			super();
			this.moment = moment;
			this.matrixes = matrixes;
		}

		public double getMoment() {
			return moment;
		}

		public List<Matrix<Double>> getMatrixes() {
			return matrixes;
		}

	}

	private void removeAllVariablesFromMap() {
		Set<String> variables = new HashSet<String>(varMap.keySet());
		for (String key : variables) {
			varMap.remove(key);
		}
	}

	private BlockingQueue<MatrixesMomentPair> drawingQueue = new LinkedBlockingQueue<MatrixesMomentPair>(
			1000);
	private ScheduledExecutorService schedueler = Executors
			.newSingleThreadScheduledExecutor();

	private final LinkedHashMap<String, double[]> varMap = new LinkedHashMap<String, double[]>();

	private List<FunctionEvaluator> evaluators = new ArrayList<FunctionEvaluator>();
	private volatile boolean calculate = true;
	private volatile boolean pauzed;

	private double time = 0;
	private double timeIncrement = 0.1;

	private DrawsFunctions drawer;
	private final DrawingForm form;

}