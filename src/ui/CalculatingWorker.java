package ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import model.FunctionEvaluator;
import model.Matrix;

import org.antlr.runtime.RecognitionException;
import org.apache.log4j.Logger;

class CalculatingWorker {
	private static Logger logger = Logger.getLogger(CalculatingWorker.class);

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

	public boolean isQueueEnabled() {
		return queueEnabled;
	}

	public void setQueueEnabled(boolean queueEnabled) {
		drawingQueue.removeAll(drawingQueue);
		this.queueEnabled = queueEnabled;
	}

	public void start() {
		Thread calculator = new Thread(new Calculator());
		calculator.start();
		schedueler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {

				if (waiting && drawingQueue.size() > 100) {
					logger
							.debug("switched waiting to FALSE, drawingQueue.size =  "
									+ drawingQueue.size());
					waiting = false;
					form.setProgress(100);
				} else {
					form.setProgress(drawingQueue.size());
				}

				if ((!waitingEnabled || !waiting) && !pauzed) {

					final MatrixesMomentPair toDraw = drawingQueue.poll();
					if (toDraw == null) {
						if (!waiting) {
							logger.debug("switched waiting to TRUE");
							waiting = true;
						}
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
		}, 100, 100, TimeUnit.MILLISECONDS);
	}

	private FutureTask<Void> queueRenableTask;
	private volatile boolean waitingEnabled = true;
	private volatile boolean waiting = true;

	/**
	 * 
	 * @param variable
	 * @param values
	 * @throws IllegalArgumentException
	 *             if <code>values</code> is null or empty
	 */
	public synchronized void modifyVarMap(String variable, double[] values) {

		if (values == null || values.length == 0) {
			throw new IllegalArgumentException();
		}

		logger.debug("modifying var map on " + variable + " : [" + values[0]
				+ "," + values[values.length - 1] + "]");
		this.varMap.put(variable, values);

		drawingQueue.removeAll(this.drawingQueue);

		if (waitingEnabled) {
			waitingEnabled = false;
			logger.debug("schedule queue re-enable");

			// if scheduled before but not executed, cancel it
			if (queueRenableTask != null && !queueRenableTask.isDone()) {
				queueRenableTask.cancel(true);
			}

			// and schedule again
			queueRenableTask = new FutureTask<Void>(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					logger.debug("queue reenabled");
					CalculatingWorker.this.waitingEnabled = true;
					return null;
				}

			});

			schedueler.schedule(queueRenableTask, 4, TimeUnit.SECONDS);

		}
	}

	private class Calculator implements Runnable {
		@Override
		public void run() {
			while (calculate && !Thread.currentThread().isInterrupted()) {

				final List<Matrix<Double>> results = new ArrayList<Matrix<Double>>();

				synchronized (CalculatingWorker.this) {

					varMap.put("t", new double[] { time });
					for (FunctionEvaluator feval : evaluators) {

						results.add(feval.calculate(varMap));

					}
				}
				if (queueEnabled) {
					try {
						drawingQueue.put(new MatrixesMomentPair(time, results));
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				} else {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							drawer.drawMatrixes(results);
							form.setTime(time);

						}
					});
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
		logger.debug("removing all variables from varMap");
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

	private volatile boolean queueEnabled = true;

	private double time = 0;
	private double timeIncrement = 0.1;

	private DrawsFunctions drawer;
	private final DrawingForm form;

}