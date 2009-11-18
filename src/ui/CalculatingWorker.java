package ui;

import java.util.ArrayList;
import java.util.Date;
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

import org.apache.log4j.Logger;

/**
 * class responsible for handling "data to be drawn" generation
 * <p>
 * calculated data is dispatched to the {@link DrawsFunctions} instance with that this
 * CalculatingWorker is configured through it's
 * {@link CalculatingWorker#setDrawer(DrawsFunctions)} method.
 * <p>
 * the function calculating is done in a calculating thread(
 * {@link Calculator#run()}<br>
 * 
 * the calculating worker may run in two modes: <br>
 * 1. dispatch calculated matrixes for drawing as soon as they are ready <br>
 * 2. in real time drawing<br>
 * In this case the drawing can be faster than the calculating can be done. To
 * solve this problem, all calculated values are placed on a queue (
 * {@link CalculatingWorker#drawingQueue}). While the queue size is below
 * <code>QUEUE_WAITING_THRESHOLD</code> the drawing is blocked. After this
 * threshold is passed, the drawing is unblocked by submitting 
 * {@link QueuePoller} to the {@link CalculatingWorker#schedueler()} executor
 * 
 * 
 * @author kux
 * 
 */
class CalculatingWorker {
	private static Logger logger = Logger.getLogger(CalculatingWorker.class);

	private final static int QUEUE_REENABLE_INTERVAL = 4;
	private final static int QUEUE_SIZE = 100;
	private final static int QUEUE_WAITING_THRESHOLD = 50;

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

	public void setTime(double time) {
		drawingQueue.removeAll(drawingQueue);
		this.time = time;
	}

	public void setTimeIncrement(double timeIncrement) {
		this.timeIncrement = timeIncrement;
	}

	public synchronized List<FunctionEvaluator> getDrawnFunctions() {
		return evaluators;
	}

	/**
	 * sets the function to be evaluated by this worker
	 * 
	 * @param evaluators
	 * @throws IllegalArgumentException
	 *             if the provided functions have different variables
	 */
	public synchronized void setDrawnFunctions(List<FunctionEvaluator> evaluators) {
		if (evaluators.size() == 0)
			return;

		Set<String> allowedVariables = evaluators.get(0).getVariables();

		for (FunctionEvaluator eval : evaluators) {
			Set<String> variables = eval.getVariables();
			if (!variables.equals(allowedVariables)) {
				throw new IllegalArgumentException("functions with different variables provided");
			}
		}

		this.drawingQueue.removeAll(this.drawingQueue);
		this.evaluators = evaluators;

		removeAllVariablesFromMap();
		allowedVariables.remove("t");
		for (String variable : allowedVariables) {
			varMap.put(variable, new double[] {});
		}

		varMap.put("t", new double[] {});

	}

	public synchronized void removeAllDrawnFunctions() {
		logger.debug("removing all drawin functions");
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
		Thread calculator = new Thread(new Calculator(), "calculating thread");
		calculator.start();
		schedueler.scheduleAtFixedRate(new QueuePoller(), (long) (timeIncrement * 1000),
				(long) (timeIncrement * 1000), TimeUnit.MILLISECONDS);
	}

	private class Calculator implements Runnable {
		@Override
		public void run() {

			int counter = 0;
			long calculatingTime = 0;

			while (calculate && !Thread.currentThread().isInterrupted()) {

				final List<Matrix<Double>> results = new ArrayList<Matrix<Double>>();

				synchronized (CalculatingWorker.this) {

					Date start = new Date();
					varMap.put("t", new double[] { time });
					for (FunctionEvaluator feval : evaluators) {

						results.add(feval.calculate(varMap));

					}
					Date end = new Date();
					calculatingTime += end.getTime() - start.getTime();
					counter++;
					if (counter % 100 == 0) {
						counter = 0;
						logger.debug("average calculating time: " + (double) calculatingTime / 100
								+ " milliseconds");
						calculatingTime = 0;
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
	}

	private class QueuePoller implements Runnable {

		@Override
		public void run() {
			if (waiting && drawingQueue.size() > QUEUE_WAITING_THRESHOLD) {
				logger.debug("switched waiting to FALSE, drawingQueue.size =  "
						+ drawingQueue.size());
				waiting = false;
				form.setProgress(100);
			} else {
				form.setProgress((int) (100.0 / QUEUE_WAITING_THRESHOLD * drawingQueue.size()));
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

		logger.trace("modifying var map on " + variable + " : [" + values[0] + ","
				+ values[values.length - 1] + "]");
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

			schedueler.schedule(queueRenableTask, QUEUE_REENABLE_INTERVAL, TimeUnit.SECONDS);

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
			QUEUE_SIZE);
	private ScheduledExecutorService schedueler = Executors.newSingleThreadScheduledExecutor();

	private final LinkedHashMap<String, double[]> varMap = new LinkedHashMap<String, double[]>();

	private List<FunctionEvaluator> evaluators = new ArrayList<FunctionEvaluator>();
	private volatile boolean calculate = true;
	private volatile boolean pauzed;

	private volatile boolean queueEnabled = true;

	private double time = 0;
	private volatile double timeIncrement = 0.1;

	private DrawsFunctions drawer;
	private final DrawingForm form;

}