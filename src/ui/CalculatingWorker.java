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

	private final LinkedHashMap<String, double[]> varMap = new LinkedHashMap<String, double[]>();

	private List<FunctionEvaluator> evaluators = new ArrayList<FunctionEvaluator>();
	private volatile boolean calculate = true;
	private volatile boolean pauzed;

	private double time = 0;
	private double timeIncrement = 0.1;

	private DrawsFunctions drawer;
	private DrawingForm form;

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

	public void execute() {
		Thread calculator = new Thread(new Calculator());
		calculator.start();
		schedueler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				final List<Matrix<Double>> toDraw = drawingQueue.poll();
				if (toDraw != null) {
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							drawer.drawMatrixes(toDraw);
							form.setTime(time);
						}
					});
				}
			}
		}, 50, 50, TimeUnit.MILLISECONDS);

	}

	public void pause() {
		this.pauzed = true;
	}

	public void resume() {
		this.pauzed = false;
	}

	public synchronized void modifyVarMap(String variable, double[] values) {
		this.varMap.put(variable, values);
	}

	public synchronized void setTime(double time) {
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
		Set<String> variables = evaluator.getVariables();
		this.evaluators.add(evaluator);
		return variables;
	}

	public synchronized void removeAllDrawnFunctions() {
		removeAllVariablesFromMap();
		this.evaluators.removeAll(this.evaluators);
	}

	private void removeAllVariablesFromMap() {
		Set<String> variables = new HashSet<String>(varMap.keySet());
		for (String key : variables) {
			varMap.remove(key);
		}
	}

	private class Calculator implements Runnable {
		@Override
		public void run() {
			while (calculate && !Thread.currentThread().isInterrupted()) {
				if (!pauzed) {
					synchronized (this) {
						varMap.put("t", new double[] { time });

						List<Matrix<Double>> results = new ArrayList<Matrix<Double>>();

						for (FunctionEvaluator feval : evaluators) {

							results.add(feval.calculate(varMap));

						}

						try {
							drawingQueue.put(results);
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
						}

						time += timeIncrement;
					}
				}
			}
		}
	}

	private BlockingQueue<List<Matrix<Double>>> drawingQueue = new LinkedBlockingQueue<List<Matrix<Double>>>(
			100);
	private ScheduledExecutorService schedueler = Executors
			.newSingleThreadScheduledExecutor();

}